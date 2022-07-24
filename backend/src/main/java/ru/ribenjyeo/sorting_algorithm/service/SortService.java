package ru.ribenjyeo.sorting_algorithm.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import ru.ribenjyeo.sorting_algorithm.bean.Line;
import ru.ribenjyeo.sorting_algorithm.bean.SortLog;
import ru.ribenjyeo.sorting_algorithm.exception.StopSortedException;

import java.util.*;
import java.util.concurrent.*;

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
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    // Константа задержки
    @Value("${wait.time}")
    private int WAIT_TIME = 100;
    private Future<?> task;


    // Добавление логов
    protected void addLog(String target, int i, List<Line> list) {
        addLog (target, List.of (i), list);
    }

    // Внутренний метод добавления логов
    protected void addLog(String target, List<Integer> indexes, List<Line> list) {
        try {
            sortLog.get (target).addLast (new SortLog (list, indexes));
            // Создание искуственной задержки
            Thread.sleep (WAIT_TIME);
        } catch (InterruptedException e) {
            logger.error ("Sort processing is interrupted. {}", e.getMessage ());
        } catch (NullPointerException npe) {
            logger.error ("Sort processing is stopped. {}", npe.getMessage ());
            task.cancel (true);
            throw new StopSortedException ();
        }
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
            task = executor.submit (() -> {
                sortList (target, list);
                salute (target, list);
            });
        }
    }

    // Завершающий метод
    private void salute(String target, List<Line> list) {
        List<Integer> indexes = new ArrayList<> ();
        list.forEach (row -> {
            int ind = list.indexOf (row);
            indexes.add (ind);
            addLog (target, indexes, list);
        });
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
        sortLog.remove (target);
    }
}
