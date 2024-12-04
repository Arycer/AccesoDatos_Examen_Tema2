package me.arycer.dam.util;

/**
 * Clase que representa un par de objetos
 * @param <TLeft> Tipo del objeto izquierdo
 * @param <TRight> Tipo del objeto derecho
 */
public class Pair<TLeft, TRight> {
    private TLeft left;
    private TRight right;

    public Pair(TLeft left, TRight right) {
        this.left = left;
        this.right = right;
    }

    public TLeft getLeft() {
        return left;
    }

    public void setLeft(TLeft left) {
        this.left = left;
    }

    public TRight getRight() {
        return right;
    }

    public void setRight(TRight right) {
        this.right = right;
    }
}
