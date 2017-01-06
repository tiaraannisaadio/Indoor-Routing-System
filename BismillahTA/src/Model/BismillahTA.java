/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.sql.SQLException;
import java.text.SimpleDateFormat;

/**
 *
 * @author cinTA_nad
 */
public class BismillahTA {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");

        Application ap = new Application();
        new Controller.Controller(ap);
//        ap.loadMap();
//        ap.makeGraph();
//        System.out.println("distance measure result : " + ap.getDistance("IF1.03.08", "IF1.01.03"));
//        ap.bfAlgo("IF1.03.08", "IF1.01.03");
//        ap.printAll();

    }

}
