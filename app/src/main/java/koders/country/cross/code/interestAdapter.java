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
import koders.country.cross.code.dataapi.ConcreteDataProvider;
import koders.country.cross.code.dataapi.DataProvider;

import static koders.country.cross.code.R.id.checkedTextView;

class interestAdapter extends ArrayAdapter<String> {

    int list;

    interestAdapter(Context context, int item, ArrayList<String> occupations) {
        super(context, R.layout.interest_row, occupations);
        list = item;
        //picsSt = pics;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {



        LayoutInflater lukesInflater = LayoutInflater.from(getContext());
        View customView = lukesInflater.inflate(R.layout.interest_row, parent, false);


       //  CheckBox chk = (CheckBox) customView.fin// findViewById(R.id.checkedTextView);

        String singleOccupation = getItem(position);

        CheckedTextView lukesText = (CheckedTextView) customView.findViewById(checkedTextView);

        lukesText.setText(singleOccupation);
        lukesText.setTextColor(Color.DKGRAY);

        return customView;
    }
}