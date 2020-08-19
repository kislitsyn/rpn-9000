package com.rpn.calculator.common.operation;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class DivideOperation extends TwoArgumentsOperation {

    @Override
    protected BigDecimal process(BigDecimal firstParameter, BigDecimal secondParameter) {
        return firstParameter.divide(secondParameter, RoundingMode.HALF_UP);
    }
}
