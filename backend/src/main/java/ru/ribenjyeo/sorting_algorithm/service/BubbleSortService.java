package ru.ribenjyeo.sorting_algorithm.service;

import org.springframework.stereotype.Service;
import ru.ribenjyeo.sorting_algorithm.bean.Line;

import java.util.List;

//////////////////////////////////////////////////////////////////////////////////
//  BubbleSortService - класс, который реализует алгоритм сортировки пузырьком  //
//////////////////////////////////////////////////////////////////////////////////

@Service
public class BubbleSortService extends SortService {

    /**
     * Сортировка пузырьком
     *
     * @param target логи
     * @param list   неотсортированный список
     * @return отсортированный список
     */
    @Override
    protected List<Line> sortList(String target, List<Line> list) {
        boolean found = true;
        while (found) {
            found = false;
            for (int i = 0; i < list.size () - 1; i++) {
                Line current = list.get (i);
                Line next = list.get (i + 1);
                if (current.needToReplace (next)) {
                    found = true;
                    list.set (i, next);
                    list.set (i + 1, current);
                    addLog (target, i, list);
                }
            }
        }
        return list;
    }
}
