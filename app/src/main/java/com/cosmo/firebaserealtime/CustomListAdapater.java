package com.cosmo.firebaserealtime;

import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomListAdapater extends BaseAdapter {
    private ArrayList<String> names;
    private ArrayList<String> lastNames;
    private ArrayList<String> keys;
    private AppCompatActivity activity;

    public CustomListAdapater(ArrayList<String> names,ArrayList<String> lastNames,ArrayList<String> keys,AppCompatActivity activity){
        this.names=names;
        this.lastNames=lastNames;
        this.keys=keys;
        this.activity=activity;
    }

    @Override
    public int getCount() {
        return names.size();
    }

    @Override
    public Object getItem(int i) {
        return keys.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view =  LayoutInflater.from(activity.getApplicationContext()).inflate(R.layout.people_list_row,viewGroup,false);
        ((TextView)view.findViewById(R.id.lastName)).setText(lastNames.get(i));
        ((TextView)view.findViewById(R.id.personNameTv)).setText(names.get(i));
        ((TextView)view.findViewById(R.id.key)).setText(keys.get(i));
        return view;
    }
}
