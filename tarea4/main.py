#!/usr/local/bin/python
# -*- coding: utf-8 -*-
# -*- encoding: utf-8 -*-

import codecs
import numpy as np
import matplotlib.pyplot as plt
from matplotlib import *
from mesa import *
from mesas import *
from registro import *
from frec import *
from restaurante import *
from plato import *
import sys

platito = plato()
rest = restaurante()
mesita = mesa()
registration = registro()
menu = platito.menu2dic() # menu es un diccionario con los platos

palabrasBuenas = [ "bueno", "buena", "excelente", "grato", "grata", "sabroso", "sabrosa", "incrible", "ameno", "amena", "agradable"]
palabrasMalas = ["pesimo", "pesima", "asqueroso", "asquerosa", "desabrido", "desabrida", "mala", "malo", "horrible", "cruda", "crudo", "apestoso"]

def leerInput(prompt):
    variable = raw_input(prompt)
    while(len(variable) == 0):
        variable = raw_input(prompt)
    return variable
def setup():
# Inicializando
    mesita.insertarMesa(1, 2, 0)
    mesita.insertarDiccionario()
    mesita.insertarMesa(2, 2, 1)
    mesita.insertarDiccionario()
    mesita.insertarMesa(3, 4, 1)
    mesita.insertarDiccionario()
    mesita.insertarMesa(69, 4, 1)
    mesita.insertarDiccionario()

def mesaDisponible(cantidadMesa):
    numMesa = -1
    for k, v in mesas.iteritems():
            if v[1] == "Disponible":
                #print k, v
                if (v[0] >= cantidadMesa):
                    print "Mesa " + str(k) + " Disponible" #muestra las mesas disponibles para los acompañantes y el cliente
                    numMesa = k
                    break
                    # como existen disponibles elegimos una mesa
    return numMesa

# ##############################################
setup()
ans = True


while ans:
    print("""
    1. Recibir clientes
    2. Despedir clientes
    3. Obtener información
    4. Clientes frecuentes
    5. Ingresos
    6. Cerrar
    """)

    ans= raw_input("¿Qué desea hacer?\n")
    if not ans:
        ans = 0
    if ans == '1':
        print("\nSe encuentra en Recibir clientes")

        #cliente
        cliente = leerInput("¿Cuál es el nombre del cliente?\n")
        #paso el nombre del cliente a la funcion que mide la frecuencia
        recv_clientes(cliente)
        #acompañantes
        acomp = leerInput("¿Cuántas personas lo acompañan?\n")
        #se le debe mostrar una lista de mesas que tenga como mínimo su el total que usaran la mesa
        cantidadMesa = int(acomp) +1

        mesaVacia = mesaDisponible(cantidadMesa)
        if mesaVacia == -1: # en el caso de no existir mesas disponibles se pide disculpas
            print "Disculpe no quedan mesas disponibles"
        else:

            #Si existen mesas se selecciona una y se marca como no disponible
            mesas[mesaVacia][1] = 'No Disponible'
            print "\nMesa "+ str(mesaVacia) +" Seleccionada\n \n"

            platito.menutxt()

            for i in range(cantidadMesa):
                seleccion = int(leerInput("Seleccione plato {0}: \n".format(i+1)))
                rest.ingreso(int(menu[seleccion][1]))
                platito.platos.append(menu[seleccion][0])

            platosTupla = tuple(platito.platos)
            platito.menuSeleccionado(cliente, mesaVacia, platosTupla)

    elif ans == '2':
        print("\nSe encuentra en Despedir clientes\n")
        retiro = int(leerInput("Seleccione la mesa del cliente que se retira:\n"))
        opinion = leerInput("Opinión Cliente: \n")

        mesas[retiro][1] = "Disponible" #deja disponible la mesa que se retira
        registration.insert2registro(retiro, platito.registroMesa, opinion)


    elif ans == '3':
        print("\nSe encuentra en Obtener Información")

        print registration.dictRegistro

        if (len(registration.dictRegistro) == 0):
            print "Lo siento no existen datos para ver información estadística"
        else:
            countBuenas = [ ]
            countMalas = [ ]
            comidas = [ ]
            countPlatos = { } # se guarda plato vendido y cuantas veces
            totalPlatos = 0 # platos vendidos

            for dia in registration.dictRegistro.keys():
                for cliente in registration.dictRegistro[dia]:
                    for goodWord in palabrasBuenas:
                        for i in range(0, cliente[1].count(goodWord)):
                            countBuenas.append(palabrasBuenas.index(goodWord))

                    for badWord in palabrasMalas:
                        for i in range(0, cliente[1].count(badWord)):
                            countMalas.append(palabrasMalas.index(badWord))

                    for plato in cliente[2]:
                        if plato in countPlatos.keys():
                            countPlatos[plato] += 1
                        else:
                            countPlatos[plato] = 1
                    totalPlatos += len(cliente[2])

            for k, v in menu.iteritems():
                comidas.append(v[0])
            pct = [0, 0, 0, 0]
            pctLabels = ['', '', '', '']
            #print countPlatos
            i = 0
            for comida in countPlatos:
                pct[i] = (countPlatos[comida] * 100) / totalPlatos
                pctLabels[i] = comida
                i += 1
            print pct

            labels = [ ]
            print countBuenas, countMalas
            i = 0
            for b in palabrasBuenas:
                if palabrasMalas[i]:
                    labels.append(b+"\n /"+palabrasMalas[i])
                else:
                    labels.append(b)
                i += 1

            if len(countBuenas) > 0:
                plt.hist(numpy.asarray(countBuenas), label="palabras buenas", alpha=0.5, width=0.3)
            if len(countMalas) > 0:
                plt.hist(numpy.asarray(countMalas), label="palabras malas", alpha=0.5, width=0.3)

            ax = plt.axes()
            ax.set_xticklabels(labels)
            plt.legend()
            plt.show()

            colors = ['yellowgreen', 'gold', 'lightskyblue', 'lightcoral']
            plt.pie(pct, labels=pctLabels, colors=colors, shadow=True, startangle=90, autopct='%1.1f%%', explode=(0.1, 0.1, 0.1, 0.1))
            plt.axis('equal')
            plt.show()
    elif ans == '4':
        print("\nSe encuentra en Clientes frecuentes")

        mostrar_frec()

    elif ans == '5':
        print("\nSe encuentra en Ingresos")
        rest.mostrar_total()

    elif ans == '6':
        print("\nAdiós!")
        registration.DicRegistro2file()
        sys.exit(0)

    elif ans != "":
        print("\n No es una opción válida")
        ans = True
