package ru.ribenjyeo.sorting_algorithm.socket;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import ru.ribenjyeo.sorting_algorithm.SortServiceFactory;
import ru.ribenjyeo.sorting_algorithm.service.BubbleSortService;

@Configuration
@EnableScheduling
@EnableWebSocket
public class SocketConfig implements WebSocketConfigurer {

    private final StateSortHandler handler;

    public SocketConfig(SortServiceFactory sortServiceFactory) {
        this.handler = new StateSortHandler (sortServiceFactory);
    }

    @Scheduled(fixedRate = 10)
    private void sendStates() {
        handler.sendState ();
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler (handler, "/websocket").setAllowedOrigins ("*");
    }
}
