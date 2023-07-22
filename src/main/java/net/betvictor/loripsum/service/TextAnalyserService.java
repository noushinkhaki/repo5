package net.betvictor.loripsum.service;

import net.betvictor.loripsum.model.TextAnalyserResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Service
public class TextAnalyserService {

    private static final Logger LOGGER = LogManager.getLogger(TextAnalyserService.class);

    public TextAnalyserResponse analyseText(Map<Integer, String> textMap, TextAnalyserResponse response) {

        var text = new StringBuilder();

        for (Map.Entry<Integer,String> entry : textMap.entrySet()) {
            text.append(entry.getValue());
        }

        LOGGER.info("The whole text is: " + "\n" + text.toString());

        var paragraphs = text.toString().split("</p>");
        Map<Integer, String> mostFrequentWordMapRes = new HashMap<>();
        var mostFrequentWordMapPerParagraph = new HashMap<Integer, String>();
        double sumParagraphSize = 0;
        double sumParagraphProcessingTime = 0;

        for (int i = 0; i < paragraphs.length; i++) {
            LOGGER.debug("Processing paragraph[" + i + "]:" + paragraphs[i]);
            var startTime = Instant.now();
            sumParagraphSize += paragraphs[i].length();
            var words = paragraphs[i].split(" ");
            var refinedWords = refineWords(words);
            mostFrequentWordMapRes = findMostFrequentWordInParagraph(refinedWords);
            mostFrequentWordMapPerParagraph.put(i, mostFrequentWordMapRes.entrySet().stream().findFirst().get().getKey()
                    + "/" + mostFrequentWordMapRes.entrySet().stream().findFirst().get().getValue());
            var endTime = Instant.now();
            var paragraphProcessingTime = Duration.between(startTime, endTime);
            sumParagraphProcessingTime += paragraphProcessingTime.toMillis();
        }
        response.setAvg_paragraph_size(String.valueOf(sumParagraphSize/paragraphs.length));
        response.setAvg_paragraph_processing_time(String.valueOf(sumParagraphProcessingTime/paragraphs.length));
        return findMostFrequentWord(mostFrequentWordMapPerParagraph, response);
    }

    private Map<Integer, String> findMostFrequentWordInParagraph(String[] refinedWords) {
        var wordFrequencyMap = new HashMap<String, Integer>();
        for (String word : refinedWords) {
            if (wordFrequencyMap.containsKey(word)) {
                wordFrequencyMap.put(word, wordFrequencyMap.get(word) + 1);
            } else {
                wordFrequencyMap.put(word, 1);
            }
        }
        var mostFrequentWord = "";
        int maxFrequency = 0;
        for (Map.Entry<String, Integer> entry : wordFrequencyMap.entrySet()) {
            if (entry.getValue() > maxFrequency) {
                mostFrequentWord = entry.getKey();
                maxFrequency = entry.getValue();
            }
        }
        var result = new HashMap<Integer, String>();
        result.put(maxFrequency, mostFrequentWord);
        return result;
    }

    private String[] refineWords(String[] words) {
        for (int i = 0; i < words.length; i++) {
            if (words[i].startsWith("<p>")) {
                words[i] = words[i].substring(3);
            }
            if (words[i].endsWith(".") || words[i].endsWith(",") || words[i].endsWith(";") || words[i].endsWith("?")) {
                words[i] = words[i].substring(0, words[i].length()-1);
            }
            words[i] = words[i];
        }
        return words;
    }

    private TextAnalyserResponse findMostFrequentWord(Map<Integer, String> mostFrequentWordMapPerParagraph, TextAnalyserResponse response) {
        var mostFrequentWord = "";
        int maxOccurancy = 0;
        for (Map.Entry<Integer, String> entry : mostFrequentWordMapPerParagraph.entrySet()) {
            var values = entry.getValue().split("/");
            if (Integer.parseInt(values[0]) > maxOccurancy) {
                maxOccurancy = Integer.parseInt(values[0]);
                mostFrequentWord = values[1];
            }
        }
        response.setFreq_word(mostFrequentWord);
        LOGGER.info("Most frequent word is: " + mostFrequentWord);
        response.setOccuranceNum(maxOccurancy);
        return response;
    }
}
