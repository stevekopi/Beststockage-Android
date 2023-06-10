package cd.sklservices.com.Beststockage.Repository.Parametres;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import cd.sklservices.com.Beststockage.Classes.Parametres.ArticleProduitFacture;
import cd.sklservices.com.Beststockage.Dao.Parametres.DaoArticleProduitFacture;
import cd.sklservices.com.Beststockage.Outils.MyDataBase;

/**
 * Created by Steve Kopi Loseme on 01/02/2021.
 */

public class ArticleProduitFactureRepository {

    private DaoArticleProduitFacture daoarticleProduitFacture  ;
    private static ArticleProduitFactureRepository instance=null;
    private static ArticleProduitFacture articleProduitFacture;
    private static Context context;
    private List<ArticleProduitFacture> articleProduitFactureArrayListe ;

    public ArticleProduitFactureRepository(Context application) {
        MyDataBase mydata = MyDataBase.getInstance(application) ;
        daoarticleProduitFacture = mydata.daoArticleProduitFacture() ;
    }

    public static final ArticleProduitFactureRepository getInstance(Context context){

        if (context!=null){
            ArticleProduitFactureRepository.context=context;}

        if (ArticleProduitFactureRepository.instance==null){
            ArticleProduitFactureRepository.instance=new ArticleProduitFactureRepository(context);
        }
        return ArticleProduitFactureRepository.instance;
    }

    public List<ArticleProduitFacture> getArticleProduitFactureArrayListe() {
        return articleProduitFactureArrayListe;
    }

    public void setArticleProduitFactureArrayListe(List<ArticleProduitFacture> articlesArrayListe) {
        this.articleProduitFactureArrayListe = articlesArrayListe;
    }

    public void ajout_sync(ArticleProduitFacture instance)
    {
        ArticleProduitFacture old = get(instance.getId()) ;
        if(old == null)
        {
            daoarticleProduitFacture.insert(instance);
        }
        else
        {
            if (instance.getPos()>old.getPos() || old.getSync_pos()==0 || old.getSync_pos()==3)
            {
                daoarticleProduitFacture.update(instance) ;
            }
        }
    }


    public ArticleProduitFacture get(String id){

        try{
            return daoarticleProduitFacture.get(id) ;

        }
        catch (Exception e){
            Log.d("Assert"," DaoAgenceLoc.get() "+e.toString());
        }
        return null;
    }

    public List<ArticleProduitFacture> loadingByProduit(String produitId) {
        try{
            return daoarticleProduitFacture.loadingByProduit(produitId);
        }
        catch (Exception e){

        }
        return null;
    }

    public List<ArticleProduitFacture> loading() {
        try{
            return daoarticleProduitFacture.loading();
        }
        catch (Exception e){

        }
        return null;
    }

    public AsyncTask delete_all()
    {
        daoarticleProduitFacture.delete_all();
        return null ;
    }

    public void gets(){

        try{
            ArrayList arrayList=new ArrayList();
            List<ArticleProduitFacture> mylist =  daoarticleProduitFacture.gets() ;
            instance.setArticleProduitFactureArrayListe(mylist);

        }
        catch (Exception e){
            Log.d("Assert","Dao ArticleProduitFacture.getsErreur: "+e.toString());
        }
    }



    public String getArticleId(){
        if (articleProduitFacture==null){return null;}
        else{return  articleProduitFacture.getArticle_id();}
    }

    public String getId(){
        if (articleProduitFacture==null){return null;}
        else{return  articleProduitFacture.getId();}
    }


    public static final Context getContext(){
        return context;
    }

    public int count(){
        return daoarticleProduitFacture.count();
    }


    public ArticleProduitFacture[] getByProduitFactureId(String produitFactureId){
        try{
            int k = 0 ;

            ArticleProduitFacture[] instances = null ;

            List<ArticleProduitFacture> articleProduitFactures =
                    daoarticleProduitFacture.getByProduitFactureId(produitFactureId);

            instances=new ArticleProduitFacture[articleProduitFactures.size()];

            for (ArticleProduitFacture ligne : articleProduitFactures){

                instances[k] = ligne ;
                k++ ;
            }
            return instances;

        }
        catch (Exception e){
            Log.d("Assert","LigneFactureRepository.getByFactureId(): "+e.toString());
            return  null;
        }
    }
}
