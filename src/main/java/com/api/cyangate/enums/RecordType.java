package com.api.cyangate.enums;

public enum RecordType {
    Tag("Tag"),
    Text("Text"),
    Custom("Custom");
    private final String displayValue;

    private RecordType(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }
}
