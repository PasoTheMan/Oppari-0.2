package com.example.oppari02;

        import android.support.design.widget.NavigationView;
        import android.view.View;
        import android.widget.TextView;

        import org.w3c.dom.Text;


public class temperatureActivity extends MainActivity
        implements NavigationView.OnNavigationItemSelectedListener

{
    @Override
    protected void onStart () {
        super.onStart();

        View m = findViewById(R.id.textView);   //tämä "poistaa" etusivun tekstin/sisällön
        m.setVisibility(View.GONE);   //tämä "poistaa" etusivun tekstin/sisällön

        View t = findViewById(R.id.textTemperature);
        t.setVisibility(View.VISIBLE);
    }

}