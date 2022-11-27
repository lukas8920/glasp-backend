package org.kehrbusch.mappers;

import org.kehrbusch.entities.MessageResponse;
import org.kehrbusch.entities.MessageResponseApi;
import org.springframework.stereotype.Component;

@Component
public class MessageResponseMapper {
    public MessageResponseApi map(MessageResponse response){
        MessageResponseApi messageResponseApi = new MessageResponseApi();
        messageResponseApi.setMessage(response.getMessage());
        return messageResponseApi;
    }
}
