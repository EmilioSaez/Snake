/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.snake;

/**
 *
 * @author emisaerar
 */
public class ConfigData {
    private static ConfigData configData = null;
    public String userName;
    public String secondUserName;
    public int deltaTimeDificulty; 
    public boolean multiplayer;
    
    private ConfigData() {
        userName = "";
        secondUserName = "";
        deltaTimeDificulty = 100;
        multiplayer = false;
    }
    
    public static ConfigData instance() {
        if (configData == null) {
            configData = new ConfigData();
        }
        return configData;
    }
}
