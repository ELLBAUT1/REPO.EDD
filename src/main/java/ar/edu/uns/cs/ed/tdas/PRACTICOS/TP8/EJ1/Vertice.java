package ar.edu.uns.cs.ed.tdas.PRACTICOS.TP8.EJ1;

import ar.edu.uns.cs.ed.tdas.tdagrafo.Vertex;
import ar.edu.uns.cs.ed.tdas.tdalista.PositionList;
import ar.edu.uns.cs.ed.tdas.Position;
import ar.edu.uns.cs.ed.tdas.PRACTICOS.TP4.EJ1.DoubleLinkedList;

public class Vertice<V,E> implements Vertex<V>{
    //Atributos de instancia
    protected V rotulo;
    protected PositionList<Arco<V,E>> listaAdyacentes;
    protected Position<Vertice<V,E>> posVertices;
    protected boolean visitado;
    //Servicios
        //Constructor
        public Vertice(V rot){
            rotulo = rot;
            listaAdyacentes = new DoubleLinkedList<>();
            visitado = false;
        }
        
        //Métodos
            //Comandos
            public void setRotulo(V rot){
                rotulo = rot;
            }

            public void setPosVertices(Position<Vertice<V,E>> pos){
                posVertices = pos;
            }

            public void setVisitado(boolean vis){
                visitado = vis;
            }

            //Consultas
            public V element(){
                return rotulo;
            }

            public PositionList<Arco<V,E>> getAdyacentes(){
                return listaAdyacentes;
            }

            public Position<Vertice<V,E>> getPosVertice(){
                return posVertices;
            }

            public boolean visitado(){
                return visitado;
            }
}
