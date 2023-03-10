package io.github.denrzv.cloudstorage.advice;

import lombok.AllArgsConstructor;

import java.io.Serial;

@AllArgsConstructor
public class ValidationException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;
    private String msg;
}
