import java.util.ArrayList;

public class Snake extends Node {
    private final ArrayList<Node> snake;

    public Snake() {
        this.snake = new ArrayList<Node>();
    }

    public void addNode(int position, int x, int y) {
        Node n = new Node();
        snake.add(position, n);
        setX(position, x);
        setY(position, y);
    }

    public void removeNode(int n) {
        snake.remove(n);
    }

    public int getX(int n) {
        return snake.get(n).getX();
    }

    public int getY(int n) {
        return snake.get(n).getY();
    }

    public void setX(int position, int X) {
        snake.get(position).setX(X);
    }

    public void setY(int position, int Y) {
        snake.get(position).setY(Y);
    }

    public int getSize() {
        return snake.size();
    }


}
