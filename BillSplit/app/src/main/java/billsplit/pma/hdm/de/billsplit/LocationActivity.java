package billsplit.pma.hdm.de.billsplit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class LocationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        // ImageButton cast
        ImageButton italienisch = (ImageButton)findViewById(R.id.italienischIcon);
        ImageButton burger = (ImageButton)findViewById(R.id.burgerIcon);
        ImageButton asiatisch = (ImageButton)findViewById(R.id.asiatischIcon);
        ImageButton deutsch = (ImageButton)findViewById(R.id.mexikanischIcon);
        ImageButton steakhouse = (ImageButton)findViewById(R.id.steakhouseIcon);
        ImageButton doener = (ImageButton)findViewById(R.id.doenerIcon);

        // OnClickListener for every ImageButton leads to MenuAcitivity
        italienisch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent locationToMenu = new Intent (getApplicationContext(), MenuActivity.class);
                locationToMenu.putExtra("location", "Italienisch");
                startActivity(locationToMenu);
            }
        });

        burger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent locationToMenu = new Intent (getApplicationContext(), MenuActivity.class);
                locationToMenu.putExtra("location", "Burger");
                startActivity(locationToMenu);
            }
        });

        asiatisch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent locationToMenu = new Intent (getApplicationContext(), MenuActivity.class);
                locationToMenu.putExtra("location", "Asiatisch");
                startActivity(locationToMenu);
            }
        });

        deutsch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent locationToMenu = new Intent (getApplicationContext(), MenuActivity.class);
                locationToMenu.putExtra("location", "Mexikanisch");
                startActivity(locationToMenu);
            }
        });

        steakhouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent locationToMenu = new Intent (getApplicationContext(), MenuActivity.class);
                locationToMenu.putExtra("location", "Steakhouse");
                startActivity(locationToMenu);
            }
        });

        doener.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent locationToMenu = new Intent (getApplicationContext(), MenuActivity.class);
                locationToMenu.putExtra("location", "Kebab");
                startActivity(locationToMenu);
            }
        });
    }
}
