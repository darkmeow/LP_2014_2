package com.barbaritalara.monopoly.model;

import com.barbaritalara.monopoly.view.Board;

import java.awt.*;

public class Ciudad extends Comprable {
    protected Color color;
    protected int renta;
    protected int rentaCasa1;
    protected int rentaCasa2;
    protected int rentaCasa3;
    protected int rentaCasa4;
    protected int rentaHotel;
    protected int precioCasaHotel;
    protected int numcasas;
    protected boolean hotel;



    public Ciudad(int id, String nombre, Color color, int precio, int renta, int rentaCasa1,
                  int rentaCasa2, int rentaCasa3, int rentaCasa4, int rentaHotel, int precioCasaHotel,
                  int numcasas, boolean hotel) {
        super(id, nombre, precio);
        this.color = color;
        this.renta = renta;
        this.rentaCasa1 = rentaCasa1;
        this.rentaCasa2 = rentaCasa2;
        this.rentaCasa3 = rentaCasa3;
        this.rentaCasa4 = rentaCasa4;
        this.rentaHotel = rentaHotel;
        this.precioCasaHotel = precioCasaHotel;
        this.numcasas = numcasas;
        this.hotel = hotel;

    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getRenta() {
        return renta;
    }

    public void setRenta(int renta) {
        this.renta = renta;
    }

    public int getRentaCasa1() {
        return rentaCasa1;
    }

    public void setRentaCasa1(int rentaCasa1) {
        this.rentaCasa1 = rentaCasa1;
    }

    public int getRentaCasa2() {
        return rentaCasa2;
    }

    public void setRentaCasa2(int rentaCasa2) {
        this.rentaCasa2 = rentaCasa2;
    }

    public int getRentaCasa3() {
        return rentaCasa3;
    }

    public void setRentaCasa3(int rentaCasa3) {
        this.rentaCasa3 = rentaCasa3;
    }

    public int getRentaCasa4() {
        return rentaCasa4;
    }

    public void setRentaCasa4(int rentaCasa4) {
        this.rentaCasa4 = rentaCasa4;
    }

    public int getRentaHotel() {
        return rentaHotel;
    }

    public void setRentaHotel(int rentaHotel) {
        this.rentaHotel = rentaHotel;
    }

    public int getPrecioCasaHotel() {
        return precioCasaHotel;
    }

    public void setPrecioCasaHotel(int precioCasaHotel) {
        this.precioCasaHotel = precioCasaHotel;
    }

    public int getNumcasas() {
        return numcasas;
    }

    public void setNumcasas(int numcasas) {
        this.numcasas = numcasas;
    }

    public boolean getHotel() {
        return hotel;
    }

    public void setHotel(boolean hotel) {
        this.hotel = hotel;
    }



    public void drawColor(Graphics g, Board board, Casilla c) {
        Insets insets = board.getInsets();
        int x = insets.left;
        int y = insets.top;
        int offset = 20;
        switch(getId()) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
                g.setColor(((Ciudad) c).getColor());
                x += Board.WIDTH - Board.CORNER_WIDTH - (Board.HORIZONTAL_BOX_HEIGHT * getId());
                y += Board.HEIGHT - Board.CORNER_HEIGHT;
                g.fillRect(x, y, Board.HORIZONTAL_BOX_HEIGHT, offset);
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
                g.setColor(((Ciudad) c).getColor());
                x += Board.CORNER_WIDTH - offset;
                y += Board.HEIGHT - Board.CORNER_HEIGHT - Board.HORIZONTAL_BOX_HEIGHT  * (getId() % 10);
                g.fillRect(x, y, offset, Board.HORIZONTAL_BOX_HEIGHT);
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
                g.setColor(((Ciudad) c).getColor());
                x = Board.CORNER_WIDTH + Board.HORIZONTAL_BOX_HEIGHT * ((getId() - 1) % 10);
                y = Board.CORNER_HEIGHT - offset;
                g.fillRect(x, y, Board.HORIZONTAL_BOX_HEIGHT, offset);
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
                g.setColor(((Ciudad) c).getColor());
                x = Board.WIDTH - Board.CORNER_WIDTH;
                y = Board.CORNER_HEIGHT + Board.HORIZONTAL_BOX_HEIGHT  * ((getId() - 1) % 10);
                g.fillRect(x, y, offset, Board.HORIZONTAL_BOX_HEIGHT);
                break;
        }
    }
}
