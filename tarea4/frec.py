# FUNCIONES PARA MEDIR FRECUENCIA DE CLIENTES
import pickle
import operator

def guardar(clientes):
    f=file("frecuencia.txt","wb");
    pickle.dump(clientes, f)
    f.close()

def leerDict():
    try:
        archi = open("frecuencia.txt","rb")
        clientes = pickle.load(archi)
        return clientes
    except IOError:
        return {}

def recv_clientes(cliente):
    clientes = leerDict()
    if cliente in clientes.keys():
        clientes[str(cliente)] = int(clientes[cliente]) + 1
    else:
        clientes[str(cliente)] = 1
    guardar(clientes)
    
def mostrar_frec():
    clientes = leerDict()
    print "Los 3 clientes mas frecuentes son: "
    if len(clientes) > 0:
        clientesSorted = sorted(clientes.items(), key=operator.itemgetter(1), reverse=True)
        i = 0
        for cliente in clientesSorted:
            if i < 3:
                print cliente
            i += 1
    else:
        print "No hay clientes"

