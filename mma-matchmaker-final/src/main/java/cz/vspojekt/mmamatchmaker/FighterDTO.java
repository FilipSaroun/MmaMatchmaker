package cz.vspojekt.mmamatchmaker;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Min;
import com.fasterxml.jackson.annotation.JsonProperty;

// DTO (Data Transfer Object) pro přenos dat o bojovníkovi mezi klientem a serverem
// Použití 'record' zajišťuje neměnnost (immutable) objektu a automatickou generaci metod
public record FighterDTO(
    Long id,
    
    // Validace vstupu: Jméno nesmí být prázdné
    @NotBlank(message = "Jméno bojovníka musí být vyplněno")
    String name,
    
    // Validace vstupu: Váhová kategorie je povinná
    @NotBlank(message = "Váhová kategorie musí být vyplněna")
    String weightClass,
    
    // Validace číselných hodnot pro zajištění logických statistik
    @Min(value = 1, message = "Ranking musí být alespoň 1")
    int ranking,
    
    @Min(value = 0, message = "Počet výher nesmí být záporný")
    int wins,
    
    @Min(value = 0, message = "Počet proher nesmí být záporný")
    int losses,
    
    String imageUrl
) {}