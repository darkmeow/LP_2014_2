package com.barbaritalara.monopoly.model;

public class Servicio extends Comprable {


    public Servicio(int id, String nombre, int precio) {

        super(id, nombre, precio);
    }

    public int getRenta1(int dados) {
        return (dados) * 4;
    }

    public int getRenta2(int dados) {
        return (dados) * 10;
    }

}
