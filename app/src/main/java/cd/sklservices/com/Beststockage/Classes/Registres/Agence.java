package cd.sklservices.com.Beststockage.Classes.Registres;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import cd.sklservices.com.Beststockage.Classes.ModelBase;

/**
 * Created by Steve Kopi Loseme on 28/01/2021.
 */

@Entity(tableName = "agence",
        foreignKeys = { @ForeignKey (entity = Address.class,parentColumns = "id",childColumns = "adresse_id",onUpdate = ForeignKey.CASCADE),
                        @ForeignKey (entity = Identity.class,parentColumns = "id",childColumns = "proprietaire_id",onUpdate = ForeignKey.CASCADE)
                      }
        )


public class Agence extends ModelBase implements Comparable<Agence> {

    @PrimaryKey @NonNull
    private String id ;
    private String adresse_id ;
    private String proprietaire_id ;
    private String type ;
    private String denomination ;
    private String appartenance ;
    private String tel;
    private String telephone2;
    private String telephone3;

    @Ignore
    private Address address;

    @Ignore
    private Identity proprietaire;






    public Agence(@NonNull String id,String adresse_id, String proprietaire_id, String type, String denomination,String tel,String telephone2,String telephone3, String appartenance, String adding_date,String updated_date,int sync_pos,int pos) {
        super(adding_date,updated_date,sync_pos,pos);
        this.id=id;
        this.adresse_id = adresse_id;
        this.proprietaire_id = proprietaire_id;
        this.type = type;
        this.denomination = denomination;
        this.tel = tel;
        this.telephone2=telephone2;
        this.telephone3=telephone3;
        this.appartenance = appartenance;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getAdresse_id() {
        return adresse_id;
    }

    public void setAdresse_id(String adresse_id) {
        this.adresse_id = adresse_id;
    }

    public String getProprietaire_id() {
        return proprietaire_id;
    }

    public void setProprietaire_id(String proprietaire_id) {
        this.proprietaire_id = proprietaire_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDenomination() {
        return denomination;
    }

    public void setDenomination(String denomination) {
        this.denomination = denomination;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getTelephone2() {
        return telephone2;
    }

    public void setTelephone2(String telephone2) {
        this.telephone2 = telephone2;
    }

    public String getTelephone3() {
        return telephone3;
    }

    public void setTelephone3(String telephone3) {
        this.telephone3 = telephone3;
    }

    public String getAppartenance() {
        return appartenance;
    }

    public void setAppartenance(String appartenance) {
        this.appartenance = appartenance;
    }


    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Identity getProprietaire() {
        return proprietaire;
    }

    public void setProprietaire(Identity proprietaire) {
        this.proprietaire = proprietaire;
    }

    @Override
    public int compareTo(@NonNull Agence o) {
        return 0;
    }
}
