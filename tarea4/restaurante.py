import pickle

class restaurante:
    def __init__(self):
        self.ingresos = self.leerLista()

    def leerLista(self):
        try:
            archi = open("ingresos.txt","rb")
            ingresos = pickle.load(archi)
            return ingresos
        except IOError:
            return []

    def guardarLista(self):
        archi = open("ingresos.txt", "wb")
        pickle.dump(self.ingresos, archi)

    def ingreso(self, valor):
        self.ingresos.append(valor)
        self.guardarLista()

    def mostrar_total(self):
        total = 0
        for valor in self.ingresos:
            total += valor
        print("el total recaudado es: ")
        print(total)

