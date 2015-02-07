package com.barbaritalara.monopoly.model;

public class Ferrocarril extends Comprable {
    public int renta;

    public Ferrocarril(int id, String nombre, int precio) {
        super(id, nombre, precio);
        renta = 25;
    }

    public int getRenta(int cantidad) {
        return renta * cantidad;
    }


}
