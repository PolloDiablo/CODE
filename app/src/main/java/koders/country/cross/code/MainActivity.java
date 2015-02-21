package koders.country.cross.code;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;




public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //////////////////////////////////////////////////////////////////////////
        //// For V2:
        //// Program should check here for saved location data
        //// and pass that directly to the JobsSelections app
        //////////////////////////////////////////////////////////////////////////

    }

    //This will submit the GPS Data to the Jobs Selection Activity
    public void submitJobSelections(View view) {

        //Create intent for Jobs Selection Activity
        Intent intent = new Intent(this, JobsSelection.class);

        //Add Data to Activity intent
        //intent.putExtra(LAT, latitude);
        //intent.putExtra(LONG, longitude);

        //Initiate Transfer
        startActivity(intent);
    }


}
