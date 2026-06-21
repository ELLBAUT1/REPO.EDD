package ar.edu.uns.cs.ed.tdas.PRACTICOS.TP10.EJ2;

import ar.edu.uns.cs.ed.tdas.Position;
import ar.edu.uns.cs.ed.tdas.tdamapeo.Map;
import ar.edu.uns.cs.ed.tdas.excepciones.InvalidKeyException;
import ar.edu.uns.cs.ed.tdas.PRACTICOS.TP4.EJ1.DoubleLinkedList;
import java.util.Comparator;
import ar.edu.uns.cs.ed.tdas.Entry;

public class MapConABB<K,V> implements Map<K,V>{
    //Atributos de instancia
    protected NodoABB<K,V> raiz;
    protected int size;
    protected Comparator<K> comparador;

    //Servicios
        //Constructor
        public MapConABB(Comparator<K> comp){
            raiz = new NodoABB<K,V>(null, null);
            size = 0;
            comparador = comp;
        }

        //Métodos
            //Comandos
            public V put(K key, V value) throws InvalidKeyException{
                checkKey(key);
                NodoABB<K,V> nodo = buscarAux(key, raiz);
                if(nodo.element() != null){
                    V oldvalue = nodo.element().getValue();
                    nodo.element().setValue(value);
                    if (nodo.eliminado()) {
                        nodo.setEliminado(false);
                        size++;
                        return null; // Retorna null porque para el usuario es una clave nueva
                    }
                    return oldvalue;
                }
                else{
                    nodo.setRotulo(new Entrada<K,V>(key, value));
                    nodo.setHijoIzq(new NodoABB<>(null, nodo));
                    nodo.setHijoDer(new NodoABB<>(null, nodo));
                    size++;
                    return null;
                }
            }

            public V remove(K key) throws InvalidKeyException{
                checkKey(key);
                NodoABB<K,V> nodo = buscarAux(key, raiz);
                if(nodo.element() == null || nodo.eliminado()){
                    throw new InvalidKeyException("Error. Clave inválida.");
                }
                V oldvalue = nodo.element().getValue();
                nodo.setEliminado(true);
                size--;
                return oldvalue;
            }
            //Consultas
            public int size(){
                return size;
            }

            public boolean isEmpty(){
                return size == 0;
            }

            public V get(K key) throws InvalidKeyException{
                checkKey(key);
                NodoABB<K,V> nodo = buscarAux(key, raiz);
                if(nodo.element() == null || nodo.eliminado()){
                    throw new InvalidKeyException("Error. Clave inválida.");
                }
                return nodo.element().getValue();
            }

            public Iterable<K> keys(){
                DoubleLinkedList<K> lista = new DoubleLinkedList<>();
                recorrerYMapearKeys(raiz, lista);
                return lista;
            }

            public Iterable<V> values(){
                DoubleLinkedList<V> lista = new DoubleLinkedList<>();
                recorrerYMapearValues(raiz, lista);
                return lista;
            }

            public Iterable<Entry<K,V>> entries(){
                DoubleLinkedList<Entry<K,V>> lista = new DoubleLinkedList<>();
                recorrerYMapearEntries(raiz, lista);
                return lista;
            }

            protected void recorrerYMapearKeys(NodoABB<K,V> nodo, DoubleLinkedList<K> lista){
                if(nodo.hasLeft()){
                    recorrerYMapearKeys(nodo.getHijoIzq(), lista);
                }
                if(nodo.element() != null && !nodo.eliminado()){
                    lista.addLast(nodo.element().getKey());
                }
                if(nodo.hasRight()){
                    recorrerYMapearKeys(nodo.getHijoDer(), lista);
                }
            }

            protected void recorrerYMapearValues(NodoABB<K,V> nodo, DoubleLinkedList<V> lista){
                if(nodo.hasLeft()){
                    recorrerYMapearValues(nodo.getHijoIzq(), lista);
                }
                if(nodo.element() != null && !nodo.eliminado()){
                    lista.addLast(nodo.element().getValue());
                }
                if(nodo.hasRight()){
                    recorrerYMapearValues(nodo.getHijoDer(), lista);
                }
            }

            protected void recorrerYMapearEntries(NodoABB<K,V> nodo, DoubleLinkedList<Entry<K,V>> lista){
                if(nodo.hasLeft()){
                    recorrerYMapearEntries(nodo.getHijoIzq(), lista);
                }
                if(nodo.element() != null && !nodo.eliminado()){
                    lista.addLast(nodo.element());
                }
                if(nodo.hasRight()){
                    recorrerYMapearEntries(nodo.getHijoDer(), lista);
                }
            }

            public boolean sucesorInorder(K key1, K key2) throws InvalidKeyException {
                checkKey(key1);
                checkKey(key2);
                NodoABB<K,V> nodo1 = buscarAux(key1, raiz);
                if (nodo1.element() == null || nodo1.eliminado() || !nodo1.element().getKey().equals(key1)) {
                    throw new InvalidKeyException("Error. Clave 1 no encontrada o inválida.");
                }
                NodoABB<K,V> sucesor = nodo1.getHijoDer();
                while (sucesor.getHijoIzq().element() != null) {
                    sucesor = sucesor.getHijoIzq();
                }
                return sucesor.element().getKey().equals(key2) && !sucesor.eliminado();
            }

            public void checkKey(K key){
                if(key == null){
                    throw new InvalidKeyException("Error. Clave nula.");
                }
            }

            protected NodoABB<K,V> buscarAux(K key, NodoABB<K,V> node){ //Este me dice cual es el nodo al que le tengo que reemplazar su valor o en su defecto, al dummy que le tengo que meter la nueva entrada
                if(node.element() == null){
                    return node;
                }
                int resultcomp = comparador.compare(key, node.element().getKey());
                if(resultcomp == 0){
                    return node;
                }
                else if(resultcomp < 0){
                    return buscarAux(key, node.getHijoIzq()); //es mas chico que el que estoy viendo, su lugar esta mas a la izquierda
                }
                else{
                    return buscarAux(key, node.getHijoDer()); //es mas grande que el que estoy viendo, su lugar esta mas a la derecha
                }
            }
    
    //Clases auxiliares
    protected class NodoABB<K,V> implements Position<Entry<K,V>>{
        protected Entrada<K,V> rotulo;
        protected NodoABB<K,V> padre;
        protected NodoABB<K,V> izq;
        protected NodoABB<K,V> der;
        protected boolean eliminado;

        //Servicios
            //Constructor
            public NodoABB(Entrada<K,V> rot, NodoABB<K,V> padre){
                rotulo = rot;
                this.padre = padre;
                izq = null;
                der = null;
                eliminado = false;
            }

            //Métodos
                //Comandos
                public void setRotulo(Entrada<K,V> rot){
                    rotulo = rot;
                }

                public void setPadre(NodoABB<K,V> father){
                    padre = father;
                }

                public void setHijoIzq(NodoABB<K,V> left){
                    izq = left;
                }

                public void setHijoDer(NodoABB<K,V> right){
                    der = right;
                }

                public void setEliminado(boolean estado){
                    eliminado = estado;
                }

                //Consultas
                public Entrada<K,V> element(){
                    return rotulo;
                }

                public NodoABB<K,V> getHijoIzq(){
                    return izq;
                }

                public NodoABB<K,V> getHijoDer(){
                    return der;
                }

                public NodoABB<K,V> getPadre(){
                    return padre;
                }

                public boolean hasLeft(){
                    return getHijoIzq() != null;
                }

                public boolean hasRight(){
                    return getHijoDer() != null;
                }

                public boolean eliminado(){
                    return eliminado;
                }
    }

    protected class Entrada<K,V> implements Entry<K,V>{
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
                public void setValue(V value){
                    valor = value;
                }

                //Consultas
                public K getKey(){
                    return clave;
                }

                public V getValue(){
                    return valor;
                }
    }
}
