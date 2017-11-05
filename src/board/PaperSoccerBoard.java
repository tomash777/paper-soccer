package board;

import boardObservers.PlayerTurnListener;
import client.PaperSoccer;
import game.PaperSoccerGame;
import pomocnicze.Coordinates2D;
import pomocnicze.UnorderedPair;

import javax.swing.*;
import java.awt.*;


public class PaperSoccerBoard extends JPanel {

    public final PaperSoccer frame;
    public PaperSoccerGame game;
    private final PaperSoccerBoardGameFunctions functions = new PaperSoccerBoardGameFunctions(this);

    public final int WIDTH ;
    private final int HEIGHT;
    public final int dimX;
    public final int dimY;

    private final int epsilon;

    private JButton[][] fields;

    private int takeX(int x) {
        double res = WIDTH *(0.1 + 0.8 * (x + 1) / (dimX + 1));
        return (int) res;
    }
    private int takeY(int x) {
        double res = HEIGHT *(0.1 + 0.8 * (x + 1) / (dimY + 1));
        return (int) res;
    }

    private boolean onBoard(int i , int j) {
        return i >= 0 && j >= 0 && i < dimX && j < dimY &&
                !((j == 0 || j == dimY - 1) && Math.abs(i - dimX/2)>1);
    }

    public void endGame(int n){
        functions.endGame(n);
    }

    public void newGame() {functions.newGame(); }

    public void wrongMove(){JOptionPane.showMessageDialog(frame, "Wrong move!");}

    public void changeTurn() {
        functions.changeTurn();
    }

    public void moveRedPoint(Coordinates2D start, Coordinates2D stop){
        fields[start.x][start.y].setBackground(new JButton().getBackground());
        fields[stop.x][stop.y].setBackground(Color.RED);
    }

    public PaperSoccerBoard(PaperSoccer frame, int width, int dimx, int dimy) {

        dimX = dimx + 1;
        dimY = dimy + 3;

        this.frame = frame;
        game = new PaperSoccerGame(this);

        WIDTH = width;
        HEIGHT = width * (dimY/dimX);

        epsilon = WIDTH/(dimY * 10);

        setPreferredSize(new Dimension(WIDTH , HEIGHT));
        setLayout(null);

        fields = new JButton[dimX][dimY];

        for(int i = 0; i < dimX; i++)
            for(int j = 0; j < dimY; j++)
                if(onBoard(i,j))
                {
                    JButton a = new JButton();
                    a.addActionListener(new PlayerTurnListener(i,j, game));

                    a.setBounds(takeX(i) - epsilon, takeY(j) - epsilon, 2 * epsilon, 2 * epsilon);
                    fields[i][j] = a;
                    add(a);
                }

        fields[dimX/2][dimY/2].setBackground(Color.RED);

        functions.addPlayButton();

        validate();
        repaint();

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.GREEN);
        int x1, y1, x2, y2;

        g.fillRect(takeX(0), takeY(1), takeX(dimX - 1) - takeX(0),takeY(dimY - 3) - takeY(0));
        g.fillRect(takeX(dimX/2 - 1),takeY(0), takeX(2)- takeX(0), takeY(1) - takeY(0));
        g.fillRect(takeX(dimX/2 - 1),takeY(dimY - 2), takeX(2)- takeX(0), takeY(1) - takeY(0));

        g.setColor(Color.BLACK);

        g.drawLine(takeX(0),takeY(1),takeX(0), takeY(dimY - 2));
        g.drawLine(takeX(0), takeY(dimY - 2), takeX(dimX/2 - 1), takeY(dimY - 2));
        g.drawLine(takeX(dimX/2 - 1), takeY(dimY - 1), takeX(dimX/2 - 1), takeY(dimY - 2));
        g.drawLine(takeX(dimX/2 - 1), takeY(dimY - 1), takeX(dimX/2 + 1), takeY(dimY - 1));
        g.drawLine(takeX(dimX/2 + 1), takeY(dimY - 1), takeX(dimX/2 + 1), takeY(dimY - 2));
        g.drawLine(takeX(dimX/2 + 1), takeY(dimY - 2), takeX(dimX - 1), takeY(dimY - 2));
        g.drawLine(takeX(dimX - 1), takeY(dimY - 2), takeX(dimX - 1), takeY(1));
        g.drawLine(takeX(dimX - 1), takeY(1), takeX(dimX/2 + 1), takeY(1));
        g.drawLine(takeX(dimX/2 + 1), takeY(0), takeX(dimX/2 + 1), takeY(1));
        g.drawLine(takeX(dimX/2 + 1), takeY(0), takeX(dimX/2 - 1), takeY(0));
        g.drawLine(takeX(dimX/2 - 1), takeY(1), takeX(dimX/2 - 1), takeY(0));
        g.drawLine(takeX(0), takeY(1), takeX(dimX/2 - 1), takeY(1));


        for(UnorderedPair<Coordinates2D,Coordinates2D> pair: game.lines) {
            x1 =  pair.getLeft().x;
            y1 =  pair.getLeft().y;
            x2 =  pair.getRight().x;
            y2 =  pair.getRight().y;
            g.drawLine(takeX(x1),takeY(y1),takeX(x2),takeY(y2));
        }

        g.setColor(Color.RED);

        for(UnorderedPair<Coordinates2D,Coordinates2D> pair: game.turnLines) {
            x1 =  pair.getLeft().x;
            y1 =  pair.getLeft().y;
            x2 =  pair.getRight().x;
            y2 =  pair.getRight().y;
            g.drawLine(takeX(x1),takeY(y1),takeX(x2),takeY(y2));
        }

    }
}
