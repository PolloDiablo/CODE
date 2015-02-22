package koders.country.cross.code;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.BaseAdapter;
import java.util.List;
import java.util.ArrayList;
import koders.country.cross.code.dataapi.ConcreteDataProvider;
import koders.country.cross.code.dataapi.DataProvider;
import koders.country.cross.code.dataapi.InterestState;
import koders.country.cross.code.dataapi.datatypes.Interest;
import koders.country.cross.code.dataapi.datatypes.Occupation;

import static koders.country.cross.code.R.id.checkedTextView;

public class MainActivity extends ActionBarActivity {

    protected static final String TAG = "occupation_name";

    private List<Occupation> theOccupations;

    private ListView interestsLV;
    private ListView occupationsLV;
    private InterestAdapter interestsArrAd;

    private CustomAdapter occupationsArrAd;
    private List<InterestState> interestsArrCmb = new ArrayList<InterestState>();

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
        loadInterestsInfo();
        interestsArrAd = new InterestAdapter(this, android.R.layout.simple_list_item_checked, interestsArrCmb );
        interestsLV.setAdapter(interestsArrAd);


        interestsLV.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        interestsLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // value toggles as expected
                boolean val = interestsLV.isItemChecked(position);
                // this is not needed because the state is maintained (at least for now)
                // although there are comments about this flaking out of the list row scrolls out of view ... that is just dumb
                // interestsLV.setItemChecked( position, !interestsLV.isItemChecked(position));
                CheckedTextView lukesText = (CheckedTextView) view.findViewById(checkedTextView);

                setInterestSelected( lukesText.getText().toString(), val );
                lukesText.setChecked( val );

                long selectedItems[] = interestsLV.getCheckedItemIds();
                List<Interest> selectedList = new ArrayList<>();
                for( int zot=0 ; (zot < selectedItems.length) ; zot++ ) {
                    selectedList.add( Interest.valueOf(((InterestState)interestsArrCmb.get(zot)).getName() ));
                }
                updateTheOccupationsStringArray( selectedList );
            }
        });

        occupationsLV = (ListView)findViewById(R.id.OccupationsListView );
        occupationsArrStr = new ArrayList<>();
        for ( Occupation iterOccup : puff.getAllOccupations( null ) ) {
            occupationsArrStr.add(iterOccup.getDisplayName());
        }
        occupationsArrAd = new CustomAdapter(this, android.R.layout.simple_selectable_list_item, occupationsArrStr );

        occupationsLV.setAdapter( occupationsArrAd );

        occupationsLV.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        // String occupation = String.valueOf(parent.getItemAtPosition(position));
                        // Toast.makeText(MainActivity.this, occupation, Toast.LENGTH_SHORT).show();
                        String theJobName = occupationsArrAd.getItem(position);
                        // need to use this to get at our Job information ...
                        submitJobSelections(view, theJobName);
                    }
                }
        );

    }

    private void loadInterestsInfo() {
        for (Interest intR : Interest.values()) {
            interestsArrCmb.add(new InterestState( intR.toString()));
        }
    }
    private void setInterestSelected( String interestStr, boolean valToSet ) {
        for (int incr = 0; incr < interestsArrCmb.size(); incr++) {
            if (((InterestState) interestsArrCmb.get(incr)).getName() == interestStr) {
                ((InterestState) interestsArrCmb.get(incr)).setSelected(valToSet);
                break;
            }
        }
    }

    private void updateTheOccupationsStringArray(List<Interest> inInterests ) {
        DataProvider puff = ConcreteDataProvider.getTheInstance();
        occupationsArrStr.clear();

        for ( Occupation iterOccup : puff.getAllOccupations( inInterests ) ) {
            occupationsArrStr.add(iterOccup.getDisplayName());
        }

        ((BaseAdapter) occupationsLV.getAdapter()).notifyDataSetChanged();
    }

    //This will submit the GPS Data to the Jobs Selection Activity
    public void submitJobSelections(View view, String theJobName ) {

        //Create intent for Jobs Selection Activity
        Intent intent = new Intent(this, JobsSelection.class);

        //Add Data to Activity intent
        intent.putExtra(TAG, theJobName);

        //Initiate Transfer
        startActivity(intent);
    }


}
