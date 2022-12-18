package cd.sklservices.com.Beststockage.OtherView;

import android.content.Context;
import android.widget.ExpandableListView;

/**
 * Created by SKL on 17/05/2019.
 */

public class SecondLevelExpandableListView extends ExpandableListView {


    public SecondLevelExpandableListView(Context context) {
        super(context);
    }

    protected void onMeasure(int withMesureSpec, int heightMeasureSpec){

        heightMeasureSpec=MeasureSpec.makeMeasureSpec(999999,MeasureSpec.AT_MOST);
        super.onMeasure(withMesureSpec,heightMeasureSpec);

    }
}
