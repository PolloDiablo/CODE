package koders.country.cross.code;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.CheckedTextView;
import android.widget.ListAdapter;
import android.widget.ScrollView;
import android.widget.LinearLayout;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Toast;

import java.util.List;
import java.util.ArrayList;
import koders.country.cross.code.dataapi.ConcreteDataProvider;
import koders.country.cross.code.dataapi.DataProvider;
import koders.country.cross.code.dataapi.datatypes.Interest;
import koders.country.cross.code.dataapi.datatypes.Occupation;

public class MainActivity extends ActionBarActivity {

    private List<Occupation> theOccupations;

    private ListView interestsLV;
    private ListView occupationsLV;
    private ArrayAdapter interestsArrAd;
    private ArrayAdapter occupationsArrAd;
    private  ArrayList<String> interestsArrStr;
    private  ArrayList<String> occupationsArrStr;

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

        interestsLV = (ListView) findViewById( R.id.InterestslistView );
        interestsArrStr = new ArrayList<>();
        for (Interest intR : Interest.values()) {
            interestsArrStr.add(intR.toString());
        }
        interestsArrAd = new interestAdapter(this, android.R.layout.simple_list_item_checked, interestsArrStr );
        interestsLV.setAdapter(interestsArrAd);


        interestsLV.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        interestsLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                updateTheOccupationsStringArray( null );
            }
        });

        //interestsLV.setItemChecked(0, true);


        occupationsLV = (ListView)findViewById(R.id.OccupationsListView );
        occupationsArrStr = new ArrayList<>();
        for ( Occupation iterOccup : puff.getAllOccupations( null ) ) {
            occupationsArrStr.add(iterOccup.getDisplayName());

        }
        occupationsArrAd = new customAdapter(this, android.R.layout.simple_selectable_list_item, occupationsArrStr );

        occupationsLV.setAdapter( occupationsArrAd );


        occupationsLV.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        // String occupation = String.valueOf(parent.getItemAtPosition(position));
                        // Toast.makeText(MainActivity.this, occupation, Toast.LENGTH_SHORT).show();
                        submitJobSelections( view );
                    }
                }
        );



    }

    private void updateTheOccupationsStringArray(List<Interest> inInterests ) {
        DataProvider puff = ConcreteDataProvider.getTheInstance();
        occupationsArrStr.clear();
        // ** test
        occupationsArrStr.add("BarfADoodle");

        for ( Occupation iterOccup : puff.getAllOccupations( inInterests ) ) {
            occupationsArrStr.add(iterOccup.getDisplayName());
        }


        ((BaseAdapter) occupationsLV.getAdapter()).notifyDataSetChanged();
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
