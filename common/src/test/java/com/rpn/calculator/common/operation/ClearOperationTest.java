package com.rpn.calculator.common.operation;

import com.rpn.calculator.common.StackElement;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;

class ClearOperationTest {

    private ClearOperation operation;

    @BeforeEach
    void setUp() {
        operation = new ClearOperation();
    }

    @Test
    void Clear_ShouldCleanTheStack() {
        //GIVEN
        Deque<StackElement> stack = new LinkedList<>();
        stack.addFirst(StackElement.fromValue("1"));
        stack.addFirst(StackElement.fromValue("5"));

        var originalStack = Collections.unmodifiableCollection(new LinkedList<>(stack));

        //WHEN
        var result = operation.process(stack);

        //THEN
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(result).isEmpty();
            softly.assertThat(stack).containsExactlyElementsOf(originalStack);
        });
    }

}