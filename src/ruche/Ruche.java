/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ruche;

//http://www.redblobgames.com/grids/hexagons/

import Modele.Arbitre;
import Modele.SimulationIA;
import Modele.TestArbitre;
import Vue.Interface;
import java.util.Properties;

/**
 *
 * @author grandmax
 */
public class Ruche {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Properties p = Configuration.proprietes();
        Interface it = new Interface();
        it.creer(args, new SimulationIA(p));
    }
    
}
