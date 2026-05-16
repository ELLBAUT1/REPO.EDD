package ar.edu.uns.cs.ed.tdas.PRACTICOS.TP2.EJ2;
import java.util.LinkedList;
public class Impar <E>{
    public LinkedList<Integer> queueimpar(LinkedList<Integer> kiwi){
        LinkedList<Integer> retorno = new LinkedList();
        while(!kiwi.isEmpty()){
            Integer entero = kiwi.remove();
            if(entero % 2 == 1)
                retorno.add(entero);
        }
        return retorno;
    }
}
