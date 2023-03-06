package cd.sklservices.com.Beststockage.Outils;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import android.util.Log;

import cd.sklservices.com.Beststockage.Classes.*;
import cd.sklservices.com.Beststockage.Classes.Controles.Controle;
import cd.sklservices.com.Beststockage.Classes.Finances.Depense;
import cd.sklservices.com.Beststockage.Classes.Finances.OperationFinance;
import cd.sklservices.com.Beststockage.Classes.Parametres.ArticleProduitFacture;
import cd.sklservices.com.Beststockage.Classes.Parametres.Devise;
import cd.sklservices.com.Beststockage.Classes.Parametres.FournisseurTaux;
import cd.sklservices.com.Beststockage.Classes.Parametres.PaymentMode;
import cd.sklservices.com.Beststockage.Classes.Parametres.ProduitFacture;
import cd.sklservices.com.Beststockage.Classes.Registres.Address;
import cd.sklservices.com.Beststockage.Classes.Registres.Agence;
import cd.sklservices.com.Beststockage.Classes.Registres.Article;
import cd.sklservices.com.Beststockage.Classes.Registres.Categorie;
import cd.sklservices.com.Beststockage.Classes.Registres.Client;
import cd.sklservices.com.Beststockage.Classes.Registres.Contenance;
import cd.sklservices.com.Beststockage.Classes.Registres.Convoyeur;
import cd.sklservices.com.Beststockage.Classes.Registres.Delegue;
import cd.sklservices.com.Beststockage.Classes.Registres.District;
import cd.sklservices.com.Beststockage.Classes.Registres.Driver;
import cd.sklservices.com.Beststockage.Classes.Registres.Fournisseur;
import cd.sklservices.com.Beststockage.Classes.Registres.Human;
import cd.sklservices.com.Beststockage.Classes.Registres.Identity;
import cd.sklservices.com.Beststockage.Classes.Registres.Quarter;
import cd.sklservices.com.Beststockage.Classes.Registres.Street;
import cd.sklservices.com.Beststockage.Classes.Registres.Town;
import cd.sklservices.com.Beststockage.Classes.Registres.Township;
import cd.sklservices.com.Beststockage.Classes.Registres.User;
import cd.sklservices.com.Beststockage.Classes.Registres.UserRole;
import cd.sklservices.com.Beststockage.Classes.Registres.Vehicule;
import cd.sklservices.com.Beststockage.Classes.Stocks.Approvisionnement;
import cd.sklservices.com.Beststockage.Classes.Stocks.Bon;
import cd.sklservices.com.Beststockage.Classes.Stocks.Bonlivraison;
import cd.sklservices.com.Beststockage.Classes.Stocks.Commande;
import cd.sklservices.com.Beststockage.Classes.Stocks.Facture;
import cd.sklservices.com.Beststockage.Classes.Stocks.LigneBonlivraison;
import cd.sklservices.com.Beststockage.Classes.Stocks.LigneCommande;
import cd.sklservices.com.Beststockage.Classes.Stocks.LigneFacture;
import cd.sklservices.com.Beststockage.Classes.Stocks.Livraison;
import cd.sklservices.com.Beststockage.Classes.Stocks.Operation;
import cd.sklservices.com.Beststockage.Dao.*;
import cd.sklservices.com.Beststockage.Dao.Controles.DaoControle;
import cd.sklservices.com.Beststockage.Dao.Finances.DaoDepense;
import cd.sklservices.com.Beststockage.Dao.Finances.DaoOperationFinance;
import cd.sklservices.com.Beststockage.Dao.Finances.DaoRapportCaisseParDate;
import cd.sklservices.com.Beststockage.Dao.Parametres.DaoArticleProduitFacture;
import cd.sklservices.com.Beststockage.Dao.Parametres.DaoDevise;
import cd.sklservices.com.Beststockage.Dao.Parametres.DaoFournisseurTaux;
import cd.sklservices.com.Beststockage.Dao.Parametres.DaoPaymentMode;
import cd.sklservices.com.Beststockage.Dao.Parametres.DaoProduitFacture;
import cd.sklservices.com.Beststockage.Dao.Stocks.Registres.DaoAdresse;
import cd.sklservices.com.Beststockage.Dao.Stocks.Registres.DaoAgence;
import cd.sklservices.com.Beststockage.Dao.Stocks.Registres.DaoArticle;
import cd.sklservices.com.Beststockage.Dao.Stocks.Registres.DaoCategorie;
import cd.sklservices.com.Beststockage.Dao.Stocks.Registres.DaoClient;
import cd.sklservices.com.Beststockage.Dao.Stocks.Registres.DaoContenance;
import cd.sklservices.com.Beststockage.Dao.Stocks.Registres.DaoConvoyeur;
import cd.sklservices.com.Beststockage.Dao.Stocks.Registres.DaoDelegue;
import cd.sklservices.com.Beststockage.Dao.Stocks.Registres.DaoDistrict;
import cd.sklservices.com.Beststockage.Dao.Stocks.Registres.DaoDriver;
import cd.sklservices.com.Beststockage.Dao.Stocks.Registres.DaoFournisseur;
import cd.sklservices.com.Beststockage.Dao.Stocks.Registres.DaoHuman;
import cd.sklservices.com.Beststockage.Dao.Stocks.Registres.DaoIdentity;
import cd.sklservices.com.Beststockage.Dao.Stocks.Registres.DaoQuarter;
import cd.sklservices.com.Beststockage.Dao.Stocks.Registres.DaoStreet;
import cd.sklservices.com.Beststockage.Dao.Stocks.Registres.DaoTown;
import cd.sklservices.com.Beststockage.Dao.Stocks.Registres.DaoTownship;
import cd.sklservices.com.Beststockage.Dao.Stocks.Registres.DaoUser;
import cd.sklservices.com.Beststockage.Dao.Stocks.Registres.DaoUserRole;
import cd.sklservices.com.Beststockage.Dao.Stocks.Registres.DaoVehicule;
import cd.sklservices.com.Beststockage.Dao.Stocks.DaoApprovisionnement;
import cd.sklservices.com.Beststockage.Dao.Stocks.DaoBon;
import cd.sklservices.com.Beststockage.Dao.Stocks.DaoBonLivraison;
import cd.sklservices.com.Beststockage.Dao.Stocks.DaoCommande;
import cd.sklservices.com.Beststockage.Dao.Stocks.DaoFacture;
import cd.sklservices.com.Beststockage.Dao.Stocks.DaoLigneBonLivraison;
import cd.sklservices.com.Beststockage.Dao.Stocks.DaoLigneCommande;
import cd.sklservices.com.Beststockage.Dao.Stocks.DaoLigneFacture;
import cd.sklservices.com.Beststockage.Dao.Stocks.DaoLivraison;
import cd.sklservices.com.Beststockage.Dao.Stocks.DaoOperation;


/**
 * Created by SKL on 30/01/2021.
 */

@Database(entities = {FournisseurTaux.class,LigneFacture.class, Facture.class, ArticleProduitFacture.class, ProduitFacture.class, PaymentMode.class,
        Controle.class, Approvisionnement.class, Bon.class,Bonlivraison.class,  Commande.class, LigneBonlivraison.class,
        LigneCommande.class, Livraison.class, TableKeyIncrementor.class, Operation.class, OperationFinance.class,
        TableInfo.class, Depense.class, Devise.class, Vehicule.class, Delegue.class , Driver.class, Convoyeur.class, Article.class,
        Contenance.class, Categorie.class, Fournisseur.class, Client.class, User.class, UserRole.class,  Agence.class, Human.class,
        Identity.class, Address.class, Street.class,Quarter.class , Township.class , District.class, Town.class}, version = 1)

public abstract class MyDataBase extends RoomDatabase {

    private static  MyDataBase instance;



    public abstract DaoFournisseurTaux daoFournisseurTaux() ;
    public abstract DaoLigneFacture daoLigneFacture() ;
    public abstract DaoFacture daoFacture() ;
    public abstract DaoArticleProduitFacture daoArticleProduitFacture() ;
    public abstract DaoProduitFacture daoProduitFacture() ;
    public abstract DaoPaymentMode daoPaymentMode() ;
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
