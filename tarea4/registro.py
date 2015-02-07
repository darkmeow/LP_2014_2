#!/usr/local/bin/python
# -*- coding: utf-8 -*-
# -*- encoding: utf-8 -*-

import time
import pickle

# un registro representará una transacción guardada en registro.pkl,
# con la fecha, el cliente, la opinión y los platos. Notar que para una
# fecha puede haber varias transacciones

class registro:
    def __init__(self):
        self.cliente = None
        self.opinion = None
        self.platos = None
        self.hora = time.strftime("%d/%m/%y")
        archivoRegistro = open("registro.pkl", "rb")
        self.dictRegistro = pickle.load(archivoRegistro)

    def insert2registro(self, retiro, diccionario, opinion):
        self.cliente = diccionario[retiro][0]
        self.opinion = opinion
        self.platos = diccionario[retiro][1]
        t = (self.cliente, self.opinion, self.platos)  #tupla
        lista = [t]
        if self.hora in self.dictRegistro.keys():
            self.dictRegistro[self.hora].append(t)
        else:
            self.dictRegistro[self.hora]=lista
        print self.dictRegistro


    def printDicRegistro(self, dicRegistro):
        print dicRegistro

    def DicRegistro2file(self):
        f = open("registro.pkl", "wb")
        pickle.dump(self.dictRegistro, f)
        f.close()
