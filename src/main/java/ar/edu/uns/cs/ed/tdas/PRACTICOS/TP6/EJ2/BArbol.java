package ar.edu.uns.cs.ed.tdas.PRACTICOS.TP6.EJ2;

import ar.edu.uns.cs.ed.tdas.tdaarbolbinario.BinaryTree;
import ar.edu.uns.cs.ed.tdas.Position;
import ar.edu.uns.cs.ed.tdas.PRACTICOS.TP4.EJ1.DoubleLinkedList;
import ar.edu.uns.cs.ed.tdas.PRACTICOS.TP6.EJ1.TNodo;
import ar.edu.uns.cs.ed.tdas.excepciones.InvalidPositionException;
import ar.edu.uns.cs.ed.tdas.excepciones.BoundaryViolationException;
import ar.edu.uns.cs.ed.tdas.excepciones.EmptyTreeException;
import ar.edu.uns.cs.ed.tdas.excepciones.InvalidOperationException;
import ar.edu.uns.cs.ed.tdas.tdalista.PositionList;
import java.util.Iterator;

public class BArbol<E> implements BinaryTree<E>{
    //Atributos de instancia
    protected BTNodo<E> root;
    protected int cantelem;

    //Servicios
        //Constructor
        public BArbol(){
            root = null;
            cantelem = 0;
        }

        //Métodos
            //Comandos
            public E replace(Position<E> v, E e){ //orden constante
                BTNodo<E> nodo = checkPosition(v);
                E retorno = nodo.element();
                nodo.setElement(e);
                return retorno;
            }

            public void createRoot(E e) throws InvalidOperationException{ //orden constante
                if(root != null){
                    throw new InvalidOperationException("El árbol ya tiene una raíz.");
                }
                root = new BTNodo<>(e, null, null, null);
                cantelem++;
            }

            public Position<E> addFirstChild(Position<E> p, E e) throws InvalidPositionException{ //orden constante
                BTNodo<E> nodo = checkPosition(p);
                if(nodo.getLeft() != null){
                    throw new InvalidPositionException("La posicion ya tiene un primer hijo.");
                }
                BTNodo<E> izq = new BTNodo<>(e, nodo, null, null);
                nodo.setLeft(izq);
                cantelem++;
                return izq;
            }

            public Position<E> addLastChild(Position<E> p, E e) throws InvalidPositionException{ //orden constante
                BTNodo<E> nodo = checkPosition(p);
                if(nodo.getRight() != null){
                    throw new InvalidPositionException("La posicion ya tiene un último hijo.");
                }
                BTNodo<E> der = new BTNodo<>(e, nodo, null, null);
                nodo.setRight(der);
                cantelem++;
                return der;
            }

            public Position<E> addBefore(Position<E> p, Position<E> rb, E e) throws InvalidPositionException{ //orden constante
                BTNodo<E> nodop = checkPosition(p);
                BTNodo<E> nodor = checkPosition(rb);
                if(nodop != nodor.getPadre() || nodop.getRight() != nodor || nodop.getLeft() != null){
                    throw new InvalidPositionException("Posición inválida.");
                }
                BTNodo<E> l = new BTNodo<>(e, nodop, null, null);
                nodop.setLeft(l);
                cantelem++;
                return l;
            }

            public Position<E> addAfter(Position<E> p, Position<E> lb, E e) throws InvalidPositionException{ //orden constante
                BTNodo<E> nodop = checkPosition(p);
                BTNodo<E> nodol = checkPosition(lb);
                if(nodop != nodol.getPadre() || nodop.getLeft() != nodol || nodop.getRight() != null){
                    throw new InvalidPositionException("Posición inválida.");
                }
                BTNodo<E> r = new BTNodo<>(e, nodop, null, null);
                nodop.setRight(r);
                cantelem++;
                return r;
            }

            public void removeExternalNode(Position<E> p) throws InvalidPositionException{ //orden constante
                BTNodo<E> nodo = checkPosition(p);
                if(!isExternal(nodo)){
                    throw new InvalidPositionException("Posición inválida.");
                }
                if(nodo == root){
                    root = null;
                    cantelem = 0;
                    return;
                }
                BTNodo<E> nodop = nodo.getPadre();
                if(nodop.getLeft() == nodo){
                    nodop.setLeft(null);
                    cantelem--;
                    return;
                }
                if(nodop.getRight() == nodo){
                    nodop.setRight(null);
                    cantelem--;
                    return;
                }
                else{
                    throw new InvalidPositionException("Árbol roto.");
                }
            }

            public void removeInternalNode(Position<E> p) throws InvalidPositionException{ //orden constante
                BTNodo<E> nodo = checkPosition(p);
                if(nodo == root){
                    if(nodo.getLeft() == null && nodo.getRight() != null){
                        root = nodo.getRight();
                        cantelem--;
                        return;
                    }
                    if(nodo.getRight() == null && nodo.getLeft() != null){
                        root = nodo.getLeft();
                        cantelem--;
                        return;
                    }
                    if(nodo.getLeft() != null && nodo.getRight() != null){
                        throw new InvalidPositionException("No se puede remover un nodo con dos hijos.");
                    }
                }
                BTNodo<E> nodop = nodo.getPadre();
                if(nodop.getLeft() == null){
                    if(nodo.getLeft() == null && nodo.getRight() != null){
                        nodop.setRight(nodo.getRight());
                        cantelem--;
                        return;
                    }
                    if(nodo.getRight() == null && nodo.getLeft() != null){
                        nodop.setRight(nodo.getLeft());
                        cantelem--;
                        return;
                    }
                    if(nodo.getLeft() != null && nodo.getRight() != null){
                        throw new InvalidPositionException("No se puede remover un nodo con dos hijos.");
                    }
                    if(nodo.getLeft() == null && nodo.getRight() == null){
                        nodop.setRight(null);
                        cantelem--;
                        return;
                    }
                }
                if(nodop.getRight() == null){
                    if(nodo.getLeft() == null && nodo.getRight() != null){
                        nodop.setLeft(nodo.getRight());
                        cantelem--;
                        return;
                    }
                    if(nodo.getRight() == null && nodo.getLeft() != null){
                        nodop.setLeft(nodo.getLeft());
                        cantelem--;
                        return;
                    }
                    if(nodo.getLeft() != null && nodo.getRight() != null){
                        throw new InvalidPositionException("No se puede remover un nodo con dos hijos.");
                    }
                    if(nodo.getLeft() == null && nodo.getRight() == null){
                        nodop.setLeft(null);
                        cantelem--;
                        return;
                    }
                }
            }

            public void removeNode(Position<E> p) throws InvalidPositionException{ //orden constante
                checkPosition(p);
                if(isInternal(p)){
                    removeInternalNode(p);
                }
                else if(isExternal(p)){
                    removeExternalNode(p);
                }
            }

            public Position<E> addLeft(Position<E> v, E r) throws InvalidPositionException, InvalidOperationException{ //orden constante
                BTNodo<E> nodo = checkPosition(v);
                if(nodo.getLeft() != null){
                    throw new InvalidOperationException("La posicion ya tiene un primer hijo.");
                }
                BTNodo<E> izq = new BTNodo<>(r, nodo, null, null);
                nodo.setLeft(izq);
                cantelem++;
                return izq;
            }

            public Position<E> addRight(Position<E> v, E r) throws InvalidPositionException, InvalidOperationException{ //orden constante
                BTNodo<E> nodo = checkPosition(v);
                if(nodo.getRight() != null){
                    throw new InvalidOperationException("La posicion ya tiene un último hijo.");
                }
                BTNodo<E> der = new BTNodo<>(r, nodo, null, null);
                nodo.setRight(der);
                cantelem++;
                return der;
            }

            public void attach(Position<E> r, BinaryTree<E> T1, BinaryTree<E> T2) throws InvalidPositionException{ //orden n (tiempo n + m)
                BTNodo<E> nodo =  checkPosition(r);
                if(!isExternal(nodo)){
                    throw new InvalidPositionException("El nodo no es una hoja.");
                }
                if(!T1.isEmpty()){
                    BTNodo<E> clon1 = clonarArbol((BTNodo<E>) T1.root(), nodo);
                    nodo.setLeft(clon1);
                    clon1.setPadre(nodo);
                }
                if(!T2.isEmpty()){
                    BTNodo<E> clon2 = clonarArbol((BTNodo<E>) T2.root(), nodo);
                    nodo.setRight(clon2);
                    clon2.setPadre(nodo);
                }
                cantelem += T1.size() + T2.size();
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
                BTNodo<E> nodo = checkPosition(v);
                if(nodo == root){
                    throw new BoundaryViolationException("Posicion inválida, la posición dada es la raíz.");
                }
                return nodo.getPadre();
            }

            public Iterable<Position<E>> children(Position<E> v) throws InvalidPositionException{ //orden constante
                BTNodo<E> nodo = checkPosition(v);
                PositionList<Position<E>> retorno = new DoubleLinkedList<>();
                if(nodo.getLeft() != null){
                    retorno.addLast(nodo.getLeft());
                }
                if(nodo.getRight() != null){
                    retorno.addLast(nodo.getRight());
                }
                return retorno;
            }

            public boolean isInternal(Position<E> v) throws InvalidPositionException{ //orden constante
                BTNodo<E> nodo = checkPosition(v);
                return nodo.getLeft() != null || nodo.getRight() != null;
            }

            public boolean isExternal(Position<E> v) throws InvalidPositionException{ //orden constante
                BTNodo<E> nodo = checkPosition(v);
                return  nodo.getLeft() == null && nodo.getRight() == null;
            }

            public boolean isRoot(Position<E> v) throws InvalidPositionException{ //orden constante
                BTNodo<E> nodo = checkPosition(v);
                return nodo == root;
            }

            public Position<E> left(Position<E> v) throws InvalidPositionException, BoundaryViolationException{ //orden constante
                BTNodo<E> nodo = checkPosition(v);
                if(nodo.getLeft() == null){
                    throw new BoundaryViolationException("La posición dada no tiene hijo izquierdo.");
                }
                return nodo.getLeft();
            }

            public Position<E> right(Position<E> v) throws InvalidPositionException, BoundaryViolationException{ //orden constante
                BTNodo<E> nodo = checkPosition(v);
                if(nodo.getRight() == null){
                    throw new BoundaryViolationException("La posición dada no tiene hijo derecho.");
                }
                return nodo.getRight();
            }

            public boolean hasLeft(Position<E> v) throws InvalidPositionException{ //orden constante
                BTNodo<E> nodo = checkPosition(v);
                return nodo.getLeft() != null;
            }

            public boolean hasRight(Position<E> v) throws InvalidPositionException{ //orden constante
                BTNodo<E> nodo = checkPosition(v);
                return nodo.getRight() != null;
            }

            protected BTNodo<E> checkPosition(Position<E> p) throws InvalidPositionException{ //orden constante
                if(p == null || isEmpty()){
                    throw new InvalidPositionException("Posición inválida.");
                }
                try{
                    return (BTNodo<E>) p;
                }
                catch (ClassCastException e){
                    throw new InvalidPositionException("Posición inválida");
                }
            }

            protected BTNodo<E> clonarArbol(BTNodo<E> node, BTNodo<E> pa){ //orden n
                BTNodo<E> retorno = new BTNodo<E>(node.element(), pa, null, null);
                if(node.getLeft() != null){
                    retorno.setLeft(clonarArbol(node.getLeft(), retorno));
                }
                if(node.getRight() != null){
                    retorno.setRight(clonarArbol(node.getRight(), retorno));
                }
                return retorno;
            }

             protected void preorden(BTNodo<E> n, PositionList<Position<E>> lista){ //orden n
                lista.addLast(n);
                if(n.getLeft() != null){
                    preorden(n.getLeft(), lista);
                }
                if(n.getRight() != null){
                    preorden(n.getRight(), lista);
                }
            }
}
