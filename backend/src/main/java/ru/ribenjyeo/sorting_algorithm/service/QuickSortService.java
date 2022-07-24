package ru.ribenjyeo.sorting_algorithm.service;

import org.springframework.stereotype.Service;
import ru.ribenjyeo.sorting_algorithm.bean.Line;

import java.util.List;

///////////////////////////////////////////////////////////////////////////////
//  QuickSortService - класс, который реализует алгоритм быстрой сортировки  //
///////////////////////////////////////////////////////////////////////////////

@Service
public class QuickSortService extends SortService {

    @Override
    protected List<Line> sortList(String target, List<Line> list) {
        return sortList (target, list, 0, list.size () - 1);
    }

    /**
     * Алгоритм быстрой сортировки
     *
     * @param target логи
     * @param list   неотсортированный список
     * @param left   начальный идекс
     * @param right  конечный индекс
     * @return отсортированный список
     */
    private List<Line> sortList(String target, List<Line> list, int left, int right) {
        if (left < right) {
            int partitionIndex = partition (target, list, left, right);

            sortList (target, list, left, partitionIndex - 1);
            sortList (target, list, partitionIndex + 1, right);
        }
        return list;
    }

    // Что-то должно случиться
    private int partition(String target, List<Line> list, int left, int right) {
        int counter = left;
        for (int i = left; i < right; i++) {
            if (list.get (i).compare (list.get (right))) {
                Line temp = list.get (counter);
                list.set (counter, list.get (i));
                list.set (i, temp);
                addLog (target, counter, list);
                counter++;
            }
        }

        Line temp = list.get (right);
        list.set (right, list.get (counter));
        list.set (counter, temp);
        addLog (target, counter, list);
        return counter;
    }
}
