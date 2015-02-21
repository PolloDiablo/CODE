package koders.country.cross.code;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.TextView;


public class JobsSelection extends ActionBarActivity {

    protected TextView mLatitudeText;
    protected TextView mLongitudeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jobs_selection);

        Intent intent = getIntent();
        String latitude = intent.getStringExtra(MainActivity.LAT);
        String longitude = intent.getStringExtra(MainActivity.LONG);

        mLatitudeText = (TextView) findViewById((R.id.textView3));
        mLongitudeText = (TextView) findViewById((R.id.textView4));

        mLatitudeText.setText(latitude);
        mLongitudeText.setText(longitude);


    }

}
