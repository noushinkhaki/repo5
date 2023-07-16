package net.betvictor.loripsum.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Fault {

    @JsonProperty("explanationText")
    private String explanationText;

    public String getExplanationText() {
        return explanationText;
    }

    public void setExplanationText(String explanationText) {
        this.explanationText = explanationText;
    }

    @Override
    public String toString() {
        return "Fault{" +
                "explanationText='" + explanationText + '\'' +
                '}';
    }
}
