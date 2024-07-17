package com.cicd.todoapi.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Priority {
    NULL, LOW, MEDIUM, HIGH;

    @JsonCreator
    public static Priority forValue(String value) {
        if (value == null || value.isEmpty()) {
            return NULL;
        }
        return Priority.valueOf(value.toUpperCase());
    }

    @JsonValue
    public String toValue() {
        if (this == NULL) {
            return null;
        }
        return this.name();
    }

//    private final String value;
/*
    Priority(String value) {
        this.value = value;
    }

    @JsonValue // JSON으로 변환할 때 Enum 값을 어떤 값으로 변환할지 설정
    public String getValue() {
        return value;
    }

    @JsonCreator // JSON으로 받을 때 Enum 값을 어떻게 변환할지 설정
    public static Priority fromValue(String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        for (Priority priority : values()) {
            if (priority.getValue().equals(value)) {
                return priority;
            }
        }
        throw new IllegalArgumentException("Unknown priority: " + value);
    }*/
}