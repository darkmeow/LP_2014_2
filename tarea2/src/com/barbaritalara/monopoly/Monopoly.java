package com.barbaritalara.monopoly;

import com.barbaritalara.monopoly.controller.Juego;
import com.barbaritalara.monopoly.event.IPanelControl;
import com.barbaritalara.monopoly.view.Board;
import com.barbaritalara.monopoly.view.PanelControl;

import javax.swing.*;

public class Monopoly {
    public static void main(String[] args) {
        Board b = new Board();
        Juego j = new Juego(b);

        JFrame frame = new JFrame("Monopoly");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setContentPane(b);
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);

        new PanelControl(b, j);
    }
}
