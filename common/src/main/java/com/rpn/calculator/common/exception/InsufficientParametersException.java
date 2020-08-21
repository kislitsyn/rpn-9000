package com.rpn.calculator.common.exception;

public class InsufficientParametersException extends StackProcessorException {

    public InsufficientParametersException() {
        super("insufficient parameters");
    }
}
