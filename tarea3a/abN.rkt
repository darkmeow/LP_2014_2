#lang racket
;;OPERADORES
;Crea nodo del arbol
(define (nodo dato left right) 
  (list dato left right));lista con datos y sus respectivos hijos
;Crea arbol vacio
(define arb_vacio null) 
;Ver dato del nodo
(define (ver_dato arbol) 
  (car arbol))
;Hijo izquierdo
(define (arb_izq arbol) 
  (car (cdr arbol)))
;Hijo derecho
(define (arb_der arbol) 
  (car (cdr (cdr arbol))))
;Verifica si es un árbol vacío
(define (arb_vacio? arbol) 
  (null? arbol))
;Verifica si nodo es una hoja (no tiene hijos)
(define (arb_hoja? arbol)
  (and (arb_vacio? (arb_izq arbol))
       (arb_vacio? (arb_der arbol))))
;Inserta dato al arbol
(define (insert_dato dato arbol) 
  (cond
    ((arb_vacio? arbol) (nodo dato arb_vacio arb_vacio))
    ((< dato (ver_dato arbol))
     (nodo (ver_dato arbol) 
           (insert_dato dato (arb_izq arbol))
           (arb_der arbol)))
    ((> dato (ver_dato arbol))
     (nodo (ver_dato arbol) (arb_izq arbol)
           (insert_dato dato (arb_der arbol)) ))
    ((= dato (ver_dato arbol)) arbol)))
;Inserta datos de una lista en un arbol
(define (insert_list datos arbol) 
  (if (null? datos)
      arbol
      (insert_list (cdr datos) (insert_dato (car datos) arbol))))

;Desencolar lista
(define (desencolar lista nombre_arch)
  (letrec ([salida (open-output-file nombre_arch #:exists 'append)]
           [desenc (lambda (l)
                     (cond [(null? l)
                            (close-output-port salida)]
                           [else
                            (display (car l) salida)
                            (display "\n" salida)
                            (desenc (cdr l))]))]
           )
    (desenc lista)
    ))
;Primera Función: (abN? arbol N) Retorna #t si arbol es un árbol binario N, 
;sino #f. Considere que puede no ser binario y/o ser N.

(define auxRet #t)
(define (abN? arbol N)
 (if 
     (eqv? arbol 'null)
     
     (void)
     
     (if (equal? (length arbol) N)
         (begin (abN? (arb_izq arbol) N) (abN? (arb_der arbol) N))
         (set! auxRet #f)
     )
 )
  auxRet
)

;Segunda Función: (crear-ab1 datos) Retorna un árbol binario 1 creado a partir
;de los números la lista datos de tal forma que todo árbol izquierdo tenga
;un número menor que el número del padre y todo árbol derecho tenga uno mayor.
(define (crear-ab1 datos) ;inserta los datos de la lista en un arbol vacio
  (insert_list datos arb_vacio))

(define lista null)

(define (lista_arb arbol)  
  (if (arb_vacio? arbol)
      '()
      (append
       (lista_arb (arb_izq arbol))   
       (list (ver_dato arbol))
       (lista_arb (arb_der arbol))))
  )
;Tercera Función: (crear-ab1-archivo archivo) Retorna un árbol binario 1 creado a partir
;de los números en archivo, los cuales están uno por línea. Se comporta igual que crear-ab1.
(define (crear-ab1-archivo file)
  (crear-ab1 (file->list file))
)
;Cuarta Función: (guardar-abN arbol archivo) Guarda los datos de cada nodo de árbol, uno por línea, en archivo. Debe recorrerse en postorden.
(define (guardar-abN arbol archivo)
    (desencolar (lista_arb arbol) archivo)
  )
;Quinta Función: (operar operarcion arbol1 arbol2) Retorna un árbol binario N producto de aplicar una operación entre los dos árboles binarios N. 
;Ambos árboles tiene igual N y exactamente la misma estructura, por lo que el árbol retornado también tiene la misma estructura.
(define (operar operacion arbol1 arbol2)
  (cond
    ((arb_vacio? arbol1) arbol1)
    ((pair? arbol1) (cons (operar operacion (car arbol1) (car arbol2))
                        (operar operacion (cdr arbol1) (cdr arbol2))))
    (else (operacion arbol1 arbol2))))

;;EJEMPLOS
(define arbol1 (nodo 2 arb_vacio arb_vacio))
(define arbol2 (crear-ab1 '(12 11 10 22 50 2 14 1 7))) ;para crear un arbol y guardarlo en arbol2
(define arbol3 (crear-ab1 '(12 11 10 22 50 2 14 1 7))) ;para crear un arbol y guardarlo en arbol3
(define arbol4 (crear-ab1 '(1 3 4 7 6 8 10 14 13)))
(define a1 '(2 (7 (2 null null) (6 (5 null null) (11 null null))) (5 null (9 (4 null null) null))))

;(guardar-abN arbol2 "ordenado") ;guarda el arbol1 en un archivo de nombre ordenado
;(crear-ab1-archivo "ordenado") ;crea arbol desde un archivo
;(operar + arbol2 arbol3) ;como sumar dos arboles
;(operar - arbol2 arbol3) ;como restar dos arboles
;(operar * arbol2 arbol3) ;como multiplicar dos arboles
;(operar / arbol2 arbol3) ;como dividir dos arboles
;(define a1 '(2 (7 (2 null null) (6 (5 null null) (11 null null))) (5 null (9 (4 null null) null))))


