/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.snake;

import static com.mycompany.snake.Direction.DOWN;
import static com.mycompany.snake.Direction.LEFT;
import static com.mycompany.snake.Direction.RIGHT;
import static com.mycompany.snake.Direction.UP;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author emisaerar
 */
public class Snake {

    private List<Node> snakeBody;
    private Direction direction;
    private DrawSquareInterface drawSquareInterface;
    private int nodesToGrow;

    public Snake(DrawSquareInterface drawSquareInterface) {
        Node n = new Node(15, 15);
        this.drawSquareInterface = drawSquareInterface;
        snakeBody = new ArrayList<Node>();
        int row = Board.NUM_ROWS_COLS / 2;
        int col = Board.NUM_ROWS_COLS / 2;
        for (int i = 0; i < 4; i++) {
            Node node = new Node(row, col - i);
            snakeBody.add(i,node);
        }
        nodesToGrow = 0;
        snakeBody.add(n);
        direction = Direction.RIGHT;
    }

    public Node getFirst() {
        return snakeBody.get(0);
    }

    public Node getLast() {
        return snakeBody.get(snakeBody.size() - 1);
    }

    public Direction getDirection() {
        return direction;
    }

    public List<Node> getSnakeBody() {
        return snakeBody;
    }

    public void setDirection(Direction d) {
        direction = d;

    }

    public void move() {
        Node node;
        switch (direction) {
            case UP:
                node = new Node(this.getFirst().getRow() - 1, this.getFirst().getCol());
                snakeBody.add(0, node);
                snakeBody.remove(snakeBody.size() - 1);
                break;
            case RIGHT:
                node = new Node(this.getFirst().getRow(), this.getFirst().getCol() + 1);
                snakeBody.add(0, node);
                snakeBody.remove(snakeBody.size() - 1);
                break;
            case LEFT:
                node = new Node(this.getFirst().getRow(), this.getFirst().getCol() - 1);
                snakeBody.add(0, node);
                snakeBody.remove(snakeBody.size() - 1);
                break;
            case DOWN:
                node = new Node(this.getFirst().getRow() + 1, this.getFirst().getCol());
                snakeBody.add(0, node);
                snakeBody.remove(snakeBody.size() - 1);
                break;
        }
    }

    public void grow(int increment) {
        Node node;
        for (int i = 0; i < increment; i++) {
            switch (direction) {
                case UP:
                    node = new Node(this.getLast().getRow() + 1, this.getLast().getCol());
                    snakeBody.add(snakeBody.size() - 1, node);
                    break;
                case RIGHT:
                    node = new Node(this.getLast().getRow(), this.getLast().getCol() - 1);
                    snakeBody.add(snakeBody.size() - 1, node);
                    break;
                case LEFT:
                    node = new Node(this.getLast().getRow(), this.getLast().getCol() + 1);
                    snakeBody.add(snakeBody.size() - 1, node);
                    break;
                case DOWN:
                    node = new Node(this.getLast().getRow() - 1, this.getLast().getCol());
                    snakeBody.add(snakeBody.size() - 1, node);
                    break;
            }
        }
    }

    public void paint(Graphics g) {
        boolean first = true;
        for (Node node : snakeBody) {
            drawSquareInterface.drawSquare(g, node.getRow(), node.getCol(), first);
            if (first) {
                first = false;
            }

        }

    }

    public boolean hitHisSelf() {
        boolean first = true;
        for (Node node : snakeBody) {
            if (first) {
                first = false;
            } else {
                if (this.getFirst().getRow() == node.getRow() && this.getFirst().getCol() == node.getCol()) {
                    return true;
                }

            }
        }
        return false;
    }

}
