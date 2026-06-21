package ar.edu.uns.cs.ed.tdas.PRACTICOS.TP8.EJ2.MATRIX;

import ar.edu.uns.cs.ed.tdas.tdagrafo.Vertex;
import ar.edu.uns.cs.ed.tdas.Position;

public class VerticeMD<V,E> implements Vertex<V>{
                //Atributos de instancia
                protected V rotulo;
                protected Position<VerticeMD<V,E>> posVertices;
                protected int indice;
                protected boolean visitado;
                //Servicios
                    //Constructor
                    public VerticeMD(V rot, int in){
                        rotulo = rot;
                        indice = in;
                        visitado = false;
                    }
                    
                    //Métodos
                        //Comandos
                        public void setRotulo(V rot){
                            rotulo = rot;
                        }

                        public void setPosVertices(Position<VerticeMD<V,E>> pos){
                            posVertices = pos;
                        }

                        public void setIndice(int in){
                            indice = in;
                        }

                        public void setVisitado(boolean vis){
                            visitado = vis;
                        }

                        //Consultas
                        public V element(){
                            return rotulo;
                        }

                        public Position<VerticeMD<V,E>> getPosVertices(){
                            return posVertices;
                        }

                        public int getIndice(){
                            return indice;
                        }

                        public boolean visitado(){
                            return visitado;
                        }

            }
