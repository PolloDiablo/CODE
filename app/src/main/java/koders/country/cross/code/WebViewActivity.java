package koders.country.cross.code;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;


public class WebViewActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Intent intent = getIntent();
        if(intent!=null) {
            String result = intent.getStringExtra("moose");
            WebView wv = new WebView(this);
            wv.getSettings().setUserAgentString("desktop");
            Log.e("WebViewActivity","Site: "+result);
            wv.loadUrl(result);
            setContentView(wv);
        }else {
            setContentView(R.layout.activity_web_view);
        }
    }

}
