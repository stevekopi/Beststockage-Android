package cd.sklservices.com.Beststockage.ViewModel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import cd.sklservices.com.Beststockage.Classes.RapportCaisseParDate;
import cd.sklservices.com.Beststockage.Repository.RapportCaisseParDateRepository;

/**
 * Created by Steve Kopi Loseme on 12/02/2021.
 */

public class RapportCaisseParDateViewModel extends AndroidViewModel {

    private RapportCaisseParDateRepository repository ;

    public RapportCaisseParDateViewModel(@NonNull Application application) {
        super(application);
        repository = new RapportCaisseParDateRepository(application) ;
    }

    public RapportCaisseParDate getLast()
    {
        return repository.getLast() ;
    }

    public ArrayList<RapportCaisseParDate> getCumulByDate(){
        return repository.getCumulByDate() ;
    }

    public List<RapportCaisseParDate> getsByDate(String date)
    {
        return repository.getsByDate(date) ;
    }

}
