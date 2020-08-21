package com.rpn.calculator.common.operation;

import com.rpn.calculator.common.StackElement;
import com.rpn.calculator.common.exception.InsufficientParametersException;
import com.rpn.calculator.common.exception.UnsupportedOperationException;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Deque;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class SqrtOperation implements OperationHandler {
    @Override
    public Deque<StackElement> process(Deque<StackElement> stack) throws InsufficientParametersException {

        var currentStack = new LinkedList<>(stack);

        StackElement element;
        try {
            element = currentStack.getFirst();
        } catch (NoSuchElementException e) {
            throw new InsufficientParametersException("Stack is empty");
        }

        if (element.getNumber().isEmpty()) {
            throw new InsufficientParametersException("Parameter is not valid for operation SQRT: " + element);
        }

        if (element.getNumber().get().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Parameter is not valid for operation SQRT: " + element);
        }

        var parameter = element.getNumber().get();

        currentStack.removeFirst();

        var result = parameter.sqrt(MathContext.DECIMAL64);

        currentStack.addFirst(new StackElement(result, stack));

        return currentStack;
    }
}