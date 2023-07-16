package net.betvictor.loripsum.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.betvictor.loripsum.exception.InvalidParameterException;
import net.betvictor.loripsum.model.ParagraphLength;
import net.betvictor.loripsum.model.TextAnalyserResponse;
import net.betvictor.loripsum.service.KafkaProducerService;
import net.betvictor.loripsum.service.LoremIpsumFetcher;
import net.betvictor.loripsum.service.TextAnalyserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.stream.Stream;

@RestController
@RequestMapping("/betvictor/text")
public class TextAnalyserController {

    private static final Logger LOGGER = LogManager.getLogger(TextAnalyserController.class);

    @Autowired
    LoremIpsumFetcher loremIpsumFetcher;

    @Autowired
    TextAnalyserService textAnalyserService;

    @Autowired
    KafkaProducerService kafkaProducerService;

    @GetMapping
    public ResponseEntity<TextAnalyserResponse> analyseText(@RequestParam(name = "p", required = true) String p,
                                                            @RequestParam(name = "l", required = true) String l) {

        Map<Integer, String> textMap = new HashMap<>();
        TextAnalyserResponse response = new TextAnalyserResponse();

        if (!Stream.of(ParagraphLength.values()).anyMatch(paragraphLength -> paragraphLength.getValue().equals(l))) {
            throw new InvalidParameterException("The value of \"l\" is invalid, it should be one of these: " +
                    "short, medium, long, verylong");
        }

        for (int i = 1; i <= Integer.valueOf(p); i++) {
            String text = loremIpsumFetcher.getText(i, l);
            textMap.put(i, text);
        }
        Instant startTime = Instant.now();

        response = textAnalyserService.analyseText(textMap, response);

        Instant endTime = Instant.now();

        response.setTotal_processing_time(String.valueOf(Duration.between(startTime, endTime).toMillis()));

        String kafkaMessage = getJson(response);

        kafkaProducerService.produce(kafkaMessage);

        return new ResponseEntity<TextAnalyserResponse>(response, HttpStatus.OK);

    }

    private String getJson(TextAnalyserResponse response) {
        ObjectMapper mapper = new ObjectMapper();
        String json = "";
        try {
            json = mapper.writeValueAsString(response);
        } catch (JsonProcessingException e) {
            LOGGER.error(e.getMessage());
        }
        return json;
    }

}
