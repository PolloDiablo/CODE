package koders.country.cross.code;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import koders.country.cross.code.dataapi.ConcreteDataProvider;
import koders.country.cross.code.dataapi.DataProvider;

class CustomAdapter extends ArrayAdapter<String> {

    int picsSt[] = {R.drawable.cube,R.drawable.cube,R.drawable.cube,R.drawable.cube,R.drawable.cube,R.drawable.cube,R.drawable.cube,R.drawable.cube,R.drawable.cube,R.drawable.cube,R.drawable.cube};
    int list;

    CustomAdapter(Context context, int item, ArrayList<String> occupations) {
        super(context, R.layout.custom_row, occupations);
        list = item;
        //picsSt = pics;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater lukesInflater = LayoutInflater.from(getContext());
        View customView = lukesInflater.inflate(R.layout.custom_row, parent, false);

        String singleOccupation = getItem(position);

        TextView lukesText = (TextView) customView.findViewById(R.id.arrayText);
        ImageView lukesImage = (ImageView) customView.findViewById(R.id.arrayImage);

        lukesText.setText(singleOccupation);
        lukesText.setTextColor(Color.DKGRAY);

        lukesImage.setImageResource(picsSt[position]);

        return customView;
    }
}