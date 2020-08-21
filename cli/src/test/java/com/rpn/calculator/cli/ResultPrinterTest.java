package com.rpn.calculator.cli;

import com.rpn.calculator.common.StackElement;
import com.rpn.calculator.common.processor.Result;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Deque;
import java.util.LinkedList;

import static org.assertj.core.api.Assertions.assertThat;

class ResultPrinterTest {

    private ByteArrayOutputStream captor;

    private ResultPrinter printer;


    @BeforeEach
    public void setUp() {
        captor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(captor));

        printer = new ResultPrinter();
    }

    @Test
    void ResultPrinter_ShouldPrintStack_ForSuccessfulResult() {
        //GIVEN
        Deque<StackElement> stack = new LinkedList<>();
        stack.addFirst(StackElement.fromValue("someElement"));
        Result<Deque<StackElement>, String> result = Result.of(stack);

        //WHEN
        printer.print(result);

        //THEN
        var actualResult = captor.toString();
        assertThat(actualResult).isEqualTo("stack: someElement \n");
    }

    @Test
    void ResultPrinter_ShouldPrintStackAndError_ForResultWithError() {
        //GIVEN
        Deque<StackElement> stack = new LinkedList<>();
        stack.addFirst(StackElement.fromValue("someElement"));
        Result<Deque<StackElement>, String> result = Result.error(stack, "errorMessage");

        //WHEN
        printer.print(result);

        //THEN
        var actualResult = captor.toString();
        assertThat(actualResult).isEqualTo("errorMessage\nstack: someElement \n");
    }

}