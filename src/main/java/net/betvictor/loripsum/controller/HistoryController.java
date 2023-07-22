package net.betvictor.loripsum.controller;

import net.betvictor.loripsum.model.MessagesResponse;
import net.betvictor.loripsum.service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/betvictor/history")
public class HistoryController {

    @Autowired
    HistoryService historyService;

    @GetMapping
    public ResponseEntity<MessagesResponse> getHistory() {
        var messages = historyService.getHistory();
        var messagesResponse = new MessagesResponse(messages);
        return new ResponseEntity<MessagesResponse>(messagesResponse, HttpStatus.OK);
    }
}
