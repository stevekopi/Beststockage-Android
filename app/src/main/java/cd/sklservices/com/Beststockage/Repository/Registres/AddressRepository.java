package cd.sklservices.com.Beststockage.Repository.Registres;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import cd.sklservices.com.Beststockage.Classes.Registres.Address;
import cd.sklservices.com.Beststockage.Dao.Stocks.Registres.DaoAdresse;
import cd.sklservices.com.Beststockage.Outils.*;

/**
 * Created by Steve Kopi Loseme on 30/01/2021.
 */

public class AddressRepository {

    private DaoAdresse adressedao ;

    private static AddressRepository instance = null;
    private static Address adresse;
    private static Context context;
    private List<Address> adressesArrayListe ;

    public AddressRepository(Context application)
    {
        MyDataBase mydata = MyDataBase.getInstance(application) ;
        adressedao = mydata.daoAdresse() ;
    }

    public static final AddressRepository getInstance(Application context){

        if (context!=null){
            AddressRepository.context=context;}

        if (AddressRepository.instance==null){
            AddressRepository.instance=new AddressRepository(context);
        }
        return AddressRepository.instance;
    }

    public void setAdressesArrayListe(List<Address> adressesArrayListe) {
        this.adressesArrayListe = adressesArrayListe;
    }

    public AsyncTask ajout_sync(Address instance)
    {
        try{
            Address old = get(instance.getId(),false) ;
            if(old == null)
            {
                adressedao.insert(instance);
            }
            else
            {
                if (instance.getPos()>old.getPos() || old.getSync_pos()==0 || old.getSync_pos()==3)
                {
                    adressedao.update(instance) ;
                }
            }
        }
        catch (Exception e){
            Log.d("Assert","AdresseRepository.gets() "+e.toString());
        }
        return null ;
    }


    public void gets(){

        try{

            List<Address> adresseArrayListe = adressedao.select_adresse() ;
            instance.setAdressesArrayListe(adresseArrayListe);

        }
        catch (Exception e){
            Log.d("Assert","DaoAdresseLoc.gets() "+e.toString());
        }
    }

    public Address get(String adresse_id,boolean isComplete){
        Address instance=adressedao.get(adresse_id) ;
        try{

            if(isComplete){
                instance.setStreet(new StreetRepository(TownRepository.getContext()).get(instance.getStreet_id()));
                instance.setQuarter(new QuarterRepository(QuarterRepository.getContext()).get(instance.getStreet().getQuarter_id()));
                instance.setTownship(new TownshipRepository(TownshipRepository.getContext()).get(instance.getQuarter().getTownship_id()));
                instance.setDistrict(new DistrictRepository(DistrictRepository.getContext()).get(instance.getTownship().getDistrict_id()));
                instance.setTown(new TownRepository(TownRepository.getContext()).get(instance.getDistrict().getTown_id()));
            }

        }
        catch (Exception e){
            Log.d("Assert","DaoAdresseLoc.get "+e.toString());
        }

        return  instance;
    }

    public AsyncTask delete_all()
    {
        adressedao.delete_all();
        return null ;
    }


    public List<Address> getAdresseSynchro()  {

        return adressedao.select_adresse_bysend() ;
    }


    public void setAdresse(Address adresse){
        AddressRepository.adresse=adresse;
    }


    public static final Context getContext(){
        return context;
    }


}
