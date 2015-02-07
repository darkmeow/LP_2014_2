    package com.barbaritalara.monopoly.controller;

    import com.barbaritalara.monopoly.model.*;
    import com.barbaritalara.monopoly.event.IPanelControl;
    import com.barbaritalara.monopoly.view.Board;

    import javax.swing.*;
    import java.util.ArrayList;
    import java.util.Collections;

    public class Juego {
        private ArrayList<Jugador> jugadores;
        private IPanelControl panelCallback;
        private Board board;
        private CartaSuerte suerte;
        private Banco banco;
        private boolean running = true;
        private int turnoActual = 0;
        private int casillaActual = 0;
        private Jugador jugadorActual = null;
        public int count = -1;
        private final Object o;


        public Juego(Board b) {
            board = b;
            jugadores = new ArrayList<Jugador>();
            o = this;
        }

        public void agregarJugador(Jugador jugador) {
            jugadores.add(jugador);
        }

        public Jugador getJugadorbyId(int id_dueno) {

            for (Jugador j : jugadores) {
                if (j.getId() == id_dueno) {
                    return j;
                }
            }
            return null;
        }


        public void empezar() {
            // empezar un nuevo juego
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    suerte = new CartaSuerte();
                    banco = new Banco(32, 12);


                    for (Jugador j : jugadores) {
                        j.setDado(Dado.tirarDados());
                        j.setDinero(1500);
                        j.setCasilla(0);
                        panelCallback.log("Jugador " + j.getId() + " tira dado " + j.getDado());
                        getBoard().add(j.getFicha());
                        j.getFicha().moverFicha(j.getId(), 0);
                    }
                    getBoard().repaint();
                    panelCallback.log("Ordenar jugadores por dado de mayor a menor");
                    Collections.sort(jugadores);

                    String orderJugadores = "Orden: ";
                    for (Jugador p : jugadores) {
                        orderJugadores += "J" + p.getId() + " ";
                    }
                    panelCallback.log(orderJugadores);

                    panelCallback.cambiarPanel();
                    panelCallback.log("Empezar juego!");
                    panelCallback.log("Total jugadores: " + jugadores.size());

                    turno(turnoActual);
                }
            });
        }

        private void turno(final int jPos) {
            getBoard().repaint();
            final Jugador j;
            turnoActual = jPos;
            if (jugadores.size() == jPos) {
                j = jugadores.get(0);
            } else {
                j = jugadores.get(jPos);
            }

            Runnable run = new Runnable() {
                public void run() {
                    if (!running)
                        return;
                    int newcasilla = 0;
                    if (j.getDinero() < 0) {
                        // EL JUGADOR PERDÍO!

                        // devolverropiedades al banco
                        j.devolverPropiedades(getBoard().getCasillas(), banco);

                        // sacar jugador del arraylist
                        jugadores.remove(j);
                        panelCallback.log("Ha perdido el jugador J" + j.getId());
                        siguienteTurno();
                    }

                    if (jugadores.size() < 2) {
                        panelCallback.log("Juego Terminado, gana J" + jugadores.get(0).getId());
                        running = false;
                        siguienteTurno();
                    }

                    if (j.isPierdeTurno()) {
                        panelCallback.log("J" + j.getId() + " pierde turno ");
                        j.setPierdeTurno(false);

                        siguienteTurno();
                    }
                    panelCallback.log("==================================");
                    jugadorActual = j;
                    panelCallback.setTurno(j);
                    panelCallback.log("Turno J" + j.getId());
                    if (j.mismoColor(getBoard().getCasillas()).size() > 0) {
                        if (banco.tieneCasas() && banco.tieneHoteles()) {
                            //PREGUNTA PANEL CUANTAS CASAS
                            panelCallback.log("Usted tiene todas las propiedades del mismo color, desea comprar hotel, casas o terminar el turno?");
                            panelCallback.confPanelPregunta(j, getBoard().getCasillas().get(getCasillaActual()), getBoard().getCasillas());
                            synchronized (o) {
                                    try {
                                        o.wait();
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                            }
                        }
                    }
                    j.setDado(Dado.tirarDados());
                    newcasilla = j.getCasilla() + j.getDado();
                    if (newcasilla > 39) {
                        newcasilla -= 40;
                        j.setDinero(j.getDinero() + 200); //El banco regala 200 al pasar o caer en la Salida
                        panelCallback.log("J" + j.getId() + " Saldo: " + j.getDinero() + " el Banco le regala 200 al pasar o caer en Salida");
                    }
                    j.setCasilla(newcasilla);
                    if (j.getCasilla() == 0) {
                        // Jugador cae en SALIDA, debe termiar el turno
                        siguienteTurno();
                    }
                    panelCallback.log("J" + j.getId() + " Saldo: " + j.getDinero() + ", tira dados y sale: " + j.getDado() + ", cae en la casilla: " + j.getCasilla());

                    Casilla c = getBoard().getCasillas().get(newcasilla);
                    if (c instanceof Comprable) {


                        if (((Comprable) c).getId_dueno() == 0) {

                            if (j.otraColor(((Comprable) c), getBoard().getCasillas())) {

                                //MOSTRAR EN PANTALLA QUE EXISTE OTRA CASILLA DEL MISMO COLOR QUE ES MIA
                                panelCallback.log("J" + j.getId() + " Ud. tiene una Casilla del mismo color! ");

                            }

                            //puede comprar titulo de la propiedad
                            if (j.puedeComprar(c)) {


                                panelCallback.log("J" + j.getId() + " Saldo: " + j.getDinero() + " Desea comprar el título de la Propiedad de " + c.getNombre().replaceAll("\\<.*?>", " ") + "?");
                                casillaActual = c.getId();
                                jugadorActual = j;
                                panelCallback.confPanelPregunta(j, c, getBoard().getCasillas());
                                synchronized (o) {
                                    try {
                                        o.wait();
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        } else {

                            if (((Comprable) c).getId_dueno() != j.getId()) {

                                //debo cobrar renta
                                if (c instanceof Ciudad) {
                                    if (((Ciudad) c).getNumcasas() == 0) {
                                        if (((Ciudad) c).getHotel()) {
                                            int aPagar = ((Ciudad) c).getRentaHotel();
                                            panelCallback.log("J" + j.getId() + " Saldo: " + j.getDinero() + " Debe pagar Renta");
                                            j.transferencia(aPagar, getJugadorbyId(((Ciudad) c).getId_dueno()));
                                            panelCallback.log("J" + j.getId() + " Nuevo Saldo: " + j.getDinero());


                                        } else { //sin Hotel y sin casas
                                            int aPagar = ((Ciudad) c).getRenta();
                                            panelCallback.log("J" + j.getId() + " Saldo: " + j.getDinero() + " Debe pagar Renta");
                                            j.transferencia(aPagar, getJugadorbyId(((Ciudad) c).getId_dueno()));
                                            panelCallback.log("J" + j.getId() + " Nuevo Saldo: " + j.getDinero());
                                        }
                                    }
                                    if (((Ciudad) c).getNumcasas() == 1) {
                                        panelCallback.log("J" + j.getId() + " Saldo: " + j.getDinero() + " Debe pagar Renta");
                                        int aPagar = ((Ciudad) c).getRentaCasa1();
                                        j.transferencia(aPagar, getJugadorbyId(((Ciudad) c).getId_dueno()));
                                        panelCallback.log("J" + j.getId() + " Nuevo Saldo: " + j.getDinero());

                                    }
                                    if (((Ciudad) c).getNumcasas() == 2) {
                                        panelCallback.log("J" + j.getId() + " Saldo: " + j.getDinero() + " Debe pagar Renta");
                                        int aPagar = ((Ciudad) c).getRentaCasa2();
                                        j.transferencia(aPagar, getJugadorbyId(((Ciudad) c).getId_dueno()));
                                        panelCallback.log("J" + j.getId() + " Nuevo Saldo: " + j.getDinero());


                                    }
                                    if (((Ciudad) c).getNumcasas() == 3) {
                                        panelCallback.log("J" + j.getId() + " Saldo: " + j.getDinero() + " Debe pagar Renta");
                                        int aPagar = ((Ciudad) c).getRentaCasa3();
                                        j.transferencia(aPagar, getJugadorbyId(((Ciudad) c).getId_dueno()));
                                        panelCallback.log("J" + j.getId() + " Nuevo Saldo: " + j.getDinero());

                                    }
                                    if (((Ciudad) c).getNumcasas() == 4) {
                                        panelCallback.log("J" + j.getId() + " Saldo: " + j.getDinero() + " Debe pagar Renta");
                                        int aPagar = ((Ciudad) c).getRentaCasa4();
                                        j.transferencia(aPagar, getJugadorbyId(((Ciudad) c).getId_dueno()));
                                        panelCallback.log("J" + j.getId() + " Nuevo Saldo: " + j.getDinero());
                                    }
                                    siguienteTurno();
                                }

                                if (c instanceof Servicio) {
                                    int duenoServicioActual = ((Servicio) c).getId_dueno();
                                    int duenoOtroServicio;
                                    if (c.getId() == 12) {
                                        duenoOtroServicio = ((Servicio) getBoard().getCasillas().get(28)).getId_dueno();
                                    } else {
                                        duenoOtroServicio = ((Servicio) getBoard().getCasillas().get(12)).getId_dueno();
                                    }

                                    if (duenoServicioActual == duenoOtroServicio) {
                                        //mismo dueño en ambos!
                                        panelCallback.log("J" + j.getId() + " Saldo: " + j.getDinero() + " Debe pagar renta a dueño de los dos servicios");
                                        panelCallback.log("J" + j.getId() + " Tire los dados");

                                        int dados = Dado.tirarDados();
                                        int aPagar = ((Servicio) c).getRenta2(dados);
                                        j.transferencia(aPagar, getJugadorbyId(duenoServicioActual));
                                        panelCallback.log("J" + j.getId() + " Su nuevo Saldo " + j.getDinero());

                                    } else {
                                        //solo dueño de un servicio
                                        int dados = Dado.tirarDados();
                                        int aPagar = ((Servicio) c).getRenta1(dados);
                                        j.transferencia(aPagar, getJugadorbyId(duenoServicioActual));
                                        panelCallback.log("J" + j.getId() + " Su nuevo Saldo " + j.getDinero());


                                    }
                                    siguienteTurno();
                                }

                                if (c instanceof Ferrocarril) {
                                    panelCallback.log("J" + j.getId() + " entra a ferrocarril ");

                                    int duenoFerrocarrilActual = ((Ferrocarril) c).getId_dueno();
                                    int cantidad = 1;
                                    switch (c.getId()) {
                                        case 5:
                                            if (duenoFerrocarrilActual == ((Ferrocarril) getBoard().getCasillas().get(15)).getId_dueno()) {
                                                cantidad += 1;
                                            }
                                            if (duenoFerrocarrilActual == ((Ferrocarril) getBoard().getCasillas().get(25)).getId_dueno()) {
                                                cantidad += 1;
                                            }
                                            if (duenoFerrocarrilActual == ((Ferrocarril) getBoard().getCasillas().get(35)).getId_dueno()) {
                                                cantidad += 1;
                                            }

                                            break;
                                        case 15:
                                            if (duenoFerrocarrilActual == ((Ferrocarril) getBoard().getCasillas().get(5)).getId_dueno()) {
                                                cantidad += 1;
                                            }
                                            if (duenoFerrocarrilActual == ((Ferrocarril) getBoard().getCasillas().get(25)).getId_dueno()) {
                                                cantidad += 1;
                                            }
                                            if (duenoFerrocarrilActual == ((Ferrocarril) getBoard().getCasillas().get(35)).getId_dueno()) {
                                                cantidad += 1;

                                            }
                                            break;
                                        case 25:
                                            if (duenoFerrocarrilActual == ((Ferrocarril) getBoard().getCasillas().get(15)).getId_dueno()) {
                                                cantidad += 1;
                                            }
                                            if (duenoFerrocarrilActual == ((Ferrocarril) getBoard().getCasillas().get(5)).getId_dueno()) {
                                                cantidad += 1;
                                            }
                                            if (duenoFerrocarrilActual == ((Ferrocarril) getBoard().getCasillas().get(35)).getId_dueno()) {
                                                cantidad += 1;
                                            }
                                            break;
                                        case 35:
                                            if (duenoFerrocarrilActual == ((Ferrocarril) getBoard().getCasillas().get(15)).getId_dueno()) {
                                                cantidad += 1;
                                            }
                                            if (duenoFerrocarrilActual == ((Ferrocarril) getBoard().getCasillas().get(25)).getId_dueno()) {
                                                cantidad += 1;
                                            }
                                            if (duenoFerrocarrilActual == ((Ferrocarril) getBoard().getCasillas().get(5)).getId_dueno()) {
                                                cantidad += 1;
                                            }
                                            break;

                                    }

                                    panelCallback.log("J" + j.getId() + " Debe pagar renta a dueño de los Ferrocarriles");
                                    int aPagar = ((Ferrocarril) c).getRenta(cantidad);
                                    j.transferencia(aPagar, getJugadorbyId(duenoFerrocarrilActual));
                                    panelCallback.log("J" + j.getId() + " Su nuevo Saldo " + j.getDinero());
                                    siguienteTurno();

                                }

                            }
                            //si soy dueño....compro o sigo con el turno
                            if (((Comprable) c).getId_dueno() == j.getId()) {
                                if (c instanceof Ferrocarril) {
                                    panelCallback.log("Usted tiene todas las propiedades del mismo color, desea comprar hotel, casas o terminar el turno?");
                                }
                                if (c instanceof Servicio) {
                                    panelCallback.log("Usted tiene todas las propiedades del mismo color, desea comprar hotel, casas o terminar el turno?");

                                }

                            }
                        }
                    }
                    //De visita en la carcel
                    if (newcasilla == 10) {
                        panelCallback.log("J" + j.getId() + " De visita en la Cárcel!");
                        siguienteTurno();
                    }


                    //Suerte
                    if (newcasilla == 2 || newcasilla == 7 || newcasilla == 17 || newcasilla == 22 ||
                            newcasilla == 33 || newcasilla == 36) {
                        panelCallback.log("J" + j.getId() + " Casilla Suerte!");

                        switch (suerte.getCartas().get(0)) {
                            case CartaSuerte.DIRECTO_A_SALIDA:
                                panelCallback.log("J" + j.getId() + " Saldo: " + j.getDinero() + " Sacó la carta 'Ve directo a la Salida'");
                                j.setCasilla(0);

                                break;
                            case CartaSuerte.DIRECTO_A_CARCEL:
                                panelCallback.log("J" + j.getId() + " Saldo: " + j.getDinero() + " Sacó la carta 'Ve directo a la Cárcel'");
                                j.carcel();
                                panelCallback.log("J" + j.getId() + " Nueva Casilla " + j.getCasilla());
                                break;
                            case CartaSuerte.BANCO_REGALA_100:
                                panelCallback.log("J" + j.getId() + " Sacó la carta 'El Banco le regala $100'");
                                panelCallback.log("J" + j.getId() + " Saldo Actual " + j.getDinero());
                                j.setDinero(j.getDinero() + 100);
                                panelCallback.log("J" + j.getId() + " Nuevo Saldo " + j.getDinero());

                                break;
                            case CartaSuerte.BANCO_REGALA_300:
                                panelCallback.log("J" + j.getId() + " Sacó la carta 'El Banco le regala $300'");
                                panelCallback.log("J" + j.getId() + " Saldo Actual " + j.getDinero());
                                j.setDinero(j.getDinero() + 300);
                                panelCallback.log("J" + j.getId() + " Nuevo Saldo " + j.getDinero());

                                break;
                            case CartaSuerte.RETROCEDA:
                                panelCallback.log("J" + j.getId() + "Saldo: " + j.getDinero() + " Sacó la carta 'Tire el Dado y retroceda lo indicado' ");
                                int dados = Dado.tirarDados();
                                int retrocede = j.getCasilla() - dados;
                                panelCallback.log("J" + j.getId() + " Retrocede " + dados + " casillas");

                                if (retrocede < 0) {
                                    retrocede += 40;
                                }
                                panelCallback.log("J" + j.getId() + " Retrocede a la casilla " + retrocede);
                                j.setCasilla(retrocede);

                                break;

                        }
                        suerte.first2end(); //deja la carta al final del mazo
                        siguienteTurno();
                    }
                    //Estacionamiento
                    if (newcasilla == 20) {
                        panelCallback.log("J" + j.getId() + "Saldo: " + j.getDinero() + " Está en Estacionamiento Gratis, termina su turno ");
                        siguienteTurno();
                    }
                    //Impuesto
                    if (newcasilla == 4 || newcasilla == 38) {
                        panelCallback.log("J" + j.getId() + "Saldo: " + j.getDinero() + " Pague al banco $100 ");
                        j.setDinero(j.getDinero() - 100);
                        panelCallback.log("J" + j.getId() + " Nuevo Saldo: " + j.getDinero());

                        siguienteTurno();
                    }
                    //Ve a la Carcel
                    if (newcasilla == 30) {
                        //lo lleva a la carcel
                        //debe perder turno
                        //descontar 50
                        panelCallback.log("J" + j.getId() + "Saldo: " + j.getDinero() + " Se ha descontado $50 de su cuenta por caer en Carcel ");
                        j.carcel();
                        panelCallback.log("J" + j.getId() + "Nuevo Saldo: " + j.getDinero());

                        siguienteTurno();
                    }

                    //ver si gano!
                    if (j.getDinero() > 9999) {
                        //Gano!

                        panelCallback.log("J" + j.getId() + "HA GANADO! FELICITACIONES!");
                        //terminar juego!!!
                        running = false;

                    }


                    getBoard().repaint();
                    try {
                        Thread.sleep(400);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };

            Thread t = new Thread(run);
            t.start();
            //SwingUtilities.invokeLater(run);

        }

        public ArrayList<Jugador> getJugadores() {
            return jugadores;
        }

        public void setJugadores(ArrayList<Jugador> jugadores) {
            this.jugadores = jugadores;
        }

        public Board getBoard() {
            return board;
        }

        public void setBoard(Board board) {
            this.board = board;
        }

        public void register(IPanelControl callback) {
            panelCallback = callback;
        }

        public void siguienteTurno() {
            synchronized (o) {
                o.notifyAll();
            }
            if (turnoActual + 1 == jugadores.size()) {
                turno(0);
            } else {
                turno(turnoActual + 1);
            }
        }

        public void comprarCasilla() {
            Jugador j = getJugadorActual();
            Casilla c = getBoard().getCasillas().get(getCasillaActual());
            //seteo el dueño de la casilla
            //Resto al monto de dinero del jugador el valor de la propiedad
            if (j.getDinero() >= ((Comprable) c).getPrecio()) {
                j.setDinero(j.getDinero() - ((Comprable) c).getPrecio());
                ((Comprable) c).setId_dueno(j.getId());
                panelCallback.log("J" + j.getId() + " propiedad comprada");
            } else {
                panelCallback.log("J" + j.getId() + " no tiene suficiente dinero para comprar la propiedad");
            }
            siguienteTurno();
        }

        public int getCasillaActual() {
            return casillaActual;
        }

        public void setCasillaActual(int casillaActual) {
            this.casillaActual = casillaActual;
        }

        public Jugador getJugadorActual() {
            return jugadorActual;
        }

        public void setJugadorActual(Jugador jugadorActual) {
            this.jugadorActual = jugadorActual;
        }

        public void comprarCasa(Item selectedItem, Integer numeroDeCasas) {
            Jugador j = getJugadorActual();
            Ciudad c = (Ciudad)getBoard().getCasillas().get(selectedItem.getId());
            panelCallback.log("J"+j.getId()+" quiere comprar "+numeroDeCasas+" casas en "+c.getNombre().replaceAll("\\<.*?>", " "));
            int costoCasas = c.getPrecioCasaHotel() * numeroDeCasas;
            if(j.getDinero() >= costoCasas) { // si jugador tiene dinero suficiente
                int casasDisponibles = 4 - c.getNumcasas();
                if(casasDisponibles >= numeroDeCasas) { // si jugador quiere comprar igual o menor cantidad de espacio de casas disponibles
                    if(banco.getNumcasas() >= numeroDeCasas) { // si banco tiene igual o mayor cantidad de casas disponibles
                        // jugador puede comprar la cantidad de casas que desea
                        c.setNumcasas(c.getNumcasas() + numeroDeCasas); // actualizar el número de casas disponibles en ciudad
                        banco.setNumcasas(banco.getNumcasas() - numeroDeCasas); // actualizar el número de casas disponibles en banco

                        j.setDinero(j.getDinero() - costoCasas); // actualizar el total de dinero del jugador
                        panelCallback.log("J"+j.getId()+" ha comprado "+numeroDeCasas+" casas en "+c.getNombre().replaceAll("\\<.*?>", " "));
                        siguienteTurno();
                    } else {
                        panelCallback.log("J"+j.getId()+" El banco sólo tiene "+banco.getNumcasas()+" casas disponibles");
                    }
                } else {
                    panelCallback.log("J"+j.getId()+" sólo se pueden construir "+casasDisponibles+" casas más en "+c.getNombre().replaceAll("\\<.*?>", " "));
                }
            } else {
                panelCallback.log("J"+j.getId()+" no tiene dinero suficiente para comprar "+numeroDeCasas+" casas en "+c.getNombre().replaceAll("\\<.*?>", " "));
            }
        }

        public void comprarHotel(Item selectedItem) {
            Jugador j = getJugadorActual();
            Ciudad c = (Ciudad)getBoard().getCasillas().get(selectedItem.getId());
            panelCallback.log("J"+j.getId()+" quiere comprar Hotel en "+c.getNombre().replaceAll("\\<.*?>", " "));
            if(j.getDinero() >= c.getPrecioCasaHotel()) { // si tiene suficiente dinero
                if(banco.getNumhoteles() > 0) { // si banco tiene al menos 1 hotel disponible
                    c.setNumcasas(0); // devolver casas al banco
                    banco.setNumcasas(banco.getNumcasas() + 4);
                    banco.setNumhoteles(banco.getNumhoteles() - 1);
                    c.setHotel(true);
                    panelCallback.log("J" + j.getId() + " ha comprado Hotel en " + c.getNombre().replaceAll("\\<.*?>", " "));
                    siguienteTurno();
                }
            } else {
                panelCallback.log("J"+j.getId()+" no tiene dinero suficiente para comprar Hotel en "+c.getNombre().replaceAll("\\<.*?>", " "));
            }
        }

        public void continuar() {
            synchronized (o) {
                o.notifyAll();
            }
        }
    }

