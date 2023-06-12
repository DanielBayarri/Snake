import javax.swing.*;

public class ControlsForm extends JFrame{
    private JButton button;
    private JPanel mainPanel;
    private JLabel Titulo;
    private JLabel Controles;

    public ControlsForm(String title) {
        super(title);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setContentPane(mainPanel);
        pack(); // Adjusts the Frame size to the main panel content
        setLocationRelativeTo(null);//Center Window

    }

}
