package net.betvictor.loripsum.model;

public enum ParagraphLength {
    SHORT("short"),
    MEDIUM( "medium"),
    LONG("long"),
    VERYLONG("verylong");

    private final String value;

    ParagraphLength(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
