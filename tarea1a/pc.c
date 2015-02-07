#include <stdlib.h>
#include <stdio.h>
#include <stdbool.h>
#include <string.h>

#include "colores.h"
#include "pc.h"
#include "ciber.h"

void agregarPC() {
    int idCiber;
    int encontrado = -1; // valor de retorno de imprimirCiber
    char so[100]; // sistema operativo
    char dd[100]; // disco duro

    printf("%s2. Instalar computador%s\n", GREEN, RESETCOLOR);

    while(encontrado < 0) {
        printf("Ingrese ID del cibercafé: ");
        scanf("%d", &idCiber);
        encontrado = imprimirCiber(idCiber);
    }

    printf("Ingrese sistema operativo: ");
    scanf("%s", so);
    printf("Ingrese capacidad de almacenamiento: ");
    scanf("%s", dd);

    struct ciber *c = buscarCiber(idCiber);
    struct pc *primero = c->pc; // apuntar al primer pc del ciber
    struct pc *nuevo; // nuevo pc
    nuevo = (pc*)malloc(sizeof(pc)); // asignar memoria para el nuevo pc
    nuevo->id = primero->id + 1; // autoincrementar el id
    strcpy(nuevo->so, so);
    strcpy(nuevo->dd, dd);
    nuevo->next = primero;
    c->pc = nuevo;
}

void imprimirPCs() {
    int idCiber;
    int encontrado = -1;

    printf("%s6. Listar computadores%s\n", GREEN, RESETCOLOR);

    while(encontrado < 0) {
        printf("Ingrese ID del cibercafé: ");
        scanf("%d", &idCiber);
        encontrado = imprimirCiber(idCiber);
    }

    struct ciber *c = buscarCiber(idCiber);
    struct pc *compu = c->pc;

    do {
        printf("%s>> [PC] ID: %d SO: %s ALMACENAMIENTO: %s%s\n", YELLOW, compu->id, compu->so, compu->dd, RESETCOLOR);
        if(compu->next != NULL) compu = compu->next;
    } while(compu->next != NULL);
}
