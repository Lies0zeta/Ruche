/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele;

/**
 *
 * @author grandmax
 */
public class Deplacement extends Coup{
    Point source;
    
    public Deplacement(int j,  Point s, Point d){
        super(j);
        source = s;
        destination = d;
    }
    
    public Point source(){
        return source;
    }
    
    public boolean equals(Object o){
        if(o instanceof Deplacement){
            Deplacement d = (Deplacement) o;
            return (d.source().equals(source) && d.destination().equals(destination) && d.joueur()==joueur);
        }
        return true;
    }
}
