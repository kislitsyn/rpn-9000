package com.rpn.calculator.common.operation;

import com.rpn.calculator.common.StackElement;
import com.rpn.calculator.common.exception.InsufficientParametersException;

import java.math.BigDecimal;
import java.util.Deque;
import java.util.LinkedList;

public abstract class TwoArgumentsOperation implements OperationHandler {

    @Override
    public Deque<StackElement> process(Deque<StackElement> stack) throws InsufficientParametersException {

        var currentStack = new LinkedList<>(stack);

        var secondParameter = getNextParameter(currentStack);

        var firstParameter = getNextParameter(currentStack);

        var result = process(firstParameter.getNumber().get(), secondParameter.getNumber().get());

        currentStack.addFirst(new StackElement(result, stack));

        return currentStack;
    }

    private StackElement getNextParameter(Deque<StackElement> stack) throws InsufficientParametersException {
        if (stack.isEmpty()) {
            throw new InsufficientParametersException("Stack is empty");
        }

        var element = stack.getFirst();

        if (element.getNumber().isEmpty()) {
            throw new InsufficientParametersException("Parameter is not valid for operation MULTIPLICATION: " + element);
        }

        return stack.removeFirst();
    }

    protected abstract BigDecimal process(BigDecimal firstParameter, BigDecimal secondParameter);
}
