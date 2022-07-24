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
import java.util.HashMap;
import java.util.Map;

public class StateSortHandler extends TextWebSocketHandler {

    private final Logger logger = LoggerFactory.getLogger (StateSortHandler.class);

    private final Gson gson = new Gson ();
    private final SortServiceFactory sortServiceFactory;
    private final Map<WebSocketSession, SortType> states = new HashMap<> ();

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
            } catch (IOException e) {
                logger.error (e.getMessage ());
            }
        }));
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        super.handleTextMessage (session, message);
        IncomeMessage income = gson.fromJson (message.getPayload (), IncomeMessage.class);
        SortType sortType = income.getSortType ();
        String target = sortType.name ();
        if(!income.isDrop ()) {
            states.put (session, sortType);
            sortServiceFactory.get (sortType).sort (target);
        } else {
            states.remove (session);
            sortServiceFactory.get (sortType).drop(target);
        }
    }
}
