/*自定义校验*/
package org.example.interview.anno;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.example.interview.validation.StateValidation;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented //元注解
@Constraint(
        validatedBy = {StateValidation.class}
)
@Target({FIELD})
@Retention(RUNTIME)
public @interface State {
    String message() default "state参数的值只能是已发布或者草稿";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
