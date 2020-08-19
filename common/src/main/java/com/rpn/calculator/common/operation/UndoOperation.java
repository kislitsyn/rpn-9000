package com.rpn.calculator.common.operation;

import com.rpn.calculator.common.StackElement;

import java.util.Deque;

public class UndoOperation implements OperationHandler {
    @Override
    public Deque<StackElement> process(Deque<StackElement> stack) {
        if (stack.isEmpty()) {
            return stack;
        }
        return stack.peekFirst().getOriginal().orElse(stack);
    }
}
