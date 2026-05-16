package ar.edu.uns.cs.ed.tdas.PRACTICOS.TP5.EJ2;

import ar.edu.uns.cs.ed.tdas.tdamapeo.Map;
import ar.edu.uns.cs.ed.tdas.tdalista.PositionList;
import ar.edu.uns.cs.ed.tdas.excepciones.InvalidKeyException;
import ar.edu.uns.cs.ed.tdas.Entry;
import ar.edu.uns.cs.ed.tdas.PRACTICOS.TP4.EJ1.DoubleLinkedList;
import ar.edu.uns.cs.ed.tdas.Position;
import java.util.Iterator;

public class MapeoAbierto<K,V> implements Map<K,V>{
    //Atributos de instancia
    protected PositionList<Entry<K, V>>[] hashmap;
    protected int cubetas;
    protected int cantelem;
    
    //Servicios
        //Constructor
        public MapeoAbierto(){
            hashmap = (PositionList<Entry<K, V>>[]) new PositionList[13];
            cubetas = 13;
            cantelem = 0;
            for(int i = 0; i < cubetas; i++){
                hashmap[i] = new DoubleLinkedList<Entry<K, V>>();
            }
        }
        
        //Métodos
            //Comandos
            public V put(K key, V value) throws InvalidKeyException{
                    checkKey(key);
                    int indice = hashCodeMapa(key);
                    PositionList<Entry<K,V>> listain = hashmap[indice];
                    for(Position<Entry<K,V>> pos: listain.positions()){
                        Entry<K,V> entrada = pos.element();
                        if(entrada.getKey().equals(key)){
                            V oldvalue = entrada.getValue();
                            Entry<K,V> actualizada = new Entrada<>(key, value);
                            listain.set(pos, actualizada);
                            return oldvalue;
                        }
                    }
                    Entry<K,V> nuevaentrada = new Entrada<>(key, value);
                    listain.addLast(nuevaentrada);
                    cantelem++;
                    if((float)cantelem / cubetas > 0.75){
                        rehash();
                    }
                    return null;
            }

            public V remove(K key) throws InvalidKeyException{
                checkKey(key);
                int indice = hashCodeMapa(key);
                PositionList<Entry<K,V>> listain = hashmap[indice];
                Iterable<Position<Entry<K,V>>> iterable = listain.positions();
                Iterator<Position<Entry<K,V>>> iterador = iterable.iterator();
                V retorno = null;
                while(iterador.hasNext()){
                    Position<Entry<K,V>> pos = iterador.next();
                    Entry<K,V> entrada = pos.element();
                    if(entrada.getKey().equals(key)){
                        retorno = entrada.getValue();
                        listain.remove(pos);
                        cantelem--;
                        return retorno;
                    }
                }
                return retorno;
            }

            //Consultas
            public int size(){
                return cantelem;
            }

            public boolean isEmpty(){
                return cantelem == 0;
            }

            public V get(K key) throws InvalidKeyException{
                checkKey(key);
                int indice = hashCodeMapa(key);
                PositionList<Entry<K,V>> listain = hashmap[indice];
                for(Position<Entry<K,V>> pos: listain.positions()){
                    Entry<K,V> entrada = pos.element();
                    if(entrada.getKey().equals(key)){
                        return entrada.getValue();
                    }
                }
                return null;
            }

            public Iterable<Entry<K,V>> entries(){
                PositionList<Entry<K,V>> retorno = new DoubleLinkedList<>();
                for(int i = 0; i < hashmap.length; i++){
                    PositionList<Entry<K,V>> listin = hashmap[i];
                    for(Position<Entry<K,V>> pos: listin.positions()){
                        Entry<K,V> entrada = pos.element();
                        retorno.addLast(entrada);
                    }
                }
                return retorno;
            }

            public Iterable<K> keys(){
                PositionList<K> retorno = new DoubleLinkedList<>();
                for(int i = 0; i < hashmap.length; i++){
                    PositionList<Entry<K,V>> listin = hashmap[i];
                    for(Position<Entry<K,V>> pos: listin.positions()){
                        Entry<K,V> entrada = pos.element();
                        retorno.addLast(entrada.getKey());
                    }
                }
                return retorno;
            }

            public Iterable<V> values(){
                PositionList<V> retorno = new DoubleLinkedList<>();
                for(int i = 0; i < hashmap.length; i++){
                    PositionList<Entry<K,V>> listin = hashmap[i];
                    for(Position<Entry<K,V>> pos: listin.positions()){
                        Entry<K,V> entrada = pos.element();
                        retorno.addLast(entrada.getValue());
                    }
                }
                return retorno;
            }

            protected int hashCodeMapa(K clave){
                return Math.abs(clave.hashCode()) % cubetas;
            }

            //Auxiliares
            protected boolean esPrimo(int numero) {
                // Casos base
                if (numero <= 1) return false;
                if (numero <= 3) return true;
                
                // Si es par, no es primo (excepto el 2 que ya cubrimos)
                if (numero % 2 == 0) return false;
                
                // Solo revisamos los números impares hasta la raíz cuadrada
                for (int i = 3; i <= Math.sqrt(numero); i += 2) {
                    if (numero % i == 0) {
                        return false; // Encontramos un divisor, no es primo
                    }
                }
                return true; // Si pasó todas las pruebas, es primo
            }

            protected int proximoPrimo(int capacidadActual) {
                int candidato = capacidadActual * 2;
                
                // Como multiplicamos por 2, "candidato" siempre va a ser par.
                // Le sumamos 1 para empezar a buscar desde el primer impar mayor al doble.
                candidato++; 
                
                // Mientras el candidato NO sea primo, le sumamos 2 (para probar el siguiente impar)
                while (!esPrimo(candidato)) {
                    candidato += 2;
                }
                
                return candidato;
            }

            protected void rehash() {
                int nuevosize = proximoPrimo(cubetas);
                PositionList<Entry<K, V>>[] oldhash = hashmap;
                cubetas = nuevosize;
                cantelem = 0;
                
                hashmap = (PositionList<Entry<K, V>>[]) new PositionList[cubetas]; 
                for (int i = 0; i < cubetas; i++) {
                    hashmap[i] = new DoubleLinkedList<Entry<K, V>>();
                }
                
                // Recorrer el arregloViejo y reinsertar todas sus entradas
                for (int i = 0; i < oldhash.length; i++){
                    PositionList<Entry<K,V>> oldlist = oldhash[i];
                    for (Position<Entry<K, V>> posentrada : oldlist.positions()) {
                        try {
                            Entry<K,V> entryactual = posentrada.element();
                            put(entryactual.getKey(), entryactual.getValue());
                        } 
                        catch (InvalidKeyException e) {
                        }
                    }
                }
            }

            protected K checkKey(K clave) throws InvalidKeyException{
                if(clave == null){
                    throw new InvalidKeyException("Clave Inválida");
                }
                return clave;
            }
}
