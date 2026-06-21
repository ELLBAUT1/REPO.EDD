package ar.edu.uns.cs.ed.tdas.PRACTICOS.REPASO2DOPARCIAL;

import ar.edu.uns.cs.ed.tdas.PRACTICOS.TP6.EJ2.BArbol;
import ar.edu.uns.cs.ed.tdas.excepciones.InvalidPositionException;
import ar.edu.uns.cs.ed.tdas.tdaarbolbinario.BinaryTree;
import ar.edu.uns.cs.ed.tdas.PRACTICOS.TP5.EJ2.MapeoAbierto;
import ar.edu.uns.cs.ed.tdas.tdamapeo.Map;
import ar.edu.uns.cs.ed.tdas.Position;

public class Ejercicio2 {
    public Map<Character, Integer> eliminarHojas(BinaryTree<Character> arbol, Position<Character> p) throws InvalidPositionException{
        Map<Character, Integer> mapeo = new MapeoAbierto<>();
        if(p == null){
            throw new InvalidPositionException("Posición inválida.");
        }
        eliminarRecursivo(p, arbol, mapeo);
        return mapeo;
    }

    protected void eliminarRecursivo(Position<Character> pos, BinaryTree<Character> tree, Map<Character, Integer> mapa) throws InvalidPositionException{
        if(tree.isInternal(pos)){
            if(tree.hasLeft(pos)){
                eliminarRecursivo(tree.left(pos), tree, mapa);
            }
            if(tree.hasRight(pos)){
                eliminarRecursivo(tree.right(pos), tree, mapa);
            }    
        }
        else if(tree.isExternal(pos)){
            if(mapa.get(pos.element()) == null){
                    mapa.put(pos.element(), 1);
            }
            else{
                mapa.put(pos.element(), mapa.get(pos.element())+1);
            }
            tree.removeNode(pos);
        
        }
    }
}
