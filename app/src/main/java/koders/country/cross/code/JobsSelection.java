package koders.country.cross.code;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationServices;

import java.util.List;
import java.util.StringTokenizer;

import koders.country.cross.code.dataapi.ConcreteDataProvider;
import koders.country.cross.code.dataapi.DataProvider;
import koders.country.cross.code.dataapi.datatypes.InfoLink;
import koders.country.cross.code.dataapi.datatypes.Occupation;


public class JobsSelection extends ActionBarActivity implements ConnectionCallbacks, OnConnectionFailedListener {

    protected static final String TAG = "job-activity";

    protected static final String ADDRESS_REQUESTED_KEY = "address-request-pending";
    protected static final String LOCATION_ADDRESS_KEY = "location-address";


    /**
     * Provides the entry point to Google Play services.
     */
    protected GoogleApiClient mGoogleApiClient;

    /**
     * Represents a geographical location.
     */
    protected Location mLastLocation;

    /**
     * Tracks whether the user has requested an address. Becomes true when the user requests an
     * address and false when the address (or an error message) is delivered. The user requests an
     * address by pressing the Fetch Address button. This may happen before GoogleApiClient
     * connects. This activity uses this boolean to keep track of the user's intent. If the value is
     * true, the activity tries to fetch the address as soon as GoogleApiClient connects.
     */
    protected boolean mAddressRequested;

    /**
     * The formatted location address.
     */
    protected String mAddressOutput;

    /**
     * Receiver registered with this activity to get the response from FetchAddressIntentService.
     */
    private AddressResultReceiver mResultReceiver;

    /**
     * Displays the location address.
     */
    protected AutoCompleteTextView actv;

    /**
     * Visible while the address is being fetched.
     */
    ProgressBar mProgressBar;

    /**
     * Kicks off the request to fetch an address when pressed.
     */
    Button mFetchAddressButton;


    //Location Data
    protected String province = "Ontario";
    protected String city = "Kingston";

    //TextView object to display Occupation Details
    protected TextView od;

    //Occupation Object
    protected Occupation current;

    //Objects to update and do things with
    private ImageView imageView;
    private ListView lv;
    private List<InfoLink> ail;
    private InfoLinkAdapter ila;
    private String[] provinces;
    private TypedArray img;
    


    private DataProvider dataLink = ConcreteDataProvider.getTheInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Intent intent = getIntent();
        String occupationname = intent.getStringExtra(MainActivity.TAG);
        setTitle(occupationname);


        setContentView(R.layout.activity_jobs_selection);
        current = dataLink.getOccupationByDisplayName(occupationname);

        ColorDrawable colorDrawable;
        switch (current.getOutlook() ) {
            case Surplus:  // Greenish
                colorDrawable  = new ColorDrawable( Color.rgb(10, 200, 10) );
                break;
            case Balance:  // yellowish
                colorDrawable  = new ColorDrawable( Color.rgb(190,195,42));
                break;
            case Shortage:  // Redish
                colorDrawable  = new ColorDrawable( Color.rgb(200,10,10));
                break;
            default:
                colorDrawable  = new ColorDrawable( Color.DKGRAY);
                break;
        }
        getSupportActionBar().setBackgroundDrawable(colorDrawable);

        //Display job Blurb information
        od = (TextView) findViewById(R.id.textView5);
        od.setText(current.getBlurb());

        lv = (ListView)findViewById(R.id.jobDetailsView );

        updateLocation("Kingston, Ontario");
        /* REPLACED WITH METHOD Call above
        //Get InfoLinks for location
        ail = dataLink.getInfoLinks(current, province, city);


        lv = (ListView)findViewById(R.id.jobDetailsView );

        ila = new InfoLinkAdapter(getApplicationContext(), android.R.layout.simple_selectable_list_item, ail );
        //Set Display
        lv.setAdapter( ila );
        */


        lv.setOnItemClickListener(
            new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    String theJobName = ila.getItem(position).getUrl();
                    goToUrl(theJobName);
                }
            }
        );

        actv = (AutoCompleteTextView) findViewById(R.id.gpsAutoCompTextView);

        //Get City, Province auto-complete strings
        String[] countries = getResources().getStringArray(R.array.list_of_cities);
        ArrayAdapter adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,countries);

        actv.setAdapter(adapter);
        ail = dataLink.getInfoLinks(current, province, city);

        ila = new InfoLinkAdapter(getApplicationContext(), android.R.layout.simple_selectable_list_item, ail );
        //Set Display
        lv.setAdapter( ila );

        actv.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View arg1, int pos, long id) {

                        String item = String.valueOf(parent.getItemAtPosition(pos));
                        if(item != null){
                            //Add call to regenerate ListView
                            updateLocation(item);
                        }
                        //Add kill focus in AutoCompleteTextView

                        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(actv.getWindowToken(), 0);
                    }
                }
        );


        actv.setOnEditorActionListener(
                new AutoCompleteTextView.OnEditorActionListener(){
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        boolean handled = false;
                        if(actionId == EditorInfo.IME_ACTION_GO){
                            Toast.makeText(JobsSelection.this, "Enter Key Pressed", Toast.LENGTH_SHORT).show();
                            handled = true;
                            String item = actv.getText().toString();
                            //Add call to regenerate ListView
                            updateLocation(item);
                            //Add kill focus in AutoCompleteTextView
                            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(actv.getWindowToken(), 0);
                        }
                        return handled;
                    }
                }
        );

        /*
        actv.setOnKeyListener(
            new AdapterView.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if (keyCode == EditorInfo.IME_ACTION_SEARCH ||
                            keyCode == EditorInfo.IME_ACTION_DONE ||
                            event.getAction() == KeyEvent.ACTION_DOWN &&
                            event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                        if (!event.isShiftPressed()) {
                            Toast.makeText(JobsSelection.this, "Enter Key Pressed", Toast.LENGTH_SHORT).show();
                        }
                            return true;
                        }
                    return false; // pass on to other listeners.
                    }
            }
        );
        */



        mResultReceiver = new AddressResultReceiver(new Handler());
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mFetchAddressButton = (Button) findViewById(R.id.gpsButton);

        // Set defaults, then update using values stored in the Bundle.
        mAddressRequested = false;
        mAddressOutput = "";
        updateValuesFromBundle(savedInstanceState);

        updateUIWidgets();

        buildGoogleApiClient();
    }

    public void updateLocation(String location){
        //Split location string and update city and province locations
        StringTokenizer sT = new StringTokenizer(location, ", ");

            city = sT.nextToken();
            province = sT.nextToken();
        if(sT.hasMoreTokens()){
            province += " " + sT.nextToken();
        }

        //Set up image
        provinces = getApplicationContext().getResources().getStringArray(R.array.provinces);
        img = getApplicationContext().getResources().obtainTypedArray(R.array.provinces_pic);
        int i;
        for(i=0; i<3; i++){
            if(provinces[i].equals(province)){
                break;
            }
        }

        imageView = (ImageView) findViewById(R.id.flagImageView);
        imageView.setImageResource(img.getResourceId(i, -1));
        //Get InfoLinks for new location
        ail = dataLink.getInfoLinks(current, province, city);

        ila = new InfoLinkAdapter(getApplicationContext(), android.R.layout.simple_selectable_list_item, ail );
        //Set Display
        lv.setAdapter( ila );

    }

    public void goToUrl(String url) {
        if (url != null) {
            final Intent intent;
            //This site we need to redirect via our desktop webview.
            if (url.contains("report-eng.do")) {
                //Launch via our own browser. (which will cause a redirect).
                intent = new Intent(this, WebViewActivity.class);
                intent.putExtra("moose", url);
            } else {
                //Create intent for general browser launch
                intent = new Intent(Intent.ACTION_VIEW);
                //Add Data to Activity intent
                intent.setData(Uri.parse(url));
            }
            //Initiate Transfer
            startActivity(intent);
        }
    }


    /**
     * Runs when user clicks the Fetch Address button. Starts the service to fetch the address if
     * GoogleApiClient is connected.
     */

    public void fetchAddressButtonHandler(View view) {
        // We only start the service to fetch the address if GoogleApiClient is connected.
        if (mGoogleApiClient.isConnected() && mLastLocation != null) {
            startIntentService();
        }
        // If GoogleApiClient isn't connected, we process the user's request by setting
        // mAddressRequested to true. Later, when GoogleApiClient connects, we launch the service to
        // fetch the address. As far as the user is concerned, pressing the Fetch Address button
        // immediately kicks off the process of getting the address.
        mAddressRequested = true;
        updateUIWidgets();
    }

    /**
     * Updates fields based on data stored in the bundle.
     */
    private void updateValuesFromBundle(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            // Check savedInstanceState to see if the address was previously requested.
            if (savedInstanceState.keySet().contains(ADDRESS_REQUESTED_KEY)) {
                mAddressRequested = savedInstanceState.getBoolean(ADDRESS_REQUESTED_KEY);
            }
            // Check savedInstanceState to see if the location address string was previously found
            // and stored in the Bundle. If it was found, display the address string in the UI.
            if (savedInstanceState.keySet().contains(LOCATION_ADDRESS_KEY)) {
                mAddressOutput = savedInstanceState.getString(LOCATION_ADDRESS_KEY);
                displayAddressOutput();
            }
        }
    }


    /**
     * Builds a GoogleApiClient. Uses the addApi() method to request the LocationServices API.
     */
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    /**
     * Runs when a GoogleApiClient object successfully connects.
     */
    @Override
    public void onConnected(Bundle connectionHint) {
        // Gets the best and most recent location currently available, which may be null
        // in rare cases when a location is not available.
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {
            // Determine whether a Geocoder is available.
            if (!Geocoder.isPresent()) {
                Toast.makeText(this, R.string.no_geocoder_available, Toast.LENGTH_LONG).show();
                return;
            }
            // It is possible that the user presses the button to get the address before the
            // GoogleApiClient object successfully connects. In such a case, mAddressRequested
            // is set to true, but no attempt is made to fetch the address (see
            // fetchAddressButtonHandler()) . Instead, we start the intent service here if the
            // user has requested an address, since we now have a connection to GoogleApiClient.
            if (mAddressRequested) {
                startIntentService();
            }
        }
    }


    /**
     * Creates an intent, adds location data to it as an extra, and starts the intent service for
     * fetching an address.
     */
    protected void startIntentService() {
        // Create an intent for passing to the intent service responsible for fetching the address.
        Intent intent = new Intent(this, FetchAddressIntentService.class);

        // Pass the result receiver as an extra to the service.
        intent.putExtra(Constants.RECEIVER, mResultReceiver);

        // Pass the location data as an extra to the service.
        intent.putExtra(Constants.LOCATION_DATA_EXTRA, mLastLocation);

        // Start the service. If the service isn't already running, it is instantiated and started
        // (creating a process for it if needed); if it is running then it remains running. The
        // service kills itself automatically once all intents are processed.
        startService(intent);
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        // Refer to the javadoc for ConnectionResult to see what error codes might be returned in
        // onConnectionFailed.
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = " + result.getErrorCode());
    }


    @Override
    public void onConnectionSuspended(int cause) {
        // The connection to Google Play services was lost for some reason. We call connect() to
        // attempt to re-establish the connection.
        Log.i(TAG, "Connection suspended");
        mGoogleApiClient.connect();
    }

    /**
     * Updates the address in the UI.
     */
    protected void displayAddressOutput() {
        actv.setText(mAddressOutput);
    }

    /**
     * Toggles the visibility of the progress bar. Enables or disables the Fetch Address button.
     */
    private void updateUIWidgets() {
        if (mAddressRequested) {
            mProgressBar.setVisibility(ProgressBar.VISIBLE);
            mFetchAddressButton.setEnabled(false);
        } else {
            mProgressBar.setVisibility(ProgressBar.GONE);
            mFetchAddressButton.setEnabled(true);
            //Add kill focus in AutoCompleteTextView
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(actv.getWindowToken(), 0);
            actv.dismissDropDown();
        }
    }

    /**
     * Shows a toast with the given text.
     */
    protected void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save whether the address has been requested.
        savedInstanceState.putBoolean(ADDRESS_REQUESTED_KEY, mAddressRequested);

        // Save the address string.
        savedInstanceState.putString(LOCATION_ADDRESS_KEY, mAddressOutput);
        super.onSaveInstanceState(savedInstanceState);
    }

    /**
     * Receiver for data sent from FetchAddressIntentService.
     */
    class AddressResultReceiver extends ResultReceiver {
        public AddressResultReceiver(Handler handler) {
            super(handler);
        }

        /**
         * Receives data sent from FetchAddressIntentService and updates the UI in MainActivity.
         */
        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {

            // Display the address string or an error message sent from the intent service.
            mAddressOutput = resultData.getString(Constants.RESULT_DATA_KEY);
            displayAddressOutput();

            // Show a toast message if an address was found.
            if (resultCode == Constants.SUCCESS_RESULT) {
                String item = actv.getText().toString();
                //Add call to regenerate ListView
                updateLocation(item);
                showToast(getString(R.string.address_found));
            }

            // Reset. Enable the Fetch Address button and stop showing the progress bar.
            mAddressRequested = false;
            updateUIWidgets();
        }
    }

}
