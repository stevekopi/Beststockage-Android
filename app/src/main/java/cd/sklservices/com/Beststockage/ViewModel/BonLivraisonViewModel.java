package cd.sklservices.com.Beststockage.ViewModel;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import cd.sklservices.com.Beststockage.ActivityFolder.MainActivity;
import cd.sklservices.com.Beststockage.Classes.*;
import cd.sklservices.com.Beststockage.Repository.*;

/**
 * Created by Steve Kopi Loseme on 05/02/2021.
 */

public class BonLivraisonViewModel extends AndroidViewModel {

    private BonLivraisonRepository repository ;

    public BonLivraisonViewModel(@NonNull Application application) {
        super(application);
        repository = new BonLivraisonRepository(application) ;
    }

    public void ajout_async(Bonlivraison instance){
        instance.setSync_pos(0);
        instance.setUpdated_date(MainActivity.getAddingDate());
        repository.ajout_sync(instance);
    }


    public void delete_all(){
        repository.delete_all();
    }

    public Bonlivraison get(String id,boolean withBon)
    {
        Bonlivraison instance=repository.get(id) ;
        if(withBon)
            instance.setBon(new BonRepository(BonRepository.getContext()).get(instance.getId()));
        return instance;
    }

    public Bonlivraison getLast(){
        return repository.getLast() ;
    }

   // public ArrayList<Bonlivraison> getDistinct(){
   //     return repository.getDistinct() ;
   // }

    public List<String> getDistinctDates(){
        return repository.getDistinctDates() ;
    }



    public LigneBonlivraison[] getByBonlivraisonId(String bonlivraisonId){
        return repository.getByBonlivraisonId(bonlivraisonId) ;
    }

    public List<Bonlivraison> getArrayListBonLivraison(){
        return repository.getArrayListBonLivraison() ;
    }

    public Bonlivraison[] getByDateBonlivraison(String bonlivraisonDate){
        try{
            int k = 0 ;
            List<Bonlivraison> mylist =  repository.getByDateBonlivraison(bonlivraisonDate);

            List<Bonlivraison> bons1 = new ArrayList<>();
            for (Bonlivraison bl : mylist){
                bons1.add(bl) ;
            }

            Bonlivraison[] bons2 = new Bonlivraison[bons1.size()] ;
            for(Bonlivraison b:bons1)
            {
                bons2[k] = get(b.getId(),true) ;
                k++ ;
            }

            return bons2;

        }
        catch (Exception e){
            Log.d("Assert","DaoLigneBonlivraisonLoc.getByDateBonlivraison(): "+e.toString());
            return  null;
        }

    }

    public List<Bonlivraison[]> getBons(List<String> date){
        try{
            List<Bonlivraison[]> instances=new ArrayList<Bonlivraison[]>();

            for (int k=0;k<date.size();k++){

                instances.add(getByDateBonlivraison(date.get(k)));
            }

            return instances;

        }
        catch (Exception e){
            Log.d("Assert","DaoLigneBonlivraisonLoc.getByIdBonlivraison(): "+e.toString());
            return  null;
        }

    }



}
