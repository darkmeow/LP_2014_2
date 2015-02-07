
class plato:

    def __init__(self):
        self.menuNew = { }
        self.platos = [ ]
        self.registroMesa = { }

    def menu2dic(self):
        archi = open('menu.txt','rb')
        linea = archi.readline()
        count = 1
        while linea!="":
            datos = linea.split(',')
            comida = datos[0]
            precio = int(datos[1])
            self.menuNew[count] = [comida, precio]
            # print str(count) + ': '+ comida + ', Valor: ' + str(precio)
            count += 1
            linea = archi.readline()
        archi.close()
        return self.menuNew

    def menutxt(self):
        for k, v in self.menuNew.iteritems():
            print k, ": ", v

    def menuSeleccionado(self, cliente, mesa, platos):
        #guardar platos en registros
        self.registroMesa[mesa] = [cliente, platos]
