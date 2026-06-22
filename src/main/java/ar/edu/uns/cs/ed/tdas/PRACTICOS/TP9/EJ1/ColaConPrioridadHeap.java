package ar.edu.uns.cs.ed.tdas.PRACTICOS.TP9.EJ1;

import ar.edu.uns.cs.ed.tdas.excepciones.EmptyPriorityQueueException;
import ar.edu.uns.cs.ed.tdas.excepciones.InvalidKeyException;
import java.util.Comparator;
import ar.edu.uns.cs.ed.tdas.Entry;
import ar.edu.uns.cs.ed.tdas.tdacolaconprioridad.PriorityQueue;

public class ColaConPrioridadHeap<K,V> implements PriorityQueue<K,V> {
    //Atributos de instancia
    protected Entrada<K,V>[] elementos;
    protected Comparator<K> comparador;
    protected int size;

    //Servicios
        //Constructor
        @SuppressWarnings("unchecked")
        public ColaConPrioridadHeap(Comparator<K> comp){
            elementos = (Entrada<K,V>[]) new Entrada[10000];
            comparador = comp;
            size = 0;
        }

        //Métodos
            //Comandos
            @SuppressWarnings("unchecked")
            public Entry<K,V> insert(K key, V value) throws InvalidKeyException{
                checkKey(key);
                Entrada<K,V> ins = new Entrada<>(key, value);
                if(size == elementos.length){
                    Entrada<K,V>[] nuevo = (Entrada<K,V>[]) new Entrada[size*2];
                    for(int i =0; i < size; i++){
                        nuevo[i] = elementos[i];
                    }
                    elementos = nuevo;
                }
                elementos[size+1] = ins;
                size++;
                acomodarHaciaArriba(size);
                return ins;
            }

            public Entry<K,V> removeMin() throws EmptyPriorityQueueException{
                if(size == 0){
                    throw new EmptyPriorityQueueException("Error. Cola vacía.");
                }
                Entrada<K,V> ret = elementos[1];
                elementos[1] = elementos[size];
                elementos[size] = null;
                size--;
                acomodarHaciaAbajo(1);
                return ret;
            }

            protected void acomodarHaciaArriba(int pos){
                boolean acomode = false;
                while(pos > 1 && !acomode){
                    if(comparador.compare(elementos[pos].getKey(), elementos[pos/2].getKey()) < 0){
                        Entrada<K,V> entrada = elementos[pos/2];
                        elementos[pos/2] = elementos[pos];
                        elementos[pos] = entrada;
                        pos = pos/2;
                    }
                    else{
                        acomode = true;
                    }
                }
            }

            @SuppressWarnings("unused")
            protected void acomodarHaciaAbajo(int pos){
                boolean acomode = false;
                while(pos*2 <= size && !acomode){
                    Entrada<K,V> entrada = elementos[pos];
                    int posHijoIzquierdo = pos * 2;
                    int posHijoDerecho = pos * 2 + 1;
                    int posMenor;
                    // ¿Tiene hijo derecho y es menor que el izquierdo?
                    if (posHijoDerecho <= size && comparador.compare(elementos[posHijoDerecho].getKey(), elementos[posHijoIzquierdo].getKey()) < 0) {
                        posMenor = posHijoDerecho;
                    } else {
                        // O no tiene derecho, o el izquierdo es menor
                        posMenor = posHijoIzquierdo;
                    }

                    // Si el padre es mayor que el hijo menor, intercambiamos
                    if (comparador.compare(elementos[pos].getKey(), elementos[posMenor].getKey()) > 0) {
                        Entrada<K, V> aux = elementos[pos];
                        elementos[pos] = elementos[posMenor];
                        elementos[posMenor] = aux;
                        pos = posMenor; // Seguimos bajando
                    } else {
                        acomode = true; // Ya está ordenado
                    }
                }
            }

            //Consultas
            public int size(){
                return size;
            }

            public boolean isEmpty(){
                return size == 0;
            }

            public Entry<K,V> min() throws EmptyPriorityQueueException{
                if(size == 0){
                    throw new EmptyPriorityQueueException("Error. Cola vacía.");
                }
                return elementos[1];
            }

            protected K checkKey(K clave) throws InvalidKeyException{
                if(clave == null){
                    throw new InvalidKeyException("Clave nula");
                }
                return clave;
            }

                
    //Clase anidada
    @SuppressWarnings("hiding")
    protected class Entrada<K, V> implements Entry<K,V>{
        //Atributos de instancia
        protected K clave;
        protected V valor;
        
        //Servicios
            //Constructor
            public Entrada(K key, V value){
                clave = key;
                valor = value;
            }

            //Métodos
                //Comandos
                public K getKey(){ 
                    return clave;
                }

                public V getValue(){ 
                    return valor; 
                }
        
                //Consultas
                public void setKey(K key){ 
                    clave = key; 
                }

                public void setValue(V value){ 
                    valor = value; 
                }
    }
}