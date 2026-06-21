package ar.edu.uns.cs.ed.tdas.PRACTICOS.TP8.EJ1.MATRIX;

import ar.edu.uns.cs.ed.tdas.tdagrafo.Edge;
import ar.edu.uns.cs.ed.tdas.Position;

public class ArcoM<V,E> implements Edge<E>{
                //Atributos de instancia
                protected E rotulo;
                protected VerticeM<V,E> v1;
                protected VerticeM<V,E> v2;
                protected Position<ArcoM<V,E>> posArcos;

                //Servicios
                    //Constructor
                    public ArcoM(VerticeM<V,E> ver1, VerticeM<V,E> ver2, E rot){
                        rotulo = rot;
                        v1 = ver1;
                        v2 = ver2;
                    }

                    //Métodos
                        //Comandos
                        public void setRotulo(E rot){
                            rotulo = rot;
                        }

                        public void setV1(VerticeM<V,E> ver1){
                            v1 = ver1;
                        }

                        public void setV2(VerticeM<V,E> ver2){
                            v2 = ver2;
                        }

                        public void setPosArcos(Position<ArcoM<V,E>> pos){
                            posArcos = pos;
                        }

                        //Consultas
                        public E element(){
                            return rotulo;
                        }

                        public VerticeM<V,E> getV1(){
                            return v1;
                        }

                        public VerticeM<V,E> getV2(){
                            return v2;
                        }

                        public Position<ArcoM<V,E>> getPosArcos(){
                            return posArcos;
                        }
            }