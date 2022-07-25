package ru.ribenjyeo.sorting_algorithm.service;

import org.springframework.stereotype.Service;
import ru.ribenjyeo.sorting_algorithm.bean.Line;

import java.util.List;

//////////////////////////////////////////////////////////////////////////
//  SelectionSortService - класс, который реализует сортировку выбором  //
//////////////////////////////////////////////////////////////////////////

@Service
public class SelectionSortService extends SortService {

    @Override
    protected List<Line> sortList(String target, List<Line> list) {
        for (int i = 0; i < list.size (); i++) {
            Line max = list.get (i);
            int maxIndex = i;
            for (int j = i + 1; j < list.size (); j++) {
                if(list.get (j).needToReplace (max)) {
                    max = list.get (j);
                    maxIndex = j;
                    addLog (target, j, list);
                }
            }
            Line temp = list.get (i);
            list.set (i, max);
            addLog (target, i, list);
            list.set (maxIndex, temp);
            addLog (target, i, list);
        }
        return null;
    }
}
