package billsplit.pma.hdm.de.billsplit;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.os.Handler;
import android.widget.TextView;

public class SplashScreen extends Activity {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        StartAnimations();
        // After timeout starts WelcomeActivity
        new Handler().postDelayed(new Runnable() {

        /*
         * Showing splash screen with a timer. This will be useful when you
         * want to show case your app logo / company
         */

            @Override
            public void run() {
                Intent i;
                i = new Intent(SplashScreen.this, WelcomeActivity.class);
                startActivity(i);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }

    private void StartAnimations() {
        Animation a1 = AnimationUtils.loadAnimation(this, R.anim.alpha);
        a1.reset();
        LinearLayout ll = (LinearLayout) findViewById(R.id.lin_lay);
        ll.clearAnimation();
        ll.startAnimation(a1);

        Animation a2 = AnimationUtils.loadAnimation(this, R.anim.translate);
        a2.reset();
        TextView tv = (TextView) findViewById(R.id.slogan);
        tv.clearAnimation();
        tv.startAnimation(a2);
    }
}