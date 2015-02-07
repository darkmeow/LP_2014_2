package com.barbaritalara.monopoly.event;

import com.barbaritalara.monopoly.model.Casilla;
import com.barbaritalara.monopoly.model.Comprable;
import com.barbaritalara.monopoly.model.Jugador;

import java.util.ArrayList;

public interface IPanelControl {
    public void cambiarPanel ();
    public void log(String l);
    public void confPanelPregunta(Jugador j, Casilla c, ArrayList<Casilla> casillas);
    void setTurno(Jugador j);
}
