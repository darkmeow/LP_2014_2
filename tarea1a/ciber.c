#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <string.h>

#include "colores.h"
#include "pc.h"
#include "ciber.h"

// agrega un ciber al inicio de lista global (push)
void agregarCiber() {
    int ppm;
    char direccion[100];

    printf("%s1. Agregar cibercafé%s\n", GREEN, RESETCOLOR);
    printf("Ingrese una dirección: ");
    scanf("%s", direccion);
    printf("Ingrese precio por minuto: ");
    scanf("%d", &ppm);

    struct ciber *primero = listaCiber; // apuntar al primer ciber de lista global
    struct ciber *nuevo; // nuevo ciber
    nuevo = (ciber*) malloc(sizeof(ciber)); // asignar memoria para el nuevo ciber

    // llenar los datos del nuevo ciber y apuntar el siguiente al primero de la lista
    nuevo->id = primero->id + 1; // autoincrementar el id
    nuevo->ppm = ppm;
    strcpy(nuevo->direccion, direccion); // copiamos el string
    nuevo->next = primero;
    nuevo->pc = (pc*)malloc(sizeof(pc)); // asignamos memoria para el primer pc

    // reemplazar el primero de la lista por el nuevo ciber
    listaCiber = nuevo;
}

// imprime todos los ciber
void imprimirListaCiber() {
    struct ciber *c = listaCiber; // apuntar c a la lista global
    printf("%s5. Listar cibercafé%s\n", GREEN, RESETCOLOR);
    if(listaCiber->id == 0){
        printf("%s*** Cibercafés no encontrados ***%s\n", RED, RESETCOLOR);
        
    } else {
        do {
            printf("%s>> [CIBER] ID: %d PPM: %d DIRECCION: %s DINERO: %d%s\n", YELLOW, c->id, c->ppm, c->direccion, c->total, RESETCOLOR);
            if(c->next != NULL) c = c->next; // reasignar c al siguiente ciber
        } while(c->next != NULL); // mientras el siguiente no sea nulo
    }
}

struct ciber *buscarCiber(int id) {
    // recorre la lista de ciber y busca el id y retorna un puntero al encontrarla
    struct ciber *c = listaCiber; // apuntar c a la lista global
    do {
        if(id == c->id)
            return c;
        if(c->next != NULL) c = c->next; // reasignar c al siguiente ciber
    } while(c->next != NULL);

    return NULL;
}

int imprimirCiber(int id) {
    struct ciber *c;
    c = buscarCiber(id);
    if(c != NULL) {
        printf("%s>> [CIBER] ID: %d PPM: %d DIRECCION: %s DINERO: %d%s\n", YELLOW, c->id, c->ppm, c->direccion, c->total, RESETCOLOR);
        return 0; // ok
    } else {
        printf("%s*** Cibercafé no encontrado ***%s\n", RED, RESETCOLOR);
        return -1; // error
    }
}

void cerrarCiber(){
    struct ciber *c;
    int idCiber;
    int encontrado = -1; // valor de retorno de imprimirCiber

    while(encontrado < 0) {
        printf("Ingrese ID del cibercafé: ");
        scanf("%d", &idCiber);
        encontrado = imprimirCiber(idCiber);
    }
    //c = buscarCiber(idCiber); //ciber que quiero borrar
        
        c = buscarCiber(idCiber); //ciber que quiero borrar
        do{
        struct pc *primero = c->pc;
        struct pc *temp;
        
        temp = primero;
        free(temp);
        if(c->pc->next != NULL) c->pc = primero->next;
        }while(c->pc->next != NULL);
    
        //ahora borro ciber

        struct ciber *prev;
        struct ciber *temp;

        temp = listaCiber; // asignar temp al primero
        
        if(c->next != NULL){
            while(temp->next != NULL && temp->next->id != idCiber){
                if(temp->next != NULL) temp = temp->next;
            }
            prev = temp;
            prev->next = c->next;
            free(c);
        } else{
            free(listaCiber);
            listaCiber = (ciber *)malloc(sizeof(ciber));
        }        
}

// guarda la estructura al archivo cibercafes
void guardar() {
    char *buffer; // cadena para almacenar la lista serializada
    int pos = 0; // posición en el buffer
    FILE *archivo = fopen("cibercafes", "wb+");
    struct ciber *c = listaCiber; // apuntar c a la lista global
    printf("%s9. Guardar%s\n", GREEN, RESETCOLOR);
    buffer = (char*)malloc(sizeof(ciber) + 1);
    do {
        memcpy(&buffer[pos], &c->id, sizeof(c->id));
        //printf("%d - %s\n",pos, &buffer[pos]);
        pos += sizeof(c->id);
        memcpy(&buffer[pos], &c->ppm, sizeof(c->ppm));
        //printf("%d - %s\n",pos, &buffer[pos]);
        pos += sizeof(c->ppm);
        memcpy(&buffer[pos], &c->direccion, sizeof(c->direccion));
        //printf("%d - %s - %lu\n",pos, &buffer[pos], sizeof(c->direccion));
        pos += strlen(c->direccion);
        // escribir el buffer al disco
        fwrite(buffer, sizeof(ciber), 1, archivo);
        pos = 0; // reiniciar pos
        memset(&buffer[0], 0, sizeof(buffer)); // limpiar buffer
        if(c->next != NULL) c = c->next;
    } while(c->next != NULL);
    fclose(archivo);
}

