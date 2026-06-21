package ar.edu.uns.cs.ed.tdas.PRACTICOS.TP6.EJ1;

import ar.edu.uns.cs.ed.tdas.Position;
import ar.edu.uns.cs.ed.tdas.tdalista.PositionList;
import ar.edu.uns.cs.ed.tdas.PRACTICOS.TP4.EJ1.DoubleLinkedList;

public class TNodo<E> implements Position<E> {
    //Atributos de instancia
    E elemento;
    TNodo<E> padre;
    PositionList<TNodo<E>> hijos;

    //Servicios
        //Constructor
        public TNodo(E elem, TNodo<E> fa){
            elemento = elem;
            padre = fa;
            hijos = new DoubleLinkedList<TNodo<E>>();
        }

        public TNodo(E elem){
            this(elem, null);
        }

        //Métodos
            //Comandos
            public void setElement(E elem){
                elemento = elem;
            }
            
            public void setPadre(TNodo<E> father){
                padre = father;
            }
            
            //Consultas
            public E element(){
                return elemento;
            }

            public TNodo<E> getPadre(){
                return padre;
            }

            public PositionList<TNodo<E>> getHijos(){
                return hijos;
            }


}
