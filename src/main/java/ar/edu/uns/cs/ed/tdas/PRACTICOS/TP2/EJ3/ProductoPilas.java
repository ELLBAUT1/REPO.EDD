package ar.edu.uns.cs.ed.tdas.PRACTICOS.TP2.EJ3;
import java.util.Stack;
public class ProductoPilas <E>{
    public Stack<E> prodpilas(Stack<E> p1, Stack<E> p2){
        Stack<E> stackprod = new Stack();
        while(!p1.isEmpty() && !p2.isEmpty()){
            if(!p1.isEmpty()){
                stackprod.push(p1.pop());
            }
            if(!p2.isEmpty()){
                stackprod.push(p2.pop());
            }
        }
        return stackprod;
    }
}
