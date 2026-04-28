package br.com.fiap.gerenciadorevento.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

public class DataFuturaValidator implements ConstraintValidator<DataFutura, LocalDate> {

    @Override
    public boolean isValid(LocalDate data, ConstraintValidatorContext context) {
        if (data == null) return true;
        return !data.isBefore(LocalDate.now());
    }
}
