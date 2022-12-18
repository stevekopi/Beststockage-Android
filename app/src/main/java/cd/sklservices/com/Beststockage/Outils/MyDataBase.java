package cd.sklservices.com.Beststockage.Outils;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import android.util.Log;

import cd.sklservices.com.Beststockage.Classes.*;
import cd.sklservices.com.Beststockage.Dao.*;


/**
 * Created by SKL on 30/01/2021.
 */

@Database(entities = {Controle.class,Approvisionnement.class,Bon.class,
   Bonlivraison.class,  Commande.class,
   LigneBonlivraison.class, LigneCommande.class, Livraison.class, TableKeyIncrementor.class, Operation.class,OperationFinance.class,
   TableInfo.class, Depense.class,Devise.class, Vehicule.class, Delegue.class , Driver.class,Convoyeur.class,Article.class,Contenance.class,Categorie.class
        , Fournisseur.class,Client.class,User.class,UserRole.class,  Agence.class,Human.class,Identity.class,Address.class, Street.class,
        Quarter.class , Township.class , District.class, Town.class}, version = 1)

public abstract class MyDataBase extends RoomDatabase {

    private static  MyDataBase instance;



    public abstract DaoControle daoControle() ;
    public abstract DaoApprovisionnement daoApprovisionnement() ;
    public abstract DaoArticle daoArticle() ;
    public abstract DaoCategorie daoCategorie() ;
    public abstract DaoContenance daoContenance() ;
    public abstract DaoBon daoBon() ;
    public abstract DaoBonLivraison daoBonlivraison() ;
    public abstract DaoCommande daoCommande() ;
    public abstract DaoLigneCommande daoLignecommande() ;
    public abstract DaoLigneBonLivraison daoLignebonlivraison() ;
    public abstract DaoFournisseur daoFournisseur() ;
    public abstract DaoLivraison daoLivraison() ;
    public abstract DaoOperation daoOperation() ;
    public abstract DaoOperationFinance daoOperationFinance() ;


    public abstract DaoRapportCaisseParDate daoRapportCaisseParDate() ;
    public abstract DaoTableInfo daoTableInfo() ;

    public abstract DaoDepense daoDepense() ;
    public abstract DaoClient daoClient() ;
    public abstract DaoTableKeyIncrementor daoTableKeyIncrementor() ;
    public abstract DaoVehicule daoVehicule() ;
    public abstract DaoDelegue daoDelegue() ;
    public abstract DaoDriver daoDriver() ;
    public abstract DaoConvoyeur daoConvoyeur() ;
    public abstract DaoUser daoUser() ;
    public abstract DaoDevise daoDevise() ;
    public abstract DaoUserRole daoUserRole() ;
    public abstract DaoAgence daoAgence() ;
    public abstract DaoHuman daoHuman() ;
    public abstract DaoIdentity daoIdentity() ;
    public abstract DaoAdresse daoAdresse() ;
    public abstract DaoStreet daoStreet() ;
    public abstract DaoQuarter daoQuarter() ;
    public abstract DaoTownship daoTownship() ;
    public abstract DaoDistrict daoDistrict() ;
    public abstract DaoTown daoTown() ;

    public static  MyDataBase getInstance(Context context)
    {
        try
        {
            if(instance == null)
            {
                instance = Room.databaseBuilder(context.getApplicationContext(),
                           MyDataBase.class, "bestStockage_test.db")
                           .allowMainThreadQueries()
                           .fallbackToDestructiveMigration()
                           .build() ;
            }

        }
        catch (Exception ex)
        {
            Log.d("Assert"," Exception Data Base Romm " + ex.toString());
        }

        return instance ;
    }

    private static RoomDatabase.Callback roomcallback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

        }
    } ;

}
