package com.example.vertretungsplan;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Base64;
import android.widget.ListView;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class Parser extends AsyncTask<Void, Void, ArrayList[]> {
    private TextView tv;
    private TextView tv2;
    private Context context;
    private ListView lv;
    private int position;
    private SharedPreferences speicher;
    private String gesucht;

    public Parser(Context pContext, TextView textView, TextView textView2, ListView listView, int pos)
    {
        super();
        context = pContext;
        tv = textView;
        tv2 = textView2;
        lv = listView;
        position = pos;
        speicher = context.getSharedPreferences("Daten", 0);
        gesucht = speicher.getString("Daten1", "");
    }
    @Override
    protected ArrayList[] doInBackground(Void... voids) {
        String url = "https://vertretungsplan.grillo-gymnasium.de";
        /*
        *vgl.: https://stackoverflow.com/questions/7679916/jsoup-connection-with-basic-access-authentication
        *Zitat beginnt hier.
         */
        String username = "schule";
        String password = "gymnasium";
        String login = username + ":" + password;
        String base64login = new String(Base64.encode(login.getBytes(), Base64.DEFAULT));
        try {
            Document document = Jsoup.connect(url).header("Authorization", "Basic " + base64login).timeout(0).get();
            Elements url2 = document.select("frame[title='Grillo-Gymnasium Gelsenkirchen - Vertretungsplan']");
            Document doc = Jsoup.connect(url + "/" + url2.get(1).attr("src")).header("Authorization", "Basic " + base64login).get();
        /*
        *Zitat endet hier.
         */

            Elements datum = doc.select("b");
            String str = datum.get(position).text();
            ArrayList<String> arr = new ArrayList<String>();
            arr.add(str);

            Element tabellen = doc.select("table").get(position);
            Elements zeile = tabellen.select("tr");

            ArrayList<Elements> inhalt = new ArrayList<Elements>();
            for(int i=0; i<zeile.size(); i++)
            {
                Element row = zeile.get(i);
                Elements tds = row.getElementsByTag("td");
                inhalt.add(tds);
            }

            if(inhalt.get(0).isEmpty())
            {
                inhalt.remove(0);
            }

            ArrayList<ArrayList<String>> vert = new ArrayList<ArrayList<String>>();
            boolean nichtleer = true;
            boolean istDabei = false;
            if(gesucht.isEmpty())
            {
                istDabei = true;
            }
            int fehler = 1;
            for(int i=0; i<inhalt.size(); i++) {
                nichtleer = true;
                ArrayList<String> temp = new ArrayList<String>();
                for (int j = 0; j < inhalt.get(i).size(); j++) {
                    Elements rows = inhalt.get(i);
                    Element row = rows.get(j);
                    String string = row.text();
                    Element proofRow = rows.get(0);
                    String proofString = proofRow.text();
                    boolean proof = proofString.isEmpty();
                    if (proof) {
                        nichtleer = false;
                    }
                    if(!gesucht.isEmpty()) {
                        if (proofString.contains(gesucht))
                        {
                            istDabei = true;
                        }
                    }
                    temp.add(string);

                }
                if (!gesucht.isEmpty() && istDabei) {
                    if ((temp.get(0).contains(gesucht))) {
                        vert.add(temp);
                    }else if(!nichtleer) {
                            ArrayList<String>  arrTemp= vert.get(vert.size() - fehler);
                            String strTemp = arrTemp.get(7);
                            String neu = strTemp + " "+ temp.get(7);
                            arrTemp.set(7, neu);
                            vert.set(vert.size() - fehler, arrTemp);
                    }
                }else if(istDabei){
                    if(nichtleer)
                    {
                        vert.add(temp);
                    }else{
                        ArrayList<String>  arrTemp= vert.get(vert.size() - fehler);
                        String strTemp = arrTemp.get(7);
                        String neu = strTemp + " "+ temp.get(7);
                        arrTemp.set(7, neu);
                        vert.set(vert.size() - fehler, arrTemp);
                    }
                }else {
                    if(temp.get(0).equals("Vertretungen sind nicht freigegeben"))
                    {
                    vert.add(temp);
                    }
                }
            }
            if(vert.isEmpty())
            {
                ArrayList<String> array = new ArrayList<String>();
                array.add("Keine Vertretungen");
                vert.add(array);
            }
            ArrayList[] result = new ArrayList[2];
            result[0] = arr;
            result[1] = vert;

            return result;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(ArrayList[] s) {
        super.onPostExecute(s);
        tv.setText((String)s[0].get(0));
        ArrayList<ArrayList<String>> arr = s[1];
        ArrayList<String> arrString = arr.get(0);
        String str = arrString.get(0);
        if(str.equals("Vertretungen sind nicht freigegeben") || str.equals("Keine Vertretungen"))
        {
            tv2.setText(str);
        }else{
           CustomAdapter adapter = new CustomAdapter(context, s[1]);
           lv.setAdapter(adapter);
        }

    }

}