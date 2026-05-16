package ar.edu.uns.cs.ed.tdas.PRACTICOS.TP4.EJ1;

import java.util.Iterator;

import ar.edu.uns.cs.ed.tdas.Position;
import ar.edu.uns.cs.ed.tdas.excepciones.BoundaryViolationException;
import ar.edu.uns.cs.ed.tdas.excepciones.EmptyListException;
import ar.edu.uns.cs.ed.tdas.excepciones.InvalidPositionException;
import ar.edu.uns.cs.ed.tdas.tdalista.PositionList;

public class DoubleLinkedList<E> implements PositionList<E>{
    //Atributos de instancia
    protected DNode<E> primero;
    protected DNode<E> ultimo;
    protected int tamaño;

    //Servicios
        //Constructor
        public DoubleLinkedList(){
            primero = new DNode<E>(null, null, null);
            ultimo = new DNode<E>(primero, null, null);
            primero.setNext(ultimo);
            tamaño = 0;
        }
        

        //Métodos
            //Comandos
            public void addFirst(E element){
                DNode<E> oldFirst = primero.getNext();
                DNode<E> nuevo = new DNode<E>(primero, oldFirst, element);
                oldFirst.setPrevious(nuevo);
                primero.setNext(nuevo);
                tamaño++;
            }

            public void addLast(E element){
                DNode<E> oldLast = ultimo.getPrevious();
                DNode<E> nuevo = new DNode<E>(oldLast, ultimo, element);
                oldLast.setNext(nuevo);
                ultimo.setPrevious(nuevo);
                tamaño++;
            }

            public void addAfter(Position<E> p, E element) throws InvalidPositionException{
                if(isEmpty() || p == null){
                    throw new InvalidPositionException("Error. Lista vacía ó posicion inválida");
                }
                else{ 
                    DNode<E> current = checkPosition(p);
                    DNode<E> oldNext = current.getNext();
                    DNode<E> nuevo = new DNode<E>(current, oldNext, element);
                    current.setNext(nuevo);
                    oldNext.setPrevious(nuevo);
                    tamaño++;
                }
            }

            public void addBefore(Position<E> p, E element) throws InvalidPositionException{
                if(isEmpty() || p == null){
                    throw new InvalidPositionException("Error. Lista vacía ó posicion inválida");
                }
                else{
                    DNode<E> current = checkPosition(p);
                    DNode<E> oldPrevious = current.getPrevious();
                    DNode<E> nuevo = new DNode<E>(oldPrevious, current, element);
                    current.setPrevious(nuevo);
                    oldPrevious.setNext(nuevo);
                    tamaño++;
                }
            }

            public E remove(Position<E> p) throws InvalidPositionException{
                if(isEmpty() || p == null){
                        throw new InvalidPositionException("Error. Lista vacía ó posicion inválida");
                }
                else{        
                    DNode<E> nodo = checkPosition(p);
                    nodo.getPrevious().setNext(nodo.getNext());
                    nodo.getNext().setPrevious(nodo.getPrevious());
                    nodo.setNext(null);
                    nodo.setPrevious(null);
                    E retorno = p.element();
                    tamaño--;
                    return retorno;
                }
            }

            public E set(Position<E> p, E element) throws InvalidPositionException{
                if(isEmpty() || p == null){
                        throw new InvalidPositionException("Error. Lista vacía ó posicion inválida");
                }
                else{        
                    DNode<E> nodo = checkPosition(p);
                    E retorno = nodo.element();
                    nodo.setElement(element);
                    return retorno;
                }
            }

            //2)
            public void addscndscndtolast(E e1, E e2) throws EmptyListException{
                if(isEmpty()){
                    throw new EmptyListException("Error. Lista vacía.");
                }
                else if(tamaño == 1){
                    addLast(e1);
                    addLast(e2);
                }
                else{
                    addAfter(primero.getNext(), e1);
                    addBefore(ultimo.getPrevious(), e2);
                }
            }

            public void eliminar(PositionList<E> lista1, PositionList<E> lista2){
                Position<E> posicion1 = (!lista1.isEmpty())?
                                        lista1.first(): null;
                if(posicion1 != null){
                    for(E elem2: lista2){
                        if(lista1.isEmpty()){ 
                            break;
                        }
                        posicion1 = lista1.first();
                        while(posicion1 != null){
                            E elem1 = posicion1.element();
                            Position<E> posicionaeliminar = posicion1;
                            Position<E> siguiente = (posicion1 == lista1.last())?
                                                null : lista1.next(posicion1);
                            if(elem1 == elem2 || (elem1 != null && elem1.equals(elem2))){
                                lista1.remove(posicionaeliminar);
                            }
                            posicion1 = siguiente;

                            if(lista1.isEmpty()){
                                break;
                            }
                        }
                    }
                }
                Position<E> posicion2 = (!lista2.isEmpty())?
                                            lista2.last(): null;
                if(posicion2 != null){
                    while(posicion2 != null){
                        lista1.addLast(posicion2.element());
                        posicion2 = (posicion2 == lista2.first())?
                                        null: lista2.prev(posicion2);
                    }
                }
            }

            //Práctica parcial unsparciales
            //1)
            public PositionList<E> dividirLista(Position<E> pos) throws EmptyListException{
                if(isEmpty()){
                    throw new EmptyListException("Error. Lista vacía.");
                }
                Position<E> cursor = pos;
                PositionList<E> listaretorno = new DoubleLinkedList<>();
                while(cursor != null){
                    Position<E> poselim = cursor;
                    listaretorno.addLast(poselim.element());
                    cursor = (cursor == last())?
                                null: next(cursor);
                    remove(poselim);
                }
                return listaretorno;
            }

            //Consultas
            public int size(){
                return tamaño;
            }

            public boolean isEmpty(){
                return tamaño == 0;
            }

            public Position<E> first() throws EmptyListException{
                if(isEmpty()){
                    throw new EmptyListException("Error, Lista vacía.");
                }
                else{
                    Position<E> retorno = primero.getNext();
                    return retorno;
                }
            }

            public Position<E> last() throws EmptyListException{
                if(isEmpty()){
                    throw new EmptyListException("Error, Lista vacía.");
                }
                else{
                    Position<E> retorno = ultimo.getPrevious();
                    return retorno;
                }
            }

            public Position<E> next(Position<E> p) throws InvalidPositionException, BoundaryViolationException{
                if(isEmpty() || p == null){
                    throw new InvalidPositionException("Error. La posicion no corresponde a la lista ó la lista está vacía.");
                }
                DNode<E> nodo = checkPosition(p);
                if(nodo.getNext() == ultimo){
                    throw new BoundaryViolationException("Error. La posicion ingresada corresponde al último elemento de la lista.");
                }
                else{
                    Position<E> retorno = checkPosition(p).getNext();
                    return retorno;
                }
            }

            public Position<E> prev(Position<E> p) throws InvalidPositionException, BoundaryViolationException{
                if(isEmpty() || p == null){
                    throw new InvalidPositionException("Error. La posicion no corresponde a la lista ó la lista está vacía.");
                }
                DNode<E> nodo = checkPosition(p);
                if(nodo.getPrevious() == primero){
                    throw new BoundaryViolationException("Error. La posicion ingresada corresponde al primer elemento de la lista.");
                }
                else{
                    Position<E> retorno = nodo.getPrevious();
                    return retorno;
                }
            }

            private DNode<E> checkPosition(Position<E> p) throws InvalidPositionException {
                if (p == null || isEmpty()) {
                    throw new InvalidPositionException("Posición nula o lista vacía.");
                }
                try {
                    return (DNode<E>) p;
                } catch (ClassCastException e) {
                    throw new InvalidPositionException("La posición no es de tipo DNode.");
                }
            }

            public Iterator<E> iterator(){
                return new ElementIterator<E>(this);
            }

            public Iterable<Position<E>> positions(){
                PositionList<Position<E>> pl = new DoubleLinkedList<Position<E>>();
                if (tamaño!=0){
                    DNode<E> n = primero.getNext();
                    while(n != ultimo){
                        pl.addLast(n);
                        n = n.getNext();
                    }
                }
                return pl;  
            }

            //3)a)
            public boolean seencuentra(E e){
                Iterator<E> iterador = iterator();
                boolean encontro = false;
                while(iterador.hasNext() && !encontro){
                    E elemento = iterador.next();
                    encontro = (e == elemento) || (e != null && e.equals(elemento));
                }
                return encontro;
            }

            //3)b)
            public int cantesta(E e){
                Iterator<E> iterador = iterator();
                int cantidad = 0;
                while(iterador.hasNext()){
                    E elemento = iterador.next();
                        cantidad=((e == elemento) || (e != null && e.equals(elemento)))?
                            cantidad+1:cantidad;
                }
                return cantidad;
            }

            //3)c)
            public boolean estanveces(E x, int n){
                Iterator<E> iterador = iterator();
                int cantidad = 0;
                while(iterador.hasNext() && cantidad < n){
                    cantidad=(iterador.next() == x)?
                        cantidad+1:cantidad;
                }
                return cantidad >= n;
            }

            //4
            public PositionList<E> listarepetida(){
                PositionList<E> listanueva = new DoubleLinkedList<>();
                for(E elem: this){
                    listanueva.addLast(elem);
                    listanueva.addLast(elem);
                }
                return listanueva;
            }

            //5
            public Iterable<Character> eliminarrepetidos(PositionList<Character> l1, PositionList<Character> l2) throws InvalidPositionException, BoundaryViolationException, EmptyListException{
                PositionList<Character> listaretorno = new DoubleLinkedList<>();
                for(Character c1: l1){
                    if(l2.isEmpty()){
                        break;
                    }
                    Position<Character> posActual = l2.first();
                    while (posActual != null) {
                       Position<Character> posSiguiente = (posActual == l2.last())? 
                                                    null : l2.next(posActual);
                        Character c2 = posActual.element();
                        boolean sonIguales = (c1 == c2) || (c1 != null && c1.equals(c2));
                        if (sonIguales) {
                            l2.remove(posActual);
                            listaretorno.addLast(c2);
                        }
                        posActual = posSiguiente;
                    }
                }
                return listaretorno;
            }

            //6)a)
            public PositionList<E> intercalarlistas(PositionList<E> lista1, PositionList<E> lista2) {
                PositionList<E> retorno = new DoubleLinkedList<>();
                
                // Pedimos los iteradores de ambas listas
                Iterator<E> it1 = lista1.iterator();
                Iterator<E> it2 = lista2.iterator();

                // El ciclo sigue mientras a ALGUNA de las dos listas le queden elementos
                while (it1.hasNext() || it2.hasNext()) {
                    
                    // Si a la lista 1 le quedan elementos, sacamos uno y lo agregamos
                    if (it1.hasNext()) {
                        retorno.addLast(it1.next());
                    }
                    
                    // Si a la lista 2 le quedan elementos, sacamos uno y lo agregamos
                    if (it2.hasNext()) {
                        retorno.addLast(it2.next());
                    }
                }
                
                return retorno;
            }

            //6)b)
            public PositionList<Integer> intercalarlistasenteros(PositionList<Integer> lista1, PositionList<Integer> lista2){
                PositionList<Integer> retorno = new DoubleLinkedList<>();
                
                // Pedimos los iteradores de ambas listas
                Iterator<Integer> it1 = lista1.iterator();
                Iterator<Integer> it2 = lista2.iterator();
                
                Integer valor1 = (it1.hasNext())?
                                    it1.next(): null;
                Integer valor2 = (it2.hasNext())?
                                    it2.next():null;

                Integer ultimoIn = null;

                // El ciclo sigue mientras las dos listas le queden elementos
                while (valor1 != null && valor2 != null) {
                    Integer aInsertar;
                    if (valor1 < valor2) {
                        aInsertar = valor1;
                        valor1 = (it1.hasNext())?
                                    it1.next():null;
                    }
                    else if (valor1 > valor2) {
                        aInsertar = valor2;
                        valor2 = (it2.hasNext())?
                                it2.next():null;
                    }
                    else{
                        aInsertar = valor1;
                        valor1 = (it1.hasNext())?
                                it1.next():null;
                        valor2 = (it2.hasNext())?
                                it2.next():null;

                    }
                    if(ultimoIn == null || !ultimoIn.equals(aInsertar)){
                        retorno.addLast(aInsertar);
                        ultimoIn = aInsertar;
                    }
                }
                //Acá ya se vació alguna de las dos listas (pq salio del while anterior)
                while(valor1 != null){
                    if(ultimoIn == null || !ultimoIn.equals(valor1)){
                        retorno.addLast(valor1);
                        ultimoIn = valor1;
                    }
                    valor1 = (it1.hasNext())?
                                it1.next(): null;
                }
                while(valor2 != null){
                    if(ultimoIn == null || !ultimoIn.equals(valor2)){
                        retorno.addLast(valor2);
                        ultimoIn = valor2;
                    }
                    valor2 = (it2.hasNext())?
                                it2.next(): null;
                }
                return retorno;
            }

            //Práctica parcial
            //3)
            public boolean iguales(PositionList<E> lin, PositionList<E> lan){
                boolean sonIguales = false;
                if(lin.size() == lan.size()){
                     sonIguales = true;
                    Iterator<E> iterador1 = lin.iterator();
                    Iterator<E> iterador2 = lan.iterator();
                    while(iterador1.hasNext() && sonIguales){
                        E elemento1 = iterador1.next();
                        E elemento2 = iterador2.next();
                        sonIguales = (elemento1 == elemento2)?
                                        true: false;    
                    }   
                }
                return sonIguales;
            }

            //4)
            public boolean incluida(PositionList<E> lan, PositionList<E> lin){
                if (lan.isEmpty()) return true; // Una lista vacía siempre está incluida
                if (lin.size() < lan.size()) return false;
                Position<E> anclaLin = lin.first();
                while (anclaLin != null) {
                    Position<E> cursorLin = anclaLin;
                    Position<E> cursorLan = lan.first();
                    boolean contenido = true;

                    // Comparamos la secuencia a partir del ancla
                    while (cursorLan != null && cursorLin != null && contenido) {
                        if (cursorLin.element() != cursorLan.element()) {
                            contenido = false; 
                        } 
                        else {
                            // Avanzamos ambos cursores esquivando excepciones
                            cursorLin = (cursorLin == lin.last())? 
                                    null : lin.next(cursorLin);
                            cursorLan = (cursorLan == lan.last())?
                                    null : lan.next(cursorLan);
                        }   
                    }
                    if (contenido && cursorLan == null) {
                        return true;
                    }
                    // Si falló, movemos el ancla un paso adelante y volvemos a empezar
                    anclaLin = (anclaLin == lin.last()) ? 
                                null : lin.next(anclaLin);
                }
                return false;
            }

            

}

