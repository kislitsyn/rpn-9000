package com.rpn.calculator.common.operation;

import java.math.BigDecimal;
import java.math.MathContext;

public class DivideOperation extends TwoArgumentsOperation {

    @Override
    protected BigDecimal process(BigDecimal firstParameter, BigDecimal secondParameter) {
        return firstParameter.divide(secondParameter, MathContext.DECIMAL64);
    }
}
