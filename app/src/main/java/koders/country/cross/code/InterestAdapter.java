package koders.country.cross.code;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

import koders.country.cross.code.dataapi.ConcreteDataProvider;
import koders.country.cross.code.dataapi.DataProvider;
import koders.country.cross.code.dataapi.InterestState;

import static koders.country.cross.code.R.id.checkedTextView;

class InterestAdapter extends ArrayAdapter<InterestState> {

    // reference to the list of Interests and their state
    private final List<InterestState> interestsArrCmb;

    InterestAdapter(Context context, int item, List<InterestState> interestList) {
        super(context, R.layout.interest_row, interestList);
        interestsArrCmb = interestList;
        //picsSt = pics;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    private static class ViewHolder{
        CheckedTextView lukesText;
    }

    static int alloccnt = 0;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if(convertView == null){
            alloccnt += 1;
            viewHolder = new ViewHolder();
            LayoutInflater lukesInflater = LayoutInflater.from(getContext());
            convertView = lukesInflater.inflate(R.layout.interest_row, parent, false);
            viewHolder.lukesText = (CheckedTextView) convertView.findViewById(checkedTextView);
            convertView.setTag(viewHolder);
            // should only need to do these for each instance
            //  CheckBox chk = (CheckBox) customView.fin// findViewById(R.id.checkedTextView);
            /*
            View lkView = (View)viewHolder.lukesText;

              so instead of this:
              catch in mainActivy so we can update the Occupations list
              will set List (text , checked pair) to be checked or not
              THEN whenever a convertView is reused we check the list
            lkView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // this be the click man!
                    CheckedTextView lukesText = (CheckedTextView)v;
                    String singleOccupation = lukesText.getText().toString();
                    boolean isItChecked = lukesText.isChecked();
                    lukesText.setChecked( !isItChecked );
                    int x = 1;
                }
            });

            */

        }else {
            viewHolder = (ViewHolder) convertView.getTag();
            // listOfOccupations.
        }



        InterestState thisItem = getItem(position);

        viewHolder.lukesText.setChecked(thisItem.isSelected());

        viewHolder.lukesText.setText(thisItem.getName());
        viewHolder.lukesText.setTextColor(Color.DKGRAY);

        return convertView;
    }
}