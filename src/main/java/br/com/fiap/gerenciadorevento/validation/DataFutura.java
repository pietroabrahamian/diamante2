package br.com.fiap.gerenciadorevento.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = DataFuturaValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DataFutura {
    String message() default "A data do evento deve ser hoje ou no futuro";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
