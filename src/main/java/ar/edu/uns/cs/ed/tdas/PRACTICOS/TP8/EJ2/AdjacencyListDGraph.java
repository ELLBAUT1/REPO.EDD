package ar.edu.uns.cs.ed.tdas.PRACTICOS.TP8.EJ2;

import ar.edu.uns.cs.ed.tdas.PRACTICOS.TP4.EJ1.DoubleLinkedList;
import ar.edu.uns.cs.ed.tdas.excepciones.InvalidVertexException;
import ar.edu.uns.cs.ed.tdas.excepciones.InvalidEdgeException;
import ar.edu.uns.cs.ed.tdas.excepciones.InvalidPositionException;
import ar.edu.uns.cs.ed.tdas.tdalista.PositionList;
import ar.edu.uns.cs.ed.tdas.tdagrafo.*;

public class AdjacencyListDGraph<V,E> implements GraphD<V,E>{
    //Atributos de instancia
    protected PositionList<VerticeD<V,E>> listaVertices;
    protected PositionList<ArcoD<V,E>> listaArcos;

    //Servicios
        //Constructor
        public AdjacencyListDGraph(){
            listaVertices = new DoubleLinkedList<>();
            listaArcos = new DoubleLinkedList<>();
        }

        //Métodos
            //Comandos
            public V replace(Vertex<V> v, V x) throws InvalidVertexException{
                VerticeD<V,E> ver = checkVertex(v);
                V ret = ver.element();
                ver.setRotulo(x);
                return ret;
            }
            public E replace(Edge<E> e, E x) throws InvalidEdgeException{
                ArcoD<V,E> arc = checkEdge(e);
                E ret = arc.element();
                arc.setRotulo(x);
                return ret;
            }

            public Vertex<V> insertVertex(V x){
                VerticeD<V,E> vertice = new VerticeD<V,E>(x);
                listaVertices.addLast((vertice));
                vertice.setPosVertices(listaVertices.last());
                return vertice;
            }

            public Edge<E> insertEdge(Vertex<V> v, Vertex<V> w, E x) throws InvalidVertexException{
                VerticeD<V,E> v1 = checkVertex(v);
                VerticeD<V,E> v2 = checkVertex(w);
                ArcoD<V,E> arco = new ArcoD<V,E>(v1, v2, x);
                listaArcos.addLast(arco);
                arco.setPosArcos(listaArcos.last());
                v1.getEmergentes().addLast(arco);
                arco.setPosListaV1(v1.getEmergentes().last());
                v2.getIncidentes().addLast(arco);
                arco.setPosListaV2(v2.getIncidentes().last());
                return arco;
            }

            public V removeVertex(Vertex<V> v) throws InvalidVertexException{
                VerticeD<V,E> vertice = checkVertex(v);
                V ret = vertice.element();
                for(ArcoD<V,E> arco: vertice.getEmergentes()){
                    listaArcos.remove(arco.getPosArcos());
                    arco.getV2().getIncidentes().remove(arco.getPosListaV2());
                }
                for(ArcoD<V,E> arco: vertice.getIncidentes()){
                    listaArcos.remove(arco.getPosArcos());
                    arco.getV1().getEmergentes().remove(arco.getPosListaV1());
                }
                listaVertices.remove(vertice.getPosVertice());
                return ret;
            }

            public E removeEdge(Edge<E> e) throws InvalidEdgeException{
                ArcoD<V,E> arco = checkEdge(e);
                E ret = arco.element();
                listaArcos.remove(arco.getPosArcos());
                arco.getV1().getEmergentes().remove(arco.getPosListaV1());
                arco.getV2().getIncidentes().remove(arco.getPosListaV2());
                return ret;
            }

            //Consultas
            public Iterable<Vertex<V>> vertices(){
                DoubleLinkedList<Vertex<V>> lista = new DoubleLinkedList<>();
                for(VerticeD<V,E> vertice : listaVertices){
                    lista.addLast(vertice);
                }
                return lista;
            }

            public Iterable<Edge<E>> edges(){
                DoubleLinkedList<Edge<E>> lista = new DoubleLinkedList<>();
                for(ArcoD<V,E> arco : listaArcos){
                    lista.addLast(arco);
                }
                return lista;
            }

            public Iterable<Edge<E>> incidentEdges(Vertex<V> v){
                VerticeD<V,E> vertice = checkVertex(v);
                DoubleLinkedList<Edge<E>> lista = new DoubleLinkedList<>();
                for(ArcoD<V,E> arco : vertice.getIncidentes()){
                    lista.addLast(arco);
                }
                return lista;
            }

            public Iterable<Edge<E>> succesorEdges(Vertex<V> v){
                VerticeD<V,E> vertice = checkVertex(v);
                DoubleLinkedList<Edge<E>> lista = new DoubleLinkedList<>();
                for(ArcoD<V,E> arco : vertice.getEmergentes()){
                    lista.addLast(arco);
                }
                return lista;
            }
            
            public Vertex<V> opposite(Vertex<V> v, Edge<E> e){
                VerticeD<V,E> vertice = checkVertex(v);
                ArcoD<V,E> arco = checkEdge(e);
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
                ArcoD<V,E> arco = checkEdge(e);
                Vertex<V>[] ret = (Vertex<V>[]) new Vertex[2];
                ret[0] = arco.getV1();
                ret[1] = arco.getV2();
                return ret;
            }

            public boolean areAdjacent(Vertex<V> v,Vertex<V> w){
                VerticeD<V,E> vertice1 = checkVertex(v);
                VerticeD<V,E> vertice2 = checkVertex(w);
                for(ArcoD<V,E> arco: vertice1.getEmergentes()){
                    if(opposite(vertice1, arco) == vertice2){
                        return true;
                    }
                }
                return false;
            }

            protected VerticeD<V,E> checkVertex(Vertex<V> v) throws InvalidVertexException{
                if(v == null){
                    throw new InvalidVertexException("Vertice nulo.");
                }
                try{
                    return (VerticeD<V,E>) v;
                }
                catch (ClassCastException e){
                    throw new InvalidVertexException("Vertice inválido.");
                }
            }

            protected ArcoD<V,E> checkEdge(Edge<E> ed) throws InvalidEdgeException{
                if(ed == null){
                    throw new InvalidEdgeException("Arco nulo.");
                }
                try{
                    return (ArcoD<V,E>) ed;
                }
                catch (ClassCastException e){
                    throw new InvalidEdgeException("Arco inválido.");
                }
            }
}
