package boardObservers;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class TurnLabel extends JLabel implements ActionListener {

    private final String TEXT1;// = "Turn of the Player1!";
    private final String TEXT2;// = "Turn of the Player2!";

    private boolean usingText1;

    public TurnLabel(String name1, String name2) {
        setHorizontalAlignment(SwingConstants.CENTER);
        TEXT1 = name1 + "'s turn!";
        TEXT2 = name2 + "'s turn!";
        setText(TEXT1);
        usingText1 = true;
    }

    private void changeLabel() {
        if(usingText1)
            setText(TEXT2);
        else
            setText(TEXT1);

        usingText1 = !usingText1;
    }

    public void actionPerformed(ActionEvent e) {
        changeLabel();
        //validate();
    }
}
