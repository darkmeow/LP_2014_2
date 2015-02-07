package com.barbaritalara.monopoly.view;

import com.barbaritalara.monopoly.controller.Juego;
import com.barbaritalara.monopoly.event.IPanelControl;
import com.barbaritalara.monopoly.model.*;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.util.ArrayList;


public class PanelControl extends JFrame implements IPanelControl {
    private JPanel panel1;
    private JPanel newGamePanel;
    private JTextField textFieldJ1;
    private JTextField textFieldJ2;
    private JTextField textFieldJ3;
    private JButton jugarButton;
    private JTextField textFieldJ4;
    private JPanel gamePanel;
    private JTextArea logTextArea;
    private JRadioButton hotelRadioButton;
    private JRadioButton casaRadioButton;
    private JComboBox comboBoxHotel;
    private JComboBox comboBoxCasas;
    private JSpinner numCasas;
    private JButton comprarButton;
    private JButton terminarTurnoButton;
    private JPanel PanelPreguntas;
    private JRadioButton propiedadRadioButton;
    private Juego j;
    private Board b;

    public PanelControl(Board b, Juego j) {
        j.register(this); // registrar callback para poder llamar eventos de IPanelControl
        DefaultCaret caret = (DefaultCaret) logTextArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        setContentPane(panel1);
        setLocation(Board.WIDTH, 0);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pack();
        setResizable(false);
        setVisible(true);

        SpinnerModel spinnerModel =
                new SpinnerNumberModel(1, //initial value
                        1, //min
                        4, //max
                        1);                //step
        numCasas.setModel(spinnerModel);

        this.j = j; // referencia al controlador del juego
        this.b = b; // referencia a la vista del tablero

        textFieldJ1.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                toggleTextField(textFieldJ1, textFieldJ2);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                toggleTextField(textFieldJ1, textFieldJ2);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                toggleTextField(textFieldJ1, textFieldJ2);
            }
        });

        textFieldJ2.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                toggleTextField(textFieldJ2, textFieldJ3);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                toggleTextField(textFieldJ2, textFieldJ3);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                toggleTextField(textFieldJ2, textFieldJ3);
            }
        });

        textFieldJ3.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                toggleTextField(textFieldJ3, textFieldJ4);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                toggleTextField(textFieldJ3, textFieldJ4);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                toggleTextField(textFieldJ3, textFieldJ4);
            }
        });

        jugarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (!textFieldJ1.getText().isEmpty())
                    getJuego().agregarJugador(new Jugador(1, textFieldJ1.getText()));

                if (!textFieldJ2.getText().isEmpty())
                    getJuego().agregarJugador(new Jugador(2, textFieldJ2.getText()));

                if (!textFieldJ3.getText().isEmpty())
                    getJuego().agregarJugador(new Jugador(3, textFieldJ3.getText()));

                if (!textFieldJ4.getText().isEmpty())
                    getJuego().agregarJugador(new Jugador(4, textFieldJ4.getText()));

                jugarButton.setEnabled(false);
                getJuego().empezar();
            }
        });
        PanelPreguntas.addComponentListener(new ComponentAdapter() {


        });


        terminarTurnoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getJuego().siguienteTurno();
            }
        });
        comprarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (propiedadRadioButton.isSelected()) {
                    getJuego().comprarCasilla();
                }
                if (casaRadioButton.isSelected()) {
                    getJuego().comprarCasa((Item) comboBoxCasas.getSelectedItem(), (Integer) numCasas.getValue());
                }
                if (hotelRadioButton.isSelected()) {
                    getJuego().comprarHotel((Item) comboBoxHotel.getSelectedItem());
                }
                getJuego().continuar();
            }
        });
    }

    private void toggleTextField(JTextField tf1, JTextField tf2) {
        if (tf1.getText().length() > 0) {
            tf2.setEditable(true);
        } else {
            tf2.setText("");
            tf2.setEditable(false);
        }
        toggleStartButton();
    }

    private void toggleStartButton() {
        int jugadores = 0;
        if (!textFieldJ1.getText().isEmpty())
            jugadores++;
        if (!textFieldJ2.getText().isEmpty())
            jugadores++;
        if (!textFieldJ3.getText().isEmpty())
            jugadores++;
        if (!textFieldJ4.getText().isEmpty())
            jugadores++;

        jugarButton.setEnabled(jugadores > 1);
    }


    public Juego getJuego() {
        return j;
    }

    public void setJuego(Juego j) {
        this.j = j;
    }

    public Board getBoard() {
        return b;
    }

    @Override
    public void cambiarPanel() {
        ((CardLayout) panel1.getLayout()).next(panel1);
        getJuego().register(this); // registrar callback para poder llamar eventos de IPanelControl
    }

    @Override
    public void log(String l) {
        logTextArea.append(l + "\n");
    }

    @Override
    public void confPanelPregunta(Jugador j, Casilla c, ArrayList<Casilla> casillas) {
        comboBoxCasas.removeAllItems();
        comboBoxHotel.removeAllItems();

        propiedadRadioButton.setText("Propiedad");
        propiedadRadioButton.setEnabled(false);
        propiedadRadioButton.setSelected(false);
        casaRadioButton.setEnabled(false);
        hotelRadioButton.setEnabled(false);
        ArrayList<Casilla> casillasMismoColor = j.mismoColor(casillas);
        ArrayList<Casilla> casillasPosibleHotel = new ArrayList<Casilla>();
        if (casillasMismoColor.size() > 0) {
            for (Casilla casilla : casillasMismoColor) {
                comboBoxCasas.addItem(new Item(casilla.getId(), casilla.getNombre().replaceAll("\\<.*?>", " ")));
                if (((Ciudad) casilla).getNumcasas() == 4)
                    casillasPosibleHotel.add(casilla);
            }
            casaRadioButton.setEnabled(true);
            casaRadioButton.setSelected(true);
            if (casillasPosibleHotel.size() > 0) {
                hotelRadioButton.setEnabled(true);
                hotelRadioButton.setSelected(true);
                for (Casilla casillaHotel : casillasPosibleHotel) {
                    comboBoxHotel.addItem(new Item(casillaHotel.getId(), casillaHotel.getNombre().replaceAll("\\<.*?>", " ")));
                }
            }
        }

        if (c instanceof Comprable) {
            if (((Comprable) c).getId_dueno() == 0) {
                propiedadRadioButton.setEnabled(true);
                propiedadRadioButton.setSelected(true);
                propiedadRadioButton.setText("Propiedad ($" + ((Comprable) c).getPrecio() + " " + c.getNombre().replaceAll("\\<.*?>", " ") + ")");
            }
        }

    }

    @Override
    public void setTurno(Jugador j) {
        gamePanel.setOpaque(true);
        gamePanel.setBackground(j.getFicha().getColor());
        gamePanel.repaint();
    }


    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        panel1 = new JPanel();
        panel1.setLayout(new CardLayout(0, 0));
        panel1.setPreferredSize(new Dimension(700, 400));
        newGamePanel = new JPanel();
        newGamePanel.setLayout(new GridLayoutManager(8, 7, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(newGamePanel, "Card1");
        final JLabel label1 = new JLabel();
        label1.setText("Juego Nuevo");
        newGamePanel.add(label1, new GridConstraints(1, 0, 1, 7, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        textFieldJ2 = new JTextField();
        textFieldJ2.setEditable(false);
        newGamePanel.add(textFieldJ2, new GridConstraints(3, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        textFieldJ1 = new JTextField();
        textFieldJ1.setText("");
        newGamePanel.add(textFieldJ1, new GridConstraints(2, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        textFieldJ3 = new JTextField();
        textFieldJ3.setEditable(false);
        newGamePanel.add(textFieldJ3, new GridConstraints(4, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("Jugador 1");
        newGamePanel.add(label2, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("Jugador 2");
        newGamePanel.add(label3, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label4 = new JLabel();
        label4.setText("Jugador 3");
        newGamePanel.add(label4, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        newGamePanel.add(spacer1, new GridConstraints(2, 6, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        newGamePanel.add(spacer2, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JLabel label5 = new JLabel();
        label5.setText("Jugador 4");
        newGamePanel.add(label5, new GridConstraints(5, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        textFieldJ4 = new JTextField();
        textFieldJ4.setEditable(false);
        newGamePanel.add(textFieldJ4, new GridConstraints(5, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final Spacer spacer3 = new Spacer();
        newGamePanel.add(spacer3, new GridConstraints(7, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        jugarButton = new JButton();
        jugarButton.setEnabled(false);
        jugarButton.setText("Jugar!");
        newGamePanel.add(jugarButton, new GridConstraints(6, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer4 = new Spacer();
        newGamePanel.add(spacer4, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        gamePanel = new JPanel();
        gamePanel.setLayout(new GridLayoutManager(5, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(gamePanel, "Card2");
        final JScrollPane scrollPane1 = new JScrollPane();
        gamePanel.add(scrollPane1, new GridConstraints(0, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        logTextArea = new JTextArea();
        logTextArea.setEditable(false);
        logTextArea.setLineWrap(true);
        logTextArea.setText("");
        scrollPane1.setViewportView(logTextArea);
        PanelPreguntas = new JPanel();
        PanelPreguntas.setLayout(new GridLayoutManager(3, 7, new Insets(0, 0, 0, 0), -1, -1));
        gamePanel.add(PanelPreguntas, new GridConstraints(3, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        hotelRadioButton = new JRadioButton();
        hotelRadioButton.setText("Hotel");
        PanelPreguntas.add(hotelRadioButton, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        casaRadioButton = new JRadioButton();
        casaRadioButton.setText("Casa");
        PanelPreguntas.add(casaRadioButton, new GridConstraints(2, 0, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        comboBoxHotel = new JComboBox();
        PanelPreguntas.add(comboBoxHotel, new GridConstraints(1, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        comboBoxCasas = new JComboBox();
        PanelPreguntas.add(comboBoxCasas, new GridConstraints(2, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        propiedadRadioButton = new JRadioButton();
        propiedadRadioButton.setText("Propiedad");
        PanelPreguntas.add(propiedadRadioButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        numCasas = new JSpinner();
        PanelPreguntas.add(numCasas, new GridConstraints(2, 5, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer5 = new Spacer();
        PanelPreguntas.add(spacer5, new GridConstraints(2, 6, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer6 = new Spacer();
        PanelPreguntas.add(spacer6, new GridConstraints(2, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JSeparator separator1 = new JSeparator();
        gamePanel.add(separator1, new GridConstraints(1, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        comprarButton = new JButton();
        comprarButton.setText("Comprar");
        gamePanel.add(comprarButton, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        terminarTurnoButton = new JButton();
        terminarTurnoButton.setText("Terminar Turno");
        gamePanel.add(terminarTurnoButton, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label6 = new JLabel();
        label6.setForeground(new Color(-1));
        label6.setText("¿Desea comprar o terminar turno?");
        gamePanel.add(label6, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        ButtonGroup buttonGroup;
        buttonGroup = new ButtonGroup();
        buttonGroup.add(casaRadioButton);
        buttonGroup.add(hotelRadioButton);
        buttonGroup.add(propiedadRadioButton);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panel1;
    }
}
