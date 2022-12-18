package cd.sklservices.com.Beststockage.Classes;

public class ModelBase {
    private String adding_date ;
    private String updated_date ;
    private int sync_pos ;
    private int pos ;

    public ModelBase(){}

    public ModelBase(String adding_date,String updated_date, int sync_pos,int pos) {
        this.adding_date = adding_date;
        this.sync_pos = sync_pos;
        this.updated_date=updated_date;
        this.pos=pos;
    }

    public String getAdding_date() {
        return adding_date;
    }

    public void setAdding_date(String adding_user_id) {
        this.adding_date = adding_user_id;
    }

    public String getUpdated_date() {
        return updated_date;
    }

    public void setUpdated_date(String updated_agence_id) {
        this.updated_date = updated_agence_id;
    }

    public int getSync_pos() {
        return sync_pos;
    }

    public void setSync_pos(int sync_pos) {
        this.sync_pos = sync_pos;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }
}
