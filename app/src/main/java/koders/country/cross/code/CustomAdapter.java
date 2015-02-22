package koders.country.cross.code;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

import koders.country.cross.code.dataapi.datatypes.Occupation;
import koders.country.cross.code.dataapi.datatypes.Outlook;

class CustomAdapter extends ArrayAdapter<Occupation> {

    private static class ViewHolder {
        TextView occupationText;
        ImageView occupationImage;
        TypedArray img;
    }

    CustomAdapter(Context context, int item, ArrayList<Occupation> occupations) {
        super(context, R.layout.custom_row, occupations);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;

        if(convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.custom_row, parent, false);
            viewHolder.occupationText = (TextView) convertView.findViewById(R.id.arrayText);
            viewHolder.occupationImage = (ImageView) convertView.findViewById(R.id.arrayImage);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.img = convertView.getResources().obtainTypedArray(R.array.list_of_occupation_icons);
        String[] occupations = convertView.getResources().getStringArray(R.array.list_of_occupations);

        Occupation singleOccupation = getItem(position);

        /*
        TODO: Should add the icon images to each object in the Occupation Class, currently is referencing: strings.xml
         */

        int i;
        for(i=0; i<10; i++){
            if(occupations[i].equals(singleOccupation.getDisplayName())){
                break;
            }
        }

        viewHolder.occupationImage.setImageResource(viewHolder.img.getResourceId(i, -1));
        viewHolder.occupationText.setText(singleOccupation.getDisplayName());
        switch (singleOccupation.getOutlook() ) {
            case Surplus:  // Redish
                viewHolder.occupationText.setTextColor(Color.rgb(200,10,10));
                break;
            case Balance:  // yellowish
                viewHolder.occupationText.setTextColor(Color.rgb(190,195,42));
                break;
            case Shortage:  // Greenish
                viewHolder.occupationText.setTextColor(Color.rgb(10,200,10));
                break;
            default:
                viewHolder.occupationText.setTextColor(Color.DKGRAY);
                break;
        }



        return convertView;
    }
}