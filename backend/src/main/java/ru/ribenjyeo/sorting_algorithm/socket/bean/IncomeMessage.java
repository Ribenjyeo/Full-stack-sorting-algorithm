package ru.ribenjyeo.sorting_algorithm.socket.bean;

import ru.ribenjyeo.sorting_algorithm.bean.SortType;

public class IncomeMessage {
    private SortType sortType;
    private boolean drop;

    public SortType getSortType() {
        return sortType;
    }

    public boolean isDrop() {
        return drop;
    }
}
