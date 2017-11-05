package game;

import java.util.Set;
import java.util.HashSet;

import pomocnicze.Coordinates2D;
import pomocnicze.UnorderedPair;
import board.PaperSoccerBoard;


public class PaperSoccerGame {

    public final int dimX;
    public final int dimY;
    private final PaperSoccerBoard board;
    public int play;
    public boolean playing = false;

    private int nowX;
    private int nowY;
    public int turn = 1;
    private PaperSoccerNeighborStructures struct = new PaperSoccerNeighborStructures(this);
    public final PaperSoccerMoveMechanism moveMechanism = new PaperSoccerMoveMechanism(this, struct);

    public int getNowX() {
        return nowX;
    }

    public int getNowY() {
        return nowY;
    }

    public void setNowX(int nowX) {
        this.nowX = nowX;
    }

    public void setNowY(int nowY) {
        this.nowY = nowY;
    }

    public boolean isGoal() {return nowY == 0 || nowY == dimY - 1;}

    public boolean[][] visited;
    public Coordinates2D[][] points;

    public Set<UnorderedPair<Coordinates2D, Coordinates2D>> lines = new HashSet<UnorderedPair<Coordinates2D, Coordinates2D>>();
    public Set<UnorderedPair<Coordinates2D, Coordinates2D>> borderLines = new HashSet<UnorderedPair<Coordinates2D, Coordinates2D>>();
    public Set<UnorderedPair<Coordinates2D, Coordinates2D>> turnLines = new HashSet<UnorderedPair<Coordinates2D, Coordinates2D>>();
    public Set<UnorderedPair<Coordinates2D, Coordinates2D>> forbiddenLines = new HashSet<UnorderedPair<Coordinates2D, Coordinates2D>>();


    public boolean onBoard(int i , int j) {
        return i >= 0 && j >= 0 && i < dimX && j < dimY &&
                !((j == 0 || j == dimY - 1) && Math.abs(i - dimX/2)>1);
    }

    public void makeMove(int i, int j){
        moveMechanism.makeMove(i, j, board);
    }


    public PaperSoccerGame(PaperSoccerBoard board) {
        this.board = board;
        this.dimX = board.dimX;
        this.dimY = board.dimY;

        nowX = (dimX - 1)/2;
        nowY = (dimY - 1)/2;

        points = new Coordinates2D[dimX][dimY];

        for(int i = 0; i < dimX; i++)
            for(int j = 0; j < dimY; j++)
                if(onBoard(i,j)) points[i][j] = new Coordinates2D(i, j);

        for(int j = 1; j < dimY - 2; j++){
            borderLines.add(new UnorderedPair<>(points[0][j], points[0][j+1]));
            borderLines.add(new UnorderedPair<>(points[dimX - 1][j], points[dimX-1][j + 1]));
        }

        for(int i = 0; i < dimX-1; i++){
            borderLines.add(new UnorderedPair<>(points[i][1], points[i+1][1]));
            borderLines.add(new UnorderedPair<>(points[i][dimY-2], points[i+1][dimY-2]));
        }

        borderLines.add(new UnorderedPair<>(points[dimX/2-1][1], points[dimX/2-1][0]));
        borderLines.add(new UnorderedPair<>(points[dimX/2+1][1], points[dimX/2+1][0]));
        borderLines.add(new UnorderedPair<>(points[dimX/2-1][dimY-2], points[dimX/2-1][dimY-1]));
        borderLines.add(new UnorderedPair<>(points[dimX/2+1][dimY-1], points[dimX/2+1][dimY-2]));

        forbiddenLines.add(new UnorderedPair<>(points[dimX/2-2][1], points[dimX/2-1][0]));
        forbiddenLines.add(new UnorderedPair<>(points[dimX/2+2][1], points[dimX/2+1][0]));
        forbiddenLines.add(new UnorderedPair<>(points[dimX/2-2][dimY-2], points[dimX/2-1][dimY-1]));
        forbiddenLines.add(new UnorderedPair<>(points[dimX/2+1][dimY-1], points[dimX/2+2][dimY-2]));

        borderLines.remove(new UnorderedPair<>(points[dimX/2-1][1], points[dimX/2][1]));
        borderLines.remove(new UnorderedPair<>(points[dimX/2+1][1], points[dimX/2][1]));
        borderLines.remove(new UnorderedPair<>(points[dimX/2-1][dimY-2], points[dimX/2][dimY-2]));
        borderLines.remove(new UnorderedPair<>(points[dimX/2+1][dimY-2], points[dimX/2][dimY-2]));

        struct.makeNeighbors(nowX, nowY);

        visited = new boolean[dimX][dimY];

        for(int j = 0; j < dimY; j++) {
            visited[dimX-1][j] = true;
            visited[0][j] = true;
        }

        for(int i = 0; i < dimX; i++){
            visited[i][1] = true;
            visited[i][dimY-2] = true;
        }


        visited[dimX/2][1] = false;
        visited[dimX/2][dimY-2] = false;
        visited[dimX/2][dimY/2] = true;


        for (int i = dimX/2-1; i <= dimX/2+1;i++){
            visited[i][0] = true;
            visited[i][dimY-1]=true;
        }
    }

}
