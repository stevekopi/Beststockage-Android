package cd.sklservices.com.Beststockage.Interfaces;

/**
 * Created by SKL on 23/04/2019.
 */

/**
 * Retour du serveur distant
 */
public interface AsyncResponse {

    void processFinish(String output) throws Exception;
}
