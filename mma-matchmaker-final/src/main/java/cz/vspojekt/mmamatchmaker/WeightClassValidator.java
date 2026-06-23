package cz.vspojekt.mmamatchmaker;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

// Validátor implementující logiku pro vlastní anotaci @WeightClass
public class WeightClassValidator implements ConstraintValidator<WeightClass, MatchRequestDTO> {

    @Autowired
    private FighterRepository fighterRepository;

    @Override
    public boolean isValid(MatchRequestDTO request, ConstraintValidatorContext context) {
        // Pokud je požadavek prázdný nebo chybí ID, vracíme true (základní validace polí proběhne jinde)
        if (request == null || request.fighterId1() == null || request.fighterId2() == null) {
            return true;
        }

        // Načtení obou bojovníků z databáze
        var f1 = fighterRepository.findById(request.fighterId1()).orElse(null);
        var f2 = fighterRepository.findById(request.fighterId2()).orElse(null);

        // Pokud jeden z bojovníků neexistuje, validace selže
        if (f1 == null || f2 == null) {
            return false;
        }

        // Zápas je legitimní pouze pokud mají bojovníci stejnou váhovou kategorii
        return f1.getWeightClass().equalsIgnoreCase(f2.getWeightClass());
    }
}