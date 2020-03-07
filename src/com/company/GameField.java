package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class GameField extends JPanel implements ActionListener {
    private final int SIZE = 320;
    private final int DOT_SIZE = 16;
    private final int ALL_DOTS = 400;
    private JLabel endLabel = new JLabel("Game Over");
    private Image dot, apple;
    private int appleX, appleY;
    private int[] x = new int[ALL_DOTS];
    private int[] y = new int[ALL_DOTS];
    private int dots;
    private Timer timer;
    private int score = 0;
    private boolean left = false,
            right = true,
            up = false,
            down = false;
    private boolean inGame = true;

    public GameField() {
        setBackground(Color.BLACK);
        loadImages();
        initGames();
        addKeyListener(new FieldKeyListener());
        setFocusable(true);
    }

    private void initGames() {
        dots = 3;
        for (int i = 0; i < dots; i++) {
            x[i] = 48 - i * DOT_SIZE;
            y[i] = 48;
        }
        timer = new Timer(250, this);
        timer.start();
        createApple();
    }

    private void loadImages() {
        ImageIcon iia = new ImageIcon("apple.png");
        apple = iia.getImage();
        ImageIcon iid = new ImageIcon("dot.png");
        dot = iid.getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (inGame) {
            g.drawImage(apple, appleX, appleY, this);
            for (int i = 0; i < dots; i++) {
                g.drawImage(dot, x[i], y[i], this);
            }

            String strScore = "Score: " + score;
            int stringWidth = g.getFontMetrics().stringWidth(strScore),
                stringHeight = g.getFontMetrics().getHeight();
            g.setColor(Color.WHITE);
            g.drawString(strScore, this.getWidth() - stringWidth, stringHeight);
        } else {
            String end = "Game Over";
            int stringWidth = g.getFontMetrics().stringWidth(end);
            g.setColor(Color.WHITE);
            g.drawString(end, (this.getWidth() - stringWidth) / 2, SIZE / 2);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (inGame) {
            checkApple();
            checkCollision();
            move();
        }
        repaint();
    }

    private void move() {
        for (int i = dots; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        if (left) {
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

        if (x[0] > this.getWidth() ||
                x[0] < 0 ||
                y[0] > this.getHeight() ||
                y[0] < 0) {
            inGame = false;
        }
    }

    private void checkApple() {
        if (x[0] == appleX && y[0] == appleY) {
            dots++;
            score += 100;
            createApple();
        }
    }

    private void createApple() {
        appleX = new Random().nextInt(20) * 16;
        appleY = new Random().nextInt(20) * 16;
    }

    class FieldKeyListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_LEFT && !right) {
                left = true;
                up = false;
                down = false;
            }
            if (key == KeyEvent.VK_RIGHT && !left) {
                right = true;
                up = false;
                down = false;
            }
            if (key == KeyEvent.VK_UP && !down) {
                left = false;
                up = true;
                right = false;
            }
            if (key == KeyEvent.VK_DOWN && !up) {
                left = false;
                down = true;
                right = false;
            }
        }
    }
}
