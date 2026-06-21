package ar.edu.uns.cs.ed.tdas.PRACTICOS.TP8.EJ1;

import ar.edu.uns.cs.ed.tdas.PRACTICOS.TP4.EJ1.DoubleLinkedList;
import ar.edu.uns.cs.ed.tdas.excepciones.InvalidVertexException;
import ar.edu.uns.cs.ed.tdas.excepciones.InvalidEdgeException;
import ar.edu.uns.cs.ed.tdas.tdalista.PositionList;
import ar.edu.uns.cs.ed.tdas.tdagrafo.*;
import ar.edu.uns.cs.ed.tdas.Position;

public class AdjacencyListGraph<V,E> implements Graph<V,E>{
    //Atributos de instancia
    protected PositionList<Vertice<V,E>> listaVertices;
    protected PositionList<Arco<V,E>> listaArcos;

    //Servicios
        //Constructor
        public AdjacencyListGraph(){
            listaVertices = new DoubleLinkedList<>();
            listaArcos = new DoubleLinkedList<>();
        }

        //Métodos
            //Comandos
            public V replace(Vertex<V> v, V x) throws InvalidVertexException{
                Vertice<V,E> ver = checkVertex(v);
                V ret = ver.element();
                ver.setRotulo(x);
                return ret;
            }
            public E replace(Edge<E> e, E x) throws InvalidEdgeException{
                Arco<V,E> arc = checkEdge(e);
                E ret = arc.element();
                arc.setRotulo(x);
                return ret;
            }

            public Vertex<V> insertVertex(V x){
                Vertice<V,E> vertice = new Vertice<V,E>(x);
                listaVertices.addLast((vertice));
                vertice.setPosVertices(listaVertices.last());
                return vertice;
            }

            public Edge<E> insertEdge(Vertex<V> v, Vertex<V> w, E x) throws InvalidVertexException{
                Vertice<V,E> v1 = checkVertex(v);
                Vertice<V,E> v2 = checkVertex(w);
                Arco<V,E> arco = new Arco<V,E>(v1, v2, x);
                listaArcos.addLast(arco);
                arco.setPosArcos(listaArcos.last());
                v1.getAdyacentes().addLast(arco);
                arco.setPosListaV1(v1.getAdyacentes().last());
                v2.getAdyacentes().addLast(arco);
                arco.setPosListaV2(v2.getAdyacentes().last());
                return arco;
            }

            public V removeVertex(Vertex<V> v) throws InvalidVertexException{
                Vertice<V,E> vertice = checkVertex(v);
                V ret = vertice.element();
                for(Arco<V,E> arco: vertice.getAdyacentes()){
                    listaArcos.remove(arco.getPosArcos());
                    arco.getV2().getAdyacentes().remove(arco.getPosListaV2());
                    arco.getV1().getAdyacentes().remove(arco.getPosListaV1());
                }
                listaVertices.remove(vertice.getPosVertice());
                return ret;
            }

            public E removeEdge(Edge<E> e) throws InvalidEdgeException{
                Arco<V,E> arco = checkEdge(e);
                E ret = arco.element();
                listaArcos.remove(arco.getPosArcos());
                arco.getV2().getAdyacentes().remove(arco.getPosListaV2());
                arco.getV1().getAdyacentes().remove(arco.getPosListaV1());
                return ret;
            }

            //Consultas
            public Iterable<Vertex<V>> vertices(){
                DoubleLinkedList<Vertex<V>> lista = new DoubleLinkedList<>();
                for(Vertice<V,E> vertice : listaVertices){
                    lista.addLast(vertice);
                }
                return lista;
            }

            public Iterable<Edge<E>> edges(){
                DoubleLinkedList<Edge<E>> lista = new DoubleLinkedList<>();
                for(Arco<V,E> arco : listaArcos){
                    lista.addLast(arco);
                }
                return lista;
            }

            public Iterable<Edge<E>> incidentEdges(Vertex<V> v){
                Vertice<V,E> vertice = checkVertex(v);
                DoubleLinkedList<Edge<E>> lista = new DoubleLinkedList<>();
                for(Arco<V,E> arco : vertice.getAdyacentes()){
                    lista.addLast(arco);
                }
                return lista;
            }

            public Vertex<V> opposite(Vertex<V> v, Edge<E> e){
                Vertice<V,E> vertice = checkVertex(v);
                Arco<V,E> arco = checkEdge(e);
                if(arco.getV1() == vertice){
                    return arco.getV2();
                }
                else if(arco.getV2() == vertice){
                    return arco.getV1();
                }
                else{
                    throw new InvalidEdgeException("El vértice no incide en el arco pasado por parámetro.");
                }
            }

            public Vertex<V>[] endvertices(Edge<E> e){
                Arco<V,E> arco = checkEdge(e);
                Vertex<V>[] ret = (Vertex<V>[]) new Vertex[2];
                ret[0] = arco.getV1();
                ret[1] = arco.getV2();
                return ret;
            }

            public boolean areAdjacent(Vertex<V> v,Vertex<V> w){
                Vertice<V,E> vertice1 = checkVertex(v);
                Vertice<V,E> vertice2 = checkVertex(w);
                for(Arco<V,E> arco: vertice1.getAdyacentes()){
                    if(opposite(vertice1, arco) == vertice2){
                        return true;
                    }
                }
                return false;
            }

            protected Vertice<V,E> checkVertex(Vertex<V> v) throws InvalidVertexException{
                if(v == null){
                    throw new InvalidVertexException("Vertice nulo.");
                }
                try{
                    return (Vertice<V,E>) v;
                }
                catch (ClassCastException e){
                    throw new InvalidVertexException("Vertice inválido.");
                }
            }

            protected Arco<V,E> checkEdge(Edge<E> ed) throws InvalidEdgeException{
                if(ed == null){
                    throw new InvalidEdgeException("Arco nulo.");
                }
                try{
                    return (Arco<V,E>) ed;
                }
                catch (ClassCastException e){
                    throw new InvalidEdgeException("Arco inválido.");
                }
            }
}
