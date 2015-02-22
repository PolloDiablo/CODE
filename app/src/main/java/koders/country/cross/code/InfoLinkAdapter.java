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
import java.util.List;

import koders.country.cross.code.dataapi.ConcreteDataProvider;
import koders.country.cross.code.dataapi.DataProvider;
import koders.country.cross.code.dataapi.datatypes.InfoLink;

class InfoLinkAdapter extends ArrayAdapter<InfoLink> {

    private static class ViewHolder {
        TextView urlTitle;
        TextView urlText;
    }

    InfoLinkAdapter(Context context, int item, List<InfoLink> infoLinks) {
        super(context, R.layout.infolink_row, infoLinks);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //Check if existing view is being reused
        ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater lukesInflater = LayoutInflater.from(getContext());
            convertView = lukesInflater.inflate(R.layout.infolink_row, parent, false);
            viewHolder.urlTitle = (TextView) convertView.findViewById(R.id.urlTitle);
            viewHolder.urlText = (TextView) convertView.findViewById(R.id.urlText);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        //Populate the data into the view using data object

        InfoLink infoLink = getItem(position);


        viewHolder.urlTitle.setText(infoLink.getDisplayName());
        viewHolder.urlText.setText(infoLink.getUrl());

        viewHolder.urlTitle.setTextColor(Color.DKGRAY);
        viewHolder.urlText.setTextColor(Color.DKGRAY);

        return convertView;
    }
}