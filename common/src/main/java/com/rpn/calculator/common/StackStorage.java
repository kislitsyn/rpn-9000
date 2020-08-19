package com.rpn.calculator.common;

import java.util.Deque;
import java.util.LinkedList;

public class StackStorage {

    private Deque<StackElement> stack = new LinkedList<>();

    public Deque<StackElement> get() {
        return stack;
    }

    public void set(Deque<StackElement> stack) {
        this.stack = stack;
    }
}
