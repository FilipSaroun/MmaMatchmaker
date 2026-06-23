package cz.vspojekt.mmamatchmaker;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

// Jednotkový test (unit test) pro MatchmakingService využívající framework Mockito
@ExtendWith(MockitoExtension.class)
class FighterServiceTest {

    // Mock objekt repository pro izolované testování business logiky
    @Mock
    private FighterRepository fighterRepository;

    // Vstříknutí mockovaných závislostí do testované služby
    @InjectMocks
    private MatchmakingService matchmakingService;

    // Test ověřující správný výpočet atraktivity zápasu mezi dvěma bojovníky
    @Test
    void testCalculateFantasyInterest_Success() {
        // Arrange (Příprava): Vytvoření testovacích dat a nastavení chování mocků
        Fighter f1 = new Fighter(); 
        f1.setId(1L);
        f1.setRanking(1);
        
        Fighter f2 = new Fighter(); 
        f2.setId(2L);
        f2.setRanking(2);
        
        when(fighterRepository.findById(1L)).thenReturn(Optional.of(f1));
        when(fighterRepository.findById(2L)).thenReturn(Optional.of(f2));

        // Act (Akce): Spuštění testované metody
        double interest = matchmakingService.calculateFantasyInterest(1L, 2L);

        // Assert (Ověření): Kontrola, zda výsledek odpovídá očekávání
        assertTrue(interest > 0, "Atraktivita by měla být kladné číslo");
    }
}