package com.barbaritalara.monopoly.model;

import java.util.ArrayList;

public class Banco {

    protected int numcasas;
    protected int numhoteles;


    public Banco( int numcasas, int numhoteles) {


        this.numcasas = numcasas;
        this.numhoteles = numhoteles;

    }

    public int getNumcasas() {
        return numcasas;
    }

    public void setNumcasas(int numcasas) {
        this.numcasas = numcasas;
    }

    public int getNumhoteles() {
        return numhoteles;
    }

    public void setNumhoteles(int numhoteles) {
        this.numhoteles = numhoteles;
    }

    public boolean tieneCasas() {

        return(getNumcasas() > 0);

    }

    public boolean tieneHoteles() {
        return(getNumhoteles() > 0);
    }
}
