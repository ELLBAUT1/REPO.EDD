package ar.edu.uns.cs.ed.tdas.PRACTICOS.TP8.EJ1.MATRIX;

import ar.edu.uns.cs.ed.tdas.tdagrafo.Vertex;
import ar.edu.uns.cs.ed.tdas.Position;

public class VerticeM<V,E> implements Vertex<V>{
                //Atributos de instancia
                protected V rotulo;
                protected Position<VerticeM<V,E>> posVertices;
                protected int indice;
                protected boolean visitado;
                //Servicios
                    //Constructor
                    public VerticeM(V rot, int in){
                        rotulo = rot;
                        indice = in;
                        visitado = false;
                    }
                    
                    //Métodos
                        //Comandos
                        public void setRotulo(V rot){
                            rotulo = rot;
                        }

                        public void setPosVertices(Position<VerticeM<V,E>> pos){
                            posVertices = pos;
                        }

                        public void setIndice(int in){
                            indice = in;
                        }

                        //Consultas
                        public V element(){
                            return rotulo;
                        }

                        public Position<VerticeM<V,E>> getPosVertices(){
                            return posVertices;
                        }

                        public int getIndice(){
                            return indice;
                        }

            }
