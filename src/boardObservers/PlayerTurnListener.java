package boardObservers;

import game.PaperSoccerGame;
import game.PaperSoccerTurn;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class PlayerTurnListener implements ActionListener {
    private int i,j;
    private PaperSoccerGame game;

    public PlayerTurnListener(int i, int j, PaperSoccerGame game) {
        this.i = i;
        this.j = j;
        this.game = game;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if(game.playing) game.makeMove(i,j);

    }
}
