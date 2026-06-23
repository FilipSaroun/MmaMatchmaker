package cz.vspojekt.mmamatchmaker;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "fighter")
public class Fighter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Jméno bojovníka nesmí být prázdné")
    @Size(min = 2, max = 50, message = "Jméno musí mít rozsah 2 až 50 znaků")
    private String name;

    private int ranking;
    private int wins;
    private int losses;

    @NotBlank(message = "Váhová kategorie je povinná")
    @Column(name = "weight_class") // Odpovídá data.sql
    private String weightClass;

    @Column(name = "image_url") // Odpovídá data.sql
    private String imageUrl;

    public Fighter() {}

    // --- GETTERY A SETTERY ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getRanking() { return ranking; }
    public void setRanking(int ranking) { this.ranking = ranking; }

    public int getWins() { return wins; }
    public void setWins(int wins) { this.wins = wins; }

    public int getLosses() { return losses; }
    public void setLosses(int losses) { this.losses = losses; }

    public String getWeightClass() { return weightClass; }
    public void setWeightClass(String weightClass) { this.weightClass = weightClass; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
}