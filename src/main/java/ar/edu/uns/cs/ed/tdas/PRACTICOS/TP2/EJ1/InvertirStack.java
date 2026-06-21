package ar.edu.uns.cs.ed.tdas.PRACTICOS.TP2.EJ1;
import java.util.Stack;
import ar.edu.uns.cs.ed.tdas.PRACTICOS.TP2.Persona;
public class InvertirStack <E>{
    public Persona[] invertir(Persona[] A){
        Stack<Persona> stack =  new Stack<>();
        for(int i = 0; i < A.length; i++){
            if(A[i] != null){
                stack.push(A[i]);
            }
        }
        int i = 0;
        while(!stack.isEmpty()){
            A[i] = stack.pop();
            i++;
        }
        return A;
    }
}
