package koders.country.cross.code;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;

import android.view.View.OnClickListener;

import android.widget.ScrollView;
import android.widget.LinearLayout;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.CheckBox;

import java.util.List;

import koders.country.cross.code.dataapi.ConcreteDataProvider;
import koders.country.cross.code.dataapi.DataProvider;
import koders.country.cross.code.dataapi.datatypes.Interest;
import koders.country.cross.code.dataapi.datatypes.Occupation;

public class MainActivity extends ActionBarActivity {

    private List<Occupation> theOccupations;

    CheckBox[] cb = new CheckBox[6];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //////////////////////////////////////////////////////////////////////////
        //// For V2:
        //// Program should check here for saved location data
        //// and pass that directly to the JobsSelections app
        //////////////////////////////////////////////////////////////////////////


        DataProvider puff = ConcreteDataProvider.getTheInstance();

        ScrollView sv = new ScrollView(this);
        final LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        sv.addView(ll);

        for (Interest intR : Interest.values()) {
            CheckBox ch = new CheckBox(this);
            ll.addView(ch);
            ch.setText(intR.toString());
        }

        for ( Occupation iterOccup : puff.getAllOccupations( null ) ) {
            TextView tv = new TextView(this);
            ll.addView(tv);
            tv.setText(iterOccup.getDisplayName());
        }

        Button b = new Button(this);
        b.setText("Take Me To Job Selections Until Wallace Fixes the Occupation Select. :)");
        ll.addView(b);

        b.setOnClickListener( new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                submitJobSelections(v);
            }
        });

        // ** Some other kinds of Dynamic widgets
/* works
        for (int i = 0; i < 6; i++) {
            cb[i] = new CheckBox(this);
            ll.addView(cb[i]);
            cb[i].setText("Dynamic Checkbox " + i);
            cb[i].setId(i + 6);

        }
*/
        /*
        TextView tv = new TextView(this);
        tv.setText("Dynamic layouts ftw!");
        ll.addView(tv);

        EditText et = new EditText(this);
        et.setText("weeeeeeeeeee~!");
        ll.addView(et);

        Button b = new Button(this);
        b.setText("I don't do anything, but I was added dynamically. :)");
        ll.addView(b);

        b.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                for (int i = 0; i < 20; i++) {
                    CheckBox ch = new CheckBox(getApplicationContext());
                    ch.setText("I'm dynamic!");

                    ll.addView(ch);
                }
            }
        });
        */

        // This commits the dynamic content to the view
        this.setContentView(sv);

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
