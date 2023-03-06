package cd.sklservices.com.Beststockage.Repository.Registres;

import android.content.Context;
import android.util.Log;

import java.util.List;

import cd.sklservices.com.Beststockage.Classes.Registres.Client;
import cd.sklservices.com.Beststockage.Classes.Registres.Identity;
import cd.sklservices.com.Beststockage.Dao.Stocks.Registres.DaoClient;
import cd.sklservices.com.Beststockage.Outils.MyDataBase;

/**
 * Created by Steve Kopi Loseme on 01/02/2021.
 */

public class ClientRepository {

    private DaoClient daoClient ;
    private static ClientRepository instance=null;
    private static Client client;
    private static Context context;
    private List<Client> clientArrayListe ;

    public ClientRepository(Context application) {
        MyDataBase mydata = MyDataBase.getInstance(application) ;
        daoClient= mydata.daoClient() ;
    }

    public static final ClientRepository getInstance(Context context){

        if (context!=null){
            ClientRepository.context=context;}

        if (ClientRepository.instance==null){
            ClientRepository.instance=new ClientRepository(context);
        }
        return ClientRepository.instance;
    }

    public void ajout_sync(Client instance)
    {
        Client old = get(instance.getId(),false) ;
        if(old == null)
        {
            daoClient.insert(instance);
        }
        else
        {
            if (instance.getPos()>old.getPos() || old.getSync_pos()==0 || old.getSync_pos()==3)
            {
                daoClient.update(instance) ;
            }
        }
    }

    public Client get(String id,boolean withHuman){

        try{
            Client instance=  daoClient.get(id) ;
            if(withHuman)
                instance.setHuman(new HumanRepository(HumanRepository.getContext()).get(instance.getId(),true));
            return instance;
        }
        catch (Exception e){
            Log.d("Assert","ClientRepository getList Erreur: "+e.toString());
        }
        return null ;
    }


    public List<Identity> getClientLoading2(String  value) {
        return daoClient.select_clientLoading2 (value) ;
    }

    public List<Identity> getClientLoading() {
        return daoClient.select_clientLoading() ;
    }

    public int count() {
        return daoClient.count() ;
    }



    public static final Context getContext(){
        return context;
    }


}
