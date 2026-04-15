/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.mycompany.snake;

import com.mycompany.snake.Interfaces.DrawSquareInterface;
import com.mycompany.snake.Interfaces.Incrementer;
import com.mycompany.snake.Interfaces.InitGamer;
import com.mycompany.snake.Interfaces.GameOverInterface;
import static com.mycompany.snake.Direction.DOWN;
import static com.mycompany.snake.Direction.LEFT;
import static com.mycompany.snake.Direction.RIGHT;
import static com.mycompany.snake.Direction.UP;
import com.mycompany.snake.Interfaces.MusicInterface;
import com.mycompany.snake.Interfaces.VisibilityInterface;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.Timer;

/**
 *
 * @author emisaerar
 */
public class Board extends javax.swing.JPanel implements DrawSquareInterface, InitGamer, VisibilityInterface, MusicInterface {

    @Override
    public void initGame() {
        initBoard();
        requestFocus();

    }

    public void setIncrementer(Incrementer incrementer) {
        this.incrementer = incrementer;
    }

    @Override
    public void changeVisibility() {
        setVisible(false);
    }

    @Override
    public void startMusic(String song) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        File file = new File(song);
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
        boardSong = AudioSystem.getClip();
        boardSong.open(audioStream);
        boardSong.loop((Clip.LOOP_CONTINUOUSLY));
        boardSong.start();
    }

    @Override
    public void stopMusic() {
        boardSong.stop();
    }

    private Clip boardSong;
    private String boardSongRute;
    private PrintWriter outputStream;
    private Incrementer incrementer;
    private int counter;
    private SpecialFood sFood;
    private Food food;
    private Snake snakeOne;
    private Snake snakeTwo;
    private Timer timer;
    private boolean canChangeOne = true;
    private boolean canChangeTwo = true;
    private MyKeyAdapter keyAdapter;
    private GameOverInterface gameOverInterface;
    public static final int NUM_ROWS_COLS = 30;
    public static final int NUM_ROW_FOOD = 15;
    public static final int NUM_COL_FOOD = 20;

    /**
     * Creates new form Board
     */
    public Board() {
        boardSongRute = "GameSong.wav";
        outputStream = null;
        initComponents();

        counter = 0;
        snakeOne = new Snake(this);
        if (ConfigData.instance().multiplayer) {
            snakeTwo = new Snake(this);
            snakeTwo.secondSnake();
        }
        food = new Food(NUM_ROW_FOOD, NUM_COL_FOOD, this);
        keyAdapter = new MyKeyAdapter();
        addKeyListener(keyAdapter);
        timer = new Timer(ConfigData.instance().deltaTimeDificulty, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                doGameLoop();
            }

        });

    }

    private void initBoard() {
        try {
            startMusic(boardSongRute); // https://www.youtube.com/watch?v=n14r9Tjx0z4
        } catch (Exception e) {

        }
        snakeOne = new Snake(this);
        snakeOne.setSnakeColor(Color.BLUE); // Serpiente 1 Azul

        if (ConfigData.instance().multiplayer) {
            snakeTwo = new Snake(this);
            snakeTwo.secondSnake();
            snakeTwo.setSnakeColor(Color.GREEN); // Serpiente 2 Verde
        }

        incrementer.reset();
        food = new Food(NUM_ROW_FOOD, NUM_COL_FOOD, this);
        setFocusable(true);
        timer.setDelay(ConfigData.instance().deltaTimeDificulty);
        timer.start();

    }

    public void setGameOverInterface(GameOverInterface gameOverInterface) {
        this.gameOverInterface = gameOverInterface;
    }

    private int count(int cont) {
        return cont + 1;
    }

    private void doGameLoop() {
        if (ConfigData.instance().multiplayer == false) {
            if (canMove(snakeOne.getFirst().getRow(), snakeOne.getFirst().getCol(), snakeOne)) {
                snakeOne.move();
                keyAdapter.setCanChange(true);
                if (sFood != null) {
                    counter++;
                }
                if (counter == 40) {
                    disapear();
                    counter = 0;
                }
                eatFood(snakeOne);

            } else {
                timer.stop();
                processGameOver();
            }
            canChangeOne = true;

        } else {
            if (canMove(snakeOne.getFirst().getRow(), snakeOne.getFirst().getCol(), snakeOne)
                    && canMove(snakeTwo.getFirst().getRow(), snakeTwo.getFirst().getCol(), snakeTwo)
                    && snakeOne.hitOtherSnake(snakeTwo) == false) {
                snakeOne.move();
                snakeTwo.move();
                keyAdapter.setCanChange(true);
                if (sFood != null) {
                    counter++;
                }
                if (counter == 40) {
                    disapear();
                    counter = 0;
                }
                eatFood(snakeOne);
                eatFood(snakeTwo);

            } else {
                timer.stop();
                processGameOver();
            }
            canChangeTwo = true;

        }

        repaint();
    }

    private void processGameOver() {
        String dificulty = "";
        if (ConfigData.instance().deltaTimeDificulty == 40) {
            dificulty = "hard";

        } else if (ConfigData.instance().deltaTimeDificulty == 60) {
            dificulty = "medium";

        } else {
            dificulty = "easy";
        }
        timer.stop();
        if (ConfigData.instance().multiplayer == false) {
            try {
                outputStream = new PrintWriter(new FileWriter("Records", true));
                outputStream.println(ConfigData.instance().userName + ":" + incrementer.returnScore() + " " + dificulty);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } else {
            try {
                outputStream = new PrintWriter(new FileWriter("TwoPlayerRecords", true));
                outputStream.println(ConfigData.instance().userName + "+" + ConfigData.instance().secondUserName + ":" + incrementer.returnScore() + " " + dificulty);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                if (outputStream != null) {
                    outputStream.close();
                }
            }

        }

        gameOverInterface.setVisible(this);

        stopMusic();
    }

    private boolean canMove(int curretRow, int currentCol, Snake snake) {
        if (snake.getDirection() == Direction.RIGHT && snake.hitHisSelf() == false) {
            if (currentCol < NUM_ROWS_COLS - 1) {
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
            if (curretRow < NUM_ROWS_COLS - 1) {
                return true;
            }
        }
        return false;

    }

    public void disapear() {
        if (sFood != null) {
            sFood = null;

        }

    }

    private void eatFood(Snake snake) {
        if (snake.getFirst().getRow() == food.getRow() && snake.getFirst().getCol() == food.getCol()) {
            snakeOne.grow(food.increase());
            incrementer.incrementScore(1);
            food.changeFoodPosition();
            while (snakeOne.isTheSnakeHere(food)) {
                food.changeFoodPosition();
            }
            System.out.println("Comida normal comida");

            if (sFood == null && (int) (Math.random() * 3) == 1) {
                sFood = new SpecialFood(0, 0, this);
                sFood.changeFoodPosition();
                while (snake.isTheSnakeHere(sFood)) {
                    sFood.changeFoodPosition();
                }
            }
        }

        if (sFood != null) {
            if (snake.getFirst().getRow() == sFood.getRow() && snake.getFirst().getCol() == sFood.getCol()) {
                snake.grow(sFood.increase());
                incrementer.incrementScore(3);
                counter = 0;
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

    @Override
    public void drawSquare(Graphics g, int row, int col, Color color, boolean isHead) {
        if (color == null) {
            color = Color.GREEN;
        }

        int x = col * squareWidth();
        int y = row * squareHeight();

        Color finalColor = isHead ? color.brighter() : color;

        g.setColor(finalColor);
        g.fillRect(x + 1, y + 1, squareWidth() - 2, squareHeight() - 2);

        g.setColor(finalColor.brighter());
        g.drawLine(x, y + squareHeight() - 1, x, y);
        g.drawLine(x, y, x + squareWidth() - 1, y);

        g.setColor(finalColor.darker());
        g.drawLine(x + 1, y + squareHeight() - 1, x + squareWidth() - 1, y + squareHeight() - 1);
        g.drawLine(x + squareWidth() - 1, y + squareHeight() - 1, x + squareWidth() - 1, y + 1);
    }
    private void paintBackground(Graphics g) {
        for (int i = 0; i < NUM_ROWS_COLS; i++) {
            for (int j = 0; j < NUM_ROWS_COLS; j++) {
                if ((i + j) % 2 == 0) {
                    g.setColor(new Color(237, 201, 175));
                    
                } else {
                    g.setColor(new Color(219, 181, 153));
                }
                int x = j * squareWidth();
                int y = i * squareHeight();
                
                g.fillRect(x, y, squareWidth(), squareHeight());
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        paintBorderGame(g);
        paintBackground(g);
        snakeOne.paint(g);
        if (ConfigData.instance().multiplayer) {
            snakeTwo.paint(g);
        }
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

    class MyKeyAdapter extends KeyAdapter {

        public MyKeyAdapter() {
            canChangeOne = true;
            if (ConfigData.instance().multiplayer) {
                canChangeTwo = true;

            }
        }

        public void setCanChange(boolean canChange) {
            canChangeOne = canChange;
        }

        @Override
        public void keyPressed(KeyEvent e) {

            if (ConfigData.instance().multiplayer == false) {

                if (canChangeOne) {

                    switch (e.getKeyCode()) {
                        case KeyEvent.VK_LEFT:
                            if (snakeOne.getDirection() == Direction.UP || snakeOne.getDirection() == Direction.DOWN) {
                                snakeOne.setDirection(Direction.LEFT);
                                canChangeOne = false;

                            }
                            break;
                        case KeyEvent.VK_RIGHT:
                            if (snakeOne.getDirection() == Direction.UP || snakeOne.getDirection() == Direction.DOWN) {
                                snakeOne.setDirection(Direction.RIGHT);
                                canChangeOne = false;

                            }
                            break;
                        case KeyEvent.VK_UP:
                            if (snakeOne.getDirection() == Direction.RIGHT || snakeOne.getDirection() == Direction.LEFT) {
                                snakeOne.setDirection(Direction.UP);
                                canChangeOne = false;

                            }
                            break;
                        case KeyEvent.VK_DOWN:
                            if (snakeOne.getDirection() == Direction.RIGHT || snakeOne.getDirection() == Direction.LEFT) {
                                snakeOne.setDirection(Direction.DOWN);
                                canChangeOne = false;

                            }
                            break;
                        default:
                            break;
                    }
                }
            } else {
                if (canChangeOne && canChangeTwo) {

                    switch (e.getKeyCode()) {
                        case KeyEvent.VK_LEFT:
                            if (snakeOne.getDirection() == Direction.UP || snakeOne.getDirection() == Direction.DOWN) {
                                snakeOne.setDirection(Direction.LEFT);
                                canChangeOne = false;

                            }
                            break;
                        case KeyEvent.VK_RIGHT:
                            if (snakeOne.getDirection() == Direction.UP || snakeOne.getDirection() == Direction.DOWN) {
                                snakeOne.setDirection(Direction.RIGHT);
                                canChangeOne = false;

                            }
                            break;
                        case KeyEvent.VK_UP:
                            if (snakeOne.getDirection() == Direction.RIGHT || snakeOne.getDirection() == Direction.LEFT) {
                                snakeOne.setDirection(Direction.UP);
                                canChangeOne = false;

                            }
                            break;
                        case KeyEvent.VK_DOWN:
                            if (snakeOne.getDirection() == Direction.RIGHT || snakeOne.getDirection() == Direction.LEFT) {
                                snakeOne.setDirection(Direction.DOWN);
                                canChangeOne = false;

                            }
                            break;
                        case KeyEvent.VK_A:
                            if (snakeTwo.getDirection() == Direction.UP || snakeTwo.getDirection() == Direction.DOWN) {
                                snakeTwo.setDirection(Direction.LEFT);
                                canChangeTwo = false;

                            }
                            break;
                        case KeyEvent.VK_D:
                            if (snakeTwo.getDirection() == Direction.UP || snakeTwo.getDirection() == Direction.DOWN) {
                                snakeTwo.setDirection(Direction.RIGHT);
                                canChangeTwo = false;

                            }
                            break;
                        case KeyEvent.VK_W:
                            if (snakeTwo.getDirection() == Direction.RIGHT || snakeTwo.getDirection() == Direction.LEFT) {
                                snakeTwo.setDirection(Direction.UP);
                                canChangeTwo = false;

                            }
                            break;
                        case KeyEvent.VK_S:
                            if (snakeTwo.getDirection() == Direction.RIGHT || snakeTwo.getDirection() == Direction.LEFT) {
                                snakeTwo.setDirection(Direction.DOWN);
                                canChangeTwo = false;

                            }
                        default:
                            break;
                    }
                }

            }
            repaint();
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
