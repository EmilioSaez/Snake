/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.snake.Interfaces;

import java.io.IOException;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author emili_d3xx9ov
 */
public interface MusicInterface{
    public void startMusic(String song) throws UnsupportedAudioFileException, IOException, LineUnavailableException;
    public void stopMusic();
}
