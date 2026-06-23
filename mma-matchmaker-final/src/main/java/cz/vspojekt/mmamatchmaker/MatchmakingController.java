package cz.vspojekt.mmamatchmaker;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

// Controller vrstva pro správu matchmakingu a analytické funkce zápasů
@RestController
@RequestMapping("/api/matchmaking")
@CrossOrigin(origins = "*") // Povoluje přístup z jakéhokoliv frontendu
public class MatchmakingController {

    private final MatchmakingService matchmakingService;

    public MatchmakingController(MatchmakingService matchmakingService) {
        this.matchmakingService = matchmakingService;
    }

    // Vyhledá vhodné soupeře pro konkrétního bojovníka na základě jeho parametrů
    @GetMapping("/opponents/{fighterId}")
    public ResponseEntity<List<FighterDTO>> getPotentialOpponents(@PathVariable Long fighterId) {
        return ResponseEntity.ok(matchmakingService.getPotentialOpponents(fighterId));
    }

    // Vypočítá míru zájmu fanoušků o potenciální zápas mezi dvěma bojovníky
    @GetMapping("/interest")
    public ResponseEntity<Double> getFantasyInterest(@RequestParam Long f1Id, @RequestParam Long f2Id) {
        return ResponseEntity.ok(matchmakingService.calculateFantasyInterest(f1Id, f2Id));
    }

    // Vytvoří a uloží nový zápas na základě validovaného požadavku
    @PostMapping("/match")
    public ResponseEntity<String> createMatch(@Valid @RequestBody MatchRequestDTO request) {
        String result = matchmakingService.arrangeMatch(request);
        return ResponseEntity.ok(result);
    }
}