package com.barbaritalara.monopoly.model;

import com.barbaritalara.monopoly.event.IPanelControl;
import com.barbaritalara.monopoly.view.Board;
import com.barbaritalara.monopoly.view.Ficha;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;


public class Jugador implements Comparable<Jugador> {
    protected int id;
    protected String nombre;
    public Ficha ficha;
    public int dado;
    public int dinero;
    public int casilla;
    public boolean pierdeTurno;

    public Jugador() {

    }

    public Jugador(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
        int r = 12;
        int x = Board.WIDTH; // right
        int y = Board.HEIGHT - 1; // bottom

        // colores de los jugadores
        switch (id) {
            default:
            case 1:
                this.ficha = new Ficha(x - r * id - Board.CORNER_WIDTH + r * 4, y, r, Color.RED);
                break;
            case 2:
                this.ficha = new Ficha(x - r * id - Board.CORNER_WIDTH + r * 4, y, r, Color.BLUE);
                break;
            case 3:
                this.ficha = new Ficha(x - r * id - Board.CORNER_WIDTH + r * 4, y, r, Color.GREEN);
                break;
            case 4:
                this.ficha = new Ficha(x - r * id - Board.CORNER_WIDTH + r * 4, y, r, Color.ORANGE);
                break;
        }
    }

    // sort por dado
    public int compareTo(Jugador j) {
        return (j.getDado() - getDado());
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

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Ficha getFicha() {
        return ficha;
    }

    public void setFicha(Ficha ficha) {
        this.ficha = ficha;
    }

    public int getDado() {
        return dado;
    }

    public void setDado(int dado) {
        this.dado = dado;
    }

    public int getDinero() {
        return dinero;
    }

    public void setDinero(int dinero) {
        this.dinero = dinero;
    }

    public int getCasilla() {
        return casilla;
    }

    public void setCasilla(int casilla) {

        this.casilla = casilla;
        getFicha().moverFicha(getId(), casilla);
    }

    public boolean puedeComprar(Casilla c) {
        if (c instanceof Comprable) {
            return (getDinero() >= ((Comprable) c).getPrecio());
        }
        return false;
    }

    public boolean deseaComprar(Casilla c) {


        boolean valor = false;
        return valor;
    }

    public boolean isPierdeTurno() {
        return pierdeTurno;
    }

    public void setPierdeTurno(boolean pierdeTurno) {
        this.pierdeTurno = pierdeTurno;
    }

    public void carcel() {
        setCasilla(10);
        setPierdeTurno(true);
        setDinero(getDinero() - 50);

    }

    public void transferencia(int aPagar, Jugador j) {
        setDinero(getDinero() - aPagar);
        j.setDinero(j.getDinero() + aPagar);
    }

    public ArrayList<Casilla> mismoColor(ArrayList<Casilla> casillas) {
        ArrayList<Color> colores = new ArrayList<Color>();
        colores.add(new Color(165, 42, 42));
        colores.add(Color.CYAN);
        colores.add(Color.PINK);
        colores.add(Color.ORANGE);
        colores.add(Color.RED);
        colores.add(Color.YELLOW);
        colores.add(Color.GREEN);
        colores.add(Color.BLUE);
        ArrayList<Casilla> casillasMismoColor = new ArrayList<Casilla>();

        for(Color color : colores) {
            boolean todas = true;
            for (Casilla c : casillas) {
                if (c instanceof Ciudad && ((Ciudad) c).getColor() == color) {
                    if(((Ciudad) c).getId_dueno() != getId()) {
                        todas = false;
                        break;
                    }
                }
            }
            if(todas) {
                for (Casilla c : casillas) {
                    if (c instanceof Ciudad && ((Ciudad) c).getColor() == color && !((Ciudad) c).getHotel()) {
                        casillasMismoColor.add(c);
                    }
                }
            }
        }
        return casillasMismoColor;
    }

    public boolean otraColor(Comprable c, ArrayList<Casilla> casillas) {

        boolean siHay = false;

        for (Casilla casi : casillas) {
            if (casi instanceof Ciudad && c instanceof Ciudad) {
                if (((Ciudad) c).getColor() == ((Ciudad) casi).getColor() && c.getId() != casi.getId()) {
                    if (((Ciudad) casi).getId_dueno() == getId()) {
                        siHay = true;
                        break;
                    } else {
                        siHay = false;
                    }
                }
            }
        }

        return (siHay);
    }

    public void devolverPropiedades(ArrayList<Casilla> casillas, Banco banco) {
        for (Casilla casi : casillas) {
            if (casi instanceof Ciudad) {
                if (((Ciudad) casi).getId_dueno() == getId()) {
                    banco.setNumcasas(banco.getNumcasas() + (((Ciudad) casi).getNumcasas())); // devuelvo casas
                    if (((Ciudad) casi).getHotel()) {
                        banco.setNumhoteles(banco.getNumhoteles() + 1); // devuelvo hotel
                    }
                    ((Ciudad) casi).setId_dueno(0); // devuelvo propiedad
                }
            }
        }
    }
}
