package cd.sklservices.com.Beststockage.Classes.Stocks;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import cd.sklservices.com.Beststockage.Classes.ModelBaseX;
import cd.sklservices.com.Beststockage.Classes.Registres.Agence;
import cd.sklservices.com.Beststockage.Classes.Registres.Article;
import cd.sklservices.com.Beststockage.Classes.Registres.User;

/**
 * Created by Steve Kopi Loseme on 29/01/2021.
 */

@Entity(tableName = "approvisionnement",
        foreignKeys = { @ForeignKey (entity = Livraison.class,parentColumns = "id",childColumns = "livraison_id",onUpdate = ForeignKey.CASCADE),
                        @ForeignKey (entity = Operation.class,parentColumns = "id",childColumns = "operation_id",onUpdate = ForeignKey.CASCADE) ,
                        @ForeignKey (entity = Agence.class,parentColumns = "id",childColumns = "agence_id",onUpdate = ForeignKey.CASCADE) ,
                        @ForeignKey (entity = Article.class,parentColumns = "id",childColumns = "article_id",onUpdate = ForeignKey.CASCADE),
                        @ForeignKey(entity = Agence.class,parentColumns = "id",childColumns = "adding_agence_id",onUpdate = ForeignKey.CASCADE),
                        @ForeignKey(entity = User.class,parentColumns = "id",childColumns = "adding_user_id",onUpdate = ForeignKey.CASCADE)
                      }
        )

public class Approvisionnement extends ModelBaseX implements Comparable<Approvisionnement> {

    @PrimaryKey @NonNull
    private String id ;
    protected String livraison_id ;
    private String operation_id ;
    private String agence_id ;
    private String article_id ;
    private int quantite ;
    private int bonus ;
    private double montant ;


    public Approvisionnement(@NonNull String id, String livraison_id, String operation_id, String agence_id, String article_id, int quantite, int bonus, double montant,String adding_user_id,String adding_agence_id, String adding_date, String updated_date, int sync_pos, int pos) {
        super(adding_user_id,adding_agence_id, adding_date, updated_date, sync_pos, pos);
        this.id = id;
        this.livraison_id = livraison_id;
        this.operation_id = operation_id;
        this.agence_id = agence_id;
        this.article_id = article_id;
        this.quantite = quantite;
        this.bonus = bonus;
        this.montant = montant;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getLivraison_id() {
        return livraison_id;
    }

    public void setLivraison_id(String livraison_id) {
        this.livraison_id = livraison_id;
    }

    public String getOperation_id() {
        return operation_id;
    }

    public void setOperation_id(String operation_id) {
        this.operation_id = operation_id;
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

    @Override
    public int compareTo(@NonNull Approvisionnement o) {
        return 0;
    }
}
