package ar.edu.uns.cs.ed.tdas.PRACTICOS.REPASO2DOPARCIAL;

import ar.edu.uns.cs.ed.tdas.PRACTICOS.TP5.EJ2.Entrada;
import ar.edu.uns.cs.ed.tdas.tdalista.PositionList;
import ar.edu.uns.cs.ed.tdas.excepciones.InvalidKeyException;
import ar.edu.uns.cs.ed.tdas.tdadiccionario.Dictionary;
import ar.edu.uns.cs.ed.tdas.Entry;
import ar.edu.uns.cs.ed.tdas.PRACTICOS.TP4.EJ1.DoubleLinkedList;
import ar.edu.uns.cs.ed.tdas.Position;
import java.util.Iterator;

public class DiccionarioAbierto2 <K,V> implements Dictionary<K,V>{
    //Atributos de instancia
    protected PositionList<Entry<K,V>>[] hashmap;
    protected int cubetas;
    protected int cantelem;

    //Servicios
        //Constructor
        public DiccionarioAbierto2(){ //orden constante
            hashmap = (PositionList<Entry<K,V>>[]) new Object[13];
            cubetas = 13;
            cantelem = 0;
            for(int i = 0; i < 13; i++){
                hashmap[i] = new DoubleLinkedList<>();
            }
        }

        //Métodos
            //Comandos
            public Entry<K,V> insert(K key, V value) throws InvalidKeyException{ //orden constante normalmente, en el peor caso orden n por el rehash
                K clave = checkKey(key);
                Entry<K,V> entry = new Entrada<K,V>(clave, value);
                hashmap[hashCodeDiccionario(clave)].addLast(entry);
                cantelem++;
                if((float) cantelem / cubetas < 0.75){
                    rehash();
                }
                return entry;
            }

            public Entry<K,V> remove(Entry<K,V> e) throws InvalidKeyException{ //orden constante
                PositionList<Entry<K,V>> listre = hashmap[hashCodeDiccionario(checkKey(e.getKey()))];
                Iterator<Position<Entry<K,V>>> iterador = listre.positions().iterator();
                while(iterador.hasNext()){
                    Position<Entry<K,V>> pos = iterador.next();
                    Entry<K,V> entry = pos.element();
                    if(entry == e){
                        listre.remove(pos);
                        cantelem--;
                        return entry;
                    }
                }
                throw new InvalidKeyException("Clave no encontrada");
            }
            //Consultas
            public int size(){ //orden constante
                return cantelem;
            }

            public boolean isEmpty(){ //orden constante
                return cantelem == 0;
            }

            public Entry<K,V> find(K key){ //orden constante
                K clave = checkKey(key);
                PositionList<Entry<K,V>> listaf = hashmap[hashCodeDiccionario(clave)];
                for(Position<Entry<K,V>> pos: listaf.positions()){
                    if(pos.element().getKey().equals(key)){
                        return pos.element();
                    }
                }
                return null;
            }

            public Iterable<Entry<K,V>> findAll(K key){ //orden constante
                K clave = checkKey(key);
                PositionList<Entry<K,V>> listaret = new DoubleLinkedList<>();
                PositionList<Entry<K,V>> listaf = hashmap[hashCodeDiccionario(clave)];
                for(Position<Entry<K,V>> pos: listaf.positions()){
                    if(pos.element().getKey().equals(key)){
                        listaret.addLast(pos.element());
                    }
                }
                return null;
            }

            public Iterable<Entry<K,V>> entries(){ //orden n
                PositionList<Entry<K,V>> listaret = new DoubleLinkedList<>();
                for(int i = 0; i < cubetas; i++){
                    PositionList<Entry<K,V>> listain = hashmap[i];
                    for(Position<Entry<K,V>> pos: listain.positions()){
                        listaret.addLast(pos.element());
                    }
                }
                return listaret;
            }

            protected void rehash(){ //orden n
                PositionList<Entry<K,V>>[] oldhash = hashmap;
                int nuevotamaño = proximoPrimo(cubetas);
                hashmap = (PositionList<Entry<K,V>>[]) new Object[nuevotamaño];
                cubetas = nuevotamaño;
                cantelem = 0;
                for(int i = 0; i < oldhash.length; i++){
                    PositionList<Entry<K,V>> listain = oldhash[i];
                    for(Position<Entry<K,V>> pos: listain.positions()){
                        insert(pos.element().getKey(), pos.element().getValue());
                    }
                }
            }
            
            protected int hashCodeDiccionario(K clave){ //orden constante
                return Math.abs(clave.hashCode()) % cubetas;
            }

            protected K checkKey(K clave){ //orden constante
                if(clave == null){
                    throw new InvalidKeyException("Clave Inválida.");
                }
                else{
                    return clave;
                }
            }

            protected boolean esPrimo(int numero){ //orden n
                if(numero <= 1){
                    return false;
                }
                if(numero <= 3){
                    return true;
                }
                else{
                    for(int i = 3; i < Math.sqrt(numero); i+= 2){
                        if(numero % i == 0){
                            return false;
                        }
                    }
                    return true;
                }
            }

            protected int proximoPrimo(int numero){ //orden n
                int retorno = numero*2 + 1;
                while(!esPrimo(retorno)){
                    retorno = retorno + 2;
                }
                return retorno;
            }

            //EJERCICIO 1
            public int todas(K key) throws InvalidKeyException{ //orden constante
                checkKey(key); //c1
                PositionList<Entry<K,V>> lista = hashmap[hashCodeDiccionario(key)]; //c2
                Iterator<Entry<K,V>> iterador = lista.iterator(); //c3
                int contador = 0; //c4
                while(iterador.hasNext()){ //c5
                    Entry<K,V> entrada = iterador.next(); //c6
                    if(entrada.getKey().equals(key)){ //c7
                        contador++; //c8
                    }
                }
                return contador; //c9
            }
}
