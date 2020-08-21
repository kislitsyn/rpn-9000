package com.rpn.calculator.cli;

import com.rpn.calculator.common.StackStorage;
import com.rpn.calculator.common.processor.StackProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Application {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        log.info("RPN 9000 has been started");

        var storage = new StackStorage();

        var processor = new StackProcessor();

        var printer = new ResultPrinter();

        var inputHandler = new InputProcessor(storage, processor, printer);

        inputHandler.start();
    }
}
