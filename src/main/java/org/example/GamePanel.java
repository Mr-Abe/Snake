package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener{

    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 25;
    static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE;
    static final int DELAY = 75;
    final int x[] = new int[GAME_UNITS];
    final int y[] = new int[GAME_UNITS];
    int bodyParts = 6;
    int applesEaten;
    int appleX;
    int appleY;
    char direction = 'R';
    boolean running = false;
    Timer timer;
    Random random;

    GamePanel(){
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        /*this.setFocusable(true); // so we can use the keyboard to control the snake
        this.requestFocus(); // this is to make sure that the panel is focused on*/
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }

    public void startGame(){

        newApple(); // generate a new apple
        running = true; // game is running
        timer = new Timer(DELAY, this); // this is the action listener
        timer.start(); // start the timer

    }

    public void paintComponent(Graphics g){
        super.paintComponent(g); // call the paintComponent method of the parent class

       /* g.setColor(Color.black); // set the color of the background
        g.fillRect(0,0,800,700); // draw the background*/

        draw(g); // draw the snake and the apple
        }

    public void draw(Graphics g){

        if(running){
            // draw the apple
            g.setColor(Color.red);
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

            // draw the snake
            for(int i = 0; i < bodyParts; i++){
                if(i == 0){
                    g.setColor(Color.green);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
                else{
                    g.setColor(new Color(45,180,0));
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }
            // draw the score
            g.setColor(Color.red);
            g.setFont(new Font("Ink Free", Font.BOLD, 40));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - metrics.stringWidth("Score: " + applesEaten))/2, g.getFont().getSize());
        }
        else{
            gameOver(g);
        }
    }

    public void newApple(){

        appleX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE; // generate a random number between 0 and 23
        appleY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE; // generate a random number between 0 and 23

    }

    public void move(){

        for (int i = bodyParts; i > 0; i--) { // move the body parts
            x[i] = x[i-1]; // move the body part to the position of the previous body part
            y[i] = y[i-1]; // move the body part to the position of the previous body part
        }

        switch (direction){ // move the snake
            case 'U': // up
                y[0] = y[0] - UNIT_SIZE;
                break;
            case 'D':  // down
                y[0] = y[0] + UNIT_SIZE;
                break;
            case 'L': // left
                x[0] = x[0] - UNIT_SIZE;
                break;
            case 'R': // right
                x[0] = x[0] + UNIT_SIZE;
                break;
        }
    }

    public void checkApple(){
        if((x[0] == appleX) && (y[0] == appleY)){ // if the snake head is on the apple
            bodyParts++; // increase the body parts
            applesEaten++; // increase the score
            newApple(); // generate a new apple
        }
    }

    public void checkCollisions(){

        for (int i =bodyParts; i > 0 ; i--) { // check if the head collides with the body
            if ((x[0] == x[i]) && (y[0] == y[i])){
                running = false;
            }
        }

        // check if the head touches the right border
        if(x[0] > SCREEN_WIDTH-UNIT_SIZE - 1) {
            running = false;
        }

        // check if the head touches the left border
        if(x[0] < 0) {
            running = false;
        }

        // check if the head touches the top border
        if(y[0] < 0) {
            running = false;
        }

        // check if the head touches the bottom border
        if(y[0] > SCREEN_HEIGHT-UNIT_SIZE - 1) {
            running = false;
        }

        if (!running) {
            timer.stop();
        }
    }

    public void gameOver(Graphics g){

            // draw the score
            g.setColor(Color.red);
            g.setFont(new Font("Ink Free", Font.BOLD, 40));
            FontMetrics metrics1 = getFontMetrics(g.getFont());
            g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - metrics1.stringWidth("Score: " + applesEaten))/2, g.getFont().getSize());

            // draw the game over text
            g.setColor(Color.red);
            g.setFont(new Font("Ink Free", Font.BOLD, 75));
            FontMetrics metrics2 = getFontMetrics(g.getFont());
            g.drawString("Game Over", (SCREEN_WIDTH - metrics2.stringWidth("Game Over"))/2, SCREEN_HEIGHT/2);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (running) {
            move();
            checkApple();
            checkCollisions();
        }
        repaint(); // call the paintComponent method again

    }

    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e){
            switch(e.getKeyCode()){
                case KeyEvent.VK_LEFT:
                    if(direction != 'R'){ // if the snake is not moving right
                        direction = 'L';  // move left
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if(direction != 'L'){ // if the snake is not moving left
                        direction = 'R'; // move right
                    }
                    break;
                case KeyEvent.VK_UP:
                    if(direction != 'D'){ // if the snake is not moving down
                        direction = 'U';  // move up
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if(direction != 'U'){ // if the snake is not moving up
                        direction = 'D'; // move down
                    }
                    break;
            }
        }
    }
}
