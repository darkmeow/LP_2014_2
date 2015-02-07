+ ---------------------------------- +
|       Tarea 2  LP-2014-2           |
|                                    |
|        Monopoly en Java            |
|                                    |
|      Barbarita Lara Martínez       |
|           201021048-0              |
|                                    |
+ ---------------------------------- +


Simulación Simplificada del juego de tablero Monopoly en Java.

Supuestos
=========

+ Se debe escribir "ant" en la raíz del directorio para ejecutar la Aplicación.

+ Las Propiedades parten teniendo un id_dueno = 0, eso significa que pertenecen al banco,
  hasta que se indique lo contrario.

+ Se ha ordenado de la siguiente forma:

        controller/* --> Controladores del juego
        event/*      --> Interfaz para llamar evento en el panel de control
        model/*      --> Modelo de datos
        view/*       --> Vistas

+ Los turnos se ordenan de mayor a menor, siendo mayor el que tiene la mayor cifra en los dados.
  Luego sigue en orden descendente. En el caso de tener la misma cifra en los dados, parte el que
  tiró primero. (No entendí el enunciado)

+ La imagen es solo referencial, se puede cambiar fácilmente de la siguiente forma:
    Cambiar la imagen en el directorio raíz "pini.jpg" y modificar en el archivo "view/Board.java"
    logo = ImageIO.read(new File("pini.png"));

+ Para ahorra código, la validación al cobrar renta, se hará al final del turno, si el dinero del jugador
  es negativo, pierde y hay que sacarlo del Arreglo de jugadores.

+ Al crear CartasSuerte se baraja el mazo.

+ Saldo Compra identifica el Saldo después de una supuesta compra.

+ "Se baraja el mazo de Suertes y se pone en el tablero." Existe el Mazo Suerte pero visualmente no está implementado el mazo en el tablero.

+ No se contabiliza el dinero del banco

+ Solo se pueden comprar casa si el jugador tiene todas las propiedades del mismo color

+ Se usa "hilo" para procesar el turno fuera del hilo principal de la parte gráfica.

+ Una forma fácil de comprobar funcionalidad, es en Comprable, setear el dueño como 1 (Jugador 1).

+ El panel se pinta del color del jugador de turno.

+ IMPORTANTE: En ‘build.properties’ modificar la variable “jdk.home.1.8” en la ruta correspondiente al jdk instalado en su propio computador.




