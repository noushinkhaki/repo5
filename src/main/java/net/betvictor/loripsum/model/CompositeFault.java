package net.betvictor.loripsum.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class CompositeFault {

    @JsonProperty("faults")
    private List<Fault> faults = new ArrayList<>();

    public List<Fault> getFaults() {
        return faults;
    }

    public void setFaults(List<Fault> faults) {
        this.faults = faults;
    }

    @Override
    public String toString() {
        return "CompositeFault{" +
                "faults=" + faults +
                '}';
    }
}
