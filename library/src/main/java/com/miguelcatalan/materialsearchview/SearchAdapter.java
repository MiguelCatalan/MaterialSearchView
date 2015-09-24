package com.miguelcatalan.materialsearchview;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Miguel Catalan Bañuls
 */
public class SearchAdapter extends BaseAdapter implements Filterable {

    private ArrayList<String> data;

    private String[] typeAheadData;

    private Drawable suggestionIcon;

    LayoutInflater inflater;

    public SearchAdapter(Context context, String[] typeAheadData) {
        inflater = LayoutInflater.from(context);
        data = new ArrayList<>();
        this.typeAheadData = typeAheadData;
    }

    public SearchAdapter(Context context, String[] typeAheadData, Drawable suggestionIcon) {
        inflater = LayoutInflater.from(context);
        data = new ArrayList<>();
        this.typeAheadData = typeAheadData;
        this.suggestionIcon = suggestionIcon;
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (!TextUtils.isEmpty(constraint)) {
                    // Retrieve the autocomplete results.
                    List<String> searchData = new ArrayList<>();

                    for (String str : typeAheadData) {
                        if (str.toLowerCase().startsWith(constraint.toString().toLowerCase())) {
                            searchData.add(str);
                        }
                    }

                    // Assign the data to the FilterResults
                    filterResults.values = searchData;
                    filterResults.count = searchData.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results.values != null) {
                    data = (ArrayList<String>) results.values;
                    notifyDataSetChanged();
                }
            }
        };
        return filter;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder mViewHolder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.suggest_item, parent, false);
            mViewHolder = new MyViewHolder(convertView);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (MyViewHolder) convertView.getTag();
        }

        String currentListData = (String) getItem(position);

        mViewHolder.textView.setText(currentListData);


        return convertView;
    }

    private class MyViewHolder {
        TextView textView;
        ImageView imageView;

        public MyViewHolder(View convertView) {
            textView = (TextView) convertView.findViewById(R.id.suggestion_text);
            if (suggestionIcon != null) {
                imageView = (ImageView) convertView.findViewById(R.id.suggestion_icon);
                imageView.setImageDrawable(suggestionIcon);
            }
        }
    }
}