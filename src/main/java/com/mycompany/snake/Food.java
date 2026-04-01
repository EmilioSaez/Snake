/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.snake;

import com.mycompany.snake.Interfaces.DrawSquareInterface;
import java.awt.Graphics;

/**
 *
 * @author emisaerar
 */
public class Food extends Node {

    private int increase;
    private DrawSquareInterface drawSquareInterface;

    public Food(int row, int col, DrawSquareInterface drawSquareInterface) {
        super(row, col);
        increase = 1;
        this.drawSquareInterface = drawSquareInterface;

    }

    public int increase() {
        return increase;
    }

    public void changeFoodPosition() {
        this.setRow(Food.returnRandomRow());
        this.setCol(Food.returnRandomCol());

    }

    public void paint(Graphics g) {
        drawSquareInterface.drawSquare(g, super.getRow(), super.getCol(), true);
    }

    public static int returnRandomRow() {
        return (int) (Math.random() * Board.NUM_ROWS_COLS);
    }

    public static int returnRandomCol() {
        return (int) (Math.random() * Board.NUM_ROWS_COLS);
    }

}
