/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.snake;

import com.mycompany.snake.Interfaces.DrawSquareInterface;
import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author emisaerar
 */
public class SpecialFood extends Food{
    private int increase;
    private int time;
    private DrawSquareInterface drawSquareInterface;
    
    public SpecialFood(int row, int col, DrawSquareInterface drawSquareInterface) {
        super(row, col, drawSquareInterface);
        this.drawSquareInterface = drawSquareInterface;
        increase = 3;
        time = 3;
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.yellow);
        drawSquareInterface.drawSquare(g, super.getRow(), super.getCol(), false);        
    }

    @Override
    public void changeFoodPosition() {
        super.changeFoodPosition(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    
    
    
    
}
