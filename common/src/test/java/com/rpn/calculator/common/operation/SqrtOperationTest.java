package com.rpn.calculator.common.operation;

import com.rpn.calculator.common.StackElement;
import com.rpn.calculator.common.exception.IllegalParametersException;
import com.rpn.calculator.common.exception.InsufficientParametersException;
import com.rpn.calculator.common.exception.StackProcessorException;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SqrtOperationTest {

    private SqrtOperation operation;

    @BeforeEach
    void setUp() {
        operation = new SqrtOperation();
    }

    @Test
    void Sqrt_ShouldCalculate_WhenStackContainsNumber() throws StackProcessorException {
        //GIVEN
        Deque<StackElement> stack = new LinkedList<>();
        stack.addFirst(StackElement.fromValue("4"));

        var originalStack = Collections.unmodifiableCollection(new LinkedList<>(stack));

        //WHEN
        var result = operation.process(stack);

        //THEN
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(result).containsExactly(StackElement.fromValue("2"));
            softly.assertThat(stack).containsExactlyElementsOf(originalStack);
        });
    }

    @Test
    void Sqrt_ShouldCalculateOnlyFirstElement_WhenStackContainsNumbers() throws StackProcessorException {
        //GIVEN
        Deque<StackElement> stack = new LinkedList<>();
        stack.addFirst(StackElement.fromValue("3"));
        stack.addFirst(StackElement.fromValue("4"));

        var originalStack = Collections.unmodifiableCollection(new LinkedList<>(stack));

        //WHEN
        var result = operation.process(stack);

        //THEN
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(result).containsExactly(StackElement.fromValue("2"), StackElement.fromValue("3"));
            softly.assertThat(stack).containsExactlyElementsOf(originalStack);
        });
    }

    @Test
    void Sqrt_ShouldThrowError_ForNegativeParameter() {
        //GIVEN
        Deque<StackElement> stack = new LinkedList<>();
        stack.addFirst(StackElement.fromValue("-1"));

        //WHEN + THEN
        assertThatThrownBy(() -> operation.process(stack))
                .isInstanceOf(IllegalParametersException.class);
    }

    @Test
    void Sqrt_ShouldThrowException_WhenStackIsEmpty() {
        //GIVEN
        Deque<StackElement> stack = new LinkedList<>();

        //WHEN + THEN
        assertThatThrownBy(() -> operation.process(stack))
                .isInstanceOf(InsufficientParametersException.class);

    }

    @Test
    void Sqrt_ShouldThrowException_WhenParameterIsNotNumber() {
        //GIVEN
        Deque<StackElement> stack = new LinkedList<>();
        stack.addFirst(StackElement.fromValue("undo"));

        //WHEN + THEN
        assertThatThrownBy(() -> operation.process(stack))
                .isInstanceOf(InsufficientParametersException.class);

    }

    @Test
    void Sqrt_ShouldStoreOriginalStack_WhenOperationIsSuccessful() throws StackProcessorException {
        //GIVEN
        Deque<StackElement> stack = new LinkedList<>();
        stack.addFirst(StackElement.fromValue("5"));

        //WHEN
        var result = operation.process(stack);

        //THEN
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(result).isNotEmpty();

            if (!result.isEmpty()) {
                var original = result.peekFirst().getOriginal();
                softly.assertThat(original).isNotEmpty();
                original.ifPresent(stackElements -> softly.assertThat(stackElements).containsExactlyElementsOf(stack));
            }
        });

    }

}