/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.mycompany.snake;

import static com.mycompany.snake.Direction.DOWN;
import static com.mycompany.snake.Direction.LEFT;
import static com.mycompany.snake.Direction.RIGHT;
import static com.mycompany.snake.Direction.UP;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.DriverManager;
import javax.swing.Timer;

/**
 *
 * @author emisaerar
 */
public class Board extends javax.swing.JPanel implements DrawSquareInterface, InitGamer {

    @Override
    public void initGame() {
        initBoard();
    }

    class MyKeyAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if (snake.getDirection() == Direction.UP || snake.getDirection() == Direction.DOWN) {
                        snake.setDirection(Direction.LEFT);
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (snake.getDirection() == Direction.UP || snake.getDirection() == Direction.DOWN) {
                        snake.setDirection(Direction.RIGHT);
                    }
                    break;
                case KeyEvent.VK_UP:
                    if (snake.getDirection() == Direction.RIGHT || snake.getDirection() == Direction.LEFT) {
                        snake.setDirection(Direction.UP);
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (snake.getDirection() == Direction.RIGHT || snake.getDirection() == Direction.LEFT) {
                        snake.setDirection(Direction.DOWN);
                    }
                    break;
                default:
                    break;
            }
            repaint();
        }
    }
    private SpecialFood sFood;
    private Food food;
    private Snake snake;
    private Timer timer;
    private MyKeyAdapter keyAdapter;
    private GameOverInterface gameOverInterface;
    private static final int DELTA_TIME = 100; // Por ahora 500, luego en el menu se podra cambiar
    public static final int NUM_ROWS_COLS = 30;
    public static final int NUM_ROW_FOOD = 15;
    public static final int NUM_COL_FOOD = 20;

    /**
     * Creates new form Board
     */
    public Board() {
        initComponents();
        snake = new Snake(this);
        food = new Food(NUM_ROW_FOOD, NUM_COL_FOOD, this);
        keyAdapter = new MyKeyAdapter();
        addKeyListener(keyAdapter);
        timer = new Timer(DELTA_TIME, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                doGameLoop();
            }

        });
        initBoard();

    }

    private void initBoard() {
        snake = new Snake(this);
        food = new Food(NUM_ROW_FOOD, NUM_COL_FOOD, this);
        setFocusable(true);
        timer.start();
    }

    public void setGameOverInterface(GameOverInterface gameOverInterface) {
        this.gameOverInterface = gameOverInterface;
    }

    private void doGameLoop() {
        if (canMove(snake.getFirst().getRow(), snake.getFirst().getCol(), snake)) {
            snake.move();
            eatFood();
        } else {
            timer.stop();
            processGameOver();
        }
        repaint();
    }

    private void processGameOver() {
        timer.stop();
        gameOverInterface.setVisible(this);
    }

    private boolean canMove(int curretRow, int currentCol, Snake snake) {
        if (snake.getDirection() == Direction.RIGHT && snake.hitHisSelf() == false) {
            if (currentCol < NUM_ROWS_COLS) {
                return true;
            }
        }
        if (snake.getDirection() == Direction.LEFT && snake.hitHisSelf() == false) {
            if (currentCol > 0) {
                return true;
            }
        }
        if (snake.getDirection() == Direction.UP && snake.hitHisSelf() == false) {
            if (curretRow > 0) {
                return true;
            }
        }
        if (snake.getDirection() == Direction.DOWN && snake.hitHisSelf() == false) {
            if (curretRow < NUM_ROWS_COLS) {
                return true;
            }
        }
        return false;

    }
private void eatFood() {

    if (snake.getFirst().getRow() == food.getRow() && snake.getFirst().getCol() == food.getCol()) {
        snake.grow(food.increase());
        food.changeFoodPosition();
        System.out.println("Comida normal comida");


        if (sFood == null && (int) (Math.random() * 3) == 1) {
            sFood = new SpecialFood(0, 0, this);
            sFood.changeFoodPosition();
        }
    }


    if (sFood != null) {
        if (snake.getFirst().getRow() == sFood.getRow() && snake.getFirst().getCol() == sFood.getCol()) {
            snake.grow(sFood.increase());
            System.out.println("¡Soy especial!");
            sFood = null;
        }
    }
}
    private void paintBorderGame(Graphics g) {
        g.setColor(Color.black);
        int with = squareWidth() * NUM_ROWS_COLS;
        int height = squareHeight() * NUM_ROWS_COLS;
        g.drawRect(0, 0, with, height);
    }

    public void drawSquare(Graphics g, int row, int col, boolean isHead) {
        int x = col * squareWidth();
        int y = row * squareHeight();
        Color color = isHead ? new Color(204, 102, 102) : new Color(102, 102, 204);
        g.setColor(color);
        g.fillRect(x + 1, y + 1, squareWidth() - 2,
                squareHeight() - 2);
        g.setColor(color.brighter());
        g.drawLine(x, y + squareHeight() - 1, x, y);
        g.drawLine(x, y, x + squareWidth() - 1, y);
        g.setColor(color.darker());
        g.drawLine(x + 1, y + squareHeight() - 1,
                x + squareWidth() - 1, y + squareHeight() - 1);
        g.drawLine(x + squareWidth() - 1,
                y + squareHeight() - 1,
                x + squareWidth() - 1, y + 1);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        paintBorderGame(g);
        snake.paint(g);
        food.paint(g);
        if (sFood != null) {
            sFood.paint(g);
        }
        Toolkit.getDefaultToolkit().sync();
    }

    private int squareWidth() {
        return getWidth() / NUM_ROWS_COLS;
    }

    private int squareHeight() {
        return getHeight() / NUM_ROWS_COLS;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 380, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 377, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
