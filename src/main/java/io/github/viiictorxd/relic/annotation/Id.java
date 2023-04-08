package io.github.viiictorxd.relic.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
public @interface Id {

    GenerationType type() default GenerationType.NONE;

    enum GenerationType {

        AUTO_INCREMENT,
        NONE
    }
}
