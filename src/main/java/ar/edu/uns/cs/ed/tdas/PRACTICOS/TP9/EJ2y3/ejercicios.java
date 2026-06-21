package ar.edu.uns.cs.ed.tdas.PRACTICOS.TP9.EJ2y3;

import ar.edu.uns.cs.ed.tdas.PRACTICOS.TP9.EJ1.ColaConPrioridadHeap;
import ar.edu.uns.cs.ed.tdas.tdacolaconprioridad.PriorityQueue;
import ar.edu.uns.cs.ed.tdas.tdadiccionario.*;
import ar.edu.uns.cs.ed.tdas.Entry;

public class ejercicios<K,V> {
    //Ejercicio 2
    public void HeapSort(int[] array){
        PriorityQueue<Integer,Integer> cola = new ColaConPrioridadHeap<>();
        for(int elem: array){
            cola.insert(elem, elem);
        }
        for(int i = 0; i < array.length; i++){
            array[i] = cola.removeMin().getValue();
        }
    }
    
    //Ejercicio 3
    public int[] valOrdenados(Dictionary<Character,Integer> d){
        PriorityQueue<Integer,Integer> cola = new ColaConPrioridadHeap<>();
        for(Entry<Character, Integer> entrada: d.entries()){
            cola.insert(entrada.getValue(), entrada.getValue());
        }
        int[] retorno = new int[cola.size()];
        for(int i = 0; i < retorno.length; i++){
            retorno[i] = cola.removeMin().getValue();
        }
        return retorno;
    }
}
