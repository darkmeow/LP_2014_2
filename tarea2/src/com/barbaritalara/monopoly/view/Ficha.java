package com.barbaritalara.monopoly.view;

import javax.swing.*;
import java.awt.*;


public class Ficha extends JComponent {
    protected int r;
    protected int x;
    protected int y;
    protected Color c;

    public Ficha(int x, int y, int r, Color c) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.c = c;
        setSize(new Dimension(r, r));
        setBounds(x, y, r, r);
        setLocation(x, y);
        setOpaque(true);
        setVisible(true);
    }

    public int getRadius() {
        return r;
    }

    public void setRadius(int r) {
        this.r = r;
    }

    public Color getColor() {
        return c;
    }

    public void setColor(Color c) {
        this.c = c;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(getColor());
        g.fillOval(0, 0, getRadius(), getRadius());
    }

    public void moverFicha(int id, int newcasilla) {
        switch(newcasilla) {
            case 0:
                x = Board.WIDTH - r * id - Board.CORNER_WIDTH + r*4;
                y = Board.HEIGHT - r - 1;
                break;
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
                x = (Board.WIDTH - r * id) - Board.HORIZONTAL_BOX_HEIGHT * newcasilla - Board.CORNER_WIDTH + r*4;
                y = Board.HEIGHT - r - 1;
                break;
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
                x = (Board.WIDTH - r * id + r*2) - Board.HORIZONTAL_BOX_HEIGHT * 11;
                y = (Board.HEIGHT + r) - Board.HORIZONTAL_BOX_HEIGHT * (newcasilla - 10) - Board.CORNER_HEIGHT;
                break;
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
                x = (r * id + r*2) + Board.HORIZONTAL_BOX_HEIGHT * (newcasilla - 20);
                y = (Board.HEIGHT + r) - Board.HORIZONTAL_BOX_HEIGHT * 10 - Board.CORNER_HEIGHT;
                break;
            case 31:
            case 32:
            case 33:
            case 34:
            case 35:
            case 36:
            case 37:
            case 38:
            case 39:
                x = (r * id + r*2) + Board.HORIZONTAL_BOX_HEIGHT * 10;
                y = r + r*2 + Board.HORIZONTAL_BOX_HEIGHT * (newcasilla - 30);
            break;
        }
        setBounds(x, y, r, r);
        setLocation(x, y);
    }
}
