package client;

import javax.swing.*;
import java.awt.*;
import board.PaperSoccerBoard;


public class PaperSoccer extends JFrame {

    private PaperSoccerBoard paperSoccerPanel;

    public void newGame(){
        getContentPane().remove(paperSoccerPanel);
        repaint();
        paperSoccerPanel = new PaperSoccerBoard(this,600,  8, 10);
        add(paperSoccerPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        repaint();
    }

    public PaperSoccer() {
        super("Paper Soccer");

        paperSoccerPanel = new PaperSoccerBoard(this,600,  8, 10);
        add(paperSoccerPanel);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        //setResizable(false);
        pack();
    }


    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new PaperSoccer();
            }
        });
    }
}
