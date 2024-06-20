package com.sparta.greeypeople.order.enumeration;

public enum Process {
    COMPLETED("완료"),
    CANCELED("취소");

    private final String process;

    Process(String process) {
        this.process = process;
    }

    public String getProcess() {
        return this.process;
    }
}