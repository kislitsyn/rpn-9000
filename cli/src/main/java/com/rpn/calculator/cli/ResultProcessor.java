package com.rpn.calculator.cli;

import com.rpn.calculator.common.handler.Result;
import com.rpn.calculator.common.StackElement;

import java.util.Deque;

public class ResultProcessor {

    public boolean process(Result<Deque<StackElement>, String> result) {

        if (result.getError().isPresent()) {
            System.out.println(result.getError().get());
            printResult(result.getResult());
            return false;
        }

        printResult(result.getResult());
        return true;
    }

    private void printResult(Deque<StackElement> result) {
        System.out.print("stack:");

        result.descendingIterator()
                .forEachRemaining(stackElement -> System.out.print(" " + stackElement.toString()));

        System.out.println(" ");
    }
}
