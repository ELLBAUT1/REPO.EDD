package ar.edu.uns.cs.ed.tdas.PRACTICOS.TP6.EJ1;

import ar.edu.uns.cs.ed.tdas.tdaarbol.Tree;
import ar.edu.uns.cs.ed.tdas.Position;
import ar.edu.uns.cs.ed.tdas.PRACTICOS.TP4.EJ1.DoubleLinkedList;
import ar.edu.uns.cs.ed.tdas.excepciones.InvalidPositionException;
import ar.edu.uns.cs.ed.tdas.excepciones.BoundaryViolationException;
import ar.edu.uns.cs.ed.tdas.excepciones.EmptyTreeException;
import ar.edu.uns.cs.ed.tdas.excepciones.InvalidOperationException;
import ar.edu.uns.cs.ed.tdas.tdalista.PositionList;
import java.util.Iterator;

public class Arbol<E> implements Tree<E>{
    //Atributos de instancia
    protected TNodo<E> root;
    protected int cantelem;

    //Servicios
        //Constructor
        public Arbol(){ //orden constante
            root = null;
            cantelem = 0;
        }

        //Métodos
            //Comandos
            public E replace(Position<E> v, E e) throws InvalidPositionException{ //orden constante
                TNodo<E> nodo = checkPosition(v);
                E retorno = nodo.element();
                nodo.setElement(e);
                return retorno;
            }

            public void createRoot(E e) throws InvalidOperationException{ //orden constante
                if(root != null){
                    throw new InvalidOperationException("Operacion inválida. El árbol ya tiene una raíz.");
                }
                else{
                    root = new TNodo<E>(e);
                    cantelem++;
                }
            }

            public Position<E> addFirstChild(Position<E> p, E e) throws InvalidPositionException{ //orden constante
                TNodo<E> nodo = checkPosition(p);
                TNodo<E> adicion = new TNodo<>(e, nodo);
                nodo.getHijos().addFirst(adicion);
                cantelem++;
                return adicion;
            }


            public Position<E> addLastChild(Position<E> p, E e) throws InvalidPositionException{ //orden constante
                TNodo<E> nodo = checkPosition(p);
                TNodo<E> adicion = new TNodo<>(e, nodo);
                nodo.getHijos().addLast(adicion);
                cantelem++;
                return adicion;
            }

            public Position<E> addBefore(Position<E> p, Position<E> rb, E e) throws InvalidPositionException{ //orden n
                TNodo<E> nodop = checkPosition(p);
                TNodo<E> nodoh = checkPosition(rb);
                if(nodoh.getPadre() != nodop){
                    throw new InvalidPositionException("Posicion inválida. La posicion padre no es padre de la posicion hermano derecho.");
                }
                PositionList<TNodo<E>> listahijos = nodop.getHijos();
                Iterator<Position<TNodo<E>>> iterador = listahijos.positions().iterator();
                Position<TNodo<E>> pos = null;
                TNodo<E> nodoact = null;
                while(iterador.hasNext() && nodoact != nodoh){
                    pos = iterador.next();
                    nodoact = pos.element();
                }
                if(nodoact != nodoh){
                    throw new InvalidPositionException("Posicion inválida. Árbol roto.");
                }
                TNodo<E> adicion = new TNodo<>(e, nodop);
                listahijos.addBefore(pos, adicion);
                cantelem++;
                return adicion;
            }

            public Position<E> addAfter(Position<E> p, Position<E> lb, E e) throws InvalidPositionException{ //orden n
                TNodo<E> nodop = checkPosition(p);
                TNodo<E> nodoh = checkPosition(lb);
                if(nodoh.getPadre() != nodop){
                    throw new InvalidPositionException("Posicion inválida. La posicion padre no es padre de la posicion hermano izquierdo.");
                }
                PositionList<TNodo<E>> listahijos = nodop.getHijos();
                Iterator<Position<TNodo<E>>> iterador = listahijos.positions().iterator();
                Position<TNodo<E>> pos = null;
                TNodo<E> nodoact = null;
                while(iterador.hasNext() && nodoact != nodoh){
                    pos = iterador.next();
                    nodoact = pos.element();
                }
                if(nodoact != nodoh){
                    throw new InvalidPositionException("Posicion inválida. Árbol roto.");
                }
                TNodo<E> adicion = new TNodo<>(e, nodop);
                listahijos.addAfter(pos, adicion);
                cantelem++;
                return adicion;
            }

            public void removeExternalNode(Position<E> p){ //orden n
                TNodo<E> nodo = checkPosition(p);
                if(!isExternal(nodo)){
                    throw new InvalidPositionException("Posicion inválida. El nodo no es hoja.");
                }
                if (nodo == root) {
                    root = null;
                    cantelem = 0;
                    return;
                }
                TNodo<E> nodop = nodo.getPadre();
                PositionList<TNodo<E>> listahijos = nodop.getHijos();
                Iterator<Position<TNodo<E>>> iterador = listahijos.positions().iterator();
                Position<TNodo<E>> pos = null;
                TNodo<E> nodoact = null;
                while(iterador.hasNext() && nodoact != nodo){
                    pos = iterador.next();
                    nodoact = pos.element();
                }
                if(nodoact != nodo){
                    throw new InvalidPositionException("Posicion inválida. Árbol roto.");
                }
                listahijos.remove(pos);
                nodo.setPadre(null);
                cantelem--;
            }

            public void removeInternalNode(Position<E> p) throws InvalidPositionException{ //orden n
                TNodo<E> nodo =  checkPosition(p);
                PositionList<TNodo<E>> listahijos = nodo.getHijos();
                if((nodo == root && ((listahijos.size() > 1) || listahijos.isEmpty())) || !isInternal(nodo)){
                    throw new InvalidPositionException("Posicion Inválida.");
                }
                if(nodo == root && listahijos.size() == 1){
                    root = listahijos.first().element();
                    cantelem--;
                }
                else{
                    PositionList<TNodo<E>> listahijosp = nodo.getPadre().getHijos();
                    Iterator<Position<TNodo<E>>> iterador = listahijosp.positions().iterator();
                    Position<TNodo<E>> pos = null;
                    TNodo<E> nodoact = null;
                    while(iterador.hasNext() && nodoact != nodo){
                        pos = iterador.next();
                        nodoact = pos.element();
                    }
                    if(nodoact != nodo){
                        throw new InvalidPositionException("Posicion inválida. Árbol roto.");
                    }
                    Position<TNodo<E>> aeliminar = pos;
                    Iterator<Position<TNodo<E>>> iterador2 = listahijos.positions().iterator();
                    Position<TNodo<E>> pos2 = null;
                    TNodo<E> nodohijo = null;
                    while(iterador2.hasNext()){
                        pos2 = iterador2.next();
                        nodohijo = pos2.element();
                        nodohijo.setPadre(nodo.getPadre());
                        listahijosp.addBefore(aeliminar, nodohijo);
                        
                    }
                    listahijosp.remove(aeliminar);
                    nodo.setPadre(null);
                    cantelem--;
                }
            }

            public void removeNode(Position<E> p) throws InvalidPositionException{ //orden n
                checkPosition(p);
                if(isInternal(p)){
                    removeInternalNode(p);
                }
                else if(isExternal(p)){
                    removeExternalNode(p);
                }
            }

            //Consultas
            public int size(){ //orden constante
                return cantelem;
            }

            public boolean isEmpty(){ //orden constante
                return cantelem == 0;
            }

            public Iterator<E> iterator(){ //orden n
                PositionList<E> listar = new DoubleLinkedList<>();
                for(Position<E> pos: positions()){
                    listar.addLast(pos.element());
                }
                return listar.iterator();
            }

            public Iterable<Position<E>> positions(){ //orden n
                PositionList<Position<E>> retorno = new DoubleLinkedList<>();
                if(!isEmpty()){
                    preorden(root, retorno);
                }
                return retorno;
            }

            public Position<E> root() throws EmptyTreeException{ //orden constante
                if(isEmpty()){
                    throw new EmptyTreeException("Error. Árbol vacio.");
                }
                return root;
            }

            public Position<E> parent(Position<E> v) throws InvalidPositionException, BoundaryViolationException{ //orden constante
                TNodo<E> nodo = checkPosition(v);
                if(nodo == root){
                    throw new BoundaryViolationException("Posicion inválida, la posición dada es la raíz.");
                }
                return nodo.getPadre();
            }

            public Iterable<Position<E>> children(Position<E> v) throws InvalidPositionException{ //orden n
                TNodo<E> nodo = checkPosition(v);
                PositionList<Position<E>> retorno = new DoubleLinkedList<>();
                for(TNodo<E> nodoh: nodo.getHijos()){
                    retorno.addLast(nodoh);
                }
                return retorno;
            }

            public boolean isInternal(Position<E> v) throws InvalidPositionException{ //orden constante
                TNodo<E> nodo = checkPosition(v);
                return nodo.getHijos().size() > 0;
            }

            public boolean isExternal(Position<E> v) throws InvalidPositionException{ //orden constante
                TNodo<E> nodo = checkPosition(v);
                return nodo.getHijos().size() == 0;
            }

            public boolean isRoot(Position<E> v) throws InvalidPositionException{ //orden constante
                TNodo<E> nodo = checkPosition(v);
                return nodo == root;
            }

            protected void preorden(TNodo<E> n, PositionList<Position<E>> lista){ //orden n
                lista.addLast(n);
                for(TNodo<E> nodo: n.getHijos()){
                    preorden(nodo, lista);
                }
            }

            protected TNodo<E> checkPosition(Position<E> nodo) throws InvalidPositionException{ //orden constante
                if(nodo == null || cantelem == 0){
                    throw new InvalidPositionException("Posicion Inválida.");
                }
                try{
                    return (TNodo<E>) nodo;
                }
                catch (ClassCastException e){
                    throw new InvalidPositionException("Posición Inválida.");
                }
            }
}   
