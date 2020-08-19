package com.rpn.calculator.cli;

import com.rpn.calculator.common.StackStorage;
import com.rpn.calculator.common.handler.StackHandler;

import java.util.Scanner;

import static java.util.Objects.requireNonNull;

public class InputProcessor {

    private final StackStorage storage;

    private final StackHandler stackHandler;

    private final ResultProcessor resultProcessor;

    public InputProcessor(StackStorage storage,
                          StackHandler stackHandler,
                          ResultProcessor resultProcessor) {
        this.storage = requireNonNull(storage, "storage");
        this.stackHandler = requireNonNull(stackHandler, "stackHandler");
        this.resultProcessor = requireNonNull(resultProcessor, "resultProcessor");
    }

    public void start() {
        try (var scanner = new Scanner(System.in)) {

            //noinspection InfiniteLoopStatement
            while(true) {
                var line = scanner.nextLine();

                var stack = storage.get();

                var result = stackHandler.process(stack, line);

                if (resultProcessor.process(result)) {
                    storage.set(result.getResult());
                }
            }
        }
    }
}
