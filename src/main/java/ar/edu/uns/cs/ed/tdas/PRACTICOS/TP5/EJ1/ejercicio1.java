package ar.edu.uns.cs.ed.tdas.PRACTICOS.TP5.EJ1;

import ar.edu.uns.cs.ed.tdas.tdalista.PositionList;
import ar.edu.uns.cs.ed.tdas.tdamapeo.Map;
import ar.edu.uns.cs.ed.tdas.Entry;
import ar.edu.uns.cs.ed.tdas.PRACTICOS.TP4.EJ1.DoubleLinkedList;
import ar.edu.uns.cs.ed.tdas.excepciones.InvalidKeyException;
import ar.edu.uns.cs.ed.tdas.tdadiccionario.Dictionary;
import ar.edu.uns.cs.ed.tdas.Position;
import java.util.Iterator;
import ar.edu.uns.cs.ed.tdas.PRACTICOS.TP5.EJ2.*;
import ar.edu.uns.cs.ed.tdas.PRACTICOS.TP5.EJ3.*;

public class ejercicio1{
    //a
    //Creamos el metodo con el que creamos un pair con el record
    protected record Pair<L, R>(L left, R right) {}

    //Nuestra clase
    public PositionList<Pair<Integer,Integer>> mapsCoincidenKey(Map<Integer,Integer> M1, Map<Integer,Integer> M2){
        Iterable<Entry<Integer,Integer>> iterable1 = M1.entries();
        Iterator<Entry<Integer, Integer>> iterador = iterable1.iterator();
        PositionList<Pair<Integer,Integer>> listaretorno = new DoubleLinkedList<>();
        while(iterador.hasNext()){
            Entry<Integer, Integer> entrada = iterador.next();
            try{
                Integer valor2 = M2.get(entrada.getKey());
                if(!entrada.getValue().equals(valor2)){
                    Pair<Integer, Integer> par1 = new Pair<Integer, Integer>(entrada.getKey(), entrada.getValue());
                    Pair<Integer, Integer> par2 = new Pair<Integer, Integer>(entrada.getKey(), valor2);
                    listaretorno.addLast(par1);
                    listaretorno.addLast(par2);
                }
            }
            catch (InvalidKeyException e){
            }
        }
        return listaretorno;
    }

    //b
    public <K, V> boolean m1enm2(Map<K,V> m1, Map<K,V> m2){
        Iterable<K> keys1 = m1.keys();
        Iterator<K> iterador1 = keys1.iterator();
        boolean retorno = true;
        while(iterador1.hasNext() && retorno){
            try{
                K key = iterador1.next();
                m2.get(key);
            }
            catch (InvalidKeyException e){
                retorno = false;
            }
        }
        return retorno;
    }

    //c
    public <K,V> Dictionary<K,V> acomodar(Dictionary<K,V> d){
        Map<K,V> mapa = new MapeoAbierto<K,V>();
        Iterable<Entry<K,V>> iterable = d.entries();
        Iterator<Entry<K,V>> iterador = iterable.iterator();
        while(iterador.hasNext()){
            Entry<K,V> entrada = iterador.next();
            mapa.put(entrada.getKey(), entrada.getValue());
        }
        Dictionary<K,V> retorno = new DiccionarioAbierto<>();
        Iterable<Entry<K,V>> iterable2 = mapa.entries();
        Iterator<Entry<K,V>> iterador2 = iterable2.iterator();
        while(iterador2.hasNext()){
            Entry<K,V> entrada = iterador2.next();
            retorno.insert(entrada.getKey(), entrada.getValue());
        }
        return retorno;
    }

    //d
    public Map<Character,Integer> mapearlista(PositionList<Character> lista){
            Iterable<Position<Character>> posiciones = lista.positions();
            Iterator<Position<Character>> iterador = posiciones.iterator();
            Map<Character, Integer> retorno = new MapeoAbierto<>();
            while(iterador.hasNext()){
                Position<Character> posicion = iterador.next();
                Character clave = posicion.element();
                Integer cantidad = 0;
                Iterator<Position<Character>> iterador2 = posiciones.iterator();
                while(iterador2.hasNext()){
                    Position<Character> posicion2 = iterador2.next();
                    Character elemento = posicion2.element();
                    if(elemento.equals(clave)){
                        cantidad++;
                    }
                }
                retorno.put(clave, cantidad);
            }
            return retorno;
    }

    //alternativa del d, que es de orden n en lugar de n^2, siendo n el size de la lista, basicamente si veo un elemento lo pongo en el mapa, si ya estaba reemplazo el value por value+1, sino le asigno 1
    /*
    public Map<Character,Integer> mapearlista(PositionList<Character> lista){
    Map<Character, Integer> retorno = new OpenMap<>();
    
    for(Position<Character> posicion : lista.positions()){
        Character clave = posicion.element();
        
        try {
            // Intentamos obtener la cantidad actual de esa letra
            Integer cantidadActual = retorno.get(clave);
            // Si no saltó al catch, significa que ya existía. Le sumamos 1.
            retorno.put(clave, cantidadActual + 1);
            
        } catch (InvalidKeyException e) {
            // Si la clave no existía en el mapa, es la primera vez que vemos esta letra
            retorno.put(clave, 1);
        }
    }
    
    return retorno;
}
    */
}
