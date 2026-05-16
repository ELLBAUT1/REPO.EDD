package ar.edu.uns.cs.ed.tdas.PRACTICOS.TP3.EJ1;

import ar.edu.uns.cs.ed.tdas.excepciones.EmptyStackException;

import ar.edu.uns.cs.ed.tdas.tdapila.Stack;

public class StackArray<E> implements Stack<E>{
    /*
    push()
    pop()
    top()
    size()
    isEmpty()
    */
   //Atributos de instancia
    protected E[] array;
    protected int cant;
   //Servicios
    //Constructor
    public StackArray(){
        array = (E[]) new Object[10];
        cant = 0;
    }
    //Métodos
        //Comandos
        public void push(E element){
            if(cant < array.length){
                array[cant] = element;
            }
            else{
                E[] newarray = (E[]) new Object[cant+1];
                for(int i = 0; i < cant; i++){
                    newarray[i] = array[i];
                }
                array = newarray;
                array[cant] = element;
            }
            cant++;
        }

        public E pop() throws ar.edu.uns.cs.ed.tdas.excepciones.EmptyStackException{
            if(size() > 0){
                E retorno = array[cant -1];
                array[cant-1] = null;
                cant--;
                return retorno;
            }
            else {
                throw new EmptyStackException("Error. Pila Vacia.");
            }
        }
        
        //Consultas
        public E top() throws ar.edu.uns.cs.ed.tdas.excepciones.EmptyStackException{
            if(size() == 0){
                throw new EmptyStackException("Error. Pila Vacia.");
            }
            else{
                return array[cant-1];
            }
        }
        
        public int size(){
            return cant;
        }

        public boolean isEmpty(){
            return cant == 0;
        }
}
