package ru.ribenjyeo.sorting_algorithm.bean;

public class Line {
    private int val;

    public Line(int val) {
        this.val = val;
    }

    public Line() {
    }

    public int getValue() {
        return val;
    }

    public boolean needToReplace(Line other){
        return this.getValue() < other.getValue();
    }
}
