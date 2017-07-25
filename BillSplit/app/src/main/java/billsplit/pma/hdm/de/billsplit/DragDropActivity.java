package billsplit.pma.hdm.de.billsplit;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.ArrayList;
import static java.lang.Float.parseFloat;

public class DragDropActivity extends AppCompatActivity implements View.OnDragListener{

    // ArrayLists in which the values from SharedPreferences will be added to
    ArrayList<String> choFood = new ArrayList<String>();
    ArrayList<String> choPrice = new ArrayList<String>();
    ArrayList<String> choNames = new ArrayList<String>();
    ArrayList<String> foodPrice = new ArrayList<String>();
    ArrayList<String> namePrice = new ArrayList<String>();

    private RelativeLayout fLayout;
    private LinearLayout nLayout;

    // counter to start next Activity when every Food is assigned
    int y = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_drag_drop);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = prefs.edit();

        // Button to get to OverviewActivity if coming from OverviewActivity
        Button toOverviewButton = (Button) findViewById(R.id.toOverviewButton);
        toOverviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dragDropToOverview = new Intent (getApplicationContext(), OverviewActivity.class);
                startActivity(dragDropToOverview);
            }
        });


        //SharedPreferences.Editor editor = prefs.edit();

        fLayout = (RelativeLayout) findViewById(R.id.dragDropRelativeLayout);

        int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 240, getResources().getDisplayMetrics());
        int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 70, getResources().getDisplayMetrics());

        RelativeLayout.LayoutParams paramsRel = new RelativeLayout.LayoutParams(width, height);

        //add a TextView for every selected Food in QuantityPriceActivity
        final int foodArrSize = prefs.getInt("foodArr", 0);
        for (int i=0; i<foodArrSize; i++) {
            // Remove old values
            editor.remove("whoAteWhat_" + i);
            editor.commit();

            // Add Food and Price to their ArrayLists
            choFood.add(prefs.getString("food_" + i, null));
            choPrice.add(String.valueOf(prefs.getFloat("price_" + i, 0)));

            //Put Food and Price together in one ArrayList
            foodPrice.add(choFood.get(i) + "\n" + String.format("%.2f", parseFloat(choPrice.get(i))) + "€");

            // Add TextView for every selected Food and Price
            // Setting the Layout of the TextView
            TextView dragView = new TextView(DragDropActivity.this);
            paramsRel.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);

            dragView.setText(foodPrice.get(i));
            dragView.setLayoutParams(paramsRel);
            dragView.setGravity(Gravity.CENTER);
            dragView.bringToFront();
            dragView.setPadding(20, 20, 20, 20);
            dragView.setBackground( getResources().getDrawable(R.drawable.round_drop));
            if (Build.VERSION.SDK_INT < 23) {
                dragView.setTextAppearance(getApplicationContext(), R.style.foodStyle);
            } else {
                dragView.setTextAppearance(R.style.foodStyle);
            }

            // That the Food is able to get dragged to the Consumer
            dragView.setOnTouchListener(new DragDropTouchListener());

            fLayout.addView(dragView, i);

        }



            nLayout = (LinearLayout) findViewById(R.id.nameLayout);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1f);

            params.setMargins(20, 10, 20, 20);

            // Add a TextView for every Consumer added in MainActivity
            int namesArrSize = prefs.getInt("nameCount", 0);
            for (int i=0; i<namesArrSize; i++) {

            // Add Name to ArrayList
            choNames.add(prefs.getString("name_" + i, null));
            namePrice.add(choNames.get(i) + " : " + String.format("%.2f", 0.00)  + "€");

            // Set Layout for TextView
            TextView nameView = new TextView(DragDropActivity.this);
            nameView.setLayoutParams(params);
            nameView.setText(namePrice.get(i));
            nameView.setGravity(Gravity.CENTER);
            nameView.setPadding(20, 5, 20, 5);
            nameView.setBackground( getResources().getDrawable(R.drawable.round_drop));

                if (Build.VERSION.SDK_INT < 23) {
                    nameView.setTextAppearance(getApplicationContext(), R.style.nameStyle);
                } else {
                    nameView.setTextAppearance(R.style.nameStyle);
                }

            // Change TextSize depending on how many People have eaten
                if (namesArrSize<=4) nameView.setTextSize(TypedValue.COMPLEX_UNIT_SP,40);
                if (namesArrSize<=7 && namesArrSize>=5) nameView.setTextSize(TypedValue.COMPLEX_UNIT_SP,30);
                if (namesArrSize>=8) nameView.setTextSize(TypedValue.COMPLEX_UNIT_SP,18);

            // That Food can be dropped on its Consumer
            nameView.setOnDragListener(this);
            nLayout.addView(nameView, i);
        }

    }

    @Override
    public boolean onDrag(View v, DragEvent event) {

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        final int foodArrSize = prefs.getInt("foodArr", 0);

            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:

                break;
            case DragEvent.ACTION_DRAG_ENTERED:

                // Hovereffect
                v.setBackground( getResources().getDrawable(R.drawable.round_enter));
                break;

            case DragEvent.ACTION_DRAG_EXITED:

                // Hovereffect backwards
                v.setBackground( getResources().getDrawable(R.drawable.round_drop));
                break;

            case DragEvent.ACTION_DROP:

                // Hovereffect backwards
                v.setBackground( getResources().getDrawable(R.drawable.round_drop));
                View view = (View) event.getLocalState();
                view.setVisibility(View.INVISIBLE);
                TextView dropTarget = (TextView) v;
                TextView dropped = (TextView) view;

                // Change the Text of the TextView where the Food was dropped
                String namePrice;
                String priceAll = dropTarget.getText().toString();
                String priceFood = dropped.getText().toString();

                // Get total Price of that Consumer
                String[] splitPriceAll = priceAll.split(" : ");
                priceAll = splitPriceAll[1];
                priceAll = priceAll.substring(0, priceAll.length()-1);
                priceAll = priceAll.replace(",", ".");
                Float pAll = parseFloat(priceAll);

                // Get Price of the Food
                String[] splitPriceFood = priceFood.split("\n");
                priceFood = splitPriceFood[1];
                priceFood = priceFood.substring(0, priceFood.length()-1);
                priceFood = priceFood.replace(",", ".");
                Float pFood = parseFloat(priceFood);

                // Set new total Price on Consumer
                pAll = pAll + pFood;
                namePrice = splitPriceAll[0] + " : " + String.format("%.2f", pAll) + "€";
                ((TextView) v).setText(namePrice);

                // Add the Food and Price assigned to Consumer for OverviewActivity
                String whoAteThis = splitPriceAll[0] + "---" + splitPriceFood[0] + "---" + splitPriceFood[1];
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("whoAteWhat_" + y, whoAteThis);
                editor.commit();


                y++;
                Button toOverview = (Button) findViewById(R.id.toOverviewButton);

                // If every Food is assigned automatically start OverviewActivity
                if (y==foodArrSize) {

                    Intent dragDropToOverview = new Intent (DragDropActivity.this, OverviewActivity.class);
                    startActivity(dragDropToOverview);

                    // Set Button visible, if coming back from OverviewActivity
                    toOverview.setVisibility(View.VISIBLE);
                }
                break;

            case DragEvent.ACTION_DRAG_ENDED:
                break;

            default:
                break;
        }
        return true;
    }
}
