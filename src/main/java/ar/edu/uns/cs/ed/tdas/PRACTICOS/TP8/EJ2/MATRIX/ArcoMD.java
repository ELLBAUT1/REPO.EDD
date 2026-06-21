package ar.edu.uns.cs.ed.tdas.PRACTICOS.TP8.EJ2.MATRIX;

import ar.edu.uns.cs.ed.tdas.tdagrafo.Edge;
import ar.edu.uns.cs.ed.tdas.Position;

public class ArcoMD<V,E> implements Edge<E>{
                //Atributos de instancia
                protected E rotulo;
                protected VerticeMD<V,E> v1;
                protected VerticeMD<V,E> v2;
                protected Position<ArcoMD<V,E>> posArcos;

                //Servicios
                    //Constructor
                    public ArcoMD(VerticeMD<V,E> ver1, VerticeMD<V,E> ver2, E rot){
                        rotulo = rot;
                        v1 = ver1;
                        v2 = ver2;
                    }

                    //Métodos
                        //Comandos
                        public void setRotulo(E rot){
                            rotulo = rot;
                        }

                        public void setV1(VerticeMD<V,E> ver1){
                            v1 = ver1;
                        }

                        public void setV2(VerticeMD<V,E> ver2){
                            v2 = ver2;
                        }

                        public void setPosArcos(Position<ArcoMD<V,E>> pos){
                            posArcos = pos;
                        }

                        //Consultas
                        public E element(){
                            return rotulo;
                        }

                        public VerticeMD<V,E> getV1(){
                            return v1;
                        }

                        public VerticeMD<V,E> getV2(){
                            return v2;
                        }

                        public Position<ArcoMD<V,E>> getPosArcos(){
                            return posArcos;
                        }
            }