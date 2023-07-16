package net.betvictor.loripsum.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TextAnalyserResponse {

    @JsonProperty
    private String freq_word;

    @JsonProperty
    private String avg_paragraph_size;

    @JsonProperty
    private String avg_paragraph_processing_time;

    @JsonProperty
    private String total_processing_time;

    @JsonIgnore
    private int occuranceNum;

    public String getFreq_word() {
        return freq_word;
    }

    public void setFreq_word(String freq_word) {
        this.freq_word = freq_word;
    }

    public String getAvg_paragraph_size() {
        return avg_paragraph_size;
    }

    public void setAvg_paragraph_size(String avg_paragraph_size) {
        this.avg_paragraph_size = avg_paragraph_size;
    }

    public String getAvg_paragraph_processing_time() {
        return avg_paragraph_processing_time;
    }

    public void setAvg_paragraph_processing_time(String avg_paragraph_processing_time) {
        this.avg_paragraph_processing_time = avg_paragraph_processing_time;
    }

    public String getTotal_processing_time() {
        return total_processing_time;
    }

    public void setTotal_processing_time(String total_processing_time) {
        this.total_processing_time = total_processing_time;
    }

    public int getOccuranceNum() {
        return occuranceNum;
    }

    public void setOccuranceNum(int occuranceNum) {
        this.occuranceNum = occuranceNum;
    }

    @Override
    public String toString() {
        return "TextAnalyserResponse{" +
                "freq_word='" + freq_word + '\'' +
                ", avg_paragraph_size='" + avg_paragraph_size + '\'' +
                ", avg_paragraph_processing_time='" + avg_paragraph_processing_time + '\'' +
                ", total_processing_time='" + total_processing_time + '\'' +
                ", occuranceNum=" + occuranceNum +
                '}';
    }
}
