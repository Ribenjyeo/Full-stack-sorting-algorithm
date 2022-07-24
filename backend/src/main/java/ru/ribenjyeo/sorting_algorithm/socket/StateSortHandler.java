package ru.ribenjyeo.sorting_algorithm.socket;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import ru.ribenjyeo.sorting_algorithm.SortServiceFactory;
import ru.ribenjyeo.sorting_algorithm.bean.SortLog;
import ru.ribenjyeo.sorting_algorithm.bean.SortType;
import ru.ribenjyeo.sorting_algorithm.socket.bean.IncomeMessage;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class StateSortHandler extends TextWebSocketHandler {

    private final Logger logger = LoggerFactory.getLogger (StateSortHandler.class);

    private final Gson gson = new Gson ();
    private final SortServiceFactory sortServiceFactory;
    private final Map<WebSocketSession, SortType> states = new ConcurrentHashMap<> ();

    public StateSortHandler(SortServiceFactory sortServiceFactory) {
        this.sortServiceFactory = sortServiceFactory;
    }

    // Получение статуса сортировок
    public void sendState() {
        states.forEach (((session, sortType) -> {
            try {
            SortLog log = sortServiceFactory.get (sortType).pollLong (sortType.name ());
            TextMessage msg = new TextMessage (gson.toJson (log));
            session.sendMessage (msg);
            } catch (IOException | IllegalStateException e) {
                logger.error(e.getMessage());
                states.remove(session);
            } catch (NullPointerException e) {
                logger.info("Sort {}. Queue is empty. " + e.getMessage(), sortType);
            }
        }));
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        super.handleTextMessage(session, message);
        IncomeMessage income = gson.fromJson(message.getPayload(), IncomeMessage.class);
        SortType sortType = income.getSortType();
        String target = sortType.name();
        if (income.isPlay()) {
            states.put(session, sortType);
            sortServiceFactory.get(sortType).sort(target);
        }
        if (income.isStop()) {
            states.remove(session);
            sortServiceFactory.get(sortType).drop(target);
        }
    }
}
