package net.betvictor.loripsum.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;

public class ErrorResponse {

    @JsonProperty("message")
    private String message;

    @JsonProperty("compositeFault")
    private CompositeFault compositeFault;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public CompositeFault getCompositeFault() {
        return compositeFault;
    }

    public void setCompositeFault(CompositeFault compositeFault) {
        this.compositeFault = compositeFault;
    }

    /**
     * Generate ErrorResponse object using the details passed in parameters.
     *
     * @param errMsg           High level error message
     * @param faultExplanation Explanation of the fault
     * @return Generated ErrorResponse object with a list of one Fault.
     */
    public static ErrorResponse generateErrResponse(String errMsg, String faultExplanation) {
        Fault fault = new Fault();
        fault.setExplanationText(faultExplanation);

        CompositeFault compositeFault = new CompositeFault();
        compositeFault.setFaults(Arrays.asList(fault));

        ErrorResponse errResponse = new ErrorResponse();
        errResponse.setCompositeFault(compositeFault);
        errResponse.setMessage(errMsg);

        return errResponse;
    }


}
