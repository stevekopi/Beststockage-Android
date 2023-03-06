package cd.sklservices.com.Beststockage.Adapters.Stocks;

import android.content.Context;
import android.widget.ExpandableListView;

/**
 * Created by SKL on 05/11/2019.
 */

public class LivraisonSecondeLevelExpandableListView extends ExpandableListView {
    public LivraisonSecondeLevelExpandableListView(Context context) {
        super(context);
    }

    protected void onMeasure(int widtMeasureSpec,int heightMeasureSpec){
        heightMeasureSpec=MeasureSpec.makeMeasureSpec(999999,MeasureSpec.AT_MOST);
        super.onMeasure(widtMeasureSpec,heightMeasureSpec);
    }
}
