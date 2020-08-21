package com.rpn.calculator.cli;

import com.rpn.calculator.common.StackStorage;
import com.rpn.calculator.common.handler.StackHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

import static java.util.Objects.requireNonNull;

public class InputProcessor {

    private static final Logger log = LoggerFactory.getLogger(InputProcessor.class);

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

                try {
                    var result = stackHandler.process(stack, line);

                    if (resultProcessor.process(result)) {
                        storage.set(result.getResult());
                    }
                } catch (Exception e) {
                    log.error("Failed to process input: {}", line, e);
                }
            }
        }
    }
}
