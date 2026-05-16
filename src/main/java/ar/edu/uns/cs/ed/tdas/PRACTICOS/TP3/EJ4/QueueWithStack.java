package ar.edu.uns.cs.ed.tdas.PRACTICOS.TP3.EJ4;

import ar.edu.uns.cs.ed.tdas.excepciones.EmptyQueueException;
import ar.edu.uns.cs.ed.tdas.tdacola.Queue;
import java.util.Stack;

public class QueueWithStack<E> implements Queue<E>{
    protected Stack<E> stack;
    protected int cantidad;
    
    //Servicios
        //Constructor
        public QueueWithStack(){
            stack = new Stack();
            cantidad = 0;
        }

        //Métodos
            //Comandos
            public void enqueue(E elem){
                Stack<E> aux = new Stack(); 
                for(E elemstack: stack){
                    aux.push(stack.pop());
                }
                aux.push(elem);
                cantidad++;
                for(E elemaux: aux){
                    stack.push(aux.pop());
                }
            }
            
            public E dequeue() throws EmptyQueueException{
                if(cantidad == 0){
                    throw new EmptyQueueException("Error. Queue vacía.");
                }
                else{
                    return stack.pop();
                }
            }

            //Consultas
            public E front() throws EmptyQueueException{
                if(cantidad == 0){
                    throw new EmptyQueueException("Error. Queue vacía.");
                }
                else{
                    return stack.peek();
                }
            }

            public int size(){
                return cantidad;
            }

            public boolean isEmpty(){
                return cantidad == 0;
            }
}
