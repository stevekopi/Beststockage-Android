package cd.sklservices.com.Beststockage.Classes.Parametres;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import cd.sklservices.com.Beststockage.Classes.Finances.OperationFinance;
import cd.sklservices.com.Beststockage.Classes.ModelBaseXR;
import cd.sklservices.com.Beststockage.Classes.Registres.Agence;
import cd.sklservices.com.Beststockage.Classes.Registres.Article;
import cd.sklservices.com.Beststockage.Classes.Registres.User;
import cd.sklservices.com.Beststockage.Classes.Stocks.Facture;

@Entity(tableName = "article_produit_facture",indices = {@Index("id")},
        foreignKeys = {
                @ForeignKey(entity = Article.class,parentColumns = "id",childColumns = "article_id",onUpdate = ForeignKey.CASCADE),
                @ForeignKey(entity = ProduitFacture.class,parentColumns = "id",childColumns = "produit_id",onUpdate = ForeignKey.CASCADE),
                @ForeignKey(entity = Devise.class,parentColumns = "id",childColumns = "devise_id",onUpdate = ForeignKey.CASCADE),

                @ForeignKey(entity = Agence.class,parentColumns = "id",childColumns = "adding_agence_id",onUpdate = ForeignKey.CASCADE),
                @ForeignKey(entity = User.class,parentColumns = "id",childColumns = "adding_user_id",onUpdate = ForeignKey.CASCADE),
                @ForeignKey(entity = User.class,parentColumns = "id",childColumns = "last_update_user_id",onUpdate = ForeignKey.CASCADE)}
)

public class ArticleProduitFacture extends ModelBaseXR {
    @PrimaryKey
    @NonNull
    private String id;
    private String article_id;
    private String produit_id;
    private Double montant_ht;
    private Double tva_rate;
    private Double tva;
    private Double montant_ttc;
    private String devise_id;
    private int quantite_min;

    public ArticleProduitFacture(@NonNull String id, String article_id, String produit_id, Double montant_ht, Double tva_rate, Double tva, Double montant_ttc, String devise_id, int quantite_min,String adding_user_id, String last_update_user_id, String adding_agence_id, String adding_date, String updated_date, int sync_pos, int pos) {
        super(adding_user_id, last_update_user_id, adding_agence_id, adding_date, updated_date, sync_pos, pos);
        this.id = id;
        this.article_id = article_id;
        this.produit_id = produit_id;
        this.montant_ht = montant_ht;
        this.tva_rate = tva_rate;
        this.tva = tva;
        this.montant_ttc = montant_ttc;
        this.devise_id = devise_id;
        this.quantite_min = quantite_min;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getArticle_id() {
        return article_id;
    }

    public void setArticle_id(String article_id) {
        this.article_id = article_id;
    }

    public String getProduit_id() {
        return produit_id;
    }

    public void setProduit_id(String produit_id) {
        this.produit_id = produit_id;
    }

    public Double getMontant_ht() {
        return montant_ht;
    }

    public void setMontant_ht(Double montant_ht) {
        this.montant_ht = montant_ht;
    }

    public Double getTva_rate() {
        return tva_rate;
    }

    public void setTva_rate(Double tva_rate) {
        this.tva_rate = tva_rate;
    }

    public Double getTva() {
        return tva;
    }

    public void setTva(Double tva) {
        this.tva = tva;
    }

    public Double getMontant_ttc() {
        return montant_ttc;
    }

    public void setMontant_ttc(Double montant_ttc) {
        this.montant_ttc = montant_ttc;
    }

    public String getDevise_id() {
        return devise_id;
    }

    public void setDevise_id(String devise_id) {
        this.devise_id = devise_id;
    }

    public int getQuantite_min() {
        return quantite_min;
    }

    public void setQuantite_min(int quantite_min) {
        this.quantite_min = quantite_min;
    }
}
