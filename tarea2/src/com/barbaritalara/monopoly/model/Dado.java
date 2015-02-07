package com.barbaritalara.monopoly.model;

import com.barbaritalara.monopoly.controller.Juego;

import java.util.*;




/**
 * Created by meow on 11-11-14.
 */
public class Dado{

    public static int tirarDados(){
        Random randomsource = new Random();
        double dado1;
        double dado2;
        double numero;
        dado1 = 1 + randomsource.nextInt(6);
        dado2 = 1 + randomsource.nextInt(6);
        numero = dado1 + dado2;
        return (int)numero;
    }
}

