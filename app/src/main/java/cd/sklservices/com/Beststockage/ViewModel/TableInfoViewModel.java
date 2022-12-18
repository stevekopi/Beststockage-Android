package cd.sklservices.com.Beststockage.ViewModel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.annotation.NonNull;

import java.util.List;

import cd.sklservices.com.Beststockage.Classes.TableInfo;
import cd.sklservices.com.Beststockage.Repository.TableInfoRepository;

/**
 * Created by Steve Kopi Loseme on 05/02/2021.
 */

public class TableInfoViewModel extends AndroidViewModel {

    private TableInfoRepository repository ;

    public TableInfoViewModel(@NonNull Application application) {
        super(application);
        repository = new TableInfoRepository(application) ;
    }

    public void initialisation(){
        repository.intitialisation();
    }

    public List<TableInfo> getByTableName_TableInfo(String table_name){
        return repository.getByTableName_TableInfo2(table_name) ;
    }

    }
