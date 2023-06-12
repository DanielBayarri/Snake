import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

public class Board extends JPanel {

    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if (!moving) {
                        if (direction != Direction.RIGHT) {
                            direction = Direction.LEFT;
                        }
                    }
                    moving = true;
                    break;
                case KeyEvent.VK_RIGHT:
                    if (!moving) {
                        if (direction != Direction.LEFT) {
                            direction = Direction.RIGHT;
                        }
                    }
                    moving = true;
                    break;
                case KeyEvent.VK_UP:
                    if (!moving) {
                        if (direction != Direction.DOWN) {
                            direction = Direction.UP;
                        }
                    }
                    moving = true;
                    break;
                case KeyEvent.VK_DOWN:
                    if (!moving) {
                        if (direction != Direction.UP) {
                            direction = Direction.DOWN;
                        }
                    }
                    moving = true;
                    break;
                case KeyEvent.VK_SPACE:
                    pauseTime();
                    break;
                default:
            }
        }
    }

    private static final int COLS = 50;
    private static final int ROWS = 30;
    private static final Node NODE = new Node();
    private Snake snake;
    private Food food;
    private SpecialFood specialFood;
    private boolean haveFood;
    private boolean haveSpecial;
    private Scoreboard scoreBoard;
    Direction direction;
    boolean gameOver = true;
    Timer timer;
    int specialTime;
    private boolean moving;

    Board() {
        snake = new Snake();
        scoreBoard = new Scoreboard();
        specialTime = 0;

        setFocusable(true);
        initComponents();
    }

    private void initComponents() {
        this.setPreferredSize(new Dimension((NODE.getWidth() * COLS), (NODE.getHeight() * ROWS)));
    }// Windows size

    private void initSnake(int size) {
        snake = new Snake();//Restart Snake
        snake.addNode(0, (COLS / 2), (ROWS / 2));
        for (int i = 1; i < size; i++) {
            snake.addNode(i, (snake.getX(0) + i), snake.getY(0));
        }
    }

    public void finisGame()  {
        if (timer != null) {
            timer.stop();
        }

    }

    public void initGame(int deltaTime) {

        initSnake(5);
        generateFood();
        generateSpecialFood();


        direction = Direction.LEFT;
        addKeyListener(new MyKeyAdapter());
        gameOver = false;
        //
        if (timer != null) {
            timer.stop();
        }
        timer = new Timer(deltaTime, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!gameOver) {
                    tick();
                    moving = false;
                } else {
                    timer.stop();
                    finisGame();
                    try {
                        showHighScores();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }

                }
                repaint();
            }
        });
        timer.start();
    }

    private void tick() {
        //Food
        if (!haveFood) {
            generateFood();
        }
        //Special Food
        if (!haveSpecial) {
            int random = ThreadLocalRandom.current().nextInt(0, 50);//Random time for create Special Food
            if (random == 0) {
                generateSpecialFood();
            }
        } else {
            specialTime++;
        }
        if (specialTime == 40) {//deltaTimer * specialTime =  duration Special Food
            specialTime = 0;
            haveSpecial = false;
        }
        move(direction);
    }

    public boolean canMove(int x, int y) {
        boolean b = true;
        int headX = snake.getX(0) + x;
        int headY = snake.getY(0) + y;

        for (int i = 0; i < snake.getSize(); i++) {
            int bodyX = snake.getX(i);
            int bodyY = snake.getY(i);
            if (x != 0 || y != 0) {
                if (headX == bodyX && headY == bodyY) {
                    b = false;
                }
            }
        }
        return !b;
    }

    public void move(Direction d) {
        int x = snake.getX(0);
        int y = snake.getY(0);
        switch (d) {
            case UP:
                //Collision : With snake || With Board limit
                if (canMove(0, -1) || snake.getY(0) == 0) {
                    System.out.println("colision");
                    gameOver = true;
                } else if (eatFood(0, -1)) {
                    snake.setY(0, y - 1);
                    addBody(x, y);
                } else if (eatSpecialFood(0, -1)) {
                    snake.setY(0, y - 1);
                    addBody(x, y);
                    addBody(x, y);
                    addBody(x, y);
                } else {
                    snake.setY(0, y - 1);
                    setBody(x, y);
                }
                break;
            case DOWN:
                if (canMove(0, +1) || snake.getY(0) == ROWS - 1) {
                    System.out.println("colision");
                    gameOver = true;
                } else if (eatFood(0, +1)) {
                    snake.setY(0, y + 1);
                    addBody(x, y);
                } else if (eatSpecialFood(0, +1)) {
                    snake.setY(0, y + 1);
                    addBody(x, y);
                    addBody(x, y);
                    addBody(x, y);
                } else {
                    snake.setY(0, y + 1);
                    setBody(x, y);
                }
                break;
            case RIGHT:
                if (canMove(+1, 0) || x == COLS - 1) {
                    System.out.println("colision");
                    gameOver = true;
                } else if (eatFood(+1, 0)) {
                    snake.setX(0, x + 1);
                    addBody(x, y);
                } else if (eatSpecialFood(+1, 0)) {
                    snake.setX(0, x + 1);
                    addBody(x, y);
                    addBody(x, y);
                    addBody(x, y);
                } else {
                    snake.setX(0, x + 1);
                    setBody(x, y);
                }
                break;
            case LEFT:
                if (canMove(-1, 0) || x == 0) {
                    System.out.println("colision");
                    gameOver = true;
                } else if (eatFood(-1, 0)) {
                    snake.setX(0, x - 1);
                    addBody(x, y);
                } else if (eatSpecialFood(-1, 0)) {
                    snake.setX(0, x - 1);
                    addBody(x, y);
                    addBody(x, y);
                    addBody(x, y);
                } else {
                    snake.setX(0, x - 1);
                    setBody(x, y);
                }
        }
    }

    private void setBody(int x, int y) {
        snake.removeNode(snake.getSize() - 1);
        snake.addNode(1, x, y);
    } //Set the body in move method

    public void pauseTime() {
        if (!gameOver) {
            timer.stop();
            gameOver = true;
        } else {
            timer.start();
            gameOver = false;
        }
    }


    //Points

    private void generateFood() {
        haveFood = true;
        food = new Food();
        int randomX = 0;
        int randomY = 0;
        boolean b = false;
        int count = 0;

        while (!b) {
            randomX = ThreadLocalRandom.current().nextInt(0, COLS);
            randomY = ThreadLocalRandom.current().nextInt(0, ROWS);
            for (int i = 0; i < snake.getSize(); i++) {
                if (snake.getX(i) != randomX || snake.getY(i) != randomY) {
                    count++;
                }
            }
            if (count == snake.getSize()) {
                b = true;
            }
        }
        food.setX(randomX);
        food.setY(randomY);
    }

    private boolean eatFood(int x, int y) {
        boolean b = false;
        int headX = snake.getX(0) + x;
        int headY = snake.getY(0) + y;
        if (headX == food.getX() && headY == food.getY()) {
            haveFood = false;
            scoreBoard.incrementScore(food.getPoints());
            b = true;
        }
        return b;
    }

    private void generateSpecialFood() {

        haveSpecial = true;
        specialFood = new SpecialFood();
        int randomX = 0;
        int randomY = 0;
        boolean b = false;
        int count = 0;

        while (!b) {
            randomX = ThreadLocalRandom.current().nextInt(0, COLS);
            randomY = ThreadLocalRandom.current().nextInt(0, ROWS);
            for (int i = 0; i < snake.getSize(); i++) {
                if (snake.getX(i) != randomX || snake.getY(i) != randomY) {
                    count++;
                }
            }
            if (count == snake.getSize()) {
                b = true;
            }
        }
        specialFood.setX(randomX);
        specialFood.setY(randomY);

    }

    private boolean eatSpecialFood(int x, int y) {
        boolean b = false;
        int headX = snake.getX(0) + x;
        int headY = snake.getY(0) + y;
        if (headX == specialFood.getX() && headY == specialFood.getY()) {
            haveSpecial = false;
            scoreBoard.incrementScore(specialFood.getPoints());
            System.out.println(specialFood.getPoints());
            b = true;
        }
        return b;
    }

    private void addBody(int x, int y) {
        snake.addNode(1, x, y);
    }//Add when eat a Food

    public void setScoreBoard(Scoreboard s) {
        scoreBoard = s;
    }


    //Paint
    @Override
    public void paint(Graphics g) {
        paintBoard(g);
        if (snake.getSize() > 0) {
            paintSnake(g);
        }
        if (haveFood) {
            paintFood(g);
        }
        if (haveSpecial) {
            paintSpecialFood(g);
        }
    }

    public void paintBoard(Graphics g) {
        Color color = new Color(15, 15, 15);
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                paintNode(g, j, i, color);
            }
        }
    }

    public void paintSnake(Graphics g) {
        Color colorHead = new Color(0, 100, 140);
        Color colorBody = new Color(8, 131, 79);

        paintNode(g, snake.getX(0), snake.getY(0), colorHead);
        for (int i = 1; i < snake.getSize(); i++) {
            paintNode(g, snake.getX(i), snake.getY(i), colorBody);
        }
    }

    public void paintFood(Graphics g) {
        Color c = new Color(160, 10, 0);
        paintNode(g, food.getX(), food.getY(), c);
    }

    public void paintSpecialFood(Graphics g) {
        Color c = new Color(232, 215, 0);
        paintNode(g, specialFood.getX(), specialFood.getY(), c);
    }

    public void paintNode(Graphics g, int x, int y, Color color) {
        g.setColor(color);
        g.fillRect(x * NODE.getWidth(), y * NODE.getHeight(), NODE.getWidth(), NODE.getHeight());//Square
        g.setColor(color.brighter());
        g.drawRect(x * NODE.getWidth(), y * NODE.getHeight(), NODE.getWidth(), NODE.getHeight());//Border

    }

    //High Scores
    public void showHighScores() throws IOException {
        HighScores dialog = new HighScores("High Score");
        dialog.checkNewRecord(scoreBoard.getScore());
        dialog.printScores();
        dialog.setVisible(true);
    }

}