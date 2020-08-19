package com.rpn.calculator.common.operation;

import com.rpn.calculator.common.StackElement;

import java.util.Deque;
import java.util.LinkedList;

public class ClearOperation implements OperationHandler {

    @Override
    public Deque<StackElement> process(Deque<StackElement> stack) {
        return new LinkedList<>();
    }
}
