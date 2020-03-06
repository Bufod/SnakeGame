package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class GameField extends JPanel implements ActionListener {
    private final int SIZE = 320;
    private final int DOT_SIZE = 16;
    private final int ALL_DOTS = 400;
    private Image dot, apple;
    private int appleX, appleY;
    private int[] x = new int[ALL_DOTS];
    private int[] y = new int[ALL_DOTS];
    private int dots;
    private Timer timer;
    private boolean left = false,
            right = true,
            up = false,
            down = true;
    private boolean inGame = true;

    public GameField() {
        setBackground(Color.BLACK);
        loadImages();
        initGames();
//        addKeyListener();
        setFocusable(true);
    }

    private void initGames() {
        dots = 3;
        for (int i = 0; i < dots; i++) {
            x[i] = 48 - i*DOT_SIZE;
            y[i] = 48;
        }
        timer = new Timer(250, this);
        timer.start();
    }

    private void loadImages() {
        ImageIcon iia = new ImageIcon("apple.png");
        apple = iia.getImage();
        ImageIcon iid = new ImageIcon("dot.png");
        dot = iid.getImage();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (inGame){
            checkApple();
            checkCollision();
            move();
        }
        repaint();
    }

    private void move() {
        for (int i = dots; i > 0; i--){
            x[i] = x[i-1];
            y[i] = y[i-1];
        }
        if (left){
            x[0] -= DOT_SIZE;
        } else if (right) {
            x[0] += DOT_SIZE;
        } else if (up) {
            y[0] -= DOT_SIZE;
        } else {
            y[0] += DOT_SIZE;
        }
    }

    private void checkCollision() {
        for (int i = dots; i > 0; i--) {
            if (i > 4 && x[0] == x[i] && y[0] == y[i]) {
                inGame = false;
                break;
            }
        }

        if (x[0] > SIZE ||
                x[0] < 0 ||
                y[0] > SIZE ||
                y[0] < 0){
            inGame = false;
        }
    }

    private void checkApple() {
        if (x[0] == appleX && y[0] == appleY){
            dots++;
            createApple();
        }
    }

    private void createApple() {
        appleX = new Random().nextInt(20)*16;
        appleY = new Random().nextInt(20)*16;
    }
}
