package com.rpn.calculator.common.operation;

import java.math.BigDecimal;

public class SubtractionOperation extends TwoArgumentsOperation {

    @Override
    protected BigDecimal process(BigDecimal firstParameter, BigDecimal secondParameter) {
        return firstParameter.subtract(secondParameter);
    }
}
