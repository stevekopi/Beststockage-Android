package cd.sklservices.com.Beststockage.Interfaces;

/**
 * Created by SKL on 16/04/2019.
 */

public interface FragmentActionListener {
    String ACTION_KEY="action_key";
    int ACTION_VALUE_SELECTED=1;
    String KEY_SELECTED="KEY_SELECTED";
    void onItemSelected(String item);
}
