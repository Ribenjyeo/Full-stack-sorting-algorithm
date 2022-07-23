package ru.ribenjyeo.sorting_algorithm.bean;

public class Line {
    private  int value;

    public Line(int value) {
        this.value = value;
    }

    public Line() {
    }

    public int getValue() {
        return value;
    }

    public boolean compare(Line other) {
        return this.getValue () > other.getValue ();
    }
}
