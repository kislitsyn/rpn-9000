package com.rpn.calculator.common.handler;

import com.rpn.calculator.common.exception.UnsupportedParametersException;
import com.rpn.calculator.common.StackElement;
import com.rpn.calculator.common.exception.InsufficientParametersException;
import com.rpn.calculator.common.operation.Operation;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Deque;
import java.util.LinkedList;
import java.util.regex.Pattern;

public class StackHandler {

    private static final String DELIMITER = " ";
    private static final Pattern SPLIT_PATTERN = Pattern.compile(DELIMITER);

    public Result<Deque<StackElement>, String> process(Deque<StackElement> stack, String inputString) {

        var inputElements = SPLIT_PATTERN.split(inputString);

        Collection<String> processedInputElements = new LinkedList<>();

        var result = stack;

        for (String input : inputElements) {

            var inputElement = convertToStackElement(input);

            try {
                result = push(result, inputElement);
            } catch (InsufficientParametersException e) {
                int position = getLastOperatorPosition(processedInputElements);
                var error = String.format("operator '%s' (position: %d): insufficient parameters", inputElement, position);
                return Result.error(result, error);
            } catch (UnsupportedParametersException e) {
                int position = getLastOperatorPosition(processedInputElements);
                var error = String.format("operator '%s' (position: %d): is not supported", inputElement, position);
                return Result.error(result, error);
            }

            processedInputElements.add(input);
        }

        return Result.of(result);
    }

    private StackElement convertToStackElement(String stringElement) {

        try {
            return new StackElement(new BigDecimal(stringElement));
        } catch (NumberFormatException e) {
            return new StackElement(stringElement);
        }
    }

    private Deque<StackElement> push(Deque<StackElement> stack, StackElement element) throws UnsupportedParametersException, InsufficientParametersException {
        if (element.getNumber().isPresent()) {
            var currentStack = new LinkedList<>(stack);
            currentStack.addFirst(new StackElement(element.getNumber().get(), stack));
            return currentStack;
        }

        return Operation.getOperationHandler(element.getElement()).process(stack);
    }

    private static int getLastOperatorPosition(Collection<String> inputItems) {
        return inputItems.stream()
                .mapToInt(String::length)
                .map(operatorLength -> operatorLength + DELIMITER.length())
                .sum();
    }
}
