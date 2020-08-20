package com.rpn.calculator.common;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.ArrayDeque;
import java.util.List;
import java.util.stream.Stream;

class StackElementTest {

    @Test
    void StackElement_ShouldContainOperatorOnly_WhenInputIsString() {
        //GIVEN
        var element = StackElement.fromValue("someOperator");

        //WHEN + THEN
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(element.getOperator()).isEqualTo("someOperator");
            softly.assertThat(element.getNumber()).isEmpty();
            softly.assertThat(element.getOriginal()).isEmpty();
            softly.assertThat(element.toString()).isEqualTo("someOperator");
        });
    }

    @Test
    void StackElement_ShouldContainNumberOnly_WhenInputIsNumber() {
        //GIVEN
        var element = StackElement.fromValue("10");

        //WHEN + THEN
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(element.getOperator()).isEqualTo(BigDecimal.TEN.toPlainString());
            softly.assertThat(element.getNumber()).hasValue(BigDecimal.TEN);
            softly.assertThat(element.getOriginal()).isEmpty();
            softly.assertThat(element.toString()).isEqualTo("10");
        });
    }

    static Stream<Arguments> StackElement_FormatNumber() {
        return Stream.of(
                Arguments.of(new BigDecimal("0"), "0"),
                Arguments.of(new BigDecimal("-1"), "-1"),
                Arguments.of(new BigDecimal("0.123"), "0.123"),
                Arguments.of(new BigDecimal("1.1234567890"), "1.123456789"),
                Arguments.of(new BigDecimal("1.12345678905"), "1.1234567891"),
                Arguments.of(new BigDecimal("999.99999999999"), "1000")
        );
    }

    @ParameterizedTest
    @MethodSource("StackElement_FormatNumber")
    void StackElement_FormatNumber(BigDecimal number, String formatedValue) {
        //GIVEN
        var element = StackElement.fromValue(number.toPlainString());

        //WHEN + THEN
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(element.getNumber()).hasValue(number);
            softly.assertThat(element.toString()).isEqualTo(formatedValue);
        });
    }

    @Test
    void StackElement_ShouldHaveOriginalStack() {
        //GIVEN
        var stack = new ArrayDeque<>(List.of(StackElement.fromValue("1"), StackElement.fromValue("2.0")));
        var element = new StackElement(BigDecimal.ONE, stack);

        //WHEN + THEN
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(element.getNumber()).hasValue(BigDecimal.ONE);
            softly.assertThat(element.getOriginal()).hasValue(stack);
        });
    }

}