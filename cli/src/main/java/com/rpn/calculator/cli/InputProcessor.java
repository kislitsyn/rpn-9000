package com.rpn.calculator.cli;

import com.rpn.calculator.common.StackStorage;
import com.rpn.calculator.common.processor.StackProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

import static java.util.Objects.requireNonNull;

public class InputProcessor {

    private static final Logger log = LoggerFactory.getLogger(InputProcessor.class);

    private final StackStorage storage;

    private final StackProcessor stackProcessor;

    private final ResultPrinter resultPrinter;

    public InputProcessor(StackStorage storage,
                          StackProcessor stackProcessor,
                          ResultPrinter resultPrinter) {
        this.storage = requireNonNull(storage, "storage");
        this.stackProcessor = requireNonNull(stackProcessor, "stackProcessor");
        this.resultPrinter = requireNonNull(resultPrinter, "resultPrinter");
    }

    public void start() {
        try (var scanner = new Scanner(System.in)) {

            //noinspection InfiniteLoopStatement
            while(true) {
                var line = scanner.nextLine();

                var stack = storage.get();

                try {
                    var result = stackProcessor.process(stack, line);

                    if (resultPrinter.print(result)) {
                        storage.set(result.getResult());
                    }
                } catch (Exception e) {
                    log.error("Failed to process input: {}", line, e);
                }
            }
        }
    }
}
