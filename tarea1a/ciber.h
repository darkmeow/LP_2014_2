typedef struct ciber {
    int id; // identificador
    int ppm; // precio por minuto
    int total; // dinero recaudado
    char direccion[100]; // dirección
    struct pc *pc; // puntero a estructura pc
    struct ciber *next; // puntero al siguiente ciber (lista enlazada)
} ciber;

extern struct ciber *listaCiber; // variable global para acceder a la lista de ciber

// prototipos
void agregarCiber();
void imprimirListaCiber();
int imprimirCiber(int id);
struct ciber *buscarCiber(int id);
void cerrarCiber();
void guardar();
