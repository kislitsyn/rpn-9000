package com.rpn.calculator.common.operation;

import com.rpn.calculator.common.StackElement;
import com.rpn.calculator.common.exception.StackProcessorException;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;

class MultiplyOperationTest {

    private MultiplyOperation operation;

    @BeforeEach
    void setUp() {
        operation = new MultiplyOperation();
    }

    @Test
    void Multiply_ShouldCalculate_WhenStackContainsNumbers() throws StackProcessorException {
        //GIVEN
        Deque<StackElement> stack = new LinkedList<>();
        stack.addFirst(StackElement.fromValue("5"));
        stack.addFirst(StackElement.fromValue("100"));

        var originalStack = Collections.unmodifiableCollection(new LinkedList<>(stack));

        //WHEN
        var result = operation.process(stack);

        //THEN
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(result).containsExactly(StackElement.fromValue("500"));
            softly.assertThat(stack).containsExactlyElementsOf(originalStack);
        });
    }

}