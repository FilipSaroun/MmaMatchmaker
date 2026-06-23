package cz.vspojekt.mmamatchmaker;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// Integrační test pro FighterController, který ověřuje HTTP komunikaci v rámci celého Spring kontextu
@SpringBootTest
@AutoConfigureMockMvc
class FighterControllerIT {

    @Autowired
    private MockMvc mockMvc;

    // Test ověřující, zda endpoint pro získání všech bojovníků vrací úspěšný HTTP status
    @Test
    void getAllFighters_ReturnsOk() throws Exception {
        // Simulace GET požadavku na /api/fighters a kontrola, že odpověď je 200 OK
        mockMvc.perform(get("/api/fighters"))
                .andExpect(status().isOk());
    }
}