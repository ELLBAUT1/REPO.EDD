package ar.edu.uns.cs.ed.tdas.PRACTICOS.TP4.EJ1;

import java.util.Iterator;
import java.util.NoSuchElementException;
import ar.edu.uns.cs.ed.tdas.Position;
import ar.edu.uns.cs.ed.tdas.tdalista.PositionList;

public class ElementIterator<E> implements Iterator<E> {
    //Atributos de instancia
    protected PositionList<E> list; // lista a iterar
    protected Position<E> cursor; // Posición del elemento corriente donde está el iterator
    //Servicios
        //Constructor
        public ElementIterator(PositionList<E> l){
            list = l;
            if (list.isEmpty()) {
                cursor = null;
            } 
            else{
                cursor = list.first();
            }
        }

        //Métodos
            //Consultas
            public boolean hasNext(){
                return cursor != null;
            }

            public E next() throws NoSuchElementException{
                if (cursor == null){
                    throw new NoSuchElementException("No existe un siguiente elemento.");
                }
                E toReturn = cursor.element();
                cursor = (cursor == list.last()) ?
                null : list.next(cursor);
                return toReturn;
            }
}

