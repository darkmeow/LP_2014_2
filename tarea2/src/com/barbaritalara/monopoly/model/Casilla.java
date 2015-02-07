package com.barbaritalara.monopoly.model;

import com.barbaritalara.monopoly.view.Board;

import javax.swing.*;
import java.awt.*;

public class Casilla {
    protected int id; //posicion
    protected String nombre;

    public Casilla() {
    }

    public Casilla(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String name) {
        this.nombre = name;
    }

    public void drawLabel(Board board) {
        Insets insets = board.getInsets();
        JLabel label = new JLabel(getNombre());
        label.setFont(new Font("Arial", Font.PLAIN, 10));
        Dimension size = label.getPreferredSize();
        label.setPreferredSize(size);
        int x = insets.left;
        int y = insets.top;
        switch(getId()) {
            case 0:
                x += Board.WIDTH - (size.width / 2 + Board.CORNER_WIDTH / 2);
                y += Board.HEIGHT - Board.CORNER_HEIGHT / 2;
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
                x += Board.WIDTH - Board.CORNER_WIDTH  - Board.HORIZONTAL_BOX_HEIGHT * getId() + (Board.HORIZONTAL_BOX_HEIGHT - size.width) / 2;
                y += Board.HEIGHT - Board.CORNER_HEIGHT / 2;
                break;
            case 10:
                x += Board.WIDTH - Board.CORNER_WIDTH  - Board.HORIZONTAL_BOX_HEIGHT * getId();
                y += Board.HEIGHT - Board.CORNER_HEIGHT / 2;
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
                //x += (Board.HORIZONTAL_BOX_HEIGHT / 2)  - Board.HOR
                // IZONTAL_BOX_HEIGHT * getId();
                x += (Board.HORIZONTAL_BOX_HEIGHT - size.width) / 2 + (Board.HORIZONTAL_BOX_HEIGHT/4) ;
                y += Board.HEIGHT - Board.CORNER_HEIGHT - Board.HORIZONTAL_BOX_HEIGHT * (getId() % 10) + (Board.HORIZONTAL_BOX_HEIGHT  - size.height) / 2;
                break;
            case 20:
                x += (Board.HORIZONTAL_BOX_HEIGHT - size.width) / 2 + (Board.HORIZONTAL_BOX_HEIGHT/4) ;
                y += (Board.HORIZONTAL_BOX_HEIGHT  - size.height) / 2;
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
                x = Board.CORNER_WIDTH + Board.HORIZONTAL_BOX_HEIGHT * ((getId() - 1) % 10) + (Board.HORIZONTAL_BOX_HEIGHT/2  - size.width/2);
                y += (Board.HORIZONTAL_BOX_HEIGHT  - size.height) / 2;
                break;
            case 30:
                x += Board.WIDTH - Board.CORNER_WIDTH + (Board.HORIZONTAL_BOX_HEIGHT/4);
                y =  Board.CORNER_HEIGHT / 2 - Board.HORIZONTAL_BOX_HEIGHT * (getId() % 10) - (Board.HORIZONTAL_BOX_HEIGHT/2  - size.height/2);
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
                x += Board.WIDTH - Board.CORNER_WIDTH + (Board.HORIZONTAL_BOX_HEIGHT/4) + size.width/4;
                y =  Board.CORNER_HEIGHT / 2 + Board.HORIZONTAL_BOX_HEIGHT * (getId() % 10);
        }
        label.setBounds(x, y, size.width, size.height);

        board.add(label);
    }



}
