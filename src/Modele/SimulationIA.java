/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele;

import Joueurs.Humain;
import Joueurs.Ordinateur;
import static Modele.Arbitre.J1;
import static Modele.Arbitre.J2;
import static Modele.Arbitre.JvIA;
import static Modele.Arbitre.JvJ;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import ruche.Reglage;

/**
 *
 * @author grandmax
 */
public class SimulationIA extends Arbitre {

    public SimulationIA(Properties p) {
        super(p);
    }
    
    @Override
    public void init(){
        
        int[] tabPieces = new int[8];
        tabPieces[0]=(int)Reglage.lis("nbReine");
        tabPieces[1]=(int)Reglage.lis("nbScarabee");
        tabPieces[2]=(int)Reglage.lis("nbSauterelle");
        tabPieces[3]=(int)Reglage.lis("nbFourmi");
        tabPieces[4]=(int)Reglage.lis("nbAraignee");
        tabPieces[5]=(int)Reglage.lis("nbCoccinelle");
        tabPieces[6]=(int)Reglage.lis("nbMoustique");  
        tabPieces[7]=(int)Reglage.lis("nbCloporte");
        
        int[] tabPieces2 = new int[8];
        for(int i=0; i<tabPieces2.length; i++)
            tabPieces2[i]=tabPieces[i];
        
        joueurs[J1] = new Ordinateur(true,Ordinateur.FACILE_HEURISTIQUE, prop, tabPieces,J1);
        joueurs[J2] = new Ordinateur(true,Ordinateur.FACILE_HEURISTIQUE, prop, tabPieces2,J2);
        
        go();
    }
    
    public void go(){
        if(joueurs[J1] instanceof Ordinateur){
            Ordinateur o = (Ordinateur) joueurs[J1];
            List<Coup[]> tab = new LinkedList();
                for(int i=0; i<joueurs[jCourant].pions().length; i++){
                    if(joueurs[jCourant].pions()[i]!=0){
                        Coup[] tmp = depotPossible(jCourant, i);
                        if(tmp!=null)
                            tab.add(tmp);
                    }
                        
                }
                   
                Coup[] tmp;
                if((tmp=deplacementPossible(jCourant))!=null)
                    tab.add(tmp);

                int taille= 0;
                Iterator<Coup[]> it = tab.iterator();
                while(it.hasNext())
                    taille+=it.next().length;
                it = tab.iterator();
                coups= new Coup[taille];
                for(int i=0; i<taille;i++){
                    Coup[] x = it.next();
                    for(int j=0; j<x.length; j++)
                        coups[i+j]=x[j];
                }
                joue(o.coup(this, coups));
        }
    }
    
    @Override
    public void prochainJoueur() {
        jCourant = ++jCourant % 2;

        if(plateau.estEncerclee(jCourant)){
            etat=FIN;
            System.err.println(jCourant+" à perdu");
        }else{
            List<Coup[]> tab = new LinkedList();
            for(int i=0; i<joueurs[jCourant].pions().length; i++){
                if(joueurs[jCourant].pions()[i]!=0){
                    Coup[] tmp = depotPossible(jCourant, i);
                    if(tmp!=null)
                        tab.add(tmp);
                }
            }

            Coup[] tmp;
            if((tmp=deplacementPossible(jCourant))!=null)
                tab.add(tmp);

            int taille= 0;
            Iterator<Coup[]> it = tab.iterator();
            while(it.hasNext())
                taille+=it.next().length;
            it = tab.iterator();
            System.out.println(nbCoup[J1]+" "+nbCoup[J2]);
            coups = new Coup[taille];
            int i=0;
            while(it.hasNext()){
                Coup[] x = it.next();
                int j;
                for(j=0; j<x.length; j++){
                    coups[i+j]=x[j];
                    //System.out.println(coups[i+j]+" "+(i+j));
                }
                 i+=j;
            }
            aucun = coups == null || coups.length<=0;
            if(aucun){
                System.out.println("Coucou");
                prochainJoueur();
            }else if(precAucun && aucun){
                etat=FIN;
                System.err.println("Match nul");
            }else{
                if(joueurs[jCourant] instanceof Ordinateur){
                    Ordinateur o = (Ordinateur) joueurs[jCourant];
                    precAucun = aucun;
                    joue(o.coup(this, coups));
                }
            }
        }
    }
    
    public void joue(Deplacement d){
                deplacePion(d);
                nbCoup[jCourant]++;
                refaire.clear();
                historique.add(d);
                System.err.println(d+" déplacement effectué par "+jCourant);
                etat=JOUE_EN_COURS;
         
    }
    public void joue(Depot d){
        if(nbCoup[jCourant]==0 && jCourant == J1){
            joueurs[jCourant].jouer(d.type());
            plateau.premierPion(FabriqueInsecte.creer(d.type(), jCourant, new Point(0,0)));
            nbCoup[jCourant]++;
            refaire.clear();
            historique.add(d);
            System.err.println("1- Dépot effectué par "+jCourant+" "+d);
            etat=JOUE_EN_COURS;
        }else if(nbCoup[jCourant]==0 && jCourant == J2){
            if(plateau.premierPionValide(d)){
            joueurs[jCourant].jouer(d.type());
                deposePion(d);
                nbCoup[jCourant]++;
                refaire.clear();
                historique.add(d);
                joueurs[jCourant].jouer(d.type());
                System.err.println("2- Dépot effectué "+d);
                etat=JOUE_EN_COURS;
            }else{
                System.err.println("Depot impossible");
            }
        }else if(deposePionValide(d) && joueurs[jCourant].pion(d.type())>0){
            
            joueurs[jCourant].jouer(d.type());
            deposePion(d);
            nbCoup[jCourant]++;
            refaire.clear();
            historique.add(d);
            System.err.println("3- Dépot effectué "+d);
            etat=JOUE_EN_COURS;
            
        }else{
            System.err.println("Depot impossible");
        }
    }
}
