package lue.attendance.com.helsebibloteket;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Fujitsu on 04-04-2018.
 */

public class JavaScriptInterface {
    Context mContext;

    JavaScriptInterface(Context c) {
        mContext = c;
    }

    public void showToast(String toast) {
        Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
    }
}
