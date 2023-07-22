package net.betvictor.loripsum.service;

import net.betvictor.loripsum.model.Message;
import net.betvictor.loripsum.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.google.common.collect.Lists;

import java.util.Comparator;
import java.util.List;

@Service
public class HistoryService {

    @Autowired
    MessageRepository messageRepository;

    public List<Message> getHistory() {
        var messages = messageRepository.findAll();
        var messageList = Lists.newArrayList(messages);
        messageList.sort(Comparator.comparing(Message::getTimestamp).reversed());
        return messageList.subList(0, Math.min(10, messageList.size()));
    }
}
