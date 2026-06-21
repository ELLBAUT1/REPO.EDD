package ar.edu.uns.cs.ed.tdas.PRACTICOS.TP3.EJ2;

import ar.edu.uns.cs.ed.tdas.excepciones.EmptyQueueException;
import ar.edu.uns.cs.ed.tdas.tdacola.Queue;

public class QueueArray<E> implements Queue<E>{
    /*
    enqueue()
    dequeue()
    front()
    size()
    isEmpty()
    */
   //Atributos de instancia
   protected E[] array;
   protected int front;
   protected int tail;
   protected int cantidad;

   //Servicios
    //Constructor
    public QueueArray(){
        array = (E[]) new Object[10];
        front = 0;
        tail = 0;
        cantidad = 0;
    }
    //Métodos
        //Comandos
        public void enqueue(E element){
            if(cantidad == array.length){
                E[] aux = (E[]) new Object[cantidad*2];
                int oldfront = front;
                for(int i = 0; i < cantidad; i++){ //lo que hacemos en este for, no es solo copiar los elementos, sino que le hacemos un unroll al arreglo, de manera que quede en el orden de la fila, y reseteamos los punteros;
                    aux[i] = array[oldfront];
                    oldfront = (oldfront + 1) % array.length;
                }
                array = aux;
                front = 0;
                tail = cantidad;
            }
            array[tail] = element;
            tail = (tail + 1) % array.length;
            cantidad++;
        }
        public E dequeue() throws EmptyQueueException{
            if(size() > 0){
            E retorno = array[front];
            array[front] = null;
            front = (front + 1) % array.length;
            cantidad--;
            return retorno;
            }
            else{
                throw new EmptyQueueException("Error. Queue vacía.");
            }
        }
        //Consultas
        public E front() throws EmptyQueueException{
            if(cantidad == 0){
                throw new EmptyQueueException("Error. Queue vacía.");
            }
            else{
                return array[front];
            }
        }
        public boolean isEmpty(){
            return cantidad == 0;
        }
        public int size(){
            return cantidad;
        }
}
