package ar.edu.uns.cs.ed.tdas.PRACTICOS.TP8.EJ2.MATRIX;

import ar.edu.uns.cs.ed.tdas.PRACTICOS.TP4.EJ1.DoubleLinkedList;
import ar.edu.uns.cs.ed.tdas.excepciones.InvalidEdgeException;
import ar.edu.uns.cs.ed.tdas.excepciones.InvalidVertexException;
import ar.edu.uns.cs.ed.tdas.tdagrafo.Edge;
import ar.edu.uns.cs.ed.tdas.tdagrafo.GraphD;
import ar.edu.uns.cs.ed.tdas.tdagrafo.Vertex;
import ar.edu.uns.cs.ed.tdas.tdalista.PositionList;

public class AdjacencyMatrixDGraph<V,E> implements GraphD<V,E> {
    //Atributos de instancia
    protected PositionList<VerticeMD<V,E>> listaVertices;
    protected PositionList<ArcoMD<V,E>> listaArcos;
    protected ArcoMD<V,E>[][] matriz;
    protected int cantVertices;

    //Servicios
        //Constructor
        public AdjacencyMatrixDGraph(){
            listaVertices = new DoubleLinkedList<>();
            listaArcos = new DoubleLinkedList<>();
            matriz = (ArcoMD<V,E>[][]) new ArcoMD[10000][10000];
        }

        //Métodos
            //Comandos
            public V replace(Vertex<V> v, V x) throws InvalidVertexException{
                VerticeMD<V,E> ver = checkVertex(v);
                V ret = ver.element();
                ver.setRotulo(x);
                return ret;
            }
            public E replace(Edge<E> e, E x) throws InvalidEdgeException{
                ArcoMD<V,E> arc = checkEdge(e);
                E ret = arc.element();
                arc.setRotulo(x);
                return ret;
            }

            public Vertex<V> insertVertex(V x){
                VerticeMD<V,E> vertice = new VerticeMD<V,E>(x, cantVertices);
                cantVertices++;
                listaVertices.addLast((vertice));
                vertice.setPosVertices(listaVertices.last());
                if(cantVertices >= matriz[0].length){
                    ArcoMD<V,E>[][] matriznueva = (ArcoMD<V,E>[][]) new ArcoMD[matriz[0].length*2][matriz[0].length*2];
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
                VerticeMD<V,E> v1 = checkVertex(v);
                VerticeMD<V,E> v2 = checkVertex(w);
                ArcoMD<V,E> arco = new ArcoMD<V,E>(v1, v2, x);
                int fila = v1.getIndice();
                int col = v2.getIndice();
                matriz[fila][col] = arco;
                listaArcos.addLast(arco);
                arco.setPosArcos(listaArcos.last());
                return arco;
            }

            public V removeVertex(Vertex<V> v) throws InvalidVertexException{
                VerticeMD<V,E> vertice = checkVertex(v);
                V ret = vertice.element();
                for(int i = 0; i < matriz[vertice.getIndice()].length; i++){
                    if(matriz[vertice.getIndice()][i] != null){
                        listaArcos.remove(matriz[vertice.getIndice()][i].getPosArcos());
                    }
                    matriz[vertice.getIndice()][i] = null;
                    if(matriz[i][vertice.getIndice()] != null){
                        listaArcos.remove(matriz[i][vertice.getIndice()].getPosArcos());
                    }
                    matriz[i][vertice.getIndice()] = null;
                }
                listaVertices.remove(vertice.getPosVertices());
                return ret;
            }

            public E removeEdge(Edge<E> e) throws InvalidEdgeException{
                ArcoMD<V,E> arco = checkEdge(e);
                E ret = arco.element();
                listaArcos.remove(arco.getPosArcos());
                matriz[arco.getV1().getIndice()][arco.getV2().getIndice()] = null;
                return ret;
            }

            //Consultas
            public Iterable<Vertex<V>> vertices(){
                DoubleLinkedList<Vertex<V>> lista = new DoubleLinkedList<>();
                for(VerticeMD<V,E> vertice : listaVertices){
                    lista.addLast(vertice);
                }
                return lista;
            }

            public Iterable<Edge<E>> edges(){
                DoubleLinkedList<Edge<E>> lista = new DoubleLinkedList<>();
                for(ArcoMD<V,E> arco : listaArcos){
                    lista.addLast(arco);
                }
                return lista;
            }

            public Iterable<Edge<E>> incidentEdges(Vertex<V> v){
                VerticeMD<V,E> vertice = checkVertex(v);
                DoubleLinkedList<Edge<E>> lista = new DoubleLinkedList<>();
                for(int i = 0; i < matriz[vertice.getIndice()].length; i++ ){
                    if(matriz[i][vertice.getIndice()] != null){
                        lista.addLast(matriz[i][vertice.getIndice()]);
                    }
                }
                return lista;
            }

            public Iterable<Edge<E>> succesorEdges(Vertex<V> v){
                VerticeMD<V,E> vertice = checkVertex(v);
                DoubleLinkedList<Edge<E>> lista = new DoubleLinkedList<>();
                for(int i = 0; i < matriz[vertice.getIndice()].length; i++ ){
                    if(matriz[vertice.getIndice()][i] != null){
                        lista.addLast(matriz[vertice.getIndice()][i]);
                    }
                }
                return lista;
            }

            public Vertex<V> opposite(Vertex<V> v, Edge<E> e){
                VerticeMD<V,E> vertice = checkVertex(v);
                ArcoMD<V,E> arco = checkEdge(e);
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
                ArcoMD<V,E> arco = checkEdge(e);
                Vertex<V>[] ret = (Vertex<V>[]) new Vertex[2];
                ret[0] = arco.getV1();
                ret[1] = arco.getV2();
                return ret;
            }

            public boolean areAdjacent(Vertex<V> v,Vertex<V> w){
                VerticeMD<V,E> vertice1 = checkVertex(v);
                VerticeMD<V,E> vertice2 = checkVertex(w);
                return matriz[vertice1.getIndice()][vertice2.getIndice()] != null;
            }

            protected VerticeMD<V,E> checkVertex(Vertex<V> v) throws InvalidVertexException{
                if(v == null){
                    throw new InvalidVertexException("VerticeMD nulo.");
                }
                try{
                    return (VerticeMD<V,E>) v;
                }
                catch (ClassCastException e){
                    throw new InvalidVertexException("VerticeMD inválido.");
                }
            }

            protected ArcoMD<V,E> checkEdge(Edge<E> ed) throws InvalidEdgeException{
                if(ed == null){
                    throw new InvalidEdgeException("ArcoMD nulo.");
                }
                try{
                    return (ArcoMD<V,E>) ed;
                }
                catch (ClassCastException e){
                    throw new InvalidEdgeException("ArcoMD inválido.");
                }
            }
}
