package cd.sklservices.com.Beststockage.Classes.Registres;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import cd.sklservices.com.Beststockage.Classes.ModelBase;


@Entity(tableName = "human", foreignKeys = { @ForeignKey(entity = Identity.class,
        parentColumns = "id",
        childColumns = "id",
        onUpdate = ForeignKey.CASCADE) }
)

public class Human extends ModelBase {
    @PrimaryKey
    @NonNull
    private String id;
    private String gender;
    private String birthday;
    private String email;
    private String photo;
    @Ignore
    private Identity identity;

    public Human(@NonNull String id, String gender, String birthday, String email, String photo,String adding_date, String updated_date, int sync_pos, int pos) {
        super(adding_date, updated_date, sync_pos, pos);
        this.id = id;
        this.gender = gender;
        this.birthday = birthday;
        this.email = email;
        this.photo = photo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Identity getIdentity() {
        return identity;
    }

    public void setIdentity(Identity identity) {
        this.identity = identity;
    }
}
