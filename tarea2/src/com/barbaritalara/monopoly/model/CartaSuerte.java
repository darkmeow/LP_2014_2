package com.barbaritalara.monopoly.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Created by meow on 12-11-14.
 */
public class CartaSuerte {
    protected ArrayList<Integer> cartas;
    //truco
    public final static int DIRECTO_A_SALIDA = 0;
    public final static int BANCO_REGALA_100 = 1;
    public final static int DIRECTO_A_CARCEL = 2;
    public final static int BANCO_REGALA_300 = 3;
    public final static int RETROCEDA = 4;

    public CartaSuerte() {

        cartas = new ArrayList<Integer>();
        cartas.add(DIRECTO_A_SALIDA);
        cartas.add(DIRECTO_A_SALIDA);
        cartas.add(DIRECTO_A_SALIDA);
        cartas.add(DIRECTO_A_SALIDA);
        cartas.add(BANCO_REGALA_100);
        cartas.add(BANCO_REGALA_100);
        cartas.add(DIRECTO_A_CARCEL);
        cartas.add(DIRECTO_A_CARCEL);
        cartas.add(DIRECTO_A_CARCEL);
        cartas.add(DIRECTO_A_CARCEL);
        cartas.add(BANCO_REGALA_300);
        cartas.add(BANCO_REGALA_300);
        cartas.add(RETROCEDA);
        cartas.add(RETROCEDA);
        cartas.add(RETROCEDA);
        cartas.add(RETROCEDA);

        long seed = System.nanoTime();
        Collections.shuffle(cartas, new Random(seed));
    }

    public ArrayList<Integer> getCartas() {
        return cartas;
    }

    public void setCartas(ArrayList<Integer> cartas) {
        this.cartas = cartas;
    }

    public void first2end() {
        int temp;
        temp = cartas.get(0);
        cartas.remove(0);
        cartas.add(temp);
    }
}
