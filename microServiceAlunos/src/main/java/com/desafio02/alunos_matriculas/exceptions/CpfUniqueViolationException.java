package com.desafio02.alunos_matriculas.exceptions;

public class CpfUniqueViolationException extends RuntimeException{
    public CpfUniqueViolationException(String message) {
        super(message);
    }
}