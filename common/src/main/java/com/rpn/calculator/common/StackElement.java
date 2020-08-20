package com.rpn.calculator.common;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Deque;
import java.util.Objects;
import java.util.Optional;

public class StackElement {

    private final BigDecimal number;
    private final String operator;
    private final Deque<StackElement> original;

    private StackElement(String operator) {
        this.number = null;
        this.operator = operator;
        this.original = null;
    }

    private StackElement(BigDecimal number) {
        this(number, null);
    }

    public StackElement(BigDecimal number, Deque<StackElement> original) {
        this.number = number;
        this.operator = number.toPlainString();
        this.original = original;
    }

    public static StackElement fromValue(String stringElement) {
        try {
            return new StackElement(new BigDecimal(stringElement));
        } catch (NumberFormatException e) {
            return new StackElement(stringElement);
        }
    }

    public Optional<BigDecimal> getNumber() {
        return Optional.ofNullable(number);
    }

    public String getOperator() {
        return operator;
    }

    public Optional<Deque<StackElement>> getOriginal() {
        return Optional.ofNullable(original);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        StackElement that = (StackElement) o;
        return Objects.equals(number, that.number) &&
                operator.equals(that.operator);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, operator);
    }

    @Override
    public String toString() {
        if (number != null) {
            if (number.scale() > 10) {
                return number.setScale(10, RoundingMode.HALF_UP).stripTrailingZeros().toPlainString();
            }
            return number.stripTrailingZeros().toPlainString();
        }
        return operator;
    }
}
