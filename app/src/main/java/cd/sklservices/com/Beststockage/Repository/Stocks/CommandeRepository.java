package cd.sklservices.com.Beststockage.Repository.Stocks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import cd.sklservices.com.Beststockage.Classes.Stocks.Commande;
import cd.sklservices.com.Beststockage.Dao.Stocks.DaoCommande;
import cd.sklservices.com.Beststockage.Dao.Stocks.DaoLigneCommande;
import cd.sklservices.com.Beststockage.Outils.*;

/**
 * Created by Steve Kopi Loseme on 02/02/2021.
 */

public class CommandeRepository {

    private DaoCommande daocommande  ;
    private DaoLigneCommande daoligne_commande ;
    private static CommandeRepository instance=null;
    private static Commande commande;
    private static Context context;
    private List<Commande> commandeArrayList;

    public CommandeRepository(Context application) {
        MyDataBase mydata = MyDataBase.getInstance(application) ;
        daocommande = mydata.daoCommande() ;
        daoligne_commande = mydata.daoLignecommande() ;
    }

    public static final CommandeRepository getInstance(Context context){

        if (context!=null){
            CommandeRepository.context=context;}

        if (CommandeRepository.instance==null){
            CommandeRepository.instance=new CommandeRepository(context);
        }
        return CommandeRepository.instance;
    }

    public List<Commande> getCommandeArrayListe() {
        return commandeArrayList;
    }

    public void setCommandeArrayList(List<Commande> bonlivraisonArrayList) {
        this.commandeArrayList = bonlivraisonArrayList;
    }

    public Commande get(String id,boolean withAmount){
            Commande instance=null;
        try{
             instance=  daocommande.get(id) ;
             if (withAmount)
                instance.setMontant(new LigneCommandeRepository(getContext()).get_montant_commande(id));
        }
        catch (Exception e){
            Log.d("Assert"," DaoCommande .get() "+e.toString());
        }
        return instance;
    }

    public ArrayList<Commande> getDistinct(){
        try{

            ArrayList arrayListe=new ArrayList();
            List<Commande> mylist =  daocommande.select_orderbydate_commande();

            for (Commande c : mylist){

                    arrayListe.add(get(c.getId(),true));
                }
            return arrayListe;

        }
        catch (Exception e){
            Log.d("Assert","DaoCommandeLoc().getDistinct : "+e.toString());
            return  null;

        }
    }

    public List<Commande> getList(){
        try{

            return  daocommande.select_orderbydate_commande() ;
          }
        catch (Exception e){
            Log.d("Assert","DaoCommandeLoc().getDistinct : "+e.toString());
            return  null;

        }
    }

    public List<Commande> getWhereAll(String Id_user, String Id_prorietaire, String Id_fournisseur, String Date_commande,
                                      String Membership, String Adding_agence_id){
        try{

            return  daocommande.select_whereAll(Id_user, Id_prorietaire, Id_fournisseur, Date_commande,
                     Membership, Adding_agence_id) ;
        }
        catch (Exception e){
            Log.d("Assert","DaoCommandeLoc().getDistinct : "+e.toString());
            return  null;

        }
    }

    public void ajout_sync(Commande instance)
    {
        Commande old = get(instance.getId(),false) ;
        if(old == null)
        {
            daocommande.insert(instance);
        }
        else
        {
            if (instance.getPos()>old.getPos() || old.getSync_pos()==0 || old.getSync_pos()==3)
            {
                daocommande.update(instance) ;
            }
        }
    }


    public Commande getLast(){
        try{
            ArrayList<Commande> data=this.getDistinct();
            Commande instance=data.get(0);
            return  instance;
        }
        catch (Exception e){
            return null;
        }
    }

    public int getSommeQuantiteCommander(String IdCommande){

        try{
            int val=10000;
            List<String> mylist =  daoligne_commande.select_sum_quantite_ligne_commande(IdCommande);

            for (String l : mylist){
                    val= Integer.valueOf(l) ;
            }

            return val;

        }
        catch (Exception e){

            Log.d("Assert","DaoCommandeLoc.getQuantiteCommander() : "+e.toString());
            return 10000;
        }
    }


    public int getSommeQuantiteApprovisionner(String IdCommande){

        try{
            int val=10000;
            List<String> mylist =  daocommande.getSommeQuantiteApprovisionner(IdCommande);

            for (String l : mylist){
                val= Integer.valueOf(l) ;
            }

            return val;

        }
        catch (Exception e){

            Log.d("Assert","DaoCommandeLoc.getQuantiteCommander() : "+e.toString());
            return 10000;
        }
    }


    public List<Commande> getCommandeSynchro(){
        try{

            return  daocommande.select_commande_bysend();
        }
        catch (Exception e){
            Log.d("Assert","DaoCommandeLoc().getDistinct : "+e.toString());
            return  null;

        }
    }

    public List<Commande> select_commande(){
        try{

            return  daocommande.select_commande() ;
        }
        catch (Exception e){
            Log.d("Assert","DaoCommandeLoc().getDistinct : "+e.toString());
            return  null;

        }
    }

    public AsyncTask delete2(String Id){
        try{
            daocommande.delete2(Id);
        }
        catch (Exception e){ }
        return null ;
    }

    public AsyncTask delete_all()
    {
        daocommande.delete_all();
        return null ;
    }

    public void delete(Commande com)
    {
        new DeleteCommandesyncTask(daocommande).execute(com) ;
    }

    public class DeleteCommandesyncTask extends AsyncTask<Commande, Void, Void>
    {
        private DaoCommande daoC ;

        private DeleteCommandesyncTask(DaoCommande daoO)
        {
            this.daoC = daoO ;
        }
        @Override
        protected Void doInBackground(Commande... com) {

            try{

                daoC.delete(com[0]) ;

            }catch (Exception e){
                Log.d("Assert"," Erreur Update Commande  " + e.toString());
            }

            return null ;
        }
    }


    public void setCommande(Commande commande){
        CommandeRepository.commande =commande;
    }


    public String getDateCommande(){
        if (commande ==null){return null;}
        else{return  commande.getDate();}
    }

    public String getId(){
        if (commande ==null){return null;}
        else{return  commande.getId();}
    }

    public static final Context getContext(){
        return context;
    }





}
