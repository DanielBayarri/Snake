import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NameForm extends JFrame{
    private JTextField textField;
    private JButton button;
    private JPanel mainPanel;
    private String playerName;

    public NameForm(String title) {
        super(title);
        playerName = "Player";
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setContentPane(mainPanel);
        pack(); // Adjusts the Frame size to the main panel content
        setLocationRelativeTo(null);//Center Window
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playerName = textField.getText();

                System.out.println(playerName);
                setVisible(false);

            }
        });
    }

    public String getPlayerName(){
        return playerName;
    }


}
