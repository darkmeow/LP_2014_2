#!/usr/local/bin/python
# -*- coding: utf-8 -*-

from mesas import *

class mesa:

    def __init__(self):
        self.valores = None
        self.numeroMesa = None

    def insertarMesa(self, numeroMesa, capacidad, indicador):
        valores = [ ]

        if (type(capacidad) == int):
            valores.append(capacidad)
        else:
            print "Debe ingresar un la capacidad de la mesa"

        if (indicador == 0):
            valores.append("No Disponible")
        elif (indicador == 1):
            valores.append("Disponible")
        else:
            print("Debe ingresar 0 ó 1")

        if (type(numeroMesa) == int):
            self.numeroMesa = numeroMesa
        else:
            print "Debe ingresar el número de la mesa!"

        self.valores = valores

    def printMesa(self):
        print(self.numeroMesa, self.valores)


    def insertarDiccionario(self):
        mesas[self.numeroMesa] = self.valores

    # def mostrarDiccionarioMesas(mesas):
    #    print mesas






