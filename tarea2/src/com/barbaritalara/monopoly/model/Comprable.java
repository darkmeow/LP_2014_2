package com.barbaritalara.monopoly.model;

public class Comprable extends Casilla {
    protected int precio;
    protected int id_dueno;

    public Comprable(int id, String nombre, int precio) {
        super(id, nombre);
        this.precio = precio;
        this.id_dueno = 0;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public int getId_dueno() {
        return id_dueno;
    }

    public void setId_dueno(int id_dueno) {
        this.id_dueno = id_dueno;
    }
}
