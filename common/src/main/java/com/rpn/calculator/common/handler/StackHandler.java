package com.rpn.calculator.common.handler;

import com.rpn.calculator.common.StackElement;
import com.rpn.calculator.common.exception.InsufficientParametersException;
import com.rpn.calculator.common.exception.UnsupportedOperationException;
import com.rpn.calculator.common.operation.Operation;

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

            var inputElement = StackElement.fromValue(input);

            try {
                result = push(result, inputElement);
            } catch (InsufficientParametersException e) {
                int position = getLastOperatorPosition(processedInputElements);
                var error = String.format("operator '%s' (position: %d): insufficient parameters", inputElement, position);
                return Result.error(result, error);
            } catch (UnsupportedOperationException e) {
                int position = getLastOperatorPosition(processedInputElements);
                var error = String.format("operator '%s' (position: %d): is not supported", inputElement, position);
                return Result.error(result, error);
            }  catch (IllegalArgumentException e) {
                int position = getLastOperatorPosition(processedInputElements);
                var error = String.format("operator '%s' (position: %d): illegal parameters", inputElement, position);
                return Result.error(result, error);
            }

            processedInputElements.add(input);
        }

        return Result.of(result);
    }

    private Deque<StackElement> push(Deque<StackElement> stack, StackElement element) throws UnsupportedOperationException, InsufficientParametersException {
        if (element.getNumber().isPresent()) {
            var currentStack = new LinkedList<>(stack);
            currentStack.addFirst(new StackElement(element.getNumber().get(), stack));
            return currentStack;
        }

        return Operation.getOperationHandler(element.getOperator()).process(stack);
    }

    private static int getLastOperatorPosition(Collection<String> inputItems) {
        return inputItems.stream()
                .mapToInt(String::length)
                .map(operatorLength -> operatorLength + DELIMITER.length())
                // position numbers start from '1'
                .sum() + 1;
    }
}
