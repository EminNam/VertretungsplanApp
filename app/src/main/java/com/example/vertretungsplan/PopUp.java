package com.example.vertretungsplan;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.widget.TextView;


public class PopUp extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstance)
    {
        super.onCreate(savedInstance);
        setContentView(R.layout.layout_popup);

        TextView stufe = (TextView)findViewById(R.id.jeweiligeStufe);
        TextView stunde = (TextView)findViewById(R.id.jeweiligeStunde);
        TextView fach = (TextView)findViewById(R.id.jeweiligesFach);
        TextView schülergruppe = (TextView)findViewById(R.id.jeweiligeSchülergruppe);
        TextView raum = (TextView)findViewById(R.id.jeweiligerRaum);
        TextView art  = (TextView)findViewById(R.id.jeweiligeArt);
        TextView vertreter = (TextView)findViewById(R.id.jeweiligeVertreter);
        TextView vertretungstext = (TextView)findViewById(R.id.jeweiligerVertretungstext);

        Bundle bundle = getIntent().getExtras();
        if(bundle!=null)
        {
            stufe.setText((CharSequence) bundle.get("stufe"));
            stunde.setText((CharSequence) bundle.get("stunde"));
            fach.setText((CharSequence) bundle.get("fach"));
            schülergruppe.setText((CharSequence) bundle.get("schülergruppe"));
            String raumString = (String) bundle.get("raum");
            raumString = raumString.replace("---","");
            raum.setText(raumString);
            String artString = (String)bundle.get("art");
            artString = artString.replace("Betreuung", "Betr");
            art.setText(artString);
            String vertreterString = (String)bundle.get("vertreter");
            vertreterString = vertreterString.replace("---", "");
            vertreterString = vertreterString.replace("+", "");
            vertreterString = vertreterString.replace("???", "");
            vertreter.setText(vertreterString);
            vertretungstext.setText((CharSequence) bundle.get("vertretungstext"));
        }

        /*
        *vgl.: https://stackoverflow.com/questions/36415889/how-to-efficiently-display-the-correct-pop-up-window-in-android-studio
        *Zitat beginnt hier.
         */
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager() .getDefaultDisplay().getMetrics(displayMetrics);

        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;


        getWindow().setLayout((int)(width*0.85), (int)(height*0.8));

        /*
        *Zitat endet hier.
         */
    }
}
