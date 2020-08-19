package com.rpn.calculator.common;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Optional;

public class StackElement {

    private final BigDecimal number;
    private final String element;
    private final Deque<StackElement> original;

    public StackElement(String element) {
        this.number = null;
        this.element = element;
        this.original = new LinkedList<>();
    }

    public StackElement(BigDecimal number) {
        this(number, null);
    }

    public StackElement(BigDecimal number, Deque<StackElement> original) {
        this.number = number;
        this.element = number.toPlainString();
        this.original = original;
    }

    public Optional<BigDecimal> getNumber() {
        return Optional.ofNullable(number);
    }

    public String getElement() {
        return element;
    }

    public Optional<Deque<StackElement>> getOriginal() {
        return Optional.ofNullable(original);
    }

    @Override
    public String toString() {
        if (number != null) {
            if (number.scale() > 10) {
                return number.setScale(10, RoundingMode.HALF_UP).toPlainString();
            }
            return number.toPlainString();
        }
        return element;
    }
}
