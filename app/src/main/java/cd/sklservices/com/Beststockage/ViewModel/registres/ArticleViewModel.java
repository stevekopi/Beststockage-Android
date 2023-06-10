package cd.sklservices.com.Beststockage.ViewModel.registres;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import cd.sklservices.com.Beststockage.Classes.Registres.Article;
import cd.sklservices.com.Beststockage.Repository.Registres.ArticleRepository;

/**
 * Created by Steve Kopi Loseme on 05/02/2021.
 */

public class ArticleViewModel extends AndroidViewModel {

    private ArticleRepository repository ;

    public ArticleViewModel(@NonNull Application application) {
        super(application);
        repository = new ArticleRepository(application) ;
    }

    public void ajout_sync(Article instance){
        repository.ajout_sync(instance);
    }

    public Article get(String id,boolean withCategorie,boolean withContenance)
    {
        return repository.get(id,withCategorie,withContenance) ;
    }

    public String getId()
    {
        return repository.getId() ;
    }

    public void gets()
    {
         repository.gets() ;
    }

    public void setArticle(Article article){
         repository.setArticle(article);
    }

    public ArrayList<Article> getByFournisseur_article(String fournisseurId)
    {
      try{
          ArrayList<Article>instances=new ArrayList<>();
          List<Article> temp= repository.getByFournisseurId(fournisseurId) ;
          for(Article item:temp){
                instances.add(get(item.getId(),true,true));
          }
          return instances;
      }
      catch (Exception e){

      }
      return null;
    }

    public int count( ) {
        return repository.count () ;
    }

    public List<Article> getArticlesArrayListe()
    {
        return repository.getArticlesArrayListe() ;
    }

    public List<Article> get_all()
    {
        return repository.get_all() ;
    }

    public List<Article> getArticlesByDesignation(String designation) {
        return repository.getArticlesByDesignation(designation) ;
    }

    public String getDesignation(){
        return repository.getDesignation() ;
    }

    public void delete_all(){
        repository.delete_all() ;
    }

    public String getContenance(){
        return repository.getContenance() ;
    }

    public String getDevise(){
        return repository.getDevise();
    }

    public String getCategorie_id(){
        return repository.getCategorie_id() ;
    }

    public Double getPrix(){
        return repository.getPrix();
    }

    public Article getArticle()
    {
        return repository.getArticle() ;
    }

    public void getLoading2(String position,List<Article> obs) {
        List<Article> temp= repository.getArticleLoading2(position) ;
           for (Article instance:temp){
            obs.add(get(instance.getId(),true,true));
        }
    }

    public void getLoading(List<Article> obs) {
        List<Article> temp= repository.getArticleLoading() ;
        for (Article instance:temp){
            obs.add(get(instance.getId(),true,true));
        }
    }

}
