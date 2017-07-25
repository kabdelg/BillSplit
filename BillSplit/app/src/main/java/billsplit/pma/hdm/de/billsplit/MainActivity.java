package billsplit.pma.hdm.de.billsplit;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private LinearLayout l_layout;
    private ScrollView scroll_list;

    int totalEditTexts = 4;
    int fullEditTexts;

    ArrayList<String> namesArray = new ArrayList<String>();
    EditText[] names;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        l_layout = (LinearLayout)findViewById(R.id.l_layout);
        scroll_list = (ScrollView)findViewById(R.id.scroll_list);

        // OnClickListener for ImageButton "Plus" creates new editable text field
        final ImageButton plus_button = (ImageButton)findViewById(R.id.plus_button);
            plus_button.setOnClickListener(new View.OnClickListener(){
             @Override
             public void onClick(View v) {
                 totalEditTexts++;
                         if(totalEditTexts == 10) {
                             plus_button.setVisibility(View.GONE);
                         }

                 LinearLayout l_layout = (LinearLayout) findViewById(R.id.l_layout);
                 EditText edit = new EditText(MainActivity.this);
                 LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                 edit.setLayoutParams(p);
                 String idName = "name" + totalEditTexts;

                 // ID stored for access in further Activities
                 edit.setId(getResources().getIdentifier(idName, "id", getPackageName()));
                 edit.setHint("Name " + totalEditTexts);
                 l_layout.addView(edit, (totalEditTexts-1));
                 edit.requestFocus();
                 }
            });

        final SharedPreferences namePrefs = PreferenceManager.getDefaultSharedPreferences(this);

        // OnClickListener for Button "Go" opens new Activity "LocationActivity"
        Button go_button = (Button)findViewById(R.id.go_button);
            go_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent mainToLocation = new Intent (getApplicationContext(), LocationActivity.class);

                    SharedPreferences.Editor nameEdit = namePrefs.edit();

                    // Stores items in Array
                    ArrayList<String> namesArray = new ArrayList<String>();

                    names = new EditText[totalEditTexts];

                    for (int i=1; i<=totalEditTexts; i++) {
                        String idName = "name" + i;

                        int resID = getResources().getIdentifier(idName, "id", getPackageName());
                        names[i-1] = ((EditText) findViewById(resID));

                        fullEditTexts = totalEditTexts;

                        if (!names[i-1].getText().toString().matches("")) {
                            namesArray.add(names[i-1].getText().toString());
                        }
                    }

                    // Counts number of names
                    nameEdit.putInt("nameCount", namesArray.size());

                    // Clears content
                    nameEdit.clear();

                    // Allows to forward content to "LocationActivity"
                    for (int i=0; i<namesArray.size(); i++) {
                        nameEdit.remove("name_" + i);
                        nameEdit.putString("name_" + i, namesArray.get(i));

                    }

                    // Commits SharedPreference
                    nameEdit.commit();

                    // Starts new Activity
                    startActivity(mainToLocation);
                }
            });
    }
}

