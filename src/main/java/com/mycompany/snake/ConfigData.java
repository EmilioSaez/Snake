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
    public int deltaTimeDificulty; 
    
    private ConfigData() {
        userName = "";
        deltaTimeDificulty = 100;
        
    }
    
    public static ConfigData instance() {
        if (configData == null) {
            configData = new ConfigData();
        }
        return configData;
    }
}
