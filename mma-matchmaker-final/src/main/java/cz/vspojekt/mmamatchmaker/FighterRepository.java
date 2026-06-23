package cz.vspojekt.mmamatchmaker;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface FighterRepository extends JpaRepository<Fighter, Long> {
    
    // Základní vyhledávání
    List<Fighter> findByWeightClass(String weightClass);
    
    // Stránkované vyhledávání podle jména (klíčové pro UI)
    Page<Fighter> findByNameContainingIgnoreCase(String name, Pageable pageable);
    
    // SLOŽITĚJŠÍ DOTAZ: Hledá elitní bojovníky (více než X výher) v dané váhové kategorii
    @Query("SELECT f FROM Fighter f WHERE f.weightClass = :weightClass AND f.wins > :minWins ORDER BY f.ranking ASC")
    List<Fighter> findTopPerformersInWeightClass(@Param("weightClass") String weightClass, @Param("minWins") int minWins);
}