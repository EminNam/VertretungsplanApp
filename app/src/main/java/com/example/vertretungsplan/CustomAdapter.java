package com.example.vertretungsplan;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.view.LayoutInflater;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;


public class CustomAdapter extends BaseAdapter
{
    Context context;
    ArrayList<ArrayList<String>> vertretungen;
    int position;


    public CustomAdapter(Context pContext, ArrayList<ArrayList<String>> pVertretungen)
    {
        context = pContext;
        vertretungen = pVertretungen;
    }
    @Override
    public int getCount() {
        return vertretungen.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Viewholder holder;
            this.position = position;
            if (convertView == null) {
                holder = new Viewholder();
                convertView = gibLayoutinflater(context).inflate(R.layout.list_layout, parent, false);

                holder.stufe = (TextView) convertView.findViewById(R.id.stufe);
                holder.fach = (TextView) convertView.findViewById(R.id.fach);
                holder.art = (TextView) convertView.findViewById(R.id.art);

                convertView.setTag(holder);
            } else {
                holder = (Viewholder) convertView.getTag();
            }
            holder.stufe.setText(vertretungen.get(position).get(0));
            holder.fach.setText(vertretungen.get(position).get(1) + " " + vertretungen.get(position).get(2));
            holder.art.setText(vertretungen.get(position).get(5));

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context, PopUp.class);
                    i.putExtra("stufe", vertretungen.get(position).get(0));
                    i.putExtra("stunde", vertretungen.get(position).get(1));
                    i.putExtra("fach", vertretungen.get(position).get(2));
                    i.putExtra("schülergruppe", vertretungen.get(position).get(3));
                    i.putExtra("raum", vertretungen.get(position).get(4));
                    i.putExtra("art", vertretungen.get(position).get(5));
                    i.putExtra("vertreter", vertretungen.get(position).get(6));
                    i.putExtra("vertretungstext", vertretungen.get(position).get(7));
                    context.startActivity(i);
                }
            });

            return convertView;
    }

    private LayoutInflater gibLayoutinflater(Context context)
    {
        return LayoutInflater.from(context);
    }

    public class Viewholder
    {
        private TextView stufe;
        private TextView fach;
        private TextView art;
    }

}
