package com.rpn.calculator.common.operation;

import com.rpn.calculator.common.StackElement;
import com.rpn.calculator.common.exception.InsufficientParametersException;

import java.util.Deque;

public interface OperationHandler {

    Deque<StackElement> process(Deque<StackElement> stack) throws InsufficientParametersException;
}
