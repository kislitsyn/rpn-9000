package com.rpn.calculator.common.processor;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

class ResultTest {

    @Test
    void Result_ShouldContainSuccessfulResult() {
        //GIVEN
        var result = Result.of("test");

        //WHEN + THEN
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(result.getError()).isEmpty();
            softly.assertThat(result.getResult()).isEqualTo("test");
        });
    }

    @Test
    void Result_ShouldContainErrorAndResult() {
        //GIVEN
        var result = Result.error("test", "errorMessage");

        //WHEN + THEN
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(result.getError()).hasValue("errorMessage");
            softly.assertThat(result.getResult()).isEqualTo("test");
        });
    }
}