package ar.edu.uns.cs.ed.tdas.PRACTICOS.TP8.EJ1.MATRIX;

import ar.edu.uns.cs.ed.tdas.PRACTICOS.TP4.EJ1.DoubleLinkedList;
import ar.edu.uns.cs.ed.tdas.excepciones.InvalidEdgeException;
import ar.edu.uns.cs.ed.tdas.excepciones.InvalidVertexException;
import ar.edu.uns.cs.ed.tdas.tdagrafo.Edge;
import ar.edu.uns.cs.ed.tdas.tdagrafo.Graph;
import ar.edu.uns.cs.ed.tdas.tdagrafo.Vertex;
import ar.edu.uns.cs.ed.tdas.tdalista.PositionList;
import ar.edu.uns.cs.ed.tdas.Position;

public class AdjacencyMatrixGraph<V,E> implements Graph<V,E> {
    //Atributos de instancia
    protected PositionList<VerticeM<V,E>> listaVertices;
    protected PositionList<ArcoM<V,E>> listaArcos;
    protected ArcoM<V,E>[][] matriz;
    protected int cantVertices;

    //Servicios
        //Constructor
        public AdjacencyMatrixGraph(){
            listaVertices = new DoubleLinkedList<>();
            listaArcos = new DoubleLinkedList<>();
            matriz = (ArcoM<V,E>[][]) new ArcoM[10000][10000];
        }

        //Métodos
            //Comandos
            public V replace(Vertex<V> v, V x) throws InvalidVertexException{
                VerticeM<V,E> ver = checkVertex(v);
                V ret = ver.element();
                ver.setRotulo(x);
                return ret;
            }
            public E replace(Edge<E> e, E x) throws InvalidEdgeException{
                ArcoM<V,E> arc = checkEdge(e);
                E ret = arc.element();
                arc.setRotulo(x);
                return ret;
            }

            public Vertex<V> insertVertex(V x){
                VerticeM<V,E> vertice = new VerticeM<V,E>(x, cantVertices);
                cantVertices++;
                listaVertices.addLast((vertice));
                vertice.setPosVertices(listaVertices.last());
                if(cantVertices >= matriz[0].length){
                    ArcoM<V,E>[][] matriznueva = (ArcoM<V,E>[][]) new ArcoM[matriz[0].length*2][matriz[0].length*2];
                    for(int i = 0; i < matriz.length; i++){
                        for(int j = 0; j < matriz[0].length; j++){
                            matriznueva[i][j] = matriz[i][j];
                        }
                    }
                    matriz = matriznueva;
                }
                return vertice;
            }

            public Edge<E> insertEdge(Vertex<V> v, Vertex<V> w, E x) throws InvalidVertexException{
                VerticeM<V,E> v1 = checkVertex(v);
                VerticeM<V,E> v2 = checkVertex(w);
                ArcoM<V,E> arco = new ArcoM<V,E>(v1, v2, x);
                int fila = v1.getIndice();
                int col = v2.getIndice();
                matriz[fila][col] = arco;
                matriz[col][fila] = arco;
                listaArcos.addLast(arco);
                arco.setPosArcos(listaArcos.last());
                return arco;
            }

            public V removeVertex(Vertex<V> v) throws InvalidVertexException{
                VerticeM<V,E> vertice = checkVertex(v);
                V ret = vertice.element();
                for(int i = 0; i < matriz[vertice.getIndice()].length; i++){
                    if(matriz[vertice.getIndice()][i] != null){
                        listaArcos.remove(matriz[vertice.getIndice()][i].getPosArcos());
                    }
                    matriz[vertice.getIndice()][i] = null;
                    matriz[i][vertice.getIndice()] = null;
                }
                listaVertices.remove(vertice.getPosVertices());
                return ret;
            }

            public E removeEdge(Edge<E> e) throws InvalidEdgeException{
                ArcoM<V,E> arco = checkEdge(e);
                E ret = arco.element();
                listaArcos.remove(arco.getPosArcos());
                matriz[arco.getV1().getIndice()][arco.getV2().getIndice()] = null;
                matriz[arco.getV2().getIndice()][arco.getV1().getIndice()] = null;
                return ret;
            }

            //Consultas
            public Iterable<Vertex<V>> vertices(){
                DoubleLinkedList<Vertex<V>> lista = new DoubleLinkedList<>();
                for(VerticeM<V,E> vertice : listaVertices){
                    lista.addLast(vertice);
                }
                return lista;
            }

            public Iterable<Edge<E>> edges(){
                DoubleLinkedList<Edge<E>> lista = new DoubleLinkedList<>();
                for(ArcoM<V,E> arco : listaArcos){
                    lista.addLast(arco);
                }
                return lista;
            }

            public Iterable<Edge<E>> incidentEdges(Vertex<V> v){
                VerticeM<V,E> vertice = checkVertex(v);
                DoubleLinkedList<Edge<E>> lista = new DoubleLinkedList<>();
                for(int i = 0; i < matriz[vertice.getIndice()].length; i++ ){
                    if(matriz[vertice.getIndice()][i] != null){
                        lista.addLast(matriz[vertice.getIndice()][i]);
                    }
                }
                return lista;
            }

            public Vertex<V> opposite(Vertex<V> v, Edge<E> e){
                VerticeM<V,E> vertice = checkVertex(v);
                ArcoM<V,E> arco = checkEdge(e);
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
                ArcoM<V,E> arco = checkEdge(e);
                Vertex<V>[] ret = (Vertex<V>[]) new Vertex[2];
                ret[0] = arco.getV1();
                ret[1] = arco.getV2();
                return ret;
            }

            public boolean areAdjacent(Vertex<V> v,Vertex<V> w){
                VerticeM<V,E> vertice1 = checkVertex(v);
                VerticeM<V,E> vertice2 = checkVertex(w);
                return matriz[vertice1.getIndice()][vertice2.getIndice()] != null && matriz[vertice2.getIndice()][vertice1.getIndice()] != null;
            }

            protected VerticeM<V,E> checkVertex(Vertex<V> v) throws InvalidVertexException{
                if(v == null){
                    throw new InvalidVertexException("Vertice nulo.");
                }
                try{
                    return (VerticeM<V,E>) v;
                }
                catch (ClassCastException e){
                    throw new InvalidVertexException("Vertice inválido.");
                }
            }

            protected ArcoM<V,E> checkEdge(Edge<E> ed) throws InvalidEdgeException{
                if(ed == null){
                    throw new InvalidEdgeException("Arco nulo.");
                }
                try{
                    return (ArcoM<V,E>) ed;
                }
                catch (ClassCastException e){
                    throw new InvalidEdgeException("Arco inválido.");
                }
            }
}
