package ru.ribenjyeo.sorting_algorithm.service;

import org.springframework.stereotype.Service;
import ru.ribenjyeo.sorting_algorithm.bean.Line;

import java.util.List;

////////////////////////////////////////////////////////////////////////////////////////
//  InsertionSortService - класс, в котором реализован алгоритм сортировки вставками  //
////////////////////////////////////////////////////////////////////////////////////////

@Service
public class InsertionSortService extends SortService {
    @Override
    protected List<Line> sortList(String target, List<Line> list) {
        for (int i = 0; i < list.size (); i++) {
            Line current = list.get (i);
            int j = i - 1;
            while (j >= 0 && list.get (j).needToReplace (current)) {
                list.set (j + 1, list.get (j));
                addLog (target, List.of (i, j), list);
                j--;
            }
            list.set (j + 1, current);
        }
        return list;
    }
}
