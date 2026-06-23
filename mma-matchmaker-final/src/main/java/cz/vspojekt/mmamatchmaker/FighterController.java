package cz.vspojekt.mmamatchmaker;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

// Controller vrstva pro správu MMA bojovníků přes REST API
@RestController
@RequestMapping("/api/fighters")
@CrossOrigin(origins = "*") // Povoluje CORS pro umožnění přístupu z různých frontendových aplikací
public class FighterController {

    private final FighterService fighterService;

    public FighterController(FighterService fighterService) {
        this.fighterService = fighterService;
    }

    // Získání seznamu všech bojovníků
    @GetMapping
    public ResponseEntity<List<FighterDTO>> getAllFighters() {
        return ResponseEntity.ok(fighterService.getAllFighters());
    }

    // Vytvoření nového bojovníka (vyžaduje validaci vstupních dat)
    @PostMapping
    public ResponseEntity<FighterDTO> createFighter(@Valid @RequestBody FighterDTO fighterDTO) {
        FighterDTO created = fighterService.createFighter(fighterDTO);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    // Aktualizace informací o stávajícím bojovníkovi podle ID
    @PutMapping("/{id}")
    public ResponseEntity<FighterDTO> updateFighter(
            @PathVariable Long id, 
            @Valid @RequestBody FighterDTO fighterDTO) {
        FighterDTO updated = fighterService.updateFighter(id, fighterDTO);
        return ResponseEntity.ok(updated);
    }

    // Odstranění bojovníka z databáze
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFighter(@PathVariable Long id) {
        fighterService.deleteFighter(id);
        return ResponseEntity.noContent().build();
    }

    // Vyhledávání bojovníků podle jména s podporou stránkování výsledků
    @GetMapping("/search")
    public ResponseEntity<Page<FighterDTO>> searchFighters(
            @RequestParam String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        Page<FighterDTO> results = fighterService.searchFightersByName(name, PageRequest.of(page, size));
        return ResponseEntity.ok(results);
    }
}