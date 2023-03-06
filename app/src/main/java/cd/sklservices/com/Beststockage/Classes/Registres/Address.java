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

@Entity(tableName = "address",
        foreignKeys = { @ForeignKey(entity = Street.class,parentColumns = "id",childColumns = "street_id",onUpdate = ForeignKey.CASCADE) }
)

public class Address extends ModelBase implements Comparable<Address> {

    @PrimaryKey
    @NonNull
    private String id ;
    private String street_id ;
    private String number ;
    private String reference;

    @Ignore
    private Street street;
    @Ignore
    private Quarter quarter;
    @Ignore
    private Township township;
    @Ignore
    private District district;
    @Ignore
    private Town town;


    public Address(@NonNull String id,String street_id, String number,String reference, String adding_date,String updated_date,int sync_pos,int pos) {
        super(adding_date,updated_date,sync_pos,pos);
        this.id=id;
        this.street_id = street_id;
        this.number = number;
        this.reference=reference;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getStreet_id() {
        return street_id;
    }

    public void setStreet_id(String street_id) {
        this.street_id = street_id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Street getStreet() {
        return street;
    }

    public void setStreet(Street street) {
        this.street = street;
    }

    public Quarter getQuarter() {
        return quarter;
    }

    public void setQuarter(Quarter quarter) {
        this.quarter = quarter;
    }

    public Township getTownship() {
        return township;
    }

    public void setTownship(Township township) {
        this.township = township;
    }

    public District getDistrict() {
        return district;
    }

    public void setDistrict(District district) {
        this.district = district;
    }

    public Town getTown() {
        return town;
    }

    public void setTown(Town town) {
        this.town = town;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    @Override
    public int compareTo(@NonNull Address o) {
        return 0;
    }
}
