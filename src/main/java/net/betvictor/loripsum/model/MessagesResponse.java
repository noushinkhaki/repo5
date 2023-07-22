package net.betvictor.loripsum.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class MessagesResponse {

    @JsonProperty
    private List<Message> messages;

    @JsonCreator
    public MessagesResponse(){}
    public MessagesResponse(List<Message> messages) {
        this.messages = messages;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    @Override
    public String toString() {
        return "MessagesResponse{" +
                "messages=" + messages +
                '}';
    }
}
