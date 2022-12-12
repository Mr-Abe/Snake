package org.example;

import javax.swing.*;

public class GameFrame extends JFrame {
    GameFrame(){
        this.add(new GamePanel());
        this.setTitle("Snake");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // close the game when we close the window
        this.setResizable(false); // we don't want to resize the window
        this.pack(); // resize the window to fit the components inside
        this.setVisible(true); // make the window visible
        this.setLocationRelativeTo(null); // make the window appear in the center of the screen
    }
}
