/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Joueurs;

import java.util.Properties;

/**
 *
 * @author grandmax
 */
public abstract class Joueur {
    String nom;
    int[] pions;
    boolean main;
    Properties prop;
    int numJoueur;
    
    public Joueur(boolean m, Properties p, int joueur){
        this.numJoueur=joueur;
        main=m;
        prop=p;
    }
    
    public void setMain(){
        main = !main;
    }
    public String nom(){
        return nom;
    }
    public int[] pions(){
        return pions;
    }
    
}
