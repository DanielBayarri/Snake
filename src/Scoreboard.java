import javax.swing.*;
import java.awt.*;

public class Scoreboard extends JPanel {
    private int score;
    private JLabel label;
    private String player;

    public Scoreboard() {
        super();
        score = 0;
        player = "Player";
        initGUI();
    }

    public void initGUI() {
        setLayout(new FlowLayout());
        label = new JLabel();
        add(label);
        label.setText(player + ": " + score);
    }

    public void incrementScore(int inc) {
        score += inc;
        label.setText(player + ": " + score);
    }

    public void setPlayer(String player) {
        this.player = player;
        label.setText(player + ": " + score);
    }

    public void setScore(int score) {
        this.score = score;
        label.setText(player + ": " + score);
    }

    public int getScore() {
        return score;
    }
}
