package ru.ribenjyeo.sorting_algorithm.bean;

import java.util.Date;
import java.util.List;

public class SortLog extends Line {
    private Long tm;
    private Integer index;
    private  List<Line> list;

    public SortLog(List<Line> list, int i) {
        this.tm = new Date ().getTime ();
        this.list = list;
        this.index = i;
    }

    public Long getTm() {
        return tm;
    }

    public Integer getIndex() {
        return index;
    }

    public List<Line> getList() {
        return list;
    }
}
