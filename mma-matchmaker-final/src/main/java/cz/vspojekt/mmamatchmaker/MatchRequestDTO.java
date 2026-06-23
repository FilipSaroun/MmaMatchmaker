package cz.vspojekt.mmamatchmaker;

import jakarta.validation.constraints.NotNull;

// DTO pro požadavek na vytvoření nového zápasu
// Anotace @WeightClass aktivuje vlastní validátor pro kontrolu váhových kategorií soupeřů
@WeightClass 
public record MatchRequestDTO(
    @NotNull(message = "ID prvního bojovníka musí být zadáno")
    Long fighterId1,
    
    @NotNull(message = "ID druhého bojovníka musí být zadáno")
    Long fighterId2
) {}