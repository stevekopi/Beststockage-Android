package cd.sklservices.com.Beststockage.Classes.Stocks;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import cd.sklservices.com.Beststockage.Classes.ModelBaseX;
import cd.sklservices.com.Beststockage.Classes.Parametres.Devise;
import cd.sklservices.com.Beststockage.Classes.Registres.Agence;
import cd.sklservices.com.Beststockage.Classes.Registres.Article;
import cd.sklservices.com.Beststockage.Classes.Registres.User;

/**
 * Created by Steve Kopi Loseme on 29/01/2021.
 */

@Entity(tableName = "ligne_commande",
        foreignKeys = { @ForeignKey (entity = Agence.class, parentColumns = "id", childColumns = "agence_id", onUpdate = ForeignKey.CASCADE),
                        @ForeignKey (entity = Commande.class,parentColumns = "id",childColumns = "commande_id",onUpdate = ForeignKey.CASCADE),
                        @ForeignKey (entity = Article.class, parentColumns = "id",childColumns = "article_id", onUpdate = ForeignKey.CASCADE),
                        @ForeignKey(entity = Agence.class,parentColumns = "id",childColumns = "adding_agence_id",onUpdate = ForeignKey.CASCADE),
                        @ForeignKey(entity = User.class,parentColumns = "id",childColumns = "adding_user_id",onUpdate = ForeignKey.CASCADE),
                        @ForeignKey(entity = Devise.class,parentColumns = "id",childColumns = "devise_id",onUpdate = ForeignKey.CASCADE)
                      }

)

public class LigneCommande extends ModelBaseX implements Comparable<LigneCommande> {

    @PrimaryKey @NonNull
    private String id ;
    private String commande_id ;
    private String agence_id ;
    private String article_id ;
    private int quantite ;
    private int bonus ;
    private double montant ;
    private String devise_id ;

    public LigneCommande( @NonNull String id, String commande_id, String agence_id, String article_id, int quantite, int bonus, double montant, String devise_id,String adding_user_id, String adding_agence_id, String adding_date, String updated_date, int sync_pos, int pos) {
        super(adding_user_id, adding_agence_id, adding_date, updated_date, sync_pos, pos);
        this.id = id;
        this.commande_id = commande_id;
        this.agence_id = agence_id;
        this.article_id = article_id;
        this.quantite = quantite;
        this.bonus = bonus;
        this.montant = montant;
        this.devise_id = devise_id;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getCommande_id() {
        return commande_id;
    }

    public void setCommande_id(String commande_id) {
        this.commande_id = commande_id;
    }

    public String getAgence_id() {
        return agence_id;
    }

    public void setAgence_id(String agence_id) {
        this.agence_id = agence_id;
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

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public String getDevise_id() {
        return devise_id;
    }

    public void setDevise_id(String devise_id) {
        this.devise_id = devise_id;
    }

    @Override
    public int compareTo(@NonNull LigneCommande o) {
        return 0;
    }
}
