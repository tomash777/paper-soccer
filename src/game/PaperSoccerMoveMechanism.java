package game;

import board.PaperSoccerBoard;
import pomocnicze.Coordinates2D;
import pomocnicze.UnorderedPair;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class PaperSoccerMoveMechanism {

    private PaperSoccerGame game;
    private PaperSoccerNeighborStructures struct;

    public PaperSoccerMoveMechanism(PaperSoccerGame game, PaperSoccerNeighborStructures struct) {
        this.struct = struct;
        this.game = game;
    }


    public void makeMove(int newX, int newY, PaperSoccerBoard board){
        int nowX = game.getNowX();
        int nowY = game.getNowY();

        if(!(newX == nowX && newY == nowY) && Math.abs(newX - nowX) <= 1 && Math.abs(newY - nowY) <= 1) {

            Coordinates2D start = game.points[nowX][nowY];
            Coordinates2D stop = game.points[newX][newY];

            UnorderedPair<Coordinates2D, Coordinates2D> pair = new UnorderedPair<Coordinates2D, Coordinates2D>(start, stop);
            if (game.lines.contains(pair) || game.borderLines.contains(pair) ||
                    game.forbiddenLines.contains(pair)) {
                board.wrongMove();
                return;
            }

            board.moveRedPoint(start, stop);

            game.lines.add(pair);

            game.setNowX(newX);
            game.setNowY(newY);
            nowX = newX;
            nowY = newY;

            if (game.visited[nowX][nowY]) game.turnLines.add(pair);

            if (newY == 0) {
                board.repaint();
                board.endGame(1);
                return;
            }

            if (newY == game.dimY - 1) {
                board.repaint();
                board.endGame(2);
                return;
            }

            struct.makeNeighbors(nowX, nowY);

            if (struct.neighbors.isEmpty()) {
                board.repaint();
                board.endGame(0);
                return;
            }

            if (!game.visited[nowX][nowY]) {
                game.visited[nowX][nowY] = true;
                game.turnLines.clear();
                board.changeTurn();
                if (game.turn == 1) {game.turn = 2; if(game.play == 1) makeComputerTurn(board);}
                else game.turn = 1;
            }

            board.repaint();

        }

        else {board.wrongMove(); return;}

    }

    public void makeComputerTurn(PaperSoccerBoard board){
        game.playing = false;

        final Timer t = new Timer(500, null);

        t.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Random rnd = new Random();
                if(struct.neighbors.isEmpty()) t.stop();

                int i;
                if (!struct.downNeighbors.isEmpty()) {
                    i = rnd.nextInt(struct.downNeighbors.size());
                    makeMove(struct.downNeighbors.get(i).x, struct.downNeighbors.get(i).y, board);
                }
                else if (!struct.sideNeighbors.isEmpty()) {
                    i = rnd.nextInt(struct.sideNeighbors.size());
                    makeMove(struct.sideNeighbors.get(i).x, struct.sideNeighbors.get(i).y, board);
                }
                else{
                    i = rnd.nextInt(struct.upNeighbors.size());
                    makeMove(struct.upNeighbors.get(i).x, struct.upNeighbors.get(i).y, board);
                }

                if (game.turn == 1 || game.isGoal()) t.stop();
            }
        });
        t.setRepeats(true);
        t.start();
        game.playing = true;
    }
}
