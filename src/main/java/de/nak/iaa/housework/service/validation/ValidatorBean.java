package de.nak.iaa.housework.service.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.stereotype.Component;


/**
 * Annotation that indicates a ValidatorBean that can be used by the {@link ValidationService}. This
 * annotation is a specialization of {@link Component} so autodetection can be performed.
 * 
 * @author David Aldrup
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface ValidatorBean {

}
