package com.rpn.calculator.common.operation;

import com.rpn.calculator.common.StackElement;
import com.rpn.calculator.common.exception.InsufficientParametersException;
import com.rpn.calculator.common.exception.StackProcessorException;

import java.math.BigDecimal;
import java.util.Deque;
import java.util.LinkedList;

public abstract class TwoArgumentsOperation implements OperationHandler {

    @Override
    public Deque<StackElement> process(Deque<StackElement> stack) throws StackProcessorException {

        var currentStack = new LinkedList<>(stack);

        var secondParameter = getNextParameter(currentStack);

        var firstParameter = getNextParameter(currentStack);

        var result = process(firstParameter, secondParameter);

        currentStack.addFirst(new StackElement(result, stack));

        return currentStack;
    }

    private BigDecimal getNextParameter(Deque<StackElement> stack) throws StackProcessorException {
        if (stack.isEmpty()) {
            throw new InsufficientParametersException();
        }

        var element = stack.removeFirst();

        if (element.getNumber().isEmpty()) {
            throw new InsufficientParametersException();
        }

        return element.getNumber().get();
    }

    protected abstract BigDecimal process(BigDecimal firstParameter, BigDecimal secondParameter);
}
