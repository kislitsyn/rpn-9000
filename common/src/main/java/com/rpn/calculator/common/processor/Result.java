package com.rpn.calculator.common.processor;

import java.util.Optional;

public class Result<T, ErrorT> {

    private final T result;
    private final ErrorT error;

    public Result(T result, ErrorT error) {
        this.result = result;
        this.error = error;
    }

    public static <T1, ErrorT1> Result<T1, ErrorT1> of(T1 result) {
        return new Result<>(result, null);
    }

    public static <T1, ErrorT1> Result<T1, ErrorT1> error(T1 result, ErrorT1 error) {
        return new Result<>(result, error);
    }

    public T getResult() {
        return result;
    }

    public Optional<ErrorT> getError() {
        return Optional.ofNullable(error);
    }
}
