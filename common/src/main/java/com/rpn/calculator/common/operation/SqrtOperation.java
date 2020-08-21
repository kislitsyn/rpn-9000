package com.rpn.calculator.common.operation;

import com.rpn.calculator.common.StackElement;
import com.rpn.calculator.common.exception.IllegalParametersException;
import com.rpn.calculator.common.exception.InsufficientParametersException;
import com.rpn.calculator.common.exception.StackProcessorException;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Deque;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class SqrtOperation implements OperationHandler {
    @Override
    public Deque<StackElement> process(Deque<StackElement> stack) throws StackProcessorException {

        var currentStack = new LinkedList<>(stack);

        StackElement element;
        try {
            element = currentStack.removeFirst();
        } catch (NoSuchElementException e) {
            throw new InsufficientParametersException();
        }

        if (element.getNumber().isEmpty()) {
            throw new InsufficientParametersException();
        }

        if (element.getNumber().get().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalParametersException();
        }

        var parameter = element.getNumber().get();

        var result = parameter.sqrt(MathContext.DECIMAL64);

        currentStack.addFirst(new StackElement(result, stack));

        return currentStack;
    }
}
