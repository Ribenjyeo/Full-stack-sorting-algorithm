package ru.ribenjyeo.sorting_algorithm.socket.bean;

import ru.ribenjyeo.sorting_algorithm.bean.SortType;

public class IncomeMessage {
    private SortType sortType;
    private boolean play;
    private boolean stop;

    public SortType getSortType() {
        return sortType;
    }

    public boolean isStop() {
        return stop;
    }

    public boolean isPlay() {
        return play;
    }
}
