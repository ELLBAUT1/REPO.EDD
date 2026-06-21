package ar.edu.uns.cs.ed.tdas.PRACTICOS.TP2.EJ4;
import java.util.LinkedList;
public class EnteroMayor<E> {
    public int mayorentero(LinkedList<Integer> q){
        int mayor = q.remove();
        LinkedList<Integer> aux = new LinkedList();
        while(!q.isEmpty()){
            Integer elemento = q.remove();
            if(elemento > mayor){
                mayor = elemento;
            }
            aux.add(elemento);
        }
        while(!aux.isEmpty()){
            q.add(aux.remove());
        }
        return mayor;
    }
}
