package cd.sklservices.com.Beststockage.Classes.Stocks;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

import cd.sklservices.com.Beststockage.ActivityFolder.MainActivity;
import cd.sklservices.com.Beststockage.Classes.ModelBaseXR;
import cd.sklservices.com.Beststockage.Classes.Parametres.Devise;
import cd.sklservices.com.Beststockage.Classes.Parametres.ProduitFacture;
import cd.sklservices.com.Beststockage.Classes.Registres.Agence;
import cd.sklservices.com.Beststockage.Classes.Registres.Identity;
import cd.sklservices.com.Beststockage.Classes.Registres.User;

@Entity(tableName = "facture",
        indices = {
                @Index("id"),@Index("produit_id"),@Index("agence_id"),
                @Index("agence_2_id"),@Index("user_id"),@Index("proprietaire_id"),@Index("payment_mode_id"),
                @Index("devise_id"),@Index("convert_devise_id"),@Index("devise_local_id"),
                @Index("adding_agence_id"),@Index("checking_agence_id"),@Index("adding_user_id"),@Index("last_update_user_id")},

        foreignKeys = {
                 @ForeignKey(entity = ProduitFacture.class,parentColumns = "id",childColumns = "produit_id",onUpdate = ForeignKey.CASCADE),
                 @ForeignKey(entity = Agence.class,parentColumns = "id",childColumns = "agence_id",onUpdate = ForeignKey.CASCADE),
                 @ForeignKey(entity = Agence.class,parentColumns = "id",childColumns = "agence_2_id",onUpdate = ForeignKey.CASCADE),

                    @ForeignKey(entity = User.class,parentColumns = "id",childColumns = "user_id",onUpdate = ForeignKey.CASCADE),
                    @ForeignKey(entity = Identity.class,parentColumns = "id",childColumns = "proprietaire_id",onUpdate = ForeignKey.CASCADE),
                    // @ForeignKey(entity = PaymentMode.class,parentColumns = "id",childColumns = "payment_mode_id",onUpdate = ForeignKey.CASCADE),

                    @ForeignKey(entity = Devise.class,parentColumns = "id",childColumns = "devise_id",onUpdate = ForeignKey.CASCADE),
                    @ForeignKey(entity = Devise.class,parentColumns = "id",childColumns = "convert_devise_id",onUpdate = ForeignKey.CASCADE),
                    @ForeignKey(entity = Devise.class,parentColumns = "id",childColumns = "devise_local_id",onUpdate = ForeignKey.CASCADE),


                    @ForeignKey(entity = Agence.class,parentColumns = "id",childColumns = "adding_agence_id",onUpdate = ForeignKey.CASCADE),
                    @ForeignKey(entity = Agence.class,parentColumns = "id",childColumns = "checking_agence_id",onUpdate = ForeignKey.CASCADE),
                    @ForeignKey(entity = User.class,parentColumns = "id",childColumns = "adding_user_id",onUpdate = ForeignKey.CASCADE),
                    @ForeignKey(entity = User.class,parentColumns = "id",childColumns = "last_update_user_id",onUpdate = ForeignKey.CASCADE)}
)

public class Facture extends ModelBaseXR implements Cloneable{
    @PrimaryKey
    @NonNull
    private String id;
    private String second_id;
    private String produit_id;
    private String agence_id;
    private String agence_2_id;
    private String user_id;
    private String proprietaire_id;
    private String payment_mode_id;
    private String membership;
    private String date;
    private double reduction_rate;
    private String devise_id;
    private String convert_devise_id;
    private String devise_local_id;
    private String observation;
    private String checking_agence_id;

    @Ignore
    private Facture Second;

    @Ignore
    private Agence Agence;

    @Ignore
    private Agence Agence2;

    @Ignore Devise Devise,ConvertDevise,LocalDevise;


    @Ignore
    private ProduitFacture Produit;

    @Ignore
    private User User;

    @Ignore
    List<LigneFacture> Lines;

    @Ignore
    private double montant_ht;
    @Ignore
    private double montant_ttc;
    @Ignore
    private double montant_net;
    @Ignore
    private double montant_local;
    @Ignore
    private double tva;

    @Ignore
    public Facture(){

        this.adding_agence_id=MainActivity.getCurrentUser().getAgence_id();
        this.Agence=MainActivity.getCurrentUser().getAgence();
        this.User=MainActivity.getCurrentUser();
        this.date=MainActivity.getDate();
        this.user_id=MainActivity.getCurrentUser().getId();
        this.adding_user_id=MainActivity.getCurrentUser().getId();
        this.last_update_user_id=MainActivity.getCurrentUser().getId();
        this.agence_id=MainActivity.getCurrentUser().getAgence_id();
        this.checking_agence_id=MainActivity.getCurrentUser().getAgence_id();
        this.observation="RAS";
        Lines=new ArrayList<>();
    }

    public Facture(@NonNull String id, String second_id, String produit_id, String agence_id, String agence_2_id, String user_id, String proprietaire_id, String payment_mode_id, String membership, String date,double reduction_rate, String devise_id, String convert_devise_id, String devise_local_id,String observation, String checking_agence_id,String adding_user_id, String last_update_user_id, String adding_agence_id, String adding_date, String updated_date, int sync_pos, int pos) {
        super(adding_user_id, last_update_user_id, adding_agence_id, adding_date, updated_date, sync_pos, pos);
        this.id = id;
        this.second_id = second_id;
        this.produit_id = produit_id;
        this.agence_id = agence_id;
        this.agence_2_id = agence_2_id;
        this.user_id = user_id;
        this.proprietaire_id = proprietaire_id;
        this.payment_mode_id = payment_mode_id;
        this.membership = membership;
        this.date = date;
        this.reduction_rate=reduction_rate;
        this.devise_id = devise_id;
        this.convert_devise_id = convert_devise_id;
        this.devise_local_id = devise_local_id;
        this.checking_agence_id = checking_agence_id;
        this.observation=observation;
        Lines=new ArrayList<>();
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

    public String getProduit_id() {
        return produit_id;
    }

    public void setProduit_id(String produit_id) {
        this.produit_id = produit_id;
    }

    public String getAgence_id() {
        return agence_id;
    }

    public void setAgence_id(String agence_id) {
        this.agence_id = agence_id;
    }

    public String getAgence_2_id() {
        return agence_2_id;
    }

    public void setAgence_2_id(String agence_2_id) {
        this.agence_2_id = agence_2_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getProprietaire_id() {
        return proprietaire_id;
    }

    public void setProprietaire_id(String proprietaire_id) {
        this.proprietaire_id = proprietaire_id;
    }

    public String getPayment_mode_id() {
        return payment_mode_id;
    }

    public void setPayment_mode_id(String payment_mode_id) {
        this.payment_mode_id = payment_mode_id;
    }

    public String getMembership() {
        return membership;
    }

    public void setMembership(String membership) {
        this.membership = membership;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDevise_id() {
        return devise_id;
    }

    public void setDevise_id(String devise_id) {
        this.devise_id = devise_id;
    }

    public String getConvert_devise_id() {
        return convert_devise_id;
    }

    public void setConvert_devise_id(String convert_devise_id) {
        this.convert_devise_id = convert_devise_id;
    }

    public String getDevise_local_id() {
        return devise_local_id;
    }

    public void setDevise_local_id(String devise_local_id) {
        this.devise_local_id = devise_local_id;
    }

    public String getChecking_agence_id() {
        return checking_agence_id;
    }

    public void setChecking_agence_id(String checking_agence_id) {
        this.checking_agence_id = checking_agence_id;
    }

    public double getMontant_ht() {
        return montant_ht;
    }

    public void setMontant_ht(double montant_ht) {
        this.montant_ht = montant_ht;
    }

    public double getMontant_ttc() {
        return montant_ttc;
    }

    public void setMontant_ttc(double montant_ttc) {
        this.montant_ttc = montant_ttc;
    }

    public double getMontant_net() {
        return montant_net;
    }

    public void setMontant_net(double montant_net) {
        this.montant_net = montant_net;
    }

    public double getMontant_local() {
        return montant_local;
    }

    public void setMontant_local(double montant_local) {
        this.montant_local = montant_local;
    }

    public String getObservation() {
        return observation;
    }

    public cd.sklservices.com.Beststockage.Classes.Registres.Agence getAgence() {
        return Agence;
    }

    public void setAgence(cd.sklservices.com.Beststockage.Classes.Registres.Agence agence) {
        Agence = agence;
        agence_id=agence.getId();
    }

    public cd.sklservices.com.Beststockage.Classes.Registres.Agence getAgence2() {
        return Agence2;
    }

    public void setAgence2(cd.sklservices.com.Beststockage.Classes.Registres.Agence agence2) {
        Agence2 = agence2;
        agence_2_id=agence2.getId();
        proprietaire_id=agence2.getProprietaire_id();
        membership=agence2.getAppartenance();
    }

    public ProduitFacture getProduit() {
        return Produit;
    }

    public void setProduit(ProduitFacture produit) {
        Produit = produit;
        produit_id=produit.getId();
        devise_id=produit.getDevise_id();
    }

    public cd.sklservices.com.Beststockage.Classes.Registres.User getUser() {
        return User;
    }

    public void setUser(cd.sklservices.com.Beststockage.Classes.Registres.User user) {
        User = user;
    }

    public double getReduction_rate() {
        return reduction_rate;
    }

    public void setReduction_rate(double reduction_rate) {
        this.reduction_rate = reduction_rate;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public Devise getDevise() {
        return Devise;
    }

    public void setDevise(Devise devise) {
        Devise = devise;
    }

    public Devise getConvertDevise() {
        return ConvertDevise;
    }

    public void setConvertDevise(Devise convertDevise) {
        ConvertDevise = convertDevise;
    }

    public Devise getLocalDevise() {
        return LocalDevise;
    }

    public void setLocalDevise(Devise localDevise) {
        LocalDevise = localDevise;
    }

    public List<LigneFacture> getLines() {
        return Lines;
    }

    public void setLines(List<LigneFacture> lines) {
        Lines = lines;
    }

    public Facture getSecond() {
        return Second;
    }

    public void setSecond(Facture second) {
        Second = second;
        second_id=second.getId();
    }

    public Object clone(){
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Facture copie(){
        return (Facture) clone();
    }

    public double getTva() {

        if(Lines.size()>0){
            for(LigneFacture ligneFacture:Lines){
                tva+=ligneFacture.getTva();
            }
        }
        return tva;
    }

    public void setTva(double tva) {
        this.tva = tva;
    }
}
