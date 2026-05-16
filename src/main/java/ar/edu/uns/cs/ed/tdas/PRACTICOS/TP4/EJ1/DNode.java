package ar.edu.uns.cs.ed.tdas.PRACTICOS.TP4.EJ1;

import ar.edu.uns.cs.ed.tdas.Position;

public class DNode <E> implements Position<E>{
    //Atributos de instancia
    protected DNode<E> anterior;
    protected DNode<E> siguiente;
    protected E elemento;
    
    //Servicios
        //Constructor
        public DNode(Position<E> ant, Position<E> sig, E elem){
            anterior = (DNode<E>) ant;
            siguiente = (DNode<E>) sig;
            elemento = elem;
        }

        //Métodos
            //Comandos
            public void setNext(DNode<E> nodo){
                siguiente = nodo;
            }

            public void setPrevious(DNode<E> nodo){
                anterior = nodo;
            }

            public void setElement(E elem){
                elemento = elem;
            }

            //Consultas

            public DNode<E> getNext(){
                return siguiente;
            }

            public DNode<E> getPrevious(){
                return anterior;
            }

            public E element(){
                return elemento;
            }
}
