package cd.sklservices.com.Beststockage.Repository;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import cd.sklservices.com.Beststockage.Classes.Contenance;
import cd.sklservices.com.Beststockage.Classes.Human;
import cd.sklservices.com.Beststockage.Dao.DaoHuman;
import cd.sklservices.com.Beststockage.Dao.DaoUser;
import cd.sklservices.com.Beststockage.Outils.MyDataBase;

/**
 * Created by Steve Kopi Loseme on 04/02/2021.
 */

public class HumanRepository {

    private DaoHuman daoHuman  ;
    private static HumanRepository instance=null;
    private static Human human;
    private static Context context;
    private List<Human> humanArrayListe ;

    public HumanRepository(Context application) {
        MyDataBase mydata = MyDataBase.getInstance(application) ;
        daoHuman = mydata.daoHuman()  ;
    }

    public static final HumanRepository getInstance(Context context){

        if (context!=null){
            HumanRepository.context=context;}

        if (HumanRepository.instance==null){
            HumanRepository.instance=new HumanRepository(context);
        }
        return HumanRepository.instance;
    }

    public List<Human> getHumanArrayListe() {
        return daoHuman.select_human() ;
    }

    public void setHumanArrayListe(List<Human> humanArrayListe) {

        try{
                this.humanArrayListe = humanArrayListe;

        }
        catch (Exception e){
            Log.d("Assert","Dao User2222 "+e.toString());
        }

    }

    public void ajout_sync(Human instance)
    {
        Human old = get(instance.getId(),false) ;
        if(old == null)
        {
            daoHuman.insert(instance);
        }
        else
        {
            if (instance.getPos()>old.getPos() || old.getSync_pos()==0 || old.getSync_pos()==3)
            {
                daoHuman.update(instance) ;
            }
        }
    }

    public Human get(String id,boolean withIdentity){

        try{
            Human instance = daoHuman.get(id) ;
            if(withIdentity)
                instance.setIdentity(new IdentityRepository(IdentityRepository.getContext()).get(instance.getId(),true));
            return instance;

        }
        catch (Exception e){
            Log.d("Assert"," Dao Human "+e.toString());
        }
        return null;
    }


    public List<Human> getBySend(){

        try{
            return  daoHuman.select_human_bysend() ;
        }
        catch (Exception e){
            Log.d("Assert"," Dao Human getBySend  "+e.toString());
        }
        return null;
    }

    public List<Human> select_all(){

        try{
            return   daoHuman.select_human() ;
        }
        catch (Exception e){
            Log.d("Asset"," Dao User kkk  "+e.toString());
        }
        return null;
    }


    public void gets(){

        try{

            List<Human> mylist =  daoHuman.select_human() ;
            if(mylist != null)
            {
                instance.setHumanArrayListe(mylist);
            }

        }
        catch (Exception e){
            Log.d("Assert","Dao User tost "+e.toString());
        }
    }


    public void delete(Human instance){
        try{
            new DeleteHumanAsyncTask(daoHuman).execute(instance) ;
        }
        catch (Exception e){
            Log.d("Assert"," Error Update User  " + e.toString());
        }

    }

    public class DeleteHumanAsyncTask extends AsyncTask<Human, Void, Void>
    {
        private DaoHuman daoHuman1;

        private DeleteHumanAsyncTask(DaoHuman daoUser)
        {
            this.daoHuman1 = daoUser ;
        }
        @Override
        protected Void doInBackground(Human... humans) {

            try{

                daoHuman1.delete(humans[0]);

            }catch (Exception e){
                Log.d("Assert","Update User " + e.toString());
            }

            return null ;
        }
    }


    public void setHuman(Human human){
        instance.human = human;
    }

    public Human getHuman(){
        if (human ==null){return null;}
        else{return human;}
    }

     public String getGender(){
        if (human ==null){return null;}
        else{return  human.getGender();}
    }

    public String getBirthday(){
        if (human ==null){return null;}
        else{return  human.getBirthday();}
    }


    public String getEmail(){
        if (human ==null){return null;}
        else{return  human.getEmail();}
    }

    public String getPhoto(){
        if (human ==null){return null;}
        else{return  human.getPhoto();}
    }

    public String getId(){
        if (human ==null){return null;}
        else{return  human.getId();}
    }

    public String getAddingDate(){
        if (human ==null){return null;}
        else{return  human.getAdding_date();}
    }

    public String getUpdatedDate(){
        if (human ==null){return null;}
        else{return  human.getUpdated_date();}
    }

    public int getSyncPos(){
        if (human ==null){return 0;}
        else{return  human.getSync_pos();}
    }

    public int getPos(){
        if (human ==null){return 0;}
        else{return  human.getPos();}
    }

    public static final Context getContext(){
        return context;
    }



}
