/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.*;
import View.*;
import java.awt.Component;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.graphstream.ui.view.Viewer;

/**
 *
 * @author tiaraannisa
 */
public class Controller extends MouseAdapter implements ActionListener, MouseListener {

    private Application model;
    private View view;
    private String lastAlgo;

    public Controller(Application model) {
        this.model = model;
        MainFrame mf = new MainFrame();
        mf.setVisible(true);
        mf.addListener(this);
        mf.addMouseListener(this);
        view = mf;
        try {
            model.loadMap();
            model.makeGraph();
//            model.printAll().addDefaultView(false);
//            model.printAll().getDefaultView().getCamera().setViewPercent(0.5);
            mf.getjShow().add(model.printAll().getDefaultView());
            mf.getjShow().scrollRectToVisible(new Rectangle(1080, 710));
        } catch (SQLException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        Object source = ae.getSource();
        if (view instanceof MainFrame) {
            MainFrame mf = (MainFrame) view;

            if (source.equals(mf.getdButton())) {
                try {
                    lastAlgo = "dijkstra";
                    String openResult = "";
                    String closeResult = "";
                    long startTime = System.nanoTime();
                    openResult += model.getResult("dijkstra", (String) mf.getjSource().getSelectedItem(), (String) mf.getjDestination().getSelectedItem());
                    long duration1 = System.nanoTime() - startTime;
                    System.out.println("execution time dijkstra : " + limitPrecision(duration1 * 0.000000001 + "", 9));
                    startTime = System.nanoTime();
                    closeResult += model.getResult("dijkstra_close", (String) mf.getjSource().getSelectedItem(), (String) mf.getjDestination().getSelectedItem());
                    long duration2 = System.nanoTime() - startTime;
                    System.out.println("execution time dijkstra_close : " + limitPrecision(duration2 * 0.000000001 + "", 9));
                    mf.getOpen().setText(openResult);
                    mf.getClose().setText(closeResult);
                    Double totalduration = limitPrecision((duration1 + duration2) * 0.000000001 + "", 9);
                    mf.getTimeExecution().setText("executed in " + totalduration + " seconds");
                    System.out.println("executed in " + totalduration + " seconds");
                    System.out.println("");
                } catch (SQLException ex) {
                    Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(mf, "Input source and destination first!");
                }
            } else if (source.equals(mf.getFwButton())) {
                lastAlgo = "fw";
                try {
                    String openResult = "";
                    String closeResult = "";
//                    openResult += model.getPath((String) mf.getjSource().getSelectedItem(), (String) mf.getjDestination().getSelectedItem());
                    openResult += model.getResult("fw", (String) mf.getjSource().getSelectedItem(), (String) mf.getjDestination().getSelectedItem());
                    long time = model.getTimeExecute();
                    System.out.println("execution time fw : " + limitPrecision(time * 0.000000001 + "", 9));
//                    model.getPath((String) mf.getjSource().getSelectedItem(), (String) mf.getjDestination().getSelectedItem());
                    closeResult += model.getResult("fw_close", (String) mf.getjSource().getSelectedItem(), (String) mf.getjDestination().getSelectedItem());
                    time += model.getTimeExecute();
                    System.out.println("execution time fw_close : " + limitPrecision(model.getTimeExecute() * 0.000000001 + "", 9));
                    mf.getOpen().setText(openResult);
                    mf.getClose().setText(closeResult);
                    mf.getTimeExecution().setText("executed in " + limitPrecision(time * 0.000000001 + "", 9) + " seconds");
                    System.out.println("executed in " + limitPrecision(time * 0.000000001 + "", 9) + " seconds");
                    System.out.println("");
                } catch (SQLException ex) {
                    Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(mf, "Input source and destination first!");
                }
            } else if (source.equals(mf.getBfButton())) {
                lastAlgo = "bf";
                try {
                    String openResult = "";
                    String closeResult = "";
//                    openResult += model.getPath((String) mf.getjSource().getSelectedItem(), (String) mf.getjDestination().getSelectedItem());
                    openResult += model.getResult("bf", (String) mf.getjSource().getSelectedItem(), (String) mf.getjDestination().getSelectedItem());
                    long time = model.getTimeExecute();
                    System.out.println("execution time bf : " + limitPrecision(time * 0.000000001 + "", 9));
//                    model.getPath((String) mf.getjSource().getSelectedItem(), (String) mf.getjDestination().getSelectedItem());
                    closeResult += model.getResult("bf_close", (String) mf.getjSource().getSelectedItem(), (String) mf.getjDestination().getSelectedItem());
                    time += model.getTimeExecute();
                    System.out.println("execution time bf_close : " + limitPrecision(model.getTimeExecute() * 0.000000001 + "", 9));
                    mf.getOpen().setText(openResult);
                    mf.getClose().setText(closeResult);
                    mf.getTimeExecution().setText("executed in " + limitPrecision(time * 0.000000001 + "", 9) + " seconds");
                    System.out.println("executed in " + limitPrecision(time * 0.000000001 + "", 9) + " seconds");
                    System.out.println("");
                } catch (SQLException ex) {
                    Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(mf, "Input source and destination first!");
                }
            } else if (source.equals(mf.getAstar())) {
                lastAlgo = "astar";
                try {
                    String openResult = "";
                    String closeResult = "";
                    long startTime = System.nanoTime();
                    openResult += model.getResult("astar", (String) mf.getjSource().getSelectedItem(), (String) mf.getjDestination().getSelectedItem());
                    long duration1 = System.nanoTime() - startTime;
                    System.out.println("execution time astar : " + limitPrecision(duration1 * 0.000000001 + "", 9));
                    mf.getOpen().setText(openResult);
                    startTime = System.nanoTime();
                    closeResult += model.getResult("astar_close", (String) mf.getjSource().getSelectedItem(), (String) mf.getjDestination().getSelectedItem());
                    long duration2 = System.nanoTime() - startTime;
                    System.out.println("execution time astar_close : " + limitPrecision(duration2 * 0.000000001 + "", 9));
                    mf.getClose().setText(closeResult);
                    Double totalduration = limitPrecision((duration1 + duration2) * 0.000000001 + "", 9);
                    mf.getTimeExecution().setText("executed in " + totalduration + " seconds");
                    System.out.println("executed in " + totalduration + " seconds");
                    System.out.println("");
                } catch (SQLException ex) {
                    Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(mf, "Input source and destination first!");
                }
            } else if (source.equals(mf.getBfButton())) {
                model.showByZ((String) mf.getzValue().getSelectedItem());
            } else if (source.equals(mf.getZoomIn())) {
                model.zoomIn();
            } else if (source.equals(mf.getZoomOut())) {
                model.zoomOut();
            } else if (source.equals(mf.getOkButton())) {
                model.showByZ((String) mf.getzValue().getSelectedItem());
            } else if (source.equals(mf.getUp())) {
                model.upView();
            } else if (source.equals(mf.getRight())) {
                model.rightView();
            } else if (source.equals(mf.getLeft())) {
                model.leftView();
            } else if (source.equals(mf.getDown())) {
                model.downView();
            } else if (source.equals(mf.getCircle())) {
                model.setView();
            } else if (source.equals(mf.getTwoD())) {
                model.show2D();
            } else if (source.equals(mf.getThreeD())) {
                model.show3D();
            }
        }
    }

    public double limitPrecision(String dblAsString, int maxDigitsAfterDecimal) {
        int multiplier = (int) Math.pow(10, maxDigitsAfterDecimal);
        double truncated = (double) ((long) ((Double.parseDouble(dblAsString)) * multiplier)) / multiplier;
//        System.out.println(dblAsString + " ==> " + truncated);
        return truncated;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Object source = e.getSource();
        if (view instanceof MainFrame) {
            MainFrame mf = (MainFrame) view;
            if (source.equals(mf.getOpen())) {
                try {
                    String tmp = model.getResult(lastAlgo, (String) mf.getjSource().getSelectedItem(), (String) mf.getjDestination().getSelectedItem());
                } catch (SQLException ex) {
                    Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else if (source.equals(mf.getClose())) {
                try {
                    String algo = lastAlgo + "_close";
//                    System.out.println(algo);
                    String tmp = model.getResult(algo, (String) mf.getjSource().getSelectedItem(), (String) mf.getjDestination().getSelectedItem());
//                    System.out.println(tmp);
                } catch (SQLException ex) {
                    Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
