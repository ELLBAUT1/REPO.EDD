package ar.edu.uns.cs.ed.tdas.PRACTICOS.TP3.EJ5;

import ar.edu.uns.cs.ed.tdas.PRACTICOS.TP3.EJ1.StackArray;
import ar.edu.uns.cs.ed.tdas.PRACTICOS.TP3.EJ2.QueueArray;
import ar.edu.uns.cs.ed.tdas.PRACTICOS.TP3.Persona;
public class ejerciciostp2 <E>{
    //InvertirStackArray
    public Persona[] invertir(Persona[] A){
        StackArray<Persona> StackArray =  new StackArray<>();
        for(int i = 0; i < A.length; i++){
            if(A[i] != null){
                StackArray.push(A[i]);
            }
        }
        int i = 0;
        while(!StackArray.isEmpty()){
            A[i] = StackArray.pop();
            i++;
        }
        return A;
    }

    //Filtrar Impar
    public QueueArray<Integer> queueimpar(QueueArray<Integer> kiwi){
        QueueArray<Integer> retorno = new QueueArray();
        while(!kiwi.isEmpty()){
            Integer entero = kiwi.dequeue();
            if(entero % 2 == 1)
                retorno.enqueue(entero);
        }
        return retorno;
    }

    //Productos Pilas
    public StackArray<E> prodpilas(StackArray<E> p1, StackArray<E> p2){
        StackArray<E> StackArrayprod = new StackArray();
        while(!p1.isEmpty() && !p2.isEmpty()){
            if(!p1.isEmpty()){
                StackArrayprod.push(p1.pop());
            }
            if(!p2.isEmpty()){
                StackArrayprod.push(p2.pop());
            }
        }
        return StackArrayprod;
    }

    //Entero Mayor
    public int mayorentero(QueueArray<Integer> q){
        int mayor = q.dequeue();
        QueueArray<Integer> aux = new QueueArray();
        while(!q.isEmpty()){
            Integer elemento = q.dequeue();
            if(elemento > mayor){
                mayor = elemento;
            }
            aux.enqueue(elemento);
        }
        while(!aux.isEmpty()){
            q.enqueue(aux.dequeue());
        }
        return mayor;
    }
}
