package cz.vspojekt.mmamatchmaker;

import org.slf4j.Logger; // Import loggeru
import org.slf4j.LoggerFactory; // Import factory
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.stream.Collectors;

// Servisní vrstva zajišťující business logiku a transformaci dat mezi Entitou a DTO
@Service
public class FighterServiceImpl implements FighterService {

    private static final Logger log = LoggerFactory.getLogger(FighterServiceImpl.class); // Definice loggeru
    private final FighterRepository fighterRepository;

    public FighterServiceImpl(FighterRepository fighterRepository) {
        this.fighterRepository = fighterRepository;
    }

    @Override
    // Načte všechny záznamy a převede je na datové přenosové objekty (DTO)
    public List<FighterDTO> getAllFighters() {
        log.info("Načítání všech bojovníků z databáze");
        return fighterRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    // Vyhledává bojovníky podle jména s podporou stránkování pro efektivní práci s daty
    public Page<FighterDTO> searchFightersByName(String name, Pageable pageable) {
        log.info("Vyhledávání bojovníků podle jména: {}", name);
        return fighterRepository.findByNameContainingIgnoreCase(name, pageable)
                .map(this::convertToDTO);
    }

    @Override
    // Získá detail bojovníka podle ID nebo vyvolá výjimku, pokud neexistuje
    public FighterDTO getFighterById(Long id) {
        log.info("Hledání bojovníka s ID: {}", id);
        Fighter fighter = fighterRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bojovník s ID " + id + " nebyl nalezen"));
        return convertToDTO(fighter);
    }

    @Override
    // Uloží nového bojovníka do databáze po konverzi z DTO na Entitu
    public FighterDTO createFighter(FighterDTO fighterDTO) {
        log.info("Vytváření nového bojovníka: {}", fighterDTO.name());
        Fighter fighter = convertToEntity(fighterDTO);
        Fighter savedFighter = fighterRepository.save(fighter);
        return convertToDTO(savedFighter);
    }

    @Override
    // Aktualizuje existující záznam v databázi na základě poskytnutých dat
    public FighterDTO updateFighter(Long id, FighterDTO fighterDTO) {
        log.info("Aktualizace bojovníka s ID: {}", id);
        Fighter existingFighter = fighterRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bojovník s ID " + id + " nebyl nalezen"));

        existingFighter.setName(fighterDTO.name());
        existingFighter.setWeightClass(fighterDTO.weightClass());
        existingFighter.setRanking(fighterDTO.ranking());
        existingFighter.setWins(fighterDTO.wins());
        existingFighter.setLosses(fighterDTO.losses());
        existingFighter.setImageUrl(fighterDTO.imageUrl());

        Fighter updatedFighter = fighterRepository.save(existingFighter);
        return convertToDTO(updatedFighter);
    }

    @Override
    // Odstraní záznam z databáze a zaloguje tuto kritickou operaci
    public void deleteFighter(Long id) {
        log.warn("Probíhá mazání bojovníka s ID: {}", id);
        if (!fighterRepository.existsById(id)) {
            throw new RuntimeException("Bojovník s ID " + id + " nebyl nalezen");
        }
        fighterRepository.deleteById(id);
    }

    // Pomocná metoda pro mapování Entity na DTO (skrytí vnitřní struktury databáze)
    private FighterDTO convertToDTO(Fighter fighter) {
        return new FighterDTO(
                fighter.getId(),
                fighter.getName(),
                fighter.getWeightClass(),
                fighter.getRanking(),
                fighter.getWins(),
                fighter.getLosses(),
                fighter.getImageUrl()
        );
    }

    // Pomocná metoda pro mapování DTO na Entitu (příprava pro uložení do databáze)
    private Fighter convertToEntity(FighterDTO dto) {
        Fighter fighter = new Fighter();
        fighter.setId(dto.id());
        fighter.setName(dto.name());
        fighter.setWeightClass(dto.weightClass());
        fighter.setRanking(dto.ranking());
        fighter.setWins(dto.wins());
        fighter.setLosses(dto.losses());
        fighter.setImageUrl(dto.imageUrl());
        return fighter;
    }
}