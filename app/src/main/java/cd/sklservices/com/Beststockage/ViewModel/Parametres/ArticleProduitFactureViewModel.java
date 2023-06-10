package cd.sklservices.com.Beststockage.ViewModel.Parametres;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import java.util.ArrayList;
import java.util.List;

import cd.sklservices.com.Beststockage.ActivityFolder.MainActivity;
import cd.sklservices.com.Beststockage.Classes.Parametres.ArticleProduitFacture;
import cd.sklservices.com.Beststockage.Repository.Parametres.ArticleProduitFactureRepository;
import cd.sklservices.com.Beststockage.ViewModel.registres.ArticleViewModel;

/**
 * Created by Steve Kopi Loseme on 05/02/2021.
 */

public class ArticleProduitFactureViewModel extends AndroidViewModel {
    private ArticleProduitFactureRepository repository ;

    public ArticleProduitFactureViewModel(@NonNull Application application) {
        super(application);
        repository = new ArticleProduitFactureRepository(application) ;
    }

    public void ajout_async(ArticleProduitFacture instance){
            instance.setSync_pos(0);
            instance.setUpdated_date(MainActivity.getAddingDate());
            repository.ajout_sync(instance);
        }


    public ArticleProduitFacture get(String id,boolean withArticle,boolean withProduct)
    {
        ArticleProduitFacture instance = repository.get(id) ;
        if(withArticle)
            instance.setArticle(new ArticleViewModel(MainActivity.application).get(instance.getArticle_id(),true,true));

        if(withProduct)
            instance.setProduitFacture(new ProduitFactureViewModel(MainActivity.application).get(instance.getProduit_id()));
        return instance;
    }

    public String getArticleId()
    {
        return repository.getArticleId() ;
    }

    public List<ArticleProduitFacture> gets()
    {
        return repository.getArticleProduitFactureArrayListe() ;
    }

    public List<ArticleProduitFacture> loadingByProduit(String produitId,boolean withArticle,boolean withProduct) {
        List<ArticleProduitFacture> list= repository.loadingByProduit(produitId) ;
        List<ArticleProduitFacture> instances=new ArrayList<>();
        for (ArticleProduitFacture a:list){
            instances.add(get(a.getId(),withArticle,withProduct));
        }
        return instances;
    }

    public void delete_all()
    {
        repository.delete_all() ;
    }

    public int count(){
        return repository.count();
    }

    public List<ArticleProduitFacture> loading(){
        return repository.loading();
    }

    public ArticleProduitFacture[] getByProduitFactureId(String Id){
        return repository.getByProduitFactureId(Id) ;
    }
}
