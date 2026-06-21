package ar.edu.uns.cs.ed.tdas.PRACTICOS.TP8.EJ3al7;

import ar.edu.uns.cs.ed.tdas.PRACTICOS.TP5.EJ2.MapeoAbierto;
import ar.edu.uns.cs.ed.tdas.excepciones.InvalidEdgeException;
import ar.edu.uns.cs.ed.tdas.excepciones.InvalidVertexException;
import ar.edu.uns.cs.ed.tdas.excepciones.InvalidKeyException;
import ar.edu.uns.cs.ed.tdas.tdagrafo.*;
import ar.edu.uns.cs.ed.tdas.tdalista.PositionList;
import ar.edu.uns.cs.ed.tdas.Entry;
import java.util.Iterator;
import ar.edu.uns.cs.ed.tdas.tdamapeo.*;
import ar.edu.uns.cs.ed.tdas.PRACTICOS.TP3.EJ4.*;
import ar.edu.uns.cs.ed.tdas.PRACTICOS.TP4.EJ1.DoubleLinkedList;
import ar.edu.uns.cs.ed.tdas.tdacola.*;

public class ejercicios<V,E> {
    //Ejercicio 3
    
    public boolean gEsConexo(Graph<V,E> grafo) throws InvalidVertexException, InvalidEdgeException, InvalidKeyException{
        Iterator<Vertex<V>> iterador = grafo.vertices().iterator();
        if(!iterador.hasNext()){
            return true;
        }
        Vertex<V> origen = iterador.next();
        Map<Vertex<V>,Boolean> visitados = new MapeoAbierto<>();

        dfs(grafo, origen, visitados);

        int cantVertices = 0;
        for(Vertex<V> vertice: grafo.vertices()){
            cantVertices++;
        }

        return cantVertices == visitados.size();
    }

    protected <V,E> void dfs(Graph<V,E> grafo, Vertex<V> vertice, Map<Vertex<V>, Boolean> mapeo) throws InvalidVertexException, InvalidEdgeException, InvalidKeyException{
            mapeo.put(vertice, true);
            for(Edge<E> arco: grafo.incidentEdges(vertice)){
                Vertex<V> opuesto = grafo.opposite(vertice, arco);
                if(mapeo.get(opuesto) == null){
                    dfs(grafo, opuesto, mapeo);
                }
            }
    }

    //Ejercicio 4
    public int caminoMasCorto(Graph<V,E> grafo, Vertex<V> v1, Vertex<V> v2){
        Queue<Vertex<V>> cola = new QueueWithStack<>();
        Map<Vertex<V>, Integer> mapeo = new MapeoAbierto<>();
        cola.enqueue(v1);
        mapeo.put(v1, 0);
        while(!cola.isEmpty()){
            Vertex<V> actual = cola.dequeue();
            if(actual == v2){
                return mapeo.get(actual);
            }
            for(Edge<E> arco: grafo.incidentEdges(actual)){
                Vertex<V> opuesto = grafo.opposite(actual, arco);
                if(mapeo.get(opuesto) == null){
                    mapeo.put(opuesto, mapeo.get(actual)+1);
                    cola.enqueue(opuesto);
                }
            }

        }
        return -1;
    }

    //Ejercicio 5
    public void existeCamino(Graph<V,E> grafo, Vertex<V> v1, Vertex<V> v2){
        PositionList<Vertex<V>> camino = new DoubleLinkedList<>();
        Map<Vertex<V>, Boolean> visitados = new MapeoAbierto<>();
        boolean existe = caminoDFS(grafo, v1, v2, visitados, camino);
        if(existe){
            System.out.println("Camino: ");
            for(Vertex<V> v: camino){
                System.out.println(v.element() + " --> ");
                System.out.println(" FIN.");
            }
        }
        else{
                System.out.println("No existe camino.");
        }
    }

    protected boolean caminoDFS(Graph<V,E> grafo, Vertex<V> v1, Vertex<V> v2, Map<Vertex<V>, Boolean> visitados, PositionList<Vertex<V>> camino){
        visitados.put(v1, true);
        camino.addLast(v1);
        if(v1 == v2){
            return true;
        }
        for(Edge<E> arco: grafo.incidentEdges(v1)){
            Vertex<V> opuesto = grafo.opposite(v1, arco);
            if(visitados.get(opuesto) == null){
                if(caminoDFS(grafo, opuesto, v2, visitados, camino)){
                    return true;
                }
            }
        }
        //Backtracking
        camino.remove(camino.last());
        visitados.remove(v1);
        return false;
    }

    //Ejercicio 6
    public void caminoCostoMinimo(Graph<V,Float> grafo, Vertex<V> v1, Vertex<V> v2){
        Map<Vertex<V>, Boolean> visitados = new MapeoAbierto<>();
        Camino<V> retorno = new Camino<>();
        Camino<V> actual = new Camino<>();
        retorno.setCosto(Float.MAX_VALUE); // INFINITO
        DFSMinimo(grafo, v1, v2, retorno, actual, visitados);
        if(retorno.getCosto() == Float.MAX_VALUE){
            System.out.println("Camino: ");
            for(Vertex<V> v: retorno.getCamino()){
                System.out.println(v.element() + " --> ");
                System.out.println(" y su costo es: " + retorno.getCosto() + " FIN.");
            }
        }
        else{
                System.out.println("No existe camino.");
        }
    }

    protected void DFSMinimo(Graph<V,Float> grafo, Vertex<V> v1, Vertex<V> v2, Camino<V> resultado, Camino<V> actual, Map<Vertex<V>, Boolean> visitados){
        visitados.put(v1, true);
        actual.getCamino().addLast(v1);

        if(v1 == v2){
            if(actual.getCosto() < resultado.getCosto()){
                resultado.setCamino(actual.clone().getCamino());
                resultado.setCosto(actual.getCosto());
            }
        }
        else{
            for(Edge<Float> arco: grafo.incidentEdges(v1)){
                Vertex<V> opuesto = grafo.opposite(v1, arco);
                if(visitados.get(opuesto) == null){
                    actual.setCosto(actual.getCosto() + arco.element());
                    DFSMinimo(grafo, opuesto, v2, resultado, actual, visitados);
                    actual.setCosto(actual.getCosto() - arco.element());
                }
            }
        }
        //Backtracking
        actual.getCamino().remove(actual.getCamino().last());
        visitados.remove(v1);
    }

    protected class Camino<V>{
        //Atributos de instancia
        protected PositionList<Vertex<V>> vertices;
        protected float costo;

        //Servicios
            //Constructor
            public Camino(){
                vertices = new DoubleLinkedList<>();
                costo = 0.0f;
            }

            //Métodos
                //Comandos
                public void setCamino(PositionList<Vertex<V>> nuevo){
                    vertices = nuevo;
                }

                public void setCosto(float nuevo){
                    costo = nuevo;
                }

                //Consultas
                public PositionList<Vertex<V>> getCamino(){
                    return vertices;
                }

                public float getCosto(){
                    return costo;
                }

                public Camino<V> clone(){
                    Camino<V> clon = new Camino<>();
                    clon.setCosto(costo);
                    for(Vertex<V> v: vertices){
                        clon.getCamino().addLast(v);
                    }
                    return clon;
                }
    }

    //Ejercicio 7
    public Iterable<Entry<Vertex<V>, Integer>> alcanzablesEnMenosDeK(Graph<V,E> grafo, Vertex<V> v, int k){
        Queue<Vertex<V>> cola = new QueueWithStack<>();
        Map<Vertex<V>, Boolean> visitados = new MapeoAbierto<>();
        Map<Vertex<V>, Integer> retorno = new MapeoAbierto<>();
        if (k <= 1) return retorno.entries();
        int contador = 1;
        visitados.put(v, true);
        retorno.put(v, contador);
        cola.enqueue(v);    
        while(!cola.isEmpty() && contador < k){
            Vertex<V> actual = cola.dequeue();
            for(Edge<E> arco: grafo.incidentEdges(actual)){
                Vertex<V> opuesto = grafo.opposite(actual, arco);
                if(visitados.get(opuesto) == null){
                    contador++;
                    if (contador >= k) {
                        return retorno.entries(); 
                    }
                    visitados.put(opuesto, true);
                    retorno.put(opuesto, contador);
                }
            }
        }
        return retorno.entries();
    }
}