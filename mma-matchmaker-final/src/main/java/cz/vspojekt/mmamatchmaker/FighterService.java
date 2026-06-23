package cz.vspojekt.mmamatchmaker;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

// Interface definující kontrakt pro servisní vrstvu aplikace
public interface FighterService {
    
    // Načte seznam všech bojovníků
    List<FighterDTO> getAllFighters();
    
    // Získá konkrétního bojovníka podle jeho unikátního identifikátoru
    FighterDTO getFighterById(Long id);
    
    // Uloží nového bojovníka do systému
    FighterDTO createFighter(FighterDTO fighterDTO);
    
    // Aktualizuje data existujícího bojovníka
    FighterDTO updateFighter(Long id, FighterDTO fighterDTO);
    
    // Odstraní bojovníka ze systému
    void deleteFighter(Long id);
    
    // Umožňuje vyhledávání bojovníků podle jména s podporou stránkování
    Page<FighterDTO> searchFightersByName(String name, Pageable pageable);
}