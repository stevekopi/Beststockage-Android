package cd.sklservices.com.Beststockage.Classes.Stocks;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Locale;

import cd.sklservices.com.Beststockage.ActivityFolder.MainActivity;
import cd.sklservices.com.Beststockage.Classes.ModelBase;
import cd.sklservices.com.Beststockage.Classes.Finances.OperationFinance;
import cd.sklservices.com.Beststockage.Classes.Parametres.Devise;
import cd.sklservices.com.Beststockage.Classes.Registres.Agence;
import cd.sklservices.com.Beststockage.Classes.Registres.Article;
import cd.sklservices.com.Beststockage.Classes.Registres.User;

/**
 * Created by Steve Kopi Loseme on 29/01/2021.
 */

@Entity(tableName = "operation",
        foreignKeys = { @ForeignKey (entity = User.class,parentColumns = "id",childColumns = "user_id",onUpdate = ForeignKey.CASCADE),
                        @ForeignKey (entity = Agence.class,parentColumns = "id",childColumns = "agence_1_id",onUpdate = ForeignKey.CASCADE) ,
                        @ForeignKey (entity = Agence.class,parentColumns = "id",childColumns = "agence_2_id",onUpdate = ForeignKey.CASCADE),
                        @ForeignKey (entity = OperationFinance.class,parentColumns = "id",childColumns = "operation_finance_id",onUpdate = ForeignKey.CASCADE),
                        @ForeignKey (entity = Devise.class,parentColumns = "id",childColumns = "devise_id",onUpdate = ForeignKey.CASCADE),
                        @ForeignKey (entity = Article.class,parentColumns = "id",childColumns = "article_id",onUpdate = ForeignKey.CASCADE)}

)


public class Operation extends ModelBase implements Comparable<Operation> {

    @PrimaryKey @NonNull
    private String id ;
    private String user_id ;
    private String operation_finance_id;
    private String agence_1_id ;
    private String agence_2_id ;
    private String article_id ;
    private String  devise_id ;
    private String type;
    private String sens_stock;
    private String sens_finance;
    private int quantite ;
    private int bonus;
    private Double montant ;
    private String date;
    private String observation ;
    private int is_confirmed;
    private int is_checked;
    private String checking_agence_id;

    @Ignore
    private String categorieCaisse;

    @Ignore
    private Article article;

    @Ignore
    private Agence AgenceBeneficiaire;

    @Ignore
    private OperationFinance operationFinance;


    public Operation(){
        super();
       // this.operationFinance=new OperationFinance();
        this.operationFinance.setType("Entree");
        categorieCaisse= MainActivity.getCurrentUser().getAgence().getType().toLowerCase(Locale.ROOT).equals("depot")?"Maison":MainActivity.getCurrentUser().getAgence().getType();

    }
    public Operation(@NonNull String id, String user_id, String operation_finance_id, String agence_1_id, String agence_2_id, String article_id, String devise_id, String type, int quantite, int bonus, Double montant, String date, String observation, int is_confirmed, int is_checked,String checking_agence_id, String adding_date, String updated_date, int sync_pos, int pos) {
        super(adding_date, updated_date, sync_pos, pos);

        this.id = id;
        this.user_id = user_id;
        this.operation_finance_id = operation_finance_id;
        this.agence_1_id = agence_1_id;
        this.agence_2_id = agence_2_id;
        this.article_id = article_id;
        this.devise_id = devise_id;
        this.type = type;
        this.quantite = quantite;
        this.bonus = bonus;
        this.montant = montant;
        this.date = date;
        this.observation = observation;
        this.is_confirmed = is_confirmed;
        this.is_checked = is_checked;
        this.checking_agence_id=checking_agence_id;

        setType(type); //Pour permettre le mappage du sens stock et sens finance

        if (type.toLowerCase(Locale.ROOT).contains("vente")){
            categorieCaisse= MainActivity.getCurrentUser().getAgence().getType().toLowerCase(Locale.ROOT).equals("depot")?"Maison":MainActivity.getCurrentUser().getAgence().getType();

            this.operationFinance=new OperationFinance(operation_finance_id,MainActivity.getCurrentUser().getAgence_id(),"",
                    devise_id,categorieCaisse,date,"Entree",montant,type+" : "+observation,"Auto",0,0,0,user_id,
                    agence_1_id,checking_agence_id,adding_date,updated_date,sync_pos,pos);
        }
        else{
            this.operation_finance_id=MainActivity.DefaultFinancialKey;
        }
    }



   public Operation(@NonNull String id, String user_id, String operation_finance_id,String sens_stock,String sens_finance, String agence_1_id, String agence_2_id, String article_id, String devise_id, String type, int quantite, int bonus, Double montant, String date, String observation, int is_confirmed, int is_checked,String checking_agence_id, String adding_date, String updated_date, int sync_pos, int pos) {
        super(adding_date, updated_date, sync_pos, pos);

        this.id = id;
        this.user_id = user_id;
        this.operation_finance_id = operation_finance_id;
        this.sens_stock = sens_stock;
        this.sens_finance = sens_finance;
        this.agence_1_id = agence_1_id;
        this.agence_2_id = agence_2_id;
        this.article_id = article_id;
        this.devise_id = devise_id;
        this.type = type;
        this.quantite = quantite;
        this.bonus = bonus;
        this.montant = montant;
        this.date = date;
        this.observation = observation;
        this.is_confirmed = is_confirmed;
        this.is_checked = is_checked;
        this.checking_agence_id=checking_agence_id;


       if (type.toLowerCase(Locale.ROOT).contains("vente")){
           categorieCaisse= MainActivity.getCurrentUser().getAgence().getType().toLowerCase(Locale.ROOT).equals("depot")?"Maison":MainActivity.getCurrentUser().getAgence().getType();

           this.operationFinance=new OperationFinance(operation_finance_id,MainActivity.getCurrentUser().getAgence_id(),"",
                   devise_id,categorieCaisse,date,"Entree",montant,type+" : "+observation,"Auto",0,0,0,user_id,
                   agence_1_id,checking_agence_id,adding_date,updated_date,sync_pos,pos);
       }
       else{
           this.operation_finance_id=MainActivity.DefaultFinancialKey;
       }
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
        this.operationFinance.setAdding_user_id(user_id);
    }

    public String getOperation_finance_id() {
        return operation_finance_id;
    }

    public void setOperation_finance_id(String operation_finance_id) {
        this.operation_finance_id = operation_finance_id;
        this.operationFinance.setId(operation_finance_id);
    }

    public String getSens_stock() {
        return sens_stock;
    }

    public void setSens_stock(String sens_stock) {
        this.sens_stock = sens_stock;
    }

    public String getSens_finance() {
        return sens_finance;
    }

    public void setSens_finance(String sens_finance) {
        this.sens_finance = sens_finance;
    }

    public String getCategorieCaisse() {
        return categorieCaisse;
    }

    public void setCategorieCaisse(String categorieCaisse) {
        this.categorieCaisse = categorieCaisse;
    }

    public String getAgence_1_id() {
        return agence_1_id;
    }

    public void setAgence_1_id(String agence_1_id) {

        this.agence_1_id = agence_1_id;
        this.operationFinance.setAgence_id(agence_1_id);
    }

    public String getAgence_2_id() {
        return agence_2_id;
    }

    public void setAgence_2_id(String agence_2_id) {

        this.agence_2_id = agence_2_id;
    }

    public String getArticle_id() {
        return article_id;
    }

    public void setArticle_id(String article_id) {
        this.article_id = article_id;
    }

    public String getDevise_id() {
        return devise_id;
    }

    public void setDevise_id(String devise_id) {

        this.devise_id = devise_id;
        this.operationFinance.setDevise_id(devise_id);
    }

    public String getType() {

        return type;
    }

    public void setType(String type) {

        if(type.toLowerCase().contains("detail"))
            this.bonus=0;
        this.type = type;

        if(type.contains("Vente"))
        {
            this.sens_stock = "Sortie";
            this.sens_finance = "Entree";
        }
        else if(type=="Entree_speciale")
        {
            this.sens_stock="Entree";
            this.sens_finance="Aucun";
        }
        else if(type=="Sortie")
        {
            this.sens_stock="Sortie";
            this.sens_finance="Aucun";
        }
        else if(type=="Entree")
        {
            this.sens_stock="Entree";
            this.sens_finance="Aucun";
        }
        else if(type=="Achat")
        {
            this.sens_stock="Entree";
            this.sens_finance="Sortie";
        }
        else if(type=="Livraison")
        {
            this.sens_stock="Entree";
            this.sens_finance="Aucun";
        }
        else if(type=="Livraison_PC")
        {
            this.sens_stock="Entree";
            this.sens_finance="Aucun";
        }
        else if(type=="Livraison_Client")
        {
            sens_stock = "Sortie";
            sens_finance = "Aucun";
        }
        else if(type=="Livraison_Commande")
        {
            this.sens_stock="Entree";
            this.sens_finance="Aucun";
        }
        else if(type=="Remise")
        {
            this.sens_stock="Entree";
            this.sens_finance="Aucun";
        }
        else
        {
            sens_stock = "Aucun";
            sens_finance = "Aucun";
        }

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
        if(type.toLowerCase().contains("detail"))
            this.bonus=0;
    }

    public Double getMontant() {
        return montant;
    }

    public void setMontant(Double montant) {

        this.montant = montant;
        this.operationFinance.setMontant(montant);
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {

        this.date = date;
        this.operationFinance.setDate(date);
    }

    public String getChecking_agence_id() {
        return checking_agence_id;
    }

    public void setChecking_agence_id(String checking_agence_id) {
        this.checking_agence_id = checking_agence_id;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {

        this.observation = observation;
        this.operationFinance.setObservation(type+" : "+observation);
    }

    public int getIs_confirmed() {
        return is_confirmed;
    }

    public int getIs_checked() {
        return is_checked;
    }

    public void setIs_confirmed(int is_confirmed) {
        this.is_confirmed = is_confirmed;
    }



    public void setIs_checked(int is_checked) {
        this.is_checked = is_checked;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {

        this.article = article;
        if(this.type.toLowerCase(Locale.ROOT).contains("vente"))
            this.operationFinance.setFournisseur_id(article.getFournisseur_id());

    }



    public OperationFinance getOperationFinance() {
        return operationFinance;
    }

    public void setOperationFinance(OperationFinance operationFinance) {
        this.operationFinance = operationFinance;
    }

    public Agence getAgenceBeneficiaire() {
        return AgenceBeneficiaire;
    }

    public void setAgenceBeneficiaire(Agence agenceBeneficiaire) {
        AgenceBeneficiaire = agenceBeneficiaire;
    }

    @Override
    public int compareTo(@NonNull Operation o) {
        return 0;
    }
}
