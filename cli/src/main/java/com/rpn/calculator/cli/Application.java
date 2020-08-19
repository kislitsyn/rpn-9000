package com.rpn.calculator.cli;

import com.rpn.calculator.common.StackStorage;
import com.rpn.calculator.common.handler.StackHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Application {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        log.info("RPN 9000 has been started");

        var storage = new StackStorage();

        var stackHandler = new StackHandler();

        var resultProcessor = new ResultProcessor();

        var inputHandler = new InputProcessor(storage, stackHandler, resultProcessor);

        inputHandler.start();
    }
}
