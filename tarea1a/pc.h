typedef struct pc {
    int id; // identificador
    char so[100]; // sistema operativo
    char dd[100]; // espacio disco duro
    bool disponible; // flag para disponible/ocupado
    bool cliente; // flag para cliente/servidor
    struct pc *servidor; // puntero a servidor (en caso de ser cliente)
    int conexiones; // cantidad de conexiones abiertas (en caso de ser servidor)
    struct pc *next; // puntero al siguiente pc (lista enlazada)
} pc;

// prototipos
void agregarPC();
void imprimirPCs();
