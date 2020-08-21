package com.rpn.calculator.common.processor;

import com.rpn.calculator.common.StackElement;
import com.rpn.calculator.common.exception.StackProcessorException;
import com.rpn.calculator.common.operation.Operation;

import java.util.Collection;
import java.util.Deque;
import java.util.LinkedList;
import java.util.regex.Pattern;

import static java.util.Objects.requireNonNull;

public class StackProcessor {

    private static final String DELIMITER = " ";
    private static final Pattern SPLIT_PATTERN = Pattern.compile(DELIMITER);

    public Result<Deque<StackElement>, String> process(Deque<StackElement> stack, String inputString) {
        requireNonNull(stack, "stack");
        requireNonNull(inputString, "inputString");

        var inputElements = SPLIT_PATTERN.split(inputString);

        Collection<String> processedInputElements = new LinkedList<>();

        var result = stack;

        for (String input : inputElements) {

            var inputElement = StackElement.fromValue(input);

            try {
                result = reduce(result, inputElement);
            } catch (StackProcessorException e) {
                int position = getLastOperatorPosition(processedInputElements);
                var error = String.format("operator '%s' (position: %d): %s", inputElement, position, e.getMessage());
                return Result.error(result, error);
            }

            processedInputElements.add(input);
        }

        return Result.of(result);
    }

    private Deque<StackElement> reduce(Deque<StackElement> stack, StackElement element) throws StackProcessorException {
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
