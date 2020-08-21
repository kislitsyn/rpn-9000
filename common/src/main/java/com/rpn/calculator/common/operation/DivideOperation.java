package com.rpn.calculator.common.operation;

import java.math.BigDecimal;

public class DivideOperation extends TwoArgumentsOperation {

    @Override
    protected BigDecimal process(BigDecimal firstParameter, BigDecimal secondParameter) {
        return firstParameter.divide(secondParameter);
    }
}