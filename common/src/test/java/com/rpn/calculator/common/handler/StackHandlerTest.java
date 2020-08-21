package com.rpn.calculator.common.handler;

import com.rpn.calculator.common.StackElement;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class StackHandlerTest {

    static Stream<Arguments> validStacksWithResults() {
        return Stream.of(
                Arguments.of(build(""), "5 2", build("5 2")),
                Arguments.of(build(""), "2 sqrt", build("1.414213562373095")),
                Arguments.of(build(""), "5 2 -", build("3")),
                Arguments.of(build("3"), "3 -", build("0")),
                Arguments.of(build("0"), "clear", build("")),
                Arguments.of(build(""), "5 4 3 2", build("5 4 3 2")),
                Arguments.of(build("20"), "5 *", build("100")),
                Arguments.of(build(""), "7 12 2 /", build("7 6")),
                Arguments.of(build("7 6"), "*", build("42")),
                Arguments.of(build("42"), "4 /", build("10.5")),
                Arguments.of(build("1 2 3 4 5"), "*", build("1 2 3 20")),
                Arguments.of(build("1 2 3 20"), "clear 3 4 -", build("-1")),
                Arguments.of(build("1 2 3 4 5"), "* * * *", build("120")),
                Arguments.of(build("1 2 3 4"), "* * /", build("0.04166666666666667"))
        );
    }

    @ParameterizedTest
    @MethodSource("validStacksWithResults")
    void StackHandler_ShouldCorrectlyCalculateStack_ForValidStack(Deque<StackElement> stack, String inputString, Deque<StackElement> expectedStack) {
        //GIVEN
        var handler = new StackHandler();

        //WHEN
        var actualResult = handler.process(stack, inputString);

        //THEN
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(actualResult).isNotNull();
            softly.assertThat(actualResult).extracting(Result::getError).isEqualTo(Optional.empty());

            var actualStack = actualResult.getResult();
            softly.assertThat(actualStack).containsExactlyElementsOf(expectedStack);
        });
    }

    static Stream<Arguments> validInputsWithResults() {
        return Stream.of(
                Arguments.of(List.of("5 4 3 2", "undo undo *", "5 *", "undo"), build("20 5")),
                Arguments.of(List.of("1 2 3 4 5", "*", "clear 3 4 -"), build("-1"))
        );
    }

    @ParameterizedTest
    @MethodSource("validInputsWithResults")
    void StackHandler_ShouldCorrectlyCalculateStack_ForSetOfInputs(List<String> inputStrings, Deque<StackElement> expectedStack) {
        //GIVEN
        var handler = new StackHandler();

        //WHEN
        Deque<StackElement> resultStack = new LinkedList<>();
        for (String inputString : inputStrings) {
            var result = handler.process(resultStack, inputString);
            resultStack = result.getResult();
            if (result.getError().isPresent()) {
                break;
            }
        }

        //THEN
        assertThat(resultStack).containsExactlyElementsOf(expectedStack);
    }

    static Stream<Arguments> notValidInputsWithResults() {
        return Stream.of(
                Arguments.of(List.of("1 2 3 * 5 + * * 6 5"), build("11"), "operator '*' (position: 15): insufficient parameters"),
                Arguments.of(List.of("1 2 *", "undo", "!"), build("1 2"), "operator '!' (position: 1): is not supported"),
                Arguments.of(List.of("1 2 3 4 5", "* - sqrt"), build("1 2 -17"), "operator 'sqrt' (position: 5): illegal parameters")
        );
    }

    @ParameterizedTest
    @MethodSource("notValidInputsWithResults")
    void StackHandler_ShouldCorrectlyCalculateStack_ForSetOfInputs(List<String> inputStrings, Deque<StackElement> expectedStack, String errorMessage) {
        //GIVEN
        var handler = new StackHandler();

        //WHEN
        String actualError = "";
        Deque<StackElement> resultStack = new LinkedList<>();
        for (String inputString : inputStrings) {
            var result = handler.process(resultStack, inputString);
            resultStack = result.getResult();
            if (result.getError().isPresent()) {
                actualError = result.getError().get();
                break;
            }
        }

        //THEN
        var softly = new SoftAssertions();
        softly.assertThat(resultStack).containsExactlyElementsOf(expectedStack);
        softly.assertThat(actualError).isEqualTo(errorMessage);
        softly.assertAll();
    }

    private static Deque<StackElement> build(String input) {
        Deque<StackElement> stack = new LinkedList<>();
        for (String inputStringElement : input.split(" ")) {
            if (inputStringElement.length() == 0) {
                continue;
            }
            var element = StackElement.fromValue(inputStringElement);
            stack.addFirst(element);
        }
        return stack;
    }

}