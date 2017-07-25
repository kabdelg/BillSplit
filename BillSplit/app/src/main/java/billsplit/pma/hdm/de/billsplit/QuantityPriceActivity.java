package billsplit.pma.hdm.de.billsplit;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.util.ArrayList;


public class QuantityPriceActivity extends AppCompatActivity {

    int quant = 1;

    // ArrayList for chosen food
    ArrayList<String> selFood = new ArrayList<String>();

    // ArrayList for price set
    ArrayList<Float> selPrice = new ArrayList<Float>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quantity_price);


        // Set header
        Bundle titleExtras = getIntent().getExtras();
        String title2 = titleExtras.getString("selected");
        TextView titleQP = (TextView) findViewById(R.id.tvPrice);
        titleQP.setText(title2);

        // Set size of popup window
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int) (width*.8), (int) (height*.45));


        final TextView tvQuantity = (TextView) findViewById(R.id.quantityView);
        tvQuantity.setText(Integer.toString(quant));

        // Functions regarding plus and minus buttons
        Button plus_pr = (Button) findViewById(R.id.plus_price);
        Button minus_pr  = (Button) findViewById(R.id.minus_price);
        plus_pr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quant++;

                tvQuantity.setText(Integer.toString(quant));
            }
        });
        minus_pr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quant>0) {
                    quant--;
                    tvQuantity.setText(Integer.toString(quant));
                }
            }
        });

        Button confirmQP = (Button) findViewById(R.id.confirmQP);
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        confirmQP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText price = (EditText) findViewById(R.id.price);

                Bundle titleExtras = getIntent().getExtras();
                String title2 = titleExtras.getString("selected");

                SharedPreferences.Editor editor = prefs.edit();

                // Function is only called when price was set
                if (!price.getText().toString().matches("")) {

                    int foodArrSize = prefs.getInt("foodArr", 0);

                    // Drag out added prices and food to avoid override
                    for (int i = 0; i < foodArrSize; i++) {
                        selFood.add(prefs.getString("food_" + i, null));
                        selPrice.add(prefs.getFloat("price_" + i, 0));
                    }

                    // Add new price and food
                    for (int i = 1; i <= quant; i++) {
                        selFood.add(title2);
                        selPrice.add(Float.parseFloat(price.getText().toString()));
                    }

                    // Forward data to shared Preferences
                    editor.putInt("foodArr", selFood.size());

                    for (int i = 0; i < selFood.size(); i++) {
                        editor.putString("food_" + i, selFood.get(i));
                        editor.putFloat("price_" + i, selPrice.get(i));

                    }
                    editor.putInt("selectedQuantity", quant);
                    editor.commit();

                    finish();
                } else {

                    // Error message, if no price has been set
                    new AlertDialog.Builder(QuantityPriceActivity.this)
                            .setTitle("Kein Preis eingegeben")
                            .setMessage("Bitte legen Sie einen Preis für diese Speise fest")
                            .setNegativeButton(
                                    "Ok",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                        }
                                    }
                            ).show();
                }
            }
        });
    }
}
