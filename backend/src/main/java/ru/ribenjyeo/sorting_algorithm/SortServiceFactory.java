package ru.ribenjyeo.sorting_algorithm;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import ru.ribenjyeo.sorting_algorithm.bean.SortType;
import ru.ribenjyeo.sorting_algorithm.service.BubbleSortService;
import ru.ribenjyeo.sorting_algorithm.service.QuickSortService;
import ru.ribenjyeo.sorting_algorithm.service.SortService;

import java.util.HashMap;
import java.util.Map;

@Component
public class SortServiceFactory {
    private final BubbleSortService bubbleSortService;
    private final QuickSortService quickSortService;

    private static final Map<SortType, SortService> handler = new HashMap<>();

    @Bean
    private void defineHandler() {
        handler.put (SortType.BUBBLE_SORT, bubbleSortService);
        handler.put (SortType.QUICK_SORT, quickSortService);
    }

    public SortServiceFactory(BubbleSortService bubbleSortService, QuickSortService quickSortService) {
        this.bubbleSortService = bubbleSortService;
        this.quickSortService = quickSortService;
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
