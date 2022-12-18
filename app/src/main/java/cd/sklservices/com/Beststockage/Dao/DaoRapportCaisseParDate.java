package cd.sklservices.com.Beststockage.Dao;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

import cd.sklservices.com.Beststockage.Classes.RapportCaisseParDate;

/**
 * Created by Steve Kopi Loseme on 11/02/2021.
 */

@Dao
public interface DaoRapportCaisseParDate {

    @Query("select op.date,sum(op.montant) as montant, (sum(op.montant) - sum(ifnull(dep.montant,0))) as reste," +
            " sum(ifnull(dep.montant,0)) as depense, dep.agence_id as agence_id from operation as op," +
            " depense as dep where op.sync_pos!='3' and op.sync_pos!='4' and  (op.agence_1_id=dep.agence_id or op.agence_2_id=dep.agence_id) " +
            " group by op.date order by op.date desc")
    public List<RapportCaisseParDate> select_rapport_caisse1() ;


    @Query("select op.date,sum(op.montant) as montant, (sum(op.montant) - sum(ifnull(dep.montant,0))) as reste," +
            " sum(ifnull(dep.montant,0)) as depense, dep.agence_id as agence_id from operation as op," +
            " depense as dep where op.sync_pos!='3' and op.sync_pos!='4' and op.date= :Date and (op.agence_1_id=dep.agence_id) " +
            " group by op.agence_1_id order by op.date desc")
    public List<RapportCaisseParDate> select_rapport_caisse2(String Date) ;




}
