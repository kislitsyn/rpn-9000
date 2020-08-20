package com.rpn.calculator.common.operation;

import com.rpn.calculator.common.StackElement;
import com.rpn.calculator.common.exception.InsufficientParametersException;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;

class DivideOperationTest {

    private DivideOperation operation;

    @BeforeEach
    void setUp() {
        operation = new DivideOperation();
    }

    @Test
    void Divide_ShouldCalculate_WhenStackContainsNumbers() throws InsufficientParametersException {
        //GIVEN
        Deque<StackElement> stack = new LinkedList<>();
        stack.addFirst(StackElement.fromValue("3"));
        stack.addFirst(StackElement.fromValue("12"));

        var originalStack = Collections.unmodifiableCollection(new LinkedList<>(stack));

        //WHEN
        var result = operation.process(stack);

        //THEN
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(result).containsExactly(StackElement.fromValue("0.25"));
            softly.assertThat(stack).containsExactlyElementsOf(originalStack);
        });
    }

}