package cz.vspojekt.mmamatchmaker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MatchmakingService {

    private static final Logger log = LoggerFactory.getLogger(MatchmakingService.class);
    private final FighterRepository fighterRepository;

    public MatchmakingService(FighterRepository fighterRepository) {
        this.fighterRepository = fighterRepository;
    }

    // TATO METODA OPRAVÍ MATCHMAKING: Najde lidi pouze ze stejné váhové kategorie!
    public List<FighterDTO> getPotentialOpponents(Long fighterId) {
        log.info("Hledání legitimních soupeřů pro bojovníka s ID: {}", fighterId);
        
        Fighter fighter = fighterRepository.findById(fighterId)
                .orElseThrow(() -> new RuntimeException("Bojovník nenalezen"));

        // Vytáhneme lidi z JEHO VÁHY, vyřadíme jeho samotného a seřadíme je podle ranku
        return fighterRepository.findByWeightClass(fighter.getWeightClass()).stream()
                .filter(f -> !f.getId().equals(fighterId)) // Zamezí zápasu sám se sebou
                .sorted((f1, f2) -> Integer.compare(f1.getRanking(), f2.getRanking())) // Seřazení 1, 2, 3...
                .map(f -> new FighterDTO(f.getId(), f.getName(), f.getWeightClass(), f.getRanking(), f.getWins(), f.getLosses(), f.getImageUrl()))
                .collect(Collectors.toList());
    }

    // Výpočet atraktivity zápasu
    public double calculateFantasyInterest(Long f1Id, Long f2Id) {
        Fighter f1 = fighterRepository.findById(f1Id).orElseThrow(() -> new RuntimeException("Bojovník 1 nenalezen"));
        Fighter f2 = fighterRepository.findById(f2Id).orElseThrow(() -> new RuntimeException("Bojovník 2 nenalezen"));
        
        double interest = 100.0 - (f1.getRanking() + f2.getRanking());
        return Math.max(0, interest);
    }

    // Oficiální potvrzení zápasu
    public String arrangeMatch(MatchRequestDTO request) {
        Fighter f1 = fighterRepository.findById(request.fighterId1()).orElseThrow(() -> new RuntimeException("Bojovník 1 nenalezen"));
        Fighter f2 = fighterRepository.findById(request.fighterId2()).orElseThrow(() -> new RuntimeException("Bojovník 2 nenalezen"));

        double interest = calculateFantasyInterest(request.fighterId1(), request.fighterId2());
        
        log.info("Zápas potvrzen: {} vs. {} (Atraktivita: {}%)", f1.getName(), f2.getName(), interest);
        return "Zápas domluven: " + f1.getName() + " vs. " + f2.getName() + " (Zájem: " + interest + "%)";
    }
}