package ar.edu.uns.cs.ed.tdas.PRACTICOS.TP8.EJ1;

import ar.edu.uns.cs.ed.tdas.tdagrafo.Edge;
import ar.edu.uns.cs.ed.tdas.Position;

public class Arco<V,E> implements Edge<E>{
    //Atributos de instancia
    protected E rotulo;
    protected Vertice<V,E> v1;
    protected Vertice<V,E> v2;
    protected Position<Arco<V,E>> posArcos;
    protected Position<Arco<V,E>> posEnListaVertices1;
    protected Position<Arco<V,E>> posEnListaVertices2;

    //Servicios
        //Constructor
        public Arco(Vertice<V,E> ver1, Vertice<V,E> ver2, E rot){
            rotulo = rot;
            v1 = ver1;
            v2 = ver2;
        }

        //Métodos
            //Comandos
            public void setRotulo(E rot){
                rotulo = rot;
            }

            public void setV1(Vertice<V,E> ver1){
                v1 = ver1;
            }

            public void setV2(Vertice<V,E> ver2){
                v2 = ver2;
            }

            public void setPosArcos(Position<Arco<V,E>> pos){
                posArcos = pos;
            }

            public void setPosListaV1(Position<Arco<V,E>> pos){
                posEnListaVertices1 = pos;
            }

            public void setPosListaV2(Position<Arco<V,E>> pos){
                posEnListaVertices2 = pos;
            }

            //Consultas
            public E element(){
                return rotulo;
            }

            public Vertice<V,E> getV1(){
                return v1;
            }

            public Vertice<V,E> getV2(){
                return v2;
            }

            public Position<Arco<V,E>> getPosArcos(){
                return posArcos;
            }

            public Position<Arco<V,E>> getPosListaV1(){
                return posEnListaVertices1;
            }

            public Position<Arco<V,E>> getPosListaV2(){
                return posEnListaVertices2;
            }
}
