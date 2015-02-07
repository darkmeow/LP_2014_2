package com.barbaritalara.monopoly.view;

import com.barbaritalara.monopoly.model.*;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Board extends JPanel {
    // board dimension
    public static final int SCALE = 3;
    public static final int WIDTH = 240 * SCALE;
    public static final int HEIGHT = 240 * SCALE;

    public static final int CORNER_WIDTH = 30 * SCALE;
    public static final int CORNER_HEIGHT = CORNER_WIDTH;
    public static final int HORIZONTAL_BOX_HEIGHT = 20 * SCALE;

    private ArrayList<Casilla> casillas;

    private BufferedImage logo;

    public Board() {
        // setup board
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBounds(0, 0, WIDTH, HEIGHT);
        setLayout(null);
        setFocusable(true);
        requestFocus();
        setBackground(Color.white);

        try{
            logo = ImageIO.read(new File("pini.png"));
        } catch ( IOException ex) {
            System.out.println("testing");
        }

        casillas = new ArrayList<Casilla>();

        setupUI();


    }

    public ArrayList<Casilla> getCasillas() {
        return casillas;
    }

    private void setupUI() {
        add(new Grid());
        Casilla c;
        c = new Otras(0, "<- SALIDA");
        casillas.add(c);
        c = new Ciudad(1, "Ovalle", new Color(165, 42, 42), 60, 2, 10, 30, 90, 160, 250, 50,0,false);
        casillas.add(c);
        c = new Suerte(2, "Suerte");
        casillas.add(c);
        c = new Ciudad(3, "<html>Punta<br>Arenas</html>", new Color(165, 42, 42), 60, 4, 20, 60, 180, 320, 450, 50,0,false);
        casillas.add(c);
        c = new Otras(4, "Impuesto");
        casillas.add(c);
        c = new Ferrocarril(5, "<html>Ferrocarril<br>Arica</html>", 200);
        casillas.add(c);
        c = new Ciudad(6, "Calama", Color.CYAN, 100, 6, 30, 90, 270, 400, 550, 50,0,false);
        casillas.add(c);
        c = new Suerte(7, "Suerte");
        casillas.add(c);
        c = new Ciudad(8, "<html>San<br>Antonio</html>", Color.CYAN, 100, 6, 30, 90, 270, 400, 550, 50,0,false);
        casillas.add(c);
        c = new Ciudad(9, "Curicó", Color.CYAN, 120, 8, 40, 100, 300, 450, 600, 50,0,false);
        casillas.add(c);
        c = new Otras(10, "Cárcel");
        casillas.add(c);
        c = new Ciudad(11, "Osorno", Color.PINK, 140, 10, 50, 150, 450, 625, 750, 100,0,false);
        casillas.add(c);
        c = new Servicio(12, "<html>Compañía<br>Eléctrica</html>", 150);
        casillas.add(c);
        c = new Ciudad(13, "Valdivia", Color.PINK, 140, 10, 50, 150, 450, 625, 740, 100,0,false);
        casillas.add(c);
        c = new Ciudad(14, "Copiapó", Color.PINK, 160, 12, 60, 180, 500, 700, 900, 100,0,false);
        casillas.add(c);
        c = new Ferrocarril(15, "Metrotren", 200);
        casillas.add(c);
        c = new Ciudad(16, "Quillota", Color.ORANGE, 180, 14, 70, 200, 550, 750, 950, 100,0,false);
        casillas.add(c);
        c = new Suerte(17, "Suerte");
        casillas.add(c);
        c = new Ciudad(18, "Los Ángeles", Color.ORANGE, 180, 14, 70, 200, 550, 750, 950, 100,0,false);
        casillas.add(c);
        c = new Ciudad(19, "Chillán", Color.ORANGE, 200, 16, 80, 220, 600, 800, 1000, 100,0,false);
        casillas.add(c);
        c = new Otras(20, "<html>Estacionamiento<br>Gratis</html>");
        casillas.add(c);
        c = new Ciudad(21, "Arica", Color.RED, 220, 18, 90, 250, 700, 875, 1050, 100,0,false);
        casillas.add(c);
        c = new Suerte(22, "Suerte");
        casillas.add(c);
        c = new Ciudad(23, "Talca", Color.RED, 220, 18, 90, 250, 700, 875, 1050, 100,0,false);
        casillas.add(c);
        c = new Ciudad(24, "<html>Puerto<br>Montt</html>", Color.RED, 240, 20, 100, 300, 750, 925, 1100, 150,0,false);
        casillas.add(c);
        c = new Ferrocarril(25, "Biotren", 200);
        casillas.add(c);
        c = new Ciudad(26, "Rancagua", Color.YELLOW, 260, 22, 110, 330, 800, 975, 1150, 150,0,false);
        casillas.add(c);
        c = new Ciudad(27, "Iquique", Color.YELLOW, 260, 22, 110, 330, 800, 975, 1150, 150,0,false);
        casillas.add(c);
        c = new Servicio(28, "<html>Compañia<br> de Agua</html>", 150);
        casillas.add(c);
        c = new Ciudad(29, "Temuco", Color.YELLOW, 280, 24, 120, 360, 850, 1025, 1200, 150,0,false);
        casillas.add(c);
        c = new Otras(30, "Ve a la Cárcel");
        casillas.add(c);
        c = new Ciudad(31, "Antofagasta", Color.GREEN, 300, 26, 130, 390, 900, 1100, 1275, 200,0,false);
        casillas.add(c);
        c = new Ciudad(32, "La Serena", Color.GREEN, 300, 26, 130, 390, 900, 1100, 1275, 200,0,false);
        casillas.add(c);
        c = new Suerte(33, "Suerte");
        casillas.add(c);
        c = new Ciudad(34, "Valparaíso", Color.GREEN, 320, 28, 150, 450, 1000, 1200, 1400, 200,0,false);
        casillas.add(c);
        c = new Ferrocarril(35, "Merval", 200);
        casillas.add(c);
        c = new Suerte(36, "Suerte");
        casillas.add(c);
        c = new Ciudad(37, "Concepción", Color.BLUE, 350, 35, 175, 500, 1100, 1300, 1500, 200,0,false);
        casillas.add(c);
        c = new Otras(38, "Impuesto");
        casillas.add(c);
        c = new Ciudad(39, "Santiago", Color.BLUE, 400, 50, 200, 600, 1400, 1700, 2000, 200,0,false);
        casillas.add(c);

        drawCasillas();
    }

    private void drawCasillas() {
        // iterar casillas y mostrarlas en pantalla
        for (Casilla c : casillas) {
            c.drawLabel(this);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(logo, 100, 200, null);

        for (Casilla c : casillas) {
            if(c instanceof Ciudad) { //Conocer el tipo de Casilla
                ((Ciudad)c).drawColor(g, this, c);
            }
        }
    }
}
