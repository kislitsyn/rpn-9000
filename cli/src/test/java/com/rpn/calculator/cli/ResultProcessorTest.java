package com.rpn.calculator.cli;

import com.rpn.calculator.common.StackElement;
import com.rpn.calculator.common.handler.Result;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Deque;
import java.util.LinkedList;

import static org.assertj.core.api.Assertions.assertThat;

class ResultProcessorTest {

    private ByteArrayOutputStream captor;

    private ResultProcessor processor;


    @BeforeEach
    public void setUp() {
        captor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(captor));

        processor = new ResultProcessor();
    }

    @Test
    void ResultProcessor_ShouldPrintStack_ForSuccessfulResult() {
        //GIVEN
        Deque<StackElement> stack = new LinkedList<>();
        stack.addFirst(StackElement.fromValue("someElement"));
        Result<Deque<StackElement>, String> result = Result.of(stack);

        //WHEN
        processor.process(result);

        //THEN
        var actualResult = captor.toString();
        assertThat(actualResult).isEqualTo("stack: someElement \n");
    }

    @Test
    void ResultProcessor_ShouldPrintStackAndError_ForResultWithError() {
        //GIVEN
        Deque<StackElement> stack = new LinkedList<>();
        stack.addFirst(StackElement.fromValue("someElement"));
        Result<Deque<StackElement>, String> result = Result.error(stack, "errorMessage");

        //WHEN
        processor.process(result);

        //THEN
        var actualResult = captor.toString();
        assertThat(actualResult).isEqualTo("errorMessage\nstack: someElement \n");
    }

}