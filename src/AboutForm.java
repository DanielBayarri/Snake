import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AboutForm extends JFrame{

    private JPanel mainPanel;
    private JLabel JLabelText1;
    private JLabel JLabelText2;

    public AboutForm(String title) {
        super(title);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setContentPane(mainPanel);
        pack(); // Adjusts the Frame size to the main panel content
        setLocationRelativeTo(null);//Center Window

    }


}
