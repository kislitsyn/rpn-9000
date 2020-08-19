package com.rpn.calculator.common.operation;

import java.math.BigDecimal;

public class MultiplyOperation extends TwoArgumentsOperation {

    @Override
    protected BigDecimal process(BigDecimal firstParameter, BigDecimal secondParameter) {
        return firstParameter.multiply(secondParameter);
    }
}
