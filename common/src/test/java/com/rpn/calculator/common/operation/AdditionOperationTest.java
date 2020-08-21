package com.rpn.calculator.common.operation;

import com.rpn.calculator.common.StackElement;
import com.rpn.calculator.common.exception.StackProcessorException;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;

class AdditionOperationTest {

    private AdditionOperation operation;

    @BeforeEach
    void setUp() {
        operation = new AdditionOperation();
    }

    @Test
    void Addition_ShouldCalculateAddition_WhenStackContainsNumbers() throws StackProcessorException {
        //GIVEN
        Deque<StackElement> stack = new LinkedList<>();
        stack.addFirst(StackElement.fromValue("1"));
        stack.addFirst(StackElement.fromValue("2"));

        var originalStack = Collections.unmodifiableCollection(new LinkedList<>(stack));

        //WHEN
        var result = operation.process(stack);

        //THEN
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(result).containsExactly(StackElement.fromValue("3"));
            softly.assertThat(stack).containsExactlyElementsOf(originalStack);
        });
    }

}