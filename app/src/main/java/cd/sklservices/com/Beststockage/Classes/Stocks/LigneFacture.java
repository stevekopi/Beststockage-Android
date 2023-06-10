package cd.sklservices.com.Beststockage.Classes.Stocks;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import cd.sklservices.com.Beststockage.Classes.Finances.OperationFinance;
import cd.sklservices.com.Beststockage.Classes.ModelBaseXR;
import cd.sklservices.com.Beststockage.Classes.Parametres.ArticleProduitFacture;
import cd.sklservices.com.Beststockage.Classes.Registres.Agence;
import cd.sklservices.com.Beststockage.Classes.Registres.Article;
import cd.sklservices.com.Beststockage.Classes.Registres.User;

@Entity(tableName = "ligne_facture",
        indices = {
        @Index("id"),@Index("facture_id"),@Index("article_produit_facture_id"),
        @Index("operation_finance_id"),@Index("article_bonus_id"),@Index("adding_agence_id"),
        @Index("checking_agence_id"),@Index("adding_user_id"),@Index("last_update_user_id")},

        foreignKeys = {
        @ForeignKey(entity = Facture.class,parentColumns = "id",childColumns = "facture_id",onUpdate = ForeignKey.CASCADE),
        //@ForeignKey(entity = ArticleProduitFacture.class,parentColumns = "id",childColumns = "article_produit_facture_id",onUpdate = ForeignKey.CASCADE),
        @ForeignKey(entity = OperationFinance.class,parentColumns = "id",childColumns = "operation_finance_id",onUpdate = ForeignKey.CASCADE),

        @ForeignKey(entity = Article.class,parentColumns = "id",childColumns = "article_bonus_id",onUpdate = ForeignKey.CASCADE),

        @ForeignKey(entity = Agence.class,parentColumns = "id",childColumns = "checking_agence_id",onUpdate = ForeignKey.CASCADE),
        @ForeignKey(entity = Agence.class,parentColumns = "id",childColumns = "adding_agence_id",onUpdate = ForeignKey.CASCADE),
        @ForeignKey(entity = User.class,parentColumns = "id",childColumns = "adding_user_id",onUpdate = ForeignKey.CASCADE),
        @ForeignKey(entity = User.class,parentColumns = "id",childColumns = "last_update_user_id",onUpdate = ForeignKey.CASCADE)}
)

public class LigneFacture extends ModelBaseXR implements Cloneable{
    @PrimaryKey
    @NonNull
    private String id;
    private String second_id;
    private String facture_id;
    private String appartenance;
    private String article_produit_facture_id;
    private String operation_finance_id;
    private String sens_stock;
    private int quantite;
    private Double montant_ht;
    private Double montant_ttc;
    private Double converted_amount;
    private Double montant_local;
    private Double reduction;
    private Double tva;
    private Double montant_net;
    private String article_bonus_id;
    private int bonus;
    private int is_checked;
    private int is_confirmed;
    private String checking_agence_id;
    @Ignore
    private Facture Facture;

    @Ignore Article ArticleBonus;

    @Ignore ArticleProduitFacture ArticleProduitFacture;

    @Ignore
    public LigneFacture(Facture facture){
        Facture=facture;
        this.adding_agence_id= facture.getAdding_agence_id();
        this.adding_user_id=facture.getAdding_user_id();
        this.last_update_user_id=facture.getLast_update_user_id();
        this.checking_agence_id=facture.getChecking_agence_id();
    }

    public LigneFacture(@NonNull String id, String second_id, String facture_id, String appartenance, String article_produit_facture_id, String operation_finance_id, String sens_stock, int quantite, Double montant_ht,Double montant_ttc, Double converted_amount, Double montant_local, Double reduction,Double tva,Double montant_net, String article_bonus_id, int bonus, int is_checked, int is_confirmed, String checking_agence_id,String adding_user_id, String last_update_user_id, String adding_agence_id, String adding_date, String updated_date, int sync_pos, int pos) {
        super(adding_user_id, last_update_user_id, adding_agence_id, adding_date, updated_date, sync_pos, pos);
        this.id = id;
        this.second_id = second_id;
        this.facture_id = facture_id;
        this.appartenance = appartenance;
        this.article_produit_facture_id = article_produit_facture_id;
        this.operation_finance_id = operation_finance_id;
        this.sens_stock = sens_stock;
        this.quantite = quantite;
        this.montant_ht = montant_ht;
        this.montant_ttc=montant_ttc;
        this.converted_amount = converted_amount;
        this.montant_local = montant_local;
        this.reduction = reduction;
        this.tva=tva;
        this.montant_net = montant_net;
        this.article_bonus_id = article_bonus_id;
        this.bonus = bonus;
        this.is_checked = is_checked;
        this.is_confirmed = is_confirmed;
        this.checking_agence_id = checking_agence_id;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getSecond_id() {
        return second_id;
    }

    public void setSecond_id(String second_id) {
        this.second_id = second_id;
    }

    public String getFacture_id() {
        return facture_id;
    }

    public void setFacture_id(String facture_id) {
        this.facture_id = facture_id;
    }

    public String getAppartenance() {
        return appartenance;
    }

    public void setAppartenance(String appartenance) {
        this.appartenance = appartenance;
    }

    public String getArticle_produit_facture_id() {
        return article_produit_facture_id;
    }

    public void setArticle_produit_facture_id(String article_produit_facture_id) {
        this.article_produit_facture_id = article_produit_facture_id;
    }

    public String getOperation_finance_id() {
        return operation_finance_id;
    }

    public void setOperation_finance_id(String operation_finance_id) {
        this.operation_finance_id = operation_finance_id;
    }

    public String getSens_stock() {
        return sens_stock;
    }

    public void setSens_stock(String sens_stock) {
        this.sens_stock = sens_stock;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
        ComputeNumbers();
    }

    public Double getMontant_ht() {
        return montant_ht;
    }

    public void setMontant_ht(Double montant_ht) {
        this.montant_ht = montant_ht;
    }

    public Double getMontant_ttc() {
        return montant_ttc;
    }

    public void setMontant_ttc(Double montant_ttc) {
        this.montant_ttc = montant_ttc;
    }

    public Double getConverted_amount() {
        return converted_amount;
    }

    public void setConverted_amount(Double converted_amount) {
        this.converted_amount = converted_amount;
    }

    public Double getReduction() {
        return reduction;
    }

    public void setReduction(Double reduction) {
        this.reduction = reduction;
    }

    public Double getTva() {
        return tva;
    }

    public void setTva(Double tva) {
        this.tva = tva;
    }

    public int getIs_checked() {
        return is_checked;
    }

    public int getIs_confirmed() {
        return is_confirmed;
    }

    public Double getMontant_local() {
        return montant_local;
    }

    public void setMontant_local(Double montant_local) {
        this.montant_local = montant_local;
    }

    public Double getMontant_net() {
        return montant_net;
    }

    public void setMontant_net(Double montant_net) {
        this.montant_net = montant_net;
    }

    public String getArticle_bonus_id() {
        return article_bonus_id;
    }

    public void setArticle_bonus_id(String article_bonus_id) {
        this.article_bonus_id = article_bonus_id;
    }

    public int getBonus() {
        return bonus;
    }

    public void setBonus(int bonus) {
        this.bonus = bonus;
    }


    public String getChecking_agence_id() {
        return checking_agence_id;
    }

    public void setChecking_agence_id(String checking_agence_id) {
        this.checking_agence_id = checking_agence_id;
    }

    public void setIs_checked(int is_checked) {
        this.is_checked = is_checked;
    }

    public void setIs_confirmed(int is_confirmed) {
        this.is_confirmed = is_confirmed;
    }

    public cd.sklservices.com.Beststockage.Classes.Stocks.Facture getFacture() {
        return Facture;
    }

    public void setFacture(cd.sklservices.com.Beststockage.Classes.Stocks.Facture facture) {
        Facture = facture;
        facture_id=facture.getId();
        this.appartenance=facture.getMembership();
        this.sens_stock=facture.getProduit().getSens_stock();
    }

    public Article getArticleBonus() {
        return ArticleBonus;
    }

    public void setArticleBonus(Article articleBonus) {
        ArticleBonus = articleBonus;
    }

    public cd.sklservices.com.Beststockage.Classes.Parametres.ArticleProduitFacture getArticleProduitFacture() {
        return ArticleProduitFacture;
    }

    public void setArticleProduitFacture(cd.sklservices.com.Beststockage.Classes.Parametres.ArticleProduitFacture articleProduitFacture) {
        ArticleProduitFacture = articleProduitFacture;
        article_produit_facture_id=articleProduitFacture.getId();
    }

    public void ComputeNumbers()
    {
        double montantReduit=0;

       if(ArticleProduitFacture!=null){
           double tvaRating = ArticleProduitFacture.getTva_rate() / 100;
           montant_ht = quantite * ArticleProduitFacture.getMontant_ht();
           if(Facture.getReduction_rate()>0){
               reduction = montant_ht * (double)(Facture.getReduction_rate()/100);
               montantReduit = montant_ht - reduction;
               tva = montantReduit * tvaRating;
               montant_ttc = montant_ht + tva;
               montant_net= montant_ttc - reduction;
           }else{

               tva = montant_ht * tvaRating;
               montant_ttc = montant_ht + tva;
               montant_net= montant_ttc;
           }
       }

        if(montant_net!=null){
            montant_net=(double)Math.round(montant_net);
        }
        else{
            montant_net=0.0;
        }
    }

    public Object clone(){
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public LigneFacture copie(){
        return (LigneFacture) clone();
    }
}
