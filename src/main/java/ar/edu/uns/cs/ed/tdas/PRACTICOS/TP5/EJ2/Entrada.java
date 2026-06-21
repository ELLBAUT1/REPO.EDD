package ar.edu.uns.cs.ed.tdas.PRACTICOS.TP5.EJ2;

import ar.edu.uns.cs.ed.tdas.Entry;

public class Entrada<K, V> implements Entry<K, V> {
                private K clave;
                private V valor;

                public Entrada(K clave, V valor) {
                    this.clave = clave;
                    this.valor = valor;
                }
                public K getKey() {
                    return clave;
                }
                public V getValue() {
                    return valor;
                }
            }