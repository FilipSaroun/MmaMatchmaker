package cz.vspojekt.mmamatchmaker;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

// Vlastní validační anotace pro kontrolu shody váhových kategorií zápasníků
@Documented
@Constraint(validatedBy = WeightClassValidator.class) // Propojení s třídou, která provádí samotnou validaci
@Target({ElementType.TYPE}) // Anotace je určena pro použití na úrovni třídy (zde pro MatchRequestDTO)
@Retention(RetentionPolicy.RUNTIME) // Anotace bude dostupná i za běhu aplikace
public @interface WeightClass {
    // Výchozí chybová zpráva, pokud validace selže
    String message() default "Bojovníci nespárovali váhu! Musí být ze stejné váhové kategorie.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}