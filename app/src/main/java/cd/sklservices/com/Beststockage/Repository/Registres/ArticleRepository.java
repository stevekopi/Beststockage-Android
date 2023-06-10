package cd.sklservices.com.Beststockage.Repository.Registres;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import cd.sklservices.com.Beststockage.Classes.Registres.Article;
import cd.sklservices.com.Beststockage.Classes.Registres.Contenance;
import cd.sklservices.com.Beststockage.Dao.Registres.DaoArticle;
import cd.sklservices.com.Beststockage.Outils.*;

/**
 * Created by Steve Kopi Loseme on 01/02/2021.
 */

public class ArticleRepository {

    private DaoArticle daoarticle  ;
    private static ArticleRepository instance=null;
    private static Article article;
    private static Context context;
    private List<Article> articlesArrayListe ;

    public ArticleRepository(Context application) {
        MyDataBase mydata = MyDataBase.getInstance(application) ;
        daoarticle = mydata.daoArticle() ;
    }

    public static final ArticleRepository getInstance(Context context){

        if (context!=null){
            ArticleRepository.context=context;}

        if (ArticleRepository.instance==null){
            ArticleRepository.instance=new ArticleRepository(context);
        }
        return ArticleRepository.instance;
    }

    public List<Article> getArticlesArrayListe() {
        List<Article> instances=new ArrayList<>();
        for (Article item:daoarticle.get_all() ){
            instances.add(get(item.getId(),true,true));
        }
        return  instances;
    }

    public List<Article> get_all() {
     return  daoarticle.get_all();
    }

    public List<Article> getArticlesByDesignation(String designation) {
        return  daoarticle.get_byDesignation_article(designation) ;
    }

    public void setArticlesArrayListe(List<Article> articlesArrayListe) {
        this.articlesArrayListe = articlesArrayListe;
    }


    public AsyncTask ajout_sync(Article instance)
    {
        Article old = get(instance.getId(),false,false) ;
        if(old == null)
        {
            daoarticle.insert(instance);
        }
        else
        {
            if (instance.getPos()>old.getPos() || old.getSync_pos()==0 || old.getSync_pos()==3)
            {
                daoarticle.update(instance) ;
            }
        }
        return null ;
    }


    public Article get(String article_id,boolean withCategorie,boolean withContenance){
        Article instance=null;
        try{

            instance=daoarticle.get(article_id) ;
            if(withCategorie)
                instance.setCategorie(new CategorieRepository(ContenanceRepository.getContext()).get(instance.getCategorie_id()));
            if(withContenance)
                instance.setContenance(new ContenanceRepository(ContenanceRepository.getContext()).get(instance.getContenance_id()));

        }
        catch (Exception e){
            Log.d("Assert"," DaoArticle .get() "+e.toString());
        }
        return instance;
    }


    public void gets(){

        try{
            ArrayList arrayList=new ArrayList();
            List<Article> mylist =  daoarticle.get_all() ;
            instance.setArticlesArrayListe(mylist);

        }
        catch (Exception e){
            Log.d("Assert","Dao Article.getsErreur: "+e.toString());
        }
    }

    public ArrayList<Article> getByFournisseurId(String fournisseurId){

        try{
            ArrayList articleArrayListe=new ArrayList();

            List<Article> mylist =  daoarticle.get_byforunisseur_article(fournisseurId) ;

            for (Article article : mylist){
                articleArrayListe.add(article) ;
            }

            return articleArrayListe;

        }
        catch (Exception e){
            Log.d("Assert","DaoArticleLoc.getByFournisseurId(): "+e.toString());
            return null;
        }
    }

    public List<Article>  getArticleLoading2(String index) {
        return daoarticle.select_articleLoading2 (index) ;
    }

    public AsyncTask delete_all()
    {
        daoarticle.delete_all();
        return null ;
    }

    public List<Article> getArticleLoading() {
       try{
            return daoarticle.select_articleLoading () ;

       }
       catch (Exception e){

       }
       return null;
    }

    public void setArticle(Article article){
        ArticleRepository.article =article;
    }

    public String getId(){
        if (article ==null){return null;}
        else{return  article.getId();}
    }

    public String getCategorie_id(){
        if (article ==null){return null;}
        else{return  article.getCategorie_id();}
    }

    public String getDesignation(){
        if (article ==null){return null;}
        else{return  article.getDesignation();}
    }

    public Article getArticle(){
        if (article ==null){return null;}
        else{return  article ;}
    }

    public Double getPrix(){
        if (article ==null){return null;}
        else{return  article.getPrix();}
    }

    public String getAddingDate(){
        if (article ==null){return null;}
        else{return  article.getAdding_date();}
    }


    public String getContenance(){
        if (article ==null){return null;}
        else{return  article.getContenance_id();}
    }

    public String getDevise(){
        if (article ==null){return null;}
        else{return  article.getDevise();}
    }

    public Contenance get_contenance(){
        if (article==null){return null;}
        else{return  article.getContenance();}
    }

    public String get_description(){
        if (article==null){return null;}
        else{return  article.getDescription();}
    }

    public static final Context getContext(){
        return context;
    }

    public int count() {
        return daoarticle.count() ;
    }
}
