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


@Entity (tableName = "user",
        foreignKeys = { @ForeignKey(entity = Human.class,parentColumns = "id",childColumns = "id",onUpdate = ForeignKey.CASCADE),
                        @ForeignKey(entity = UserRole.class,parentColumns = "id",childColumns = "user_role_id",onUpdate = ForeignKey.CASCADE)}
        )

public class User extends ModelBase implements Comparable<User> {

    @PrimaryKey @NonNull
    private String id ;

    private String agence_id ;
    private String user_role_id ;

    private String username ;

    private String password ;

    private String statut ;

    @Ignore
    private Human human;

    @Ignore
    private Agence agence;

    @Ignore
    private UserRole role;

    public User(@NonNull String id,String agence_id, String user_role_id, String username, String password,String statut,String adding_date, String updated_date, int sync_pos, int pos) {
        super(adding_date, updated_date, sync_pos, pos);
        this.agence_id = agence_id;
        this.user_role_id = user_role_id;
        this.username = username;
        this.password = password;
        this.statut = statut;
        this.id=id;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getAgence_id() {
        return agence_id;
    }

    public void setAgence_id(String agence_id) {
        this.agence_id = agence_id;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUser_role_id() {
        return user_role_id;
    }

    public void setUser_role_id(String user_role_id) {
        this.user_role_id = user_role_id;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public Human getHuman() {
        return human;
    }

    public void setHuman(Human human) {
        this.human = human;
    }

    public Agence getAgence() {
        return agence;
    }

    public void setAgence(Agence agence) {
        this.agence = agence;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    @Override
    public int compareTo(@NonNull User o) {
        return 0;
    }
}
