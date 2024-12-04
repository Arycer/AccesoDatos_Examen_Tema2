package me.arycer.dam.util;

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
