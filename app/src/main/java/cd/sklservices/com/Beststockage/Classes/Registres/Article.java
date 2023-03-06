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

@Entity(tableName = "article",
        foreignKeys = { @ForeignKey (entity = Categorie.class,parentColumns = "id",childColumns = "categorie_id",onUpdate = ForeignKey.CASCADE),
                        @ForeignKey (entity = Fournisseur.class,parentColumns = "id",childColumns = "fournisseur_id",onUpdate = ForeignKey.CASCADE),
                        @ForeignKey (entity = Contenance.class,parentColumns = "id",childColumns = "contenance_id",onUpdate = ForeignKey.CASCADE)}
)


public class Article extends ModelBase implements Comparable<Article> {

    @PrimaryKey @NonNull
    private  String id ;

    private String categorie_id ;
    private String fournisseur_id ;
    private String devise ;
    private String contenance_id;
    private String section_1 ;
    private String section_2 ;
    private String designation ;

    private Double prix ;
    private Double prix_detail ;
    private Double remise ;
    private Double poids ;
    private String unite_poids ;
    private String grammage ;
    private String qr_code ;
    private String code_barre ;
    private String lien_web ;
    @Ignore
    private Categorie categorie;

    @Ignore
    private Contenance contenance;

    @Ignore
    private String description;



    public Article(@NonNull String id, String categorie_id, String fournisseur_id, String devise, String contenance_id,String section_1,String section_2, String designation, Double prix,Double prix_detail,Double remise,Double poids,String unite_poids,String grammage,String qr_code,String code_barre,String lien_web,String adding_date, String updated_date, int sync_pos, int pos) {
        super(adding_date, updated_date, sync_pos, pos);
        this.id = id;
        this.categorie_id = categorie_id;
        this.fournisseur_id = fournisseur_id;
        this.devise = devise;
        this.contenance_id = contenance_id;
        this.designation = designation;
        this.section_1 = section_1;
        this.section_2 = section_2;
        this.prix = prix;
        this.prix_detail = prix_detail;
        this.remise = remise;
        this.poids = poids;
        this.unite_poids = unite_poids;
        this.grammage = grammage;
        this.qr_code = qr_code;
        this.code_barre = code_barre;
        this.lien_web = lien_web;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getCategorie_id() {
        return categorie_id;
    }

    public void setCategorie_id(String categorie_id) {
        this.categorie_id = categorie_id;
    }

    public String getFournisseur_id() {
        return fournisseur_id;
    }

    public void setFournisseur_id(String fournisseur_id) {
        this.fournisseur_id = fournisseur_id;
    }

    public String getDevise() {
        return devise;
    }

    public void setDevise(String devise) {
        this.devise = devise;
    }

    public String getContenance_id() {
        return contenance_id;
    }

    public void setContenance_id(String contenance_id) {
        this.contenance_id = contenance_id;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public Double getPrix() {
        return prix;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
    }

    public Double getPrix_detail() {
        return prix_detail;
    }

    public void setPrix_detail(Double prix_detail) {
        this.prix_detail = prix_detail;
    }

    public Contenance getContenance() {
        return contenance;
    }

    public String getSection_1() {
        return section_1;
    }

    public void setSection_1(String section_1) {
        this.section_1 = section_1;
    }

    public String getSection_2() {
        return section_2;
    }

    public void setSection_2(String section_2) {
        this.section_2 = section_2;
    }

    public Double getRemise() {
        return remise;
    }

    public void setRemise(Double remise) {
        this.remise = remise;
    }

    public Double getPoids() {
        return poids;
    }

    public void setPoids(Double poids) {
        this.poids = poids;
    }

    public String getUnite_poids() {
        return unite_poids;
    }

    public void setUnite_poids(String unite_poids) {
        this.unite_poids = unite_poids;
    }

    public String getGrammage() {
        return grammage;
    }

    public void setGrammage(String grammage) {
        this.grammage = grammage;
    }

    public String getQr_code() {
        return qr_code;
    }

    public void setQr_code(String qr_code) {
        this.qr_code = qr_code;
    }

    public String getCode_barre() {
        return code_barre;
    }

    public void setCode_barre(String code_barre) {
        this.code_barre = code_barre;
    }

    public String getLien_web() {
        return lien_web;
    }

    public void setLien_web(String lien_web) {
        this.lien_web = lien_web;
    }

    public void setContenance(Contenance contenance) {
        this.contenance = contenance;
    }

    public String getDescription() {
        if(this.contenance!=null){
            return getDesignation()+" "+contenance.getCapacite();
        }
        else{
            return getDesignation();
        }
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    @Override
    public int compareTo(@NonNull Article o) {
        return 0;
    }
}
