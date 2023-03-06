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

@Entity(tableName = "identity",
        foreignKeys = { @ForeignKey(entity = Address.class,parentColumns = "id",childColumns = "address_id",onUpdate = ForeignKey.CASCADE) }
)

public class Identity extends ModelBase implements Comparable<Identity> {

    @PrimaryKey @NonNull
    private String id ;
    private String address_id;
    private String type ;
    private String name ;
    private String telephone ;
    private String telephone2 ;
    private String telephone3 ;
    @Ignore
    private Address address ;

    @Ignore
    public Identity(){

    }

    public Identity(@NonNull String id, String address_id, String type, String name, String telephone, String telephone2, String telephone3, String adding_date, String updated_date, int sync_pos, int pos) {
        super(adding_date,updated_date,sync_pos,pos);
        this.address_id = address_id;
        this.type = type;
        this.name = name;
        this.telephone = telephone;
        this.telephone2 = telephone2;
        this.telephone3 = telephone3;
        this.id=id;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getAddress_id() {
        return address_id;
    }

    public void setAddress_id(String address_id) {
        this.address_id = address_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelephone() {
        return telephone;
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

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public int compareTo(@NonNull Identity o) {
        return 0;
    }
}
