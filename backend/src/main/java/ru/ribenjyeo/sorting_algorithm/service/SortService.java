package ru.ribenjyeo.sorting_algorithm.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.ribenjyeo.sorting_algorithm.bean.Line;
import ru.ribenjyeo.sorting_algorithm.bean.SortLog;

import java.util.*;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;

////////////////////////////////////////////////////////////////////////////////////////////
//  SortService - занимается складывает и достает из логов события, которые генирируются  //
//       во время сортировки, запускает саму сортировку в отдельном трейде и генирурет    //
//                                исходный массив сортировки                              //
////////////////////////////////////////////////////////////////////////////////////////////

abstract public class SortService {
    // Константа количества элементов генируемых элементов
    private final static int LIST_CAPACITY = 100;
    protected final Logger logger = LoggerFactory.getLogger (getClass ());
    // Карта очередей
    private final Map<String, BlockingDeque<SortLog>> sortLog = new HashMap<> ();
    // Создание потока с неограниченной очередью
    private final Executor executor = Executors.newSingleThreadExecutor ();

    // Добавление логов
    protected void addLog(String target, int i, List<Line> list) {
        sortLog.get (target).addLast (new SortLog (list, i));
    }

    /**
     * Получение наших логов
     *
     * @param target элемент
     * @return наш лог
     */
    public SortLog pollLong(String target) {
        return sortLog.get (target).poll ();
    }

    // Запуск сортировыки в отдельным трейде
    public void sort(String target) {
        if (!sortLog.containsKey (target)) {
            List<Line> list = generateList ();
            sortLog.put (target, new LinkedBlockingDeque<> ());
            executor.execute (() -> {
                sortList (target, list);
            });
        }
    }

    /**
     * Генирирует список элементов
     *
     * @return список элементов
     */
    private List<Line> generateList() {
        Random random = new Random ();
        List<Line> res = new LinkedList<> ();
        for (int i = 0; i < LIST_CAPACITY; i++) {
            res.add (new Line (random.nextInt (LIST_CAPACITY)));
        }
        return res;
    }

    // Метод сортировки, который нужно реализовать
    abstract protected List<Line> sortList(String target, List<Line> list);

    // Остановка сортировки
    public void drop(String target) {
        sortLog.remove(target);
    }
}
