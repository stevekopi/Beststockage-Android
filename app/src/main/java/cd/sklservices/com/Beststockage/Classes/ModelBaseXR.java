package cd.sklservices.com.Beststockage.Classes;

public class ModelBaseXR extends ModelBaseX{
    protected String last_update_user_id;

    public ModelBaseXR(String adding_user_id,String last_update_user_id, String adding_agence_id,String adding_date, String updated_date, int sync_pos, int pos) {
        super(adding_user_id,adding_agence_id,adding_date, updated_date, sync_pos, pos);
        this.last_update_user_id=last_update_user_id;
    }

   public String getLast_update_user_id() {
        return last_update_user_id;
    }

    public void setLast_update_user_id(String last_update_user_id) {
        this.last_update_user_id = last_update_user_id;
    }

}
