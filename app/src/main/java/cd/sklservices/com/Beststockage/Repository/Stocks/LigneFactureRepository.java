package cd.sklservices.com.Beststockage.Repository.Stocks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import cd.sklservices.com.Beststockage.Classes.Stocks.LigneFacture;
import cd.sklservices.com.Beststockage.Dao.Stocks.DaoLigneFacture;
import cd.sklservices.com.Beststockage.Outils.MyDataBase;

/**
 * Created by Steve Kopi Loseme on 04/02/2021.
 */

public class LigneFactureRepository {

    private DaoLigneFacture daoligne  ;
    private static LigneFactureRepository instance=null;
    private static LigneFacture ligne ;
    private static Context context;
    private List<LigneFacture> ligneArrayListe ;

    public static final Context getContext(){
        return context;
    }

    public LigneFactureRepository(Context application) {
        MyDataBase mydata = MyDataBase.getInstance(application) ;
        daoligne = mydata.daoLigneFacture() ;
    }

    public static final LigneFactureRepository getInstance(Context context){

        if (context!=null){
            LigneFactureRepository.context=context;}

        if (LigneFactureRepository.instance==null){
            LigneFactureRepository.instance=new LigneFactureRepository(context);
        }
        return LigneFactureRepository.instance;
    }

    public List<LigneFacture> getLigneArrayListe() {
        return ligneArrayListe;
    }

    public void setLigneArrayListe(List<LigneFacture> ligneArrayListe) {
        this.ligneArrayListe = ligneArrayListe;
    }

    public void setLigneFacture(LigneFacture ligneC)
    {
        LigneFactureRepository.ligne = ligneC;
    }


    public LigneFacture getLigneFacture(){
        if (ligne ==null){return null;}
        else{return  ligne ;}
    }

    public LigneFacture[] getByFactureId(String factureId){
        try{
            int k = 0 ;

            LigneFacture[] ligneFactures = null ;

            List<LigneFacture> lignes =
                    daoligne.get_by_facture_id(factureId);

            ligneFactures=new LigneFacture[lignes.size()];

            for (LigneFacture ligne : lignes){

                ligneFactures[k] = ligne ;
                k++ ;
            }

            return ligneFactures;

        }
        catch (Exception e){
            Log.d("Assert","LigneFactureRepository.getByFactureId(): "+e.toString());
            return  null;
        }
    }

    public int add_async(LigneFacture instance)
    {
        try{
            LigneFacture old = get(instance.getId()) ;
            if(old == null)
            {
                daoligne.insert(instance);
                return 1;
            }
            else
            {
                if (instance.getPos()>old.getPos() || old.getSync_pos()==0 || old.getSync_pos()==3)
                {
                    daoligne.update(instance);
                    return 1;
                }
            }
        }
        catch (Exception e){
            Log.d("Assert","LigneFactureRepository.ajout_async(): "+e.toString());
        }
        return -1;
    }

    public void ajout_sync(LigneFacture instance)
    {
        LigneFacture old = get(instance.getId()) ;
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


    public LigneFacture get(String id){

        try{
            return   daoligne.get(id) ;

        }
        catch (Exception e){
            Log.d("Assert"," Dao lignefacture  "+e.toString());
        }
        return null;
    }

    public int quantite_stock(String agence_id,String article_id){

        try{
            int q_add=daoligne.quantite_add(agence_id,article_id);
            int q_less=daoligne.quantite_less(agence_id,article_id);

            int b_add=daoligne.bonus_add(agence_id,article_id);
            int b_less=daoligne.bonus_less(agence_id,article_id);

            int rep=q_add-q_less+b_add-b_less;

            return rep;

        }
        catch (Exception e){
            Log.d("Assert"," Dao ligneFacture  "+e.toString());
        }
        return 0;
    }



    public AsyncTask delete2(String Id){

        try{
            daoligne.delete2(Id);
        }
        catch (Exception e){
            Log.d("Assert"," Dao ligne bon facture  "+e.toString());
        }
        return null;
    }

    public List<LigneFacture> getLigneFactureSynchro(){

        try{
            return daoligne.select_ligne_facture_bysend() ;
        }
        catch (Exception e){
            Log.d("Assert"," Dao ligne bon facture  "+e.toString());
        }
        return null;
    }

    public double get_montant_ht_facture(String facture_id){

        try{

            return daoligne.get_montant_ht_facture(facture_id) ;
        }
        catch (Exception e){
            Log.d("Assert"," LigneFactureRepository get_montant_facture :  "+e.toString());
        }
        return 0;
    }

    public double get_montant_local_facture(String facture_id){

        try{

            return daoligne.get_montant_local_facture(facture_id) ;
        }
        catch (Exception e){
            Log.d("Assert"," LigneFactureRepository get_montant_facture :  "+e.toString());
        }
        return 0;
    }

    public double get_montant_net_facture(String facture_id){

        try{

            return daoligne.get_montant_net_facture(facture_id) ;
        }
        catch (Exception e){
            Log.d("Assert"," LigneFactureRepository get_montant_facture :  "+e.toString());
        }
        return 0;
    }

    public double get_montant_ttc_facture(String facture_id){

        try{

            return daoligne.get_montant_ttc_facture(facture_id) ;
        }
        catch (Exception e){
            Log.d("Assert"," LigneFactureRepository get_montant_facture :  "+e.toString());
        }
        return 0;
    }

    public void gets(){

        try{
            ArrayList arrayList=new ArrayList();
            List<LigneFacture> mylist =  daoligne.select_ligne_facture() ;
            instance.setLigneArrayListe(mylist);

        }
        catch (Exception e){
            Log.d("Assert","Dao Ligne facture .getsErreur: "+e.toString());
        }
    }

    public List<LigneFacture> getList(){

        try{
            return daoligne.select_ligne_facture() ;

        }
        catch (Exception e){
            Log.d("Assert","Dao Ligne facture .getsErreur: "+e.toString());
        }
        return null ;
    }

    public int getQuantite(String IdLigneFacture){

        try{

            int qt = 0 ;
            List<LigneFacture> mylist =  daoligne.select_quantite_ligne_facture(IdLigneFacture) ;

            for (LigneFacture l : mylist){
                qt = Integer.valueOf(l.getQuantite()) ;
            }

            return qt ;

        }
        catch (Exception e){
            Log.d("Assert","Dao Ligne facture .getsErreur: "+e.toString());
        }

        return 0 ;
    }

    public List<LigneFacture> ligne_facture_from_facture(String FactureId){

        try{
             return daoligne.select_byId_facture_ligne_facture(FactureId) ;

        }
        catch (Exception e){
            Log.d("Assert","DaoArticleLoc.getByFournisseurId(): "+e.toString());
            return null;
        }
    }


    public List<LigneFacture> ligne_facture_from_facture2(String FactureId){

        try{
            return  daoligne.select_byId_facture_ligne_facture(FactureId) ;

        }
        catch (Exception e){
            Log.d("Assert","DaoArticleLoc.getByFournisseurId(): "+e.toString());
            return null;
        }
    }


    public AsyncTask update(LigneFacture l)
    {
        new UpdateLigneAsyncTask(daoligne).execute(l) ;

        return null ;
    }

    public void update2(LigneFacture l)
    {
        daoligne.update(l);
    }

    public class UpdateLigneAsyncTask extends AsyncTask<LigneFacture, Void, Void>
    {
        private DaoLigneFacture daol ;

        private UpdateLigneAsyncTask(DaoLigneFacture daol)
        {
            this.daol = daol ;
        }
        @Override
        protected Void doInBackground(LigneFacture... l) {

            try{
                    daol.update(l[0]);

            }catch (Exception e){
                Log.d("Assert","Ajout Ligne Bon de Facture   ");
            }

            return null ;
        }
    }

    public void delete(LigneFacture ligne)
    {
        new DeleteLigneFacturesyncTask(daoligne).execute(ligne) ;
    }

    public class DeleteLigneFacturesyncTask extends AsyncTask<LigneFacture, Void, Void>
    {
        private DaoLigneFacture daoL ;

        private DeleteLigneFacturesyncTask(DaoLigneFacture daoO)
        {
            this.daoL = daoO ;
        }
        @Override
        protected Void doInBackground(LigneFacture... ligneFactures) {

            try{

                daoL.delete(ligneFactures[0]) ;

            }catch (Exception e){
                Log.d("Assert"," Erreur Update Facture  " + e.toString());
            }

            return null ;
        }
    }





}
