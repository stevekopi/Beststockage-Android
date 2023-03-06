package cd.sklservices.com.Beststockage.Classes.Parametres;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import cd.sklservices.com.Beststockage.Classes.ModelBase;

@Entity(tableName = "devise")

public class Devise extends ModelBase {
    @PrimaryKey
    @NonNull
    private String id;
    private String code;
    private String designation;
    private String description;
    private String symbole;
    private int is_local;
    private int is_default;
    private int is_default_converter;

    public Devise(@NonNull String id, String code, String designation, String description, String symbole, int is_local, int is_default, int is_default_converter,String adding_date, String updated_date, int sync_pos, int pos) {
        super(adding_date, updated_date, sync_pos, pos);
        this.id = id;
        this.code = code;
        this.designation = designation;
        this.description = description;
        this.symbole = symbole;
        this.is_local = is_local;
        this.is_default = is_default;
        this.is_default_converter = is_default_converter;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSymbole() {
        return symbole;
    }

    public void setSymbole(String symbole) {
        this.symbole = symbole;
    }

    public int getIs_local() {
        return is_local;
    }

    public void setIs_local(int is_local) {
        this.is_local = is_local;
    }

    public int getIs_default() {
        return is_default;
    }

    public void setIs_default(int is_default) {
        this.is_default = is_default;
    }

    public int getIs_default_converter() {
        return is_default_converter;
    }

    public void setIs_default_converter(int is_default_converter) {
        this.is_default_converter = is_default_converter;
    }
}
