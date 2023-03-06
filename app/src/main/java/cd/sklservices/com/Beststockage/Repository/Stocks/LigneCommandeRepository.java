package cd.sklservices.com.Beststockage.Repository.Stocks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import cd.sklservices.com.Beststockage.Classes.Stocks.LigneCommande;
import cd.sklservices.com.Beststockage.Dao.Stocks.DaoLigneCommande;
import cd.sklservices.com.Beststockage.Outils.*;

/**
 * Created by Steve Kopi Loseme on 04/02/2021.
 */

public class LigneCommandeRepository {

    private DaoLigneCommande daoligne  ;
    private static LigneCommandeRepository instance=null;
    private static LigneCommande ligne ;
    private static Context context;
    private List<LigneCommande> ligneArrayListe ;

    public LigneCommandeRepository(Context application) {
        MyDataBase mydata = MyDataBase.getInstance(application) ;
        daoligne = mydata.daoLignecommande() ;
    }

    public static final LigneCommandeRepository getInstance(Context context){

        if (context!=null){
            LigneCommandeRepository.context=context;}

        if (LigneCommandeRepository.instance==null){
            LigneCommandeRepository.instance=new LigneCommandeRepository(context);
        }
        return LigneCommandeRepository.instance;
    }

    public List<LigneCommande> getLigneArrayListe() {
        return ligneArrayListe;
    }

    public void setLigneArrayListe(List<LigneCommande> ligneArrayListe) {
        this.ligneArrayListe = ligneArrayListe;
    }

    public void setLigneCommande(LigneCommande ligneC)
    {
        LigneCommandeRepository.ligne = ligneC;
    }


    public LigneCommande getLigneCommande(){
        if (ligne ==null){return null;}
        else{return  ligne ;}
    }

    public void ajout_sync(LigneCommande instance)
    {
        LigneCommande old = get(instance.getId()) ;
        if(old == null)
        {
            daoligne.insert(instance);
        }
        else
        {
            if (instance.getPos()>old.getPos() || old.getSync_pos()==0 || old.getSync_pos()==3)
            {
                daoligne.update(instance) ;
            }
        }
    }


    public void delete_all()
    {
        daoligne.delete_all();
    }


    public LigneCommande get(String id){

        try{
            return   daoligne.get(id) ;

        }
        catch (Exception e){
            Log.d("Assert"," Dao ligne bon commande  "+e.toString());
        }
        return null;
    }


    public AsyncTask delete2(String Id){

        try{
            daoligne.delete2(Id);
        }
        catch (Exception e){
            Log.d("Assert"," Dao ligne bon commande  "+e.toString());
        }
        return null;
    }

    public List<LigneCommande> getLigneCommandeSynchro(){

        try{
            return daoligne.select_ligne_commande_bysend() ;
        }
        catch (Exception e){
            Log.d("Assert"," Dao ligne bon commande  "+e.toString());
        }
        return null;
    }

    public double get_montant_commande(String commande_id){

        try{

            return daoligne.get_montant_commande(commande_id) ;
        }
        catch (Exception e){
            Log.d("Assert"," LigneCommandeRepository get_montant_commande :  "+e.toString());
        }
        return 0;
    }

    public void gets(){

        try{
            ArrayList arrayList=new ArrayList();
            List<LigneCommande> mylist =  daoligne.select_ligne_commande() ;
            instance.setLigneArrayListe(mylist);

        }
        catch (Exception e){
            Log.d("Assert","Dao Ligne commande .getsErreur: "+e.toString());
        }
    }

    public List<LigneCommande> getList(){

        try{
            return daoligne.select_ligne_commande() ;

        }
        catch (Exception e){
            Log.d("Assert","Dao Ligne commande .getsErreur: "+e.toString());
        }
        return null ;
    }

    public int getQuantite(String IdLigneCommande){

        try{

            int qt = 0 ;
            List<LigneCommande> mylist =  daoligne.select_quantite_ligne_commande(IdLigneCommande) ;

            for (LigneCommande l : mylist){
                qt = Integer.valueOf(l.getQuantite()) ;
            }

            return qt ;

        }
        catch (Exception e){
            Log.d("Assert","Dao Ligne commande .getsErreur: "+e.toString());
        }

        return 0 ;
    }

    public List<LigneCommande> ligne_commande_from_commande(String CommandeId){

        try{
             return daoligne.select_byId_commande_ligne_commande(CommandeId) ;

        }
        catch (Exception e){
            Log.d("Assert","DaoArticleLoc.getByFournisseurId(): "+e.toString());
            return null;
        }
    }

    public List<LigneCommande> ligne_commande_from_commande2(String CommandeId){

        try{
            return  daoligne.select_byId_commande_ligne_commande(CommandeId) ;

        }
        catch (Exception e){
            Log.d("Assert","DaoArticleLoc.getByFournisseurId(): "+e.toString());
            return null;
        }
    }


    public AsyncTask update(LigneCommande l)
    {
        new UpdateLigneAsyncTask(daoligne).execute(l) ;

        return null ;
    }

    public void update2(LigneCommande l)
    {
        daoligne.update(l);
    }

    public class UpdateLigneAsyncTask extends AsyncTask<LigneCommande, Void, Void>
    {
        private DaoLigneCommande daol ;

        private UpdateLigneAsyncTask(DaoLigneCommande daol)
        {
            this.daol = daol ;
        }
        @Override
        protected Void doInBackground(LigneCommande... l) {

            try{
                    daol.update(l[0]);

            }catch (Exception e){
                Log.d("Assert","Ajout Ligne Bon de Commande   ");
            }

            return null ;
        }
    }

    public void delete(LigneCommande ligne)
    {
        new DeleteLigneCommandesyncTask(daoligne).execute(ligne) ;
    }

    public class DeleteLigneCommandesyncTask extends AsyncTask<LigneCommande, Void, Void>
    {
        private DaoLigneCommande daoL ;

        private DeleteLigneCommandesyncTask(DaoLigneCommande daoO)
        {
            this.daoL = daoO ;
        }
        @Override
        protected Void doInBackground(LigneCommande... ligneCommandes) {

            try{

                daoL.delete(ligneCommandes[0]) ;

            }catch (Exception e){
                Log.d("Assert"," Erreur Update Commande  " + e.toString());
            }

            return null ;
        }
    }





}
