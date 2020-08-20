package com.rpn.calculator.common.operation;

import com.rpn.calculator.common.StackElement;
import com.rpn.calculator.common.exception.InsufficientParametersException;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.Deque;
import java.util.LinkedList;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TwoArgumentsOperationTest {

    static Stream<Arguments> twoArgumentsOperations() {
        return Stream.of(Operation.values())
                .filter(operation -> TwoArgumentsOperation.class.isAssignableFrom(operation.getHandler().getClass()))
                .map(Arguments::of);
    }

    @ParameterizedTest
    @MethodSource("twoArgumentsOperations")
    void TwoArgumentOperation_ShouldThrowException_WhenStackDoesNotContainEnoughParameters(Operation operationType) {
        //GIVEN
        Deque<StackElement> stack = new LinkedList<>();
        stack.addFirst(StackElement.fromValue("1"));

        //WHEN + THEN
        assertThatThrownBy(() -> operationType.getHandler().process(stack))
                .isInstanceOf(InsufficientParametersException.class);

    }

    @ParameterizedTest
    @MethodSource("twoArgumentsOperations")
    void TwoArgumentOperation_ShouldThrowException_WhenStackIsEmpty(Operation operationType) {
        //GIVEN
        Deque<StackElement> stack = new LinkedList<>();

        //WHEN + THEN
        assertThatThrownBy(() -> operationType.getHandler().process(stack))
                .isInstanceOf(InsufficientParametersException.class);

    }

    @ParameterizedTest
    @MethodSource("twoArgumentsOperations")
    void TwoArgumentOperation_ShouldThrowException_WhenNotAllParametersAreNumbers(Operation operationType) {
        //GIVEN
        Deque<StackElement> stack = new LinkedList<>();
        stack.addFirst(StackElement.fromValue("1"));
        stack.addFirst(StackElement.fromValue("undo"));

        //WHEN + THEN
        assertThatThrownBy(() -> operationType.getHandler().process(stack))
                .isInstanceOf(InsufficientParametersException.class);

    }

    @ParameterizedTest
    @MethodSource("twoArgumentsOperations")
    void TwoArgumentOperation_ShouldStoreOriginalStack_WhenOperationIsSuccessful(Operation operationType) throws InsufficientParametersException {
        //GIVEN
        Deque<StackElement> stack = new LinkedList<>();
        stack.addFirst(StackElement.fromValue("1"));
        stack.addFirst(StackElement.fromValue("5"));

        //WHEN
        var result = operationType.getHandler().process(stack);

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