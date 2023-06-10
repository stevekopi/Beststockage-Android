package cd.sklservices.com.Beststockage.Repository.Registres;

import android.content.Context;
import android.util.Log;

import java.util.List;

import cd.sklservices.com.Beststockage.Classes.Registres.Identity;
import cd.sklservices.com.Beststockage.Dao.Registres.DaoIdentity;
import cd.sklservices.com.Beststockage.Outils.MyDataBase;

/**
 * Created by Steve Kopi Loseme on 01/02/2021.
 */

public class IdentityRepository {

    private DaoIdentity daoidentity ;
    private static IdentityRepository instance=null;
    private static Identity identity;
    private static Context context;
    private List<Identity> identityArrayListe ;

    public IdentityRepository(Context application) {
        MyDataBase mydata = MyDataBase.getInstance(application) ;
        daoidentity = mydata.daoIdentity() ;
    }

    public static final IdentityRepository getInstance(Context context){

        if (context!=null){
            IdentityRepository.context=context;}

        if (IdentityRepository.instance==null){
            IdentityRepository.instance=new IdentityRepository(context);
        }
        return IdentityRepository.instance;
    }

    public List<Identity> gets() {
        return daoidentity.gets() ;
    }

    public void ajout_async(Identity instance)
    {
        try{
            Identity old = get(instance.getId(),false) ;
            if(old == null)
            {
                daoidentity.insert(instance);
            }
            else
            {
                if (instance.getPos()>old.getPos() || old.getSync_pos()==0 || old.getSync_pos()==3)
                {
                    daoidentity.update(instance) ;
                }
            }
        }
        catch (Exception e){
            Log.d("Assert","IdentityRepository.ajout_async() "+e.toString());
        }
    }


    public Identity get(String id,boolean witAddress){
        Identity instance=daoidentity.get(id) ;
        try{
           if(witAddress)
                instance.setAddress(new AddressRepository(AddressRepository.getContext()).get(instance.getAddress_id(),true));
        }
        catch (Exception e){
            Log.d("Assert"," IdentityRepository.get() "+e.toString());
        }
        return instance;
    }


    public List<Identity> getIdentitySynchro()  {

        return daoidentity.select_identity_bysend() ;
    }

    public String select_name_by_agence_id(String AgenceId)  {

        return daoidentity.select_name_by_agence_id(AgenceId) ;
    }



    public void setIdentity(Identity identity){
        IdentityRepository.identity = identity;
    }

    public Identity getIdentity(){
        if (identity ==null){return null;}
        else{
            return  get(identity.getId(),true) ;}
    }

    public String getId(){
        if (identity==null){return null;}
        else{return  identity.getId();}
    }

    public static final Context getContext(){
        return context;
    }

}
