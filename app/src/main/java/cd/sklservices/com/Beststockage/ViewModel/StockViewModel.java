package cd.sklservices.com.Beststockage.ViewModel;

import android.app.Application;
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

public class StockViewModel extends AndroidViewModel {

    private StockRepository repository ;

    public StockViewModel(@NonNull Application application) {
        super(application);
        repository = new StockRepository(application) ;
    }


    public Boolean test_if_operation_attente(String Id_agence, String Id_article)
    {
        return repository.test_if_operation_attente(Id_agence, Id_article) ;
    }

    public int Qte(String article_id)
    {
        return repository.Qte(MainActivity.getCurrentUser().getAgence_id(), article_id) ;
    }

}
