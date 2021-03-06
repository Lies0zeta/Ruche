/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vue;

import Modele.Case;
import Modele.Etendeur;
import Modele.Insecte;
import Modele.Plateau;
import Modele.Point;
import Modele.Visiteur;
import static Vue.Dessinateur.c;
import java.util.Iterator;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author brignone
 */
public class Pointeur extends Visiteur {
    static Canvas c;
    Etendeur etendeur;
    MouseEvent me;
    
    public Pointeur (Canvas c, MouseEvent me) {
        this.c = c;
        this.me = me;
        etendeur = new Etendeur();
    }
    
    public Pointeur (Canvas c) {
        this.c = c;
        this.me = me;
        etendeur = new Etendeur();
    }
    
    public void addEvent (MouseEvent me) {
        this.me = me;
    }
    
    @Override
    public boolean visite (Plateau p) {
        etendeur.fixeEchelle(c, p);
        p.depointe();
        return false;
    }
    
    @Override
    public boolean visite (Case c) {
        boolean b = true;
        double x1, x2, y1, y2;
        etendeur.fixeComposant(c);
        double [][] coords = Interface.hex_corner(etendeur.x(), etendeur.y(), etendeur.h()/2);
        for (int i = 0; i < 6; i++) {
            x1 = coords[0][(i+1)%6] - coords[0][i];
            y1 = coords[1][(i+1)%6] - coords[1][i];

            x2 = me.getX() - coords[0][i];
            y2 = me.getY() - coords[1][i];
            
            b = b&&((x1*y2) - (x2*y1))>0;
        }
        if (b) {
            //System.out.println("[" + c.position().x()+ ";" + c.position().y()+ "]");
            c.tete().pointe();
        }
        return false;
    }
    
    @Override
    public boolean visite (ButtonDrawer b) {
        Iterator<Insecte> it = b.lp1.iterator();
        while (it.hasNext()) {
            it.next().accept(this);
        }
        it = b.lp2.iterator();
        while (it.hasNext()) {
            it.next().accept(this);
        }
        return false;
    }
    
    @Override
    public boolean visite (Insecte c) {
        boolean b = true;
        double x1, x2, y1, y2;
        etendeur.fixeComposant(c);
        double [][] coords = Interface.hex_corner(etendeur.x(), etendeur.y(), etendeur.h()/2);
        for (int i = 0; i < 6; i++) {
            x1 = coords[0][(i+1)%6] - coords[0][i];
            y1 = coords[1][(i+1)%6] - coords[1][i];

            x2 = me.getX() - coords[0][i];
            y2 = me.getY() - coords[1][i];
            
            b = b&&((x1*y2) - (x2*y1))>0;
        }
        if (b) {
            //System.out.println("[" + c.position().x()+ ";" + c.position().y()+ "]");
            c.pointe();
        }
        return false;
    }
}
