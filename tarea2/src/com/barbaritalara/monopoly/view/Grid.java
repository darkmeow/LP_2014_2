package com.barbaritalara.monopoly.view;

import javax.swing.*;
import java.awt.*;

public class Grid extends JComponent {

    public Grid() {
        setSize(Board.WIDTH, Board.HEIGHT);
        setBounds(0, 0, Board.WIDTH, Board.HEIGHT);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // draw board grid
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLACK);

        // draw corners and monopoly lines
        g2d.drawLine(0, Board.CORNER_HEIGHT, Board.WIDTH, Board.CORNER_HEIGHT);
        g2d.drawLine(0, Board.HEIGHT - Board.CORNER_HEIGHT, Board.WIDTH, Board.HEIGHT - Board.CORNER_HEIGHT);
        g2d.drawLine(Board.CORNER_WIDTH, 0, Board.CORNER_WIDTH, Board.HEIGHT);
        g2d.drawLine(Board.WIDTH - Board.CORNER_WIDTH, 0, Board.WIDTH - Board.CORNER_WIDTH, Board.HEIGHT);

        // draw boxes between corners
        for(int i = 1; i < 9; i++) {
            g2d.drawLine(0, Board.CORNER_HEIGHT + Board.HORIZONTAL_BOX_HEIGHT * i, Board.CORNER_WIDTH, Board.CORNER_HEIGHT + Board.HORIZONTAL_BOX_HEIGHT * i); // horizontal left
            g2d.drawLine(Board.WIDTH - Board.CORNER_WIDTH, Board.CORNER_HEIGHT + Board.HORIZONTAL_BOX_HEIGHT * i, Board.WIDTH, Board.CORNER_HEIGHT + Board.HORIZONTAL_BOX_HEIGHT * i); // horizontal right
            g2d.drawLine(Board.CORNER_WIDTH + Board.HORIZONTAL_BOX_HEIGHT * i, 0, Board.CORNER_WIDTH + Board.HORIZONTAL_BOX_HEIGHT * i, Board.CORNER_HEIGHT); // vertical top
            g2d.drawLine(Board.CORNER_WIDTH + Board.HORIZONTAL_BOX_HEIGHT * i, Board.HEIGHT - Board.CORNER_HEIGHT, Board.CORNER_WIDTH + Board.HORIZONTAL_BOX_HEIGHT * i, Board.HEIGHT); // vertical bottom
        }
    }
}
