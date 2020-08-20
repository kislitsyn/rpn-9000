package com.rpn.calculator.common.operation;

import com.rpn.calculator.common.StackElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Deque;
import java.util.LinkedList;

import static org.assertj.core.api.Assertions.assertThat;

class UndoOperationTest {

    private UndoOperation operation;

    @BeforeEach
    void setUp() {
        operation = new UndoOperation();
    }

    @Test
    void Undo_ShouldRestoreOriginalStack_IfItExists() {
        //GIVEN
        Deque<StackElement> originalStack = new LinkedList<>();
        originalStack.addFirst(StackElement.fromValue("1"));

        Deque<StackElement> stack = new LinkedList<>();
        stack.addFirst(new StackElement(new BigDecimal("2"), originalStack));

        //WHEN
        var result = operation.process(stack);

        //THEN
        assertThat(result).containsExactlyElementsOf(originalStack);
    }

    @Test
    void Undo_ShouldNotReplaceStackWithOriginal_IfOriginalStackIsEmpty() {
        //GIVEN
        Deque<StackElement> stack = new LinkedList<>();
        stack.addFirst(StackElement.fromValue("2"));

        //WHEN
        var result = operation.process(stack);

        //THEN
        assertThat(result).containsExactlyElementsOf(stack);
    }

    @Test
    void Undo_ShouldReturnEmptyStack_IfStackIsEmpty() {
        //GIVEN
        Deque<StackElement> stack = new LinkedList<>();

        //WHEN
        var result = operation.process(stack);

        //THEN
        assertThat(result).isEmpty();
    }

}