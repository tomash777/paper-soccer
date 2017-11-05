package board;

import boardObservers.TurnLabel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class PaperSoccerBoardGameFunctions {
    private final PaperSoccerBoard board;
    private JButton componentTurn;
    private TurnLabel turnLabel;
    private String player1;
    private String player2;
    private JButton playButton;
    int play;

    public PaperSoccerBoardGameFunctions(PaperSoccerBoard board) {
        this.board = board;
    }

    public void endGame(int n) {
        if (n == 0 && board.game.turn == 1)
            JOptionPane.showMessageDialog(board.frame, player1 + " has lost by lack of moves!");
        else if (n == 0 && board.game.turn == 2)
            JOptionPane.showMessageDialog(board.frame, player2 + " has lost by lack of moves!");
        else if (n == 1)
            JOptionPane.showMessageDialog(board.frame, player1 + " has won by scoring the goal!");
        else
            JOptionPane.showMessageDialog(board.frame, player2 + " has won by scoring the goal!");
        board.frame.newGame();
    }

    public void newGame() {
        board.remove(playButton);
        Object[] options = {"Human vs Human",
                "Human vs Computer"};
        play = JOptionPane.showOptionDialog(board,
                "How would You like to play?",
                "Players",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);
        if (play == 1) {
            player1 = JOptionPane.showInputDialog("Please input Player's name!","Player");
            player2 = "Computer";
        } else {
            player1 = JOptionPane.showInputDialog("Please input First Player's name!","Player1");
            player2 = JOptionPane.showInputDialog("Please input Second Player's name!","Player2");
        }

        board.game.play = play;
        board.game.playing = true;

        componentTurn = new JButton();
        turnLabel = new TurnLabel(player1, player2);
        componentTurn.addActionListener(turnLabel);

        turnLabel.setBounds(board.WIDTH/2 - 150,10,300,50);
        board.add(componentTurn);
        board.add(turnLabel);
        board.repaint();
    }

    public void changeTurn(){
        componentTurn.doClick();
    }

    public void addPlayButton(){
        playButton = new JButton();
        playButton.setBounds(board.WIDTH/2 - 50, 20, 100, 30);
        playButton.setText("PLAY !");
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                newGame();
            }
        });

        board.add(playButton);
    }
}
