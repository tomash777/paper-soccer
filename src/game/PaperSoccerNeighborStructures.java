package game;

import pomocnicze.Coordinates2D;
import pomocnicze.UnorderedPair;

import java.util.ArrayList;

/**
 * Created by tomasz on 05.11.17.
 */
public class PaperSoccerNeighborStructures {
    public ArrayList<Coordinates2D> neighbors = new ArrayList<Coordinates2D>();
    public ArrayList<Coordinates2D> downNeighbors = new ArrayList<Coordinates2D>();
    public ArrayList<Coordinates2D> sideNeighbors = new ArrayList<Coordinates2D>();
    public ArrayList<Coordinates2D> upNeighbors = new ArrayList<Coordinates2D>();

    private PaperSoccerGame game;

    public PaperSoccerNeighborStructures(PaperSoccerGame game) {
        this.game = game;
    }

    public void makeNeighbors(int x, int y){
        neighbors.clear();
        downNeighbors.clear();
        sideNeighbors.clear();
        upNeighbors.clear();
        for (int k = -1; k < 2; k++)
            for (int l = -1; l < 2; l++)
                if (!(k == 0 && l == 0) && game.onBoard(x + k, y + l)) {
                    Coordinates2D newStart = game.points[x][y];
                    Coordinates2D newStop = game.points[x + k][y + l];
                    UnorderedPair<Coordinates2D, Coordinates2D> newPair = new UnorderedPair<Coordinates2D, Coordinates2D>(newStop, newStart);
                    if (!(game.lines.contains(newPair) || game.borderLines.contains(newPair))) {
                        neighbors.add(newStop);
                        if (l == 1) downNeighbors.add(newStop);
                        else if(l == 0) sideNeighbors.add(newStop);
                        else upNeighbors.add(newStop);
                    }
                }
    }
}
