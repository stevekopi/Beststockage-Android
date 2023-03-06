package cd.sklservices.com.Beststockage.Classes;

import androidx.room.Ignore;

import cd.sklservices.com.Beststockage.Classes.Parametres.Devise;

public class AmountModelBase extends ModelBaseX{
   protected Double montant,montant_local,converted_amount;
   protected String devise_id,local_devise_id,convert_devise_id;
   @Ignore
   protected Devise Devise,LocalDevise,ConvertDevise;

    @Ignore
   public AmountModelBase(){

   }

    public AmountModelBase(Double montant, String devise_id,String adding_user_id, String adding_agence_id, String adding_date, String updated_date, int sync_pos, int pos) {
        super(adding_user_id, adding_agence_id, adding_date, updated_date, sync_pos, pos);
        this.montant = montant;
        this.devise_id = devise_id;
    }

    public AmountModelBase(Double montant, String devise_id, String local_devise_id, String convert_devise_id,String adding_user_id, String adding_agence_id, String adding_date, String updated_date, int sync_pos, int pos) {
        this(montant, devise_id,adding_user_id, adding_agence_id, adding_date, updated_date, sync_pos, pos);
        //this.montant_local = new FournisseurTauxRepository(FournisseurTauxRepository.getContext()).get;
        this.converted_amount = converted_amount;

    }

    public AmountModelBase(Double montant, Double montant_local, Double converted_amount, String devise_id, String local_devise_id, String convert_devise_id,String adding_user_id, String adding_agence_id, String adding_date, String updated_date, int sync_pos, int pos) {
        super(adding_user_id, adding_agence_id, adding_date, updated_date, sync_pos, pos);
        this.montant = montant;
        this.montant_local = montant_local;
        this.converted_amount = converted_amount;
        this.devise_id = devise_id;
        this.local_devise_id = local_devise_id;
        this.convert_devise_id = convert_devise_id;
    }

    public Double getMontant() {
        return montant;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    public Double getMontant_local() {
        return montant_local;
    }

    public void setMontant_local(Double montant_local) {
        this.montant_local = montant_local;
    }

    public Double getConverted_amount() {
        return converted_amount;
    }

    public void setConverted_amount(Double converted_amount) {
        this.converted_amount = converted_amount;
    }

    public String getDevise_id() {
        return devise_id;
    }

    public void setDevise_id(String devise_id) {
        this.devise_id = devise_id;
    }

    public String getLocal_devise_id() {
        return local_devise_id;
    }

    public void setLocal_devise_id(String local_devise_id) {
        this.local_devise_id = local_devise_id;
    }

    public String getConvert_devise_id() {
        return convert_devise_id;
    }

    public void setConvert_devise_id(String convert_devise_id) {
        this.convert_devise_id = convert_devise_id;
    }

    public cd.sklservices.com.Beststockage.Classes.Parametres.Devise getDevise() {
        return Devise;
    }

    public void setDevise(cd.sklservices.com.Beststockage.Classes.Parametres.Devise devise) {
        Devise = devise;
    }

    public cd.sklservices.com.Beststockage.Classes.Parametres.Devise getLocalDevise() {
        return LocalDevise;
    }

    public void setLocalDevise(cd.sklservices.com.Beststockage.Classes.Parametres.Devise localDevise) {
        LocalDevise = localDevise;
    }

    public cd.sklservices.com.Beststockage.Classes.Parametres.Devise getConvertDevise() {
        return ConvertDevise;
    }

    public void setConvertDevise(cd.sklservices.com.Beststockage.Classes.Parametres.Devise convertDevise) {
        ConvertDevise = convertDevise;
    }
}
