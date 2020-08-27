package com.myservice.mymarket.service.shared;

import com.myservice.mymarket.model.StockQuote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class SocketStockIntegration {

    @Autowired
    private SimpMessagingTemplate template;

    @MessageMapping("/refreshStockQuote")
    @SendTo("/topic/refreshStockQuote")
    public void broadcastStockQuote(@Payload StockQuote stockQuote) {
        this.template.convertAndSend("/topic/refreshStockQuote", stockQuote);
    }
}
