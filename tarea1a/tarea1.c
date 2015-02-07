#include <stdlib.h>
#include <stdio.h>
#include <strings.h>
#include <stdbool.h>

#include "colores.h"
#include "pc.h"
#include "ciber.h"

struct ciber *listaCiber;

void menu() {
    int cmd;
    do {
        printf("+-----------------------+\n");
        printf("|1. Abrir cibercafé\t|\n");
        printf("|2. Instalar computador\t|\n");
        printf("|3. Cerrar cibercafé\t|\n");
        printf("|4. Desechar computador\t|\n");
        printf("|5. Listar cibercafés\t|\n");
        printf("|6. Listar computadores\t|\n");
        printf("|7. Asignar computador\t|\n");
        printf("|8. Desocupar computador|\n");
        printf("|9. Guardar\t\t|\n");
        printf("|10. Generar reporte\t|\n");
        printf("|11. Salir\t\t|\n");
        printf("+-----------------------+\n");
        printf("> ");
        scanf("%d", &cmd);
        if(cmd >= 1 && cmd < 12) {
            switch(cmd) {
                case 1:
                    agregarCiber();
                    break;
                case 2:
                    agregarPC();
                    break;
                case 3:
                    cerrarCiber();
                    break;
                case 5:
                    imprimirListaCiber();
                    break;
                case 6:
                    imprimirPCs();
                    break;
                case 9:
                    guardar();
                    break;
                case 11:
                    exit(0);
                    break;
                default:
                    printf("%s*** Menú no implementado ***%s\n", RED, RESETCOLOR);
                    menu();
                    break;
            }
        } else {
            printf("%s*** Menú no implementado ***%s\n", RED, RESETCOLOR);
            menu();
        }
    } while(cmd != 0);
}

int main() {
    listaCiber = (ciber*)malloc(sizeof(ciber)); // asignar memoria para el primer ciber
    menu();
    return 0;
}
