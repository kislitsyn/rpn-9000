package com.rpn.calculator.cli;

import com.rpn.calculator.common.StackElement;
import com.rpn.calculator.common.processor.Result;

import java.util.Deque;

import static java.util.Objects.requireNonNull;

public class ResultPrinter {

    public boolean print(Result<Deque<StackElement>, String> result) {
        requireNonNull(result, "result");
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
