/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package com.mycompany.snake;

import com.mycompany.snake.Interfaces.InitGamer;
import com.mycompany.snake.Interfaces.GameOverInterface;
import com.mycompany.snake.Interfaces.MusicInterface;
import java.awt.Component;
import com.mycompany.snake.Interfaces.RestartAplicationInteface;
import com.mycompany.snake.Interfaces.VisibilityInterface;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.WindowStateListener;
import java.beans.Visibility;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author emisaerar
 */
public class GameOverDialog extends javax.swing.JDialog implements GameOverInterface, MusicInterface {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(GameOverDialog.class.getName());
    private InitGamer initGamer;
    private BufferedReader input;
    private RestartAplicationInteface restartAplicationInteface;
    private VisibilityInterface visibilityInterface;
    private String menuSongRute = "GameOverMenuSong.wav";
    private Clip gameOverMusic;

    /**
     * Creates new form GameOverDialog
     */
    public GameOverDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);

        input = null;
        initComponents();

    }

    private void setRecords() {
        String lastLine = "";
        String bestLine = "";
        int maxPuntuations = -1;
        String puntosString = "";
        if (ConfigData.instance().multiplayer == false) {
            try {
                input = new BufferedReader(new FileReader("Records"));
                String line;
                while ((line = input.readLine()) != null) {
                    puntosString = line.split(":")[1].trim().split(" ")[0];

                    if (maxPuntuations < Integer.parseInt(puntosString)) {
                        maxPuntuations = Integer.parseInt(puntosString);
                        bestLine = line;
                    }
                    lastLine = line;

                }
                jrecord.setText(bestLine);
                jpuntuation.setText(lastLine);

            } catch (Exception e) {
                System.err.println(e);
            } finally {
                if (input != null) {
                    try {
                        input.close();
                    } catch (IOException ex) {
                        System.getLogger(GameOverDialog.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
                    }

                }
            }
        } else {
            try {
                input = new BufferedReader(new FileReader("TwoPlayerRecords"));
                String line;
                while ((line = input.readLine()) != null) {
                    puntosString = line.split(":")[1].trim().split(" ")[0];

                    if (maxPuntuations < Integer.parseInt(puntosString)) {
                        maxPuntuations = Integer.parseInt(puntosString);
                        bestLine = line;
                    }
                    lastLine = line;

                }
                jrecord.setText(bestLine);
                jpuntuation.setText(lastLine);

            } catch (Exception e) {
                System.err.println(e);
            } finally {
                if (input != null) {
                    try {
                        input.close();
                    } catch (IOException ex) {
                        System.getLogger(GameOverDialog.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
                    }

                }
            }

        }

    }

    public GameOverDialog() {
        super();
        initComponents();
        this.setLocationRelativeTo(null);
        this.setLocationRelativeTo(null);
        this.getContentPane().setBackground(new java.awt.Color(89, 65, 45));
        jLabel1.setForeground(new java.awt.Color(231, 76, 60));
        java.awt.Color crema = new java.awt.Color(255, 245, 230);
        jLabel2.setForeground(crema);
        jLabel3.setForeground(crema);
        jrecord.setForeground(crema);
        jpuntuation.setForeground(crema);
        jButton1.setBackground(new java.awt.Color(219, 181, 153));
        jButton1.setForeground(java.awt.Color.BLACK);
        jButton2.setBackground(new java.awt.Color(120, 90, 70));
        jButton2.setForeground(java.awt.Color.WHITE);
        jButton3.setBackground(new java.awt.Color(160, 130, 100));
        jButton3.setForeground(java.awt.Color.WHITE);
        jSeparator1.setForeground(new java.awt.Color(219, 181, 153));

    }

    public void setInitGamer(InitGamer initGamer) {
        this.initGamer = initGamer;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jpuntuation = new javax.swing.JLabel();
        jrecord = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem4 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMinimumSize(null);

        jLabel1.setFont(new java.awt.Font("Liberation Sans", 0, 36)); // NOI18N
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("properties/Bundle_es_ES"); // NOI18N
        jLabel1.setText(bundle.getString("You Died")); // NOI18N

        jButton1.setText(bundle.getString("New Game")); // NOI18N
        jButton1.addActionListener(this::jButton1ActionPerformed);

        jButton2.setText(bundle.getString("Exit")); // NOI18N
        jButton2.addActionListener(this::jButton2ActionPerformed);

        jButton3.setText(bundle.getString("Settings")); // NOI18N
        jButton3.addActionListener(this::jButton3ActionPerformed);

        jpuntuation.setText(bundle.getString("GameOverDialog.jpuntuation.text")); // NOI18N

        jrecord.setText(bundle.getString("GameOverDialog.jrecord.text")); // NOI18N

        jLabel2.setText(bundle.getString("Record")); // NOI18N

        jLabel3.setText(bundle.getString("Your Puntuation")); // NOI18N

        jMenu2.setText(bundle.getString("GameOverDialog.jMenu2.text")); // NOI18N

        jMenuItem4.setText(bundle.getString("GameOverDialog.jMenuItem4.text")); // NOI18N
        jMenuItem4.addActionListener(this::jMenuItem4ActionPerformed);
        jMenu2.add(jMenuItem4);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(78, 78, 78)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButton1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton2))
                            .addComponent(jLabel3)
                            .addComponent(jpuntuation, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jSeparator1)
                            .addComponent(jrecord, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(118, 118, 118)
                        .addComponent(jButton3)))
                .addContainerGap(120, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jrecord, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jpuntuation, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addGap(18, 18, 18)
                .addComponent(jButton3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        setVisible(false);
        stopMusic();
        initGamer.initGame();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        stopMusic();
        ConfigData.instance().multiplayer = false;
        restartAplicationInteface.resetAll();
        visibilityInterface.changeVisibility();
        setVisible(false);

    }//GEN-LAST:event_jButton3ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        javax.swing.JOptionPane.showMessageDialog(this, "Más informacion aqui : https://emiliosaez.github.io/Css_Clase/ ");
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                GameOverDialog dialog = new GameOverDialog(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    @Override
    public void setVisible(Component component) {
        setLocationRelativeTo(component);
        setVisible(true);

        try {
            startMusic(menuSongRute); // https://www.youtube.com/watch?v=n14r9Tjx0z4
        } catch (Exception e) {
            logger.log(java.util.logging.Level.SEVERE, "No se pudo reproducir la música", e);
        }
        setRecords();

    }

    public void setVisibilityInterface(VisibilityInterface vi) {
        this.visibilityInterface = vi;
    }

    public void setRestartAplicationInterface(RestartAplicationInteface rai) {
        this.restartAplicationInteface = rai;
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel jpuntuation;
    private javax.swing.JLabel jrecord;
    // End of variables declaration//GEN-END:variables

    @Override
    public void startMusic(String song) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        File file = new File(song);
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
        gameOverMusic = AudioSystem.getClip();
        gameOverMusic.open(audioStream);
        gameOverMusic.loop((Clip.LOOP_CONTINUOUSLY));
        gameOverMusic.start();
    }

    @Override
    public void stopMusic() {
        gameOverMusic.stop();
    }

}
