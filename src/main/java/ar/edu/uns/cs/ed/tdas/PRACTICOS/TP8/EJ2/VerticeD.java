package ar.edu.uns.cs.ed.tdas.PRACTICOS.TP8.EJ2;

import ar.edu.uns.cs.ed.tdas.tdagrafo.Vertex;
import ar.edu.uns.cs.ed.tdas.tdalista.PositionList;
import ar.edu.uns.cs.ed.tdas.Position;
import ar.edu.uns.cs.ed.tdas.PRACTICOS.TP4.EJ1.DoubleLinkedList;

public class VerticeD<V,E> implements Vertex<V>{
    //Atributos de instancia
    protected V rotulo;
    protected PositionList<ArcoD<V,E>> listaEmergentes;
    protected PositionList<ArcoD<V,E>> listaIncidentes;
    protected Position<VerticeD<V,E>> posVertices;
    protected boolean visitado;
    //Servicios
        //Constructor
        public VerticeD(V rot){
            rotulo = rot;
            listaEmergentes = new DoubleLinkedList<>();
            listaIncidentes = new DoubleLinkedList<>();
            visitado = false;
        }
        
        //Métodos
            //Comandos
            public void setRotulo(V rot){
                rotulo = rot;
            }

            public void setPosVertices(Position<VerticeD<V,E>> pos){
                posVertices = pos;
            }

            public void setVisitado(boolean vis){
                visitado = vis;
            }

            //Consultas
            public V element(){
                return rotulo;
            }

            public PositionList<ArcoD<V,E>> getEmergentes(){
                return listaEmergentes;
            }

            public PositionList<ArcoD<V,E>> getIncidentes(){
                return listaIncidentes;
            }

            public Position<VerticeD<V,E>> getPosVertice(){
                return posVertices;
            }

            public boolean visitado(){
                return visitado;
            }
}
