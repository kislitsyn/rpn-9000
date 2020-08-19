package com.rpn.calculator.common.operation;

import java.math.BigDecimal;

public class AdditionOperation extends TwoArgumentsOperation {

    @Override
    protected BigDecimal process(BigDecimal firstParameter, BigDecimal secondParameter) {
        return firstParameter.add(secondParameter);
    }
}
