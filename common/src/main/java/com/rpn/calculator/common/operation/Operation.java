package com.rpn.calculator.common.operation;

import com.rpn.calculator.common.exception.UnsupportedOperationException;

import java.util.regex.Pattern;
import java.util.stream.Stream;

public enum Operation {

    UNDO(Pattern.compile("undo"), new UndoOperation()),

    CLEAR(Pattern.compile("clear"), new ClearOperation()),

    SQRT(Pattern.compile("sqrt"), new SqrtOperation()),

    MULTIPLICATION(Pattern.compile("\\*"), new MultiplyOperation()),

    DIVISION(Pattern.compile("/"), new DivideOperation()),

    ADDITION(Pattern.compile("\\+"), new AdditionOperation()),

    SUBTRACTION(Pattern.compile("-"), new SubtractionOperation());

    private final Pattern pattern;
    private final OperationHandler handler;

    Operation(Pattern pattern, OperationHandler handler) {
        this.pattern = pattern;
        this.handler = handler;
    }

    public OperationHandler getHandler() {
        return handler;
    }

    public static OperationHandler getOperationHandler(String element) throws UnsupportedOperationException {
        return Stream.of(Operation.values())
                .filter(operation -> operation.pattern.matcher(element).matches())
                .findFirst()
                .orElseThrow(UnsupportedOperationException::new)
                .handler;
    }
}
