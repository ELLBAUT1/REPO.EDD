package ar.edu.uns.cs.ed.tdas.PRACTICOS.TP6.EJ2;

import ar.edu.uns.cs.ed.tdas.Position;

public class BTNodo<E> implements Position<E>{
    //Atributos de instancia
    protected E elemento;
    protected BTNodo<E> padre;
    protected BTNodo<E> left;
    protected BTNodo<E> right;

    //Servicios
        //Constructor
        public BTNodo(E elem, BTNodo<E> fa, BTNodo<E> izq, BTNodo<E> der){
            elemento = elem;
            padre = fa;
            left = izq;
            right = der;
        }

        //Métodos
            //Comandos
            public void setElement(E elem){
                elemento = elem;
            }

            public void setPadre(BTNodo<E> fa){
                padre = fa;
            }

            public void setLeft(BTNodo<E> l){
                left = l;
            }

            public void setRight(BTNodo<E> r){
                right = r;
            }

            //Consultas
            public E element(){
                return elemento;
            }

            public BTNodo<E> getPadre(){
                return padre;
            }

            public BTNodo<E> getLeft(){
                return left;
            }

            public BTNodo<E> getRight(){
                return right;
            }

}
