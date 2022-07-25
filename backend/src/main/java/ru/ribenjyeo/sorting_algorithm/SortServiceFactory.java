package ru.ribenjyeo.sorting_algorithm;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import ru.ribenjyeo.sorting_algorithm.bean.SortType;
import ru.ribenjyeo.sorting_algorithm.service.*;

import java.util.HashMap;
import java.util.Map;

@Component
public class SortServiceFactory {
    private final BubbleSortService bubbleSortService;
    private final QuickSortService quickSortService;
    private final InsertionSortService insertionSortService;
    private final HeapSortService heapSortService;
    private final SelectionSortService selectionSortService;

    private static final Map<SortType, SortService> handler = new HashMap<>();

    @Bean
    private void defineHandler() {
        handler.put (SortType.BUBBLE_SORT, bubbleSortService);
        handler.put (SortType.QUICK_SORT, quickSortService);
        handler.put (SortType.INSERTION_SORT, insertionSortService);
        handler.put (SortType.HEAP_SORT, heapSortService);
        handler.put (SortType.SELECTION_SORT, selectionSortService);
    }

    public SortServiceFactory(
            BubbleSortService bubbleSortService,
            QuickSortService quickSortService,
            InsertionSortService insertionSortService,
            HeapSortService heapSortService,
            SelectionSortService selectionSortService
    ) {
        this.bubbleSortService = bubbleSortService;
        this.quickSortService = quickSortService;
        this.insertionSortService = insertionSortService;
        this.heapSortService = heapSortService;
        this.selectionSortService = selectionSortService;
    }

    /**
     * Получение типа сортировки
     *
     * @param type выбор из Enum
     * @return тип сортировки
     */
    public SortService get (SortType type) {
        return handler.get (type);
    }
}
