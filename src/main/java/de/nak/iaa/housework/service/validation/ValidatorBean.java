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
@SuppressWarnings("rawtypes")
public @interface ValidatorBean {

	public static final Class <? extends Validator> NULL_REP = Validator.class;
	
	/**
	 * Gibt an, dass dieses ValidatorBean von einem anderen Validator abh�ngig ist.
	 * Dies ist sinnvoll bei beispielsweise Konsistenzpr�fungen auf die fachliche Pr�fungen folgen,
	 * welche von bestimmten Attributen abh�ngig sind. Dadurch erspart man sich das wiederholte pr�fen auf null-Werte
	 * @return
	 */
	Class<? extends Validator> previentValidator() default Validator.class;
}
