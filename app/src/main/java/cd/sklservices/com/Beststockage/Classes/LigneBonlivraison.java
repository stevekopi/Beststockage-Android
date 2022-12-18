package cd.sklservices.com.Beststockage.Classes;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

/**
 * Created by Steve Kopi Loseme on 29/01/2021.
 */

@Entity(tableName = "ligne_bonlivraison",
        foreignKeys = { @ForeignKey (entity = Bonlivraison.class,parentColumns = "id",childColumns = "bonlivraison_id",onUpdate = ForeignKey.CASCADE),
                        @ForeignKey (entity = Operation.class,parentColumns = "id",childColumns = "operation_id",onUpdate = ForeignKey.CASCADE) ,
                        @ForeignKey (entity = Article.class,parentColumns = "id",childColumns = "article_id",onUpdate = ForeignKey.CASCADE),
                        @ForeignKey(entity = Agence.class,parentColumns = "id",childColumns = "adding_agence_id",onUpdate = ForeignKey.CASCADE),
                        @ForeignKey(entity = User.class,parentColumns = "id",childColumns = "adding_user_id",onUpdate = ForeignKey.CASCADE),
                        @ForeignKey(entity = Devise.class,parentColumns = "id",childColumns = "devise_id",onUpdate = ForeignKey.CASCADE)
                      }

        )

public class LigneBonlivraison extends ModelBaseX implements Comparable<LigneBonlivraison> {

    @PrimaryKey @NonNull
    private String id ;
    private String bonlivraison_id ;
    private String operation_id ;
    private String article_id ;
    private int quantite ;
    private int bonus ;
    private Double montant ;
    private Double montant_dollars ;
    private String devise_id ;


    public LigneBonlivraison(@NonNull String id, String bonlivraison_id, String operation_id, String article_id, int quantite, int bonus, Double montant, Double montant_dollars,
                             String devise_id,String adding_user_id, String adding_agence_id, String adding_date, String updated_date, int sync_pos, int pos) {
        super(adding_user_id, adding_agence_id, adding_date, updated_date, sync_pos, pos);
        this.id = id;
        this.bonlivraison_id = bonlivraison_id;
        this.operation_id = operation_id;
        this.article_id = article_id;
        this.quantite = quantite;
        this.bonus = bonus;
        this.montant = montant;
        this.montant_dollars = montant_dollars;
        this.devise_id = devise_id;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getBonlivraison_id() {
        return bonlivraison_id;
    }

    public void setBonlivraison_id(String bonlivraison_id) {
        this.bonlivraison_id = bonlivraison_id;
    }

    public String getOperation_id() {
        return operation_id;
    }

    public void setOperation_id(String operation_id) {
        this.operation_id = operation_id;
    }

    public String getArticle_id() {
        return article_id;
    }

    public void setArticle_id(String article_id) {
        this.article_id = article_id;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public int getBonus() {
        return bonus;
    }

    public void setBonus(int bonus) {
        this.bonus = bonus;
    }

    public Double getMontant() {
        return montant;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    public Double getMontant_dollars() {
        return montant_dollars;
    }

    public void setMontant_dollars(Double montant_dollars) {
        this.montant_dollars = montant_dollars;
    }

    public String getDevise_id() {
        return devise_id;
    }

    public void setDevise_id(String devise_id) {
        this.devise_id = devise_id;
    }

    @Override
    public int compareTo(@NonNull LigneBonlivraison o) {
        return 0;
    }
}
