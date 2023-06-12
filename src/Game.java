import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Game extends JFrame {
    private JPanel gamePanel;
    private Board board;
    private Scoreboard scoreboard;
    private HighScores highScoresForm;
    private NameForm nameForm;
    private AboutForm aboutForm;
    private ControlsForm controlsForm;
    //Menu Bar
    private final JMenuBar menuBar = new JMenuBar();
    private final JMenu menuGame = new JMenu("Game");
    private final JMenu menuOptions = new JMenu("Options");
    private final JMenu menuAbout = new JMenu("About");
    private final JMenuItem menuGameStart = new JMenuItem("Start");
    private final JMenuItem menuGameHighScores = new JMenuItem("High Scores");
    private final JMenuItem menuGameExit = new JMenuItem("Exit");
    private final JMenu difficultyLevel = new JMenu("Difficulty");
    private final JMenuItem menuOptionName = new JMenuItem("Player Name");
    private final JCheckBoxMenuItem dEasy = new JCheckBoxMenuItem("Easy");
    private final JCheckBoxMenuItem dMedium = new JCheckBoxMenuItem("Medium");
    private final JCheckBoxMenuItem dHard = new JCheckBoxMenuItem("Hard");
    private final JMenuItem aboutControls = new JMenuItem("Controls");
    private final JMenuItem aboutGame = new JMenuItem("About Game");


    public Game(String tittle) throws IOException {

        super(tittle);
        initComponents();

    }

    public void initComponents() throws IOException {

        highScoresForm = new HighScores("High Scores");
        nameForm = new NameForm("Player Name");
        controlsForm = new ControlsForm("Game Controls");
        aboutForm = new AboutForm("Game Controls");

        //MenuBar
        menuBar.add(menuGame);
        menuGame.add(menuGameStart);
        menuGame.add(menuGameHighScores);
        menuGame.add(menuGameExit);

        menuBar.add(menuOptions);
        menuOptions.add(difficultyLevel);
        difficultyLevel.add(dEasy);
        difficultyLevel.add(dMedium);
        difficultyLevel.add(dHard);
        dMedium.setSelected(true);

        menuBar.add(menuAbout);
        menuAbout.add(aboutControls);
        menuAbout.add(aboutGame);
        menuOptions.add(menuOptionName);

        menuGameStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                scoreboard.setPlayer(nameForm.getPlayerName());
                if (dEasy.isSelected()) {
                    restartGame(300, dEasy);
                } else if (dMedium.isSelected())
                    restartGame(100, dMedium);
                else {
                    restartGame(60, dHard);
                }
            }
        });
        menuGameHighScores.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    highScoresForm.initListScores();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                highScoresForm.printScores();
                highScoresForm.setVisible(true);
            }
        });
        menuGameExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        dEasy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                restartGame(300, dEasy);

            }
        });
        dMedium.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                restartGame(100, dMedium);
            }
        });
        dHard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                restartGame(60, dHard);
            }
        });
        menuOptionName.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nameForm.setVisible(true);
            }
        });

        aboutControls.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controlsForm.setVisible(true);
            }
        });
        aboutGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                aboutForm.setVisible(true);

            }
        });

        //Window Options
        setJMenuBar(menuBar);//setMenuBar
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//Cerrar y que no aparezca nada en la consola.
        setContentPane(gamePanel);
        pack(); //Adjust the Frame size to the main panel content
        setResizable(false);
        setLocationRelativeTo(null);//Center Window
    }

    public void restartGame(int dTime, JCheckBoxMenuItem diff) {
        //Set delta Time
        dEasy.setSelected(false);
        dMedium.setSelected(false);
        dHard.setSelected(false);
        diff.setSelected(true);
        //restart game
        board.finisGame();
        scoreboard.setScore(0);
        board.setScoreBoard(scoreboard);
        board.initGame(dTime);
    }

    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new Game("Snake").setVisible(true);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        board = new Board();
    }


}
