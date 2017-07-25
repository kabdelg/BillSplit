package billsplit.pma.hdm.de.billsplit;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.Toast;

public class MenuActivity extends AppCompatActivity {

    ExpandableListAdapter expListAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List <String>> listDataChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        final TextView title = (TextView)findViewById(R.id.title);

        // Get Values from MainActivity
        Bundle extras = getIntent().getExtras();
        String title1 = extras.getString("location");

        expListView = (ExpandableListView)findViewById(R.id.exp1);

        prepareListData();

        expListAdapter = new billsplit.pma.hdm.de.billsplit.ExpandableListAdapter(this, listDataHeader, listDataChild);

        expListView.setAdapter(expListAdapter);

        // Start new Activity "DragDropActivity" on Button "Speisen zuweisen"
        Button foodAssign = (Button) findViewById(R.id.foodAssign);
        foodAssign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent menuToDD = new Intent(getApplicationContext(), DragDropActivity.class);
                startActivity(menuToDD);
            }
        });

        // Stores chosen products in Array for accessibility
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        final int foodArrSize = prefs.getInt("foodArr", 0);

        // Delete chosen Products on Button "Speisen löschen"
        Button foodDelete = (Button) findViewById(R.id.foodDelete);
        foodDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences.Editor editor = prefs.edit();

                editor.remove("foodArr");
                editor.remove("counter");
                for (int i=0; i<foodArrSize; i++) {
                    editor.remove("food_" + i);
                    editor.remove("price_" + i);
                }
                editor.remove("selectedQuantity");

                editor.commit();

                Toast.makeText(getApplicationContext(), "Alle Speisen gelöscht", Toast.LENGTH_SHORT).show();

            }
        });



        // Listview Group click listener
        expListView.setOnGroupClickListener(new OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                return false;
            }
        });

        // Listview on child click listener
        expListView.setOnChildClickListener(new OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {

                Intent menuToQP = new Intent (getApplicationContext(), QuantityPriceActivity.class);
                menuToQP.putExtra("selected", listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition));
                startActivity(menuToQP);

                return false;
            }
        });



    }

    // Create ExpandableList Items
    private void prepareListData() {

        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        Bundle extras = getIntent().getExtras();
        String title1 = extras.getString("location");

        TextView title = (TextView)findViewById(R.id.title);
        title.setText(title1);

        // Each case a category displays other ExpandableList items
        switch (title1) {
            case "Italienisch":

                // List headers as Expandables
                listDataHeader.add("Vorspeisen");
                listDataHeader.add("Salate");
                listDataHeader.add("Pizza");
                listDataHeader.add("Pasta");
                listDataHeader.add("Getränke");

                // List items as list entries (not expandable)
                List<String> VorspeisenIta = new ArrayList<String>();
                VorspeisenIta.add("Carpaccio");
                VorspeisenIta.add("Bruschetta");
                VorspeisenIta.add("Antipasti-Teller");
                VorspeisenIta.add("Vitello tonnato");

                List<String> SalateIta = new ArrayList<String>();
                SalateIta.add("Ceasar Salate");
                SalateIta.add("Insalata Mista");
                SalateIta.add("Insalata di Pollo");
                SalateIta.add("Tomate-Mozzarella Salat");

                List<String> Pizza = new ArrayList<String>();
                Pizza.add("Pizza Margherita");
                Pizza.add("Pizza Schinken");
                Pizza.add("Pizza Funghi");
                Pizza.add("Pizza Salami");
                Pizza.add("Pizza Vulcano");
                Pizza.add("Pizza Diavolo");
                Pizza.add("Pizza Hawaii");

                List<String> Pasta = new ArrayList<String>();
                Pasta.add("Penne all'arrabiata");
                Pasta.add("Pasta Patro");
                Pasta.add("Pasta Verdure");
                Pasta.add("Pasta alla Tonno");
                Pasta.add("Lasagne");
                Pasta.add("Penne al Forno");

                List<String> GetränkeIta = new ArrayList<String>();
                GetränkeIta.add("Cola");
                GetränkeIta.add("Fanta");
                GetränkeIta.add("Sprite");
                GetränkeIta.add("Wasser");
                GetränkeIta.add("Sprudel");

                // Setting List headers
                listDataChild.put(listDataHeader.get(0), VorspeisenIta);
                listDataChild.put(listDataHeader.get(1), SalateIta);
                listDataChild.put(listDataHeader.get(2), Pizza);
                listDataChild.put(listDataHeader.get(3), Pasta);
                listDataChild.put(listDataHeader.get(4), GetränkeIta);

                break;

            case "Burger":

                // List headers as Expandables
                listDataHeader.add("Salate");
                listDataHeader.add("Burger");
                listDataHeader.add("Beilagen");
                listDataHeader.add("Getränke");

                // List items as list entries (not expandable)
                List<String> SalateBur = new ArrayList<String>();
                SalateBur.add("Ceasar Salate");
                SalateBur.add("Insalata Mista");
                SalateBur.add("American Salad");
                SalateBur.add("Tomate-Mozzarella Salat");

                List<String> Burger = new ArrayList<String>();
                Burger.add("Hamburger");
                Burger.add("Cheeseburger");
                Burger.add("Bacon-Cheeseburger");
                Burger.add("Veggieburger");

                List<String> Beilagen = new ArrayList<String>();
                Beilagen.add("Pommes");
                Beilagen.add("Chickenwings");
                Beilagen.add("Coleslaw");
                Beilagen.add("Onion Rings");

                List<String> GetränkeBur = new ArrayList<String>();
                GetränkeBur.add("Cola");
                GetränkeBur.add("Fanta");
                GetränkeBur.add("Sprite");
                GetränkeBur.add("Wasser");
                GetränkeBur.add("Sprudel");

                // Setting List headers
                listDataChild.put(listDataHeader.get(0), SalateBur);
                listDataChild.put(listDataHeader.get(1), Burger);
                listDataChild.put(listDataHeader.get(2), Beilagen);
                listDataChild.put(listDataHeader.get(3), GetränkeBur);

                break;

            case "Asiatisch":

                // List headers as Expandables
                listDataHeader.add("Vorspeisen");
                listDataHeader.add("Huhn");
                listDataHeader.add("Schwein");
                listDataHeader.add("Ente");
                listDataHeader.add("Rind");
                listDataHeader.add("Getränke");

                // List items as list entries (not expandable)
                List<String> VorspeisenAsia = new ArrayList<String>();
                VorspeisenAsia.add("Frühlingsrollen");
                VorspeisenAsia.add("Edame");
                VorspeisenAsia.add("Krabbenbrot");
                VorspeisenAsia.add("Wan Tan");

                List<String> Huhn = new ArrayList<String>();
                Huhn.add("Gebackenes Huhn");
                Huhn.add("Huhn Chop Suey");
                Huhn.add("Knuspriges Huhn");
                Huhn.add("Huhn Szechuan");

                List<String> Schwein = new ArrayList<String>();
                Schwein.add("Schwein Teriyaki");
                Schwein.add("Schwein Chop Suey");
                Schwein.add("Knuspriges Schwein");
                Schwein.add("Schwein Szechuan");

                List<String> Ente = new ArrayList<String>();
                Ente.add("Gebackene Ente");
                Ente.add("Ente Chop Suey");
                Ente.add("Knusprige Ente");
                Ente.add("Ba-Bao-Jah Ente");

                List<String> Rind = new ArrayList<String>();
                Rind.add("Rind Teriyaki");
                Rind.add("Rind Chop Suey");
                Rind.add("Knuspriges Rind");
                Rind.add("Rind Szechuan");

                List<String> GetränkeAsia = new ArrayList<String>();
                GetränkeAsia.add("Cola");
                GetränkeAsia.add("Fanta");
                GetränkeAsia.add("Sprite");
                GetränkeAsia.add("Wasser");
                GetränkeAsia.add("Sprudel");

                // Setting List headers
                listDataChild.put(listDataHeader.get(0), VorspeisenAsia);
                listDataChild.put(listDataHeader.get(1), Huhn);
                listDataChild.put(listDataHeader.get(2), Schwein);
                listDataChild.put(listDataHeader.get(3), Ente);
                listDataChild.put(listDataHeader.get(4), Rind);
                listDataChild.put(listDataHeader.get(5), GetränkeAsia);

                break;

            case "Mexikanisch":

                // List headers as Expandables
                listDataHeader.add("Vorspeisen");
                listDataHeader.add("Taco");
                listDataHeader.add("Getränke");

                // List items as list entries (not expandable)
                List<String> VorspeisenMex = new ArrayList<String>();
                VorspeisenMex.add("Tortilla");
                VorspeisenMex.add("Nachos mit Käse überbacken");
                VorspeisenMex.add("Ceviche");
                VorspeisenMex.add("Jitomates Rellenos");

                List<String> Taco = new ArrayList<String>();
                Taco.add("Tacos mit Gemüse");
                Taco.add("Tacos mit Huhn");
                Taco.add("Tacos mit Rind");
                Taco.add("Tacos mit Allerlei");

                List<String> GetränkeMex = new ArrayList<String>();
                GetränkeMex.add("Cola");
                GetränkeMex.add("Fanta");
                GetränkeMex.add("Sprite");
                GetränkeMex.add("Wasser");
                GetränkeMex.add("Sprudel");

                // Setting List headers
                listDataChild.put(listDataHeader.get(0), VorspeisenMex);
                listDataChild.put(listDataHeader.get(1), Taco);
                listDataChild.put(listDataHeader.get(2), GetränkeMex);

                break;

            case "Steakhouse":

                // List headers as Expandables
                listDataHeader.add("Vorspeisen");
                listDataHeader.add("Steaks");
                listDataHeader.add("Getränke");

                // List items as list entries (not expandable)
                List<String> VorspeisenSteak = new ArrayList<String>();
                VorspeisenSteak.add("Gemischter Salat");
                VorspeisenSteak.add("Ofenkartoffeln");
                VorspeisenSteak.add("Onion Rings");
                VorspeisenSteak.add("Chickenwings");

                List<String> Steaks = new ArrayList<String>();
                Steaks.add("Rump Steak");
                Steaks.add("T-Bone Steak");
                Steaks.add("Tomahawk Steak");
                Steaks.add("Rib-Eye Steak");


                List<String> GetränkeSteak = new ArrayList<String>();
                GetränkeSteak.add("Cola");
                GetränkeSteak.add("Fanta");
                GetränkeSteak.add("Sprite");
                GetränkeSteak.add("Wasser");
                GetränkeSteak.add("Sprudel");

                // Setting List headers
                listDataChild.put(listDataHeader.get(0), VorspeisenSteak);
                listDataChild.put(listDataHeader.get(1), Steaks);
                listDataChild.put(listDataHeader.get(2), GetränkeSteak);

                break;

            case "Kebab":

                //List headers as Expandables
                listDataHeader.add("Vorspeisen");
                listDataHeader.add("Kebab");
                listDataHeader.add("Getränke");

                //List items as list entries (not expandable)
                List<String> VorspeisenKebab = new ArrayList<String>();
                VorspeisenKebab.add("Salat");
                VorspeisenKebab.add("Baklava");
                VorspeisenKebab.add("Sarma");

                List<String> Kebabs = new ArrayList<String>();
                Kebabs.add("Döner");
                Kebabs.add("Yufka");
                Kebabs.add("Lahmacun");
                Kebabs.add("Iskender");


                List<String> GetränkeKebab = new ArrayList<String>();
                GetränkeKebab.add("Cola");
                GetränkeKebab.add("Fanta");
                GetränkeKebab.add("Sprite");
                GetränkeKebab.add("Uludag");
                GetränkeKebab.add("Ayran");

                // Setting List headers
                listDataChild.put(listDataHeader.get(0), VorspeisenKebab);
                listDataChild.put(listDataHeader.get(1), Kebabs);
                listDataChild.put(listDataHeader.get(2), GetränkeKebab);

                break;

        }

    }
}
