/*
 * Created by Maciej Bigos & Jan Stawiński & Michalina Olczyk
 * Copyright (c) 2021. All rights reserved
 * Last modified 01.01.21 17:38
 */

package com.example.poszukiwaczeskarbw.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.poszukiwaczeskarbw.R;
import com.example.poszukiwaczeskarbw.logika.Baza;
import com.example.poszukiwaczeskarbw.logika.Mapa;

import java.util.ArrayList;
import java.util.HashMap;

public class ListaSkarbow extends AppCompatActivity {

    ArrayList<Mapa> mapas;
    Baza b = Baza.getBaza();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_skarbow);
        mapas = b.pobierzWszytskieMapyZBazyDanych();
        ListView listaMap = findViewById(R.id.id45);
//        //ListView listaMap2 = findViewById(R.id.id45);
//        StableArrayAdapter adapter = new StableArrayAdapter(this,R.layout.maplistitem,R.id.firstLine, mapas);
//        //StableArrayAdapter adapter2 = new StableArrayAdapter(this,R.layout.maplistitem,R.id.secondLine, mapas);
//        listaMap.setAdapter(adapter);
//        //listaMap2.setAdapter(adapter2);
//        listaMap.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Mapa mapa = (Mapa) parent.getItemAtPosition(position);
//                zaladujMape(mapa);
//            }
//        });
        CustomStuDataAdapter adapter = new CustomStuDataAdapter(this,mapas);
        listaMap.setAdapter(adapter);
        listaMap.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Mapa mapa = (Mapa) parent.getItemAtPosition(position);
                zaladujMape(mapa);
            }
        });
    }

    private void zaladujMape(Mapa mapa){
        Intent szukajSkarbu = new Intent(getApplicationContext(), SzukajSkarb.class);
        szukajSkarbu.putExtra("id_mapy",mapa.get_ID());
        startActivity(szukajSkarbu);
    }

    private class StableArrayAdapter extends ArrayAdapter<Mapa> {

        HashMap<Mapa, Integer> mIdMap = new HashMap<Mapa, Integer>();

        public StableArrayAdapter(Context context, int layoutResourceId,int textViewResourceId,
                                  ArrayList<Mapa> objects) {
            super(context, layoutResourceId,textViewResourceId, objects);
            for (int i = 0; i < objects.size(); ++i) {
                mIdMap.put(objects.get(i), objects.get(i).get_ID());
            }
        }

        @Override
        public long getItemId(int position) {
            Mapa item = getItem(position);
            return mIdMap.get(item);
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

    }

    public class CustomStuDataAdapter extends ArrayAdapter<Mapa> {

        Context _context;
        private ArrayList<Mapa> objects;
        LayoutInflater inflater;

        public CustomStuDataAdapter (Context context,
                                     ArrayList<Mapa> objects) {
            super(context,0, objects);
            this.objects = objects;
            _context = context;
            inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        class ViewHolder{
            TextView Roll_No;
            TextView Stu_Name;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            int viewType = this.getItemViewType(position);
            Mapa item = objects.get(position);
            //  convertView = null;
            View rootView = convertView;
            final ViewHolder viewHolder;
            if (rootView == null) {
                viewHolder= new ViewHolder();
                rootView = inflater.inflate(R.layout.maplistitem,
                        parent, false);

                viewHolder.Roll_No = (TextView) rootView .findViewById(R.id.rollnoText);
                viewHolder.Stu_Name = (TextView)rootView .findViewById(R.id.stunameText);

                rootView.setTag(viewHolder);

            }
            else {
                viewHolder= (ViewHolder) rootView.getTag();
            }



            viewHolder.Roll_No.setText(item.getNazwa());
            String desc = item.getImieAutora() + " " + item.getNazwiskoAutora();
            viewHolder.Stu_Name.setText(desc);

            return rootView;
        }
    }


}