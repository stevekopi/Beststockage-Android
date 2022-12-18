package cd.sklservices.com.Beststockage.Classes;

public class ModelBaseX extends ModelBase{
    private String adding_user_id;
    private String adding_agence_id;

    public ModelBaseX(String adding_user_id, String adding_agence_id,String adding_date, String updated_date, int sync_pos, int pos) {
        super(adding_date, updated_date, sync_pos, pos);
        this.adding_user_id = adding_user_id;
        this.adding_agence_id = adding_agence_id;
    }

    public ModelBaseX() {

    }

    public String getAdding_user_id() {
        return adding_user_id;
    }

    public void setAdding_user_id(String adding_user_id) {
        this.adding_user_id = adding_user_id;
    }

    public String getAdding_agence_id() {
        return adding_agence_id;
    }

    public void setAdding_agence_id(String adding_agence_id) {
        this.adding_agence_id = adding_agence_id;
    }

}
