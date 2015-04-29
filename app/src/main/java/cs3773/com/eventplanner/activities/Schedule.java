package cs3773.com.eventplanner.activities;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import cs3773.com.eventplanner.R;

public class Schedule extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        Bundle bundle = getIntent().getExtras();
        String Time = bundle.getString("Time");
        String Description = bundle.getString("Description");
        String Time2 = bundle.getString("Time2");
        String Description2 = bundle.getString("Description2");
        String Time3 = bundle.getString("Time3");
        String Description3 = bundle.getString("Description3");
        String Time4 = bundle.getString("Time4");
        String Description4 = bundle.getString("Description4");
        String Time5 = bundle.getString("Time5");
        String Description5 = bundle.getString("Description5");
        String Time6 = bundle.getString("Time6");
        String Description6 = bundle.getString("Description6");
        TextView txtView = (TextView) findViewById(R.id.TimeTV);
        txtView.setText(Time);
        TextView txtView2 = (TextView) findViewById(R.id.DescriptionTV);
        txtView2.setText(Description);

        TextView txtView3 = (TextView) findViewById(R.id.TimeTV2);
        txtView3.setText(Time2);
        TextView txtView4 = (TextView) findViewById(R.id.DescriptionTV2);
        txtView4.setText(Description2);

        TextView txtView5 = (TextView) findViewById(R.id.TimeTV3);
        txtView5.setText(Time3);
        TextView txtView6 = (TextView) findViewById(R.id.DescriptionTV3);
        txtView6.setText(Description3);

        TextView txtView7 = (TextView) findViewById(R.id.TimeTV4);
        txtView7.setText(Time4);
        TextView txtView8 = (TextView) findViewById(R.id.DescriptionTV4);
        txtView8.setText(Description4);

        TextView txtView9 = (TextView) findViewById(R.id.TimeTV5);
        txtView9.setText(Time5);
        TextView txtView10 = (TextView) findViewById(R.id.DescriptionTV5);
        txtView10.setText(Description5);

        TextView txtView11 = (TextView) findViewById(R.id.TimeTV6);
        txtView11.setText(Time6);
        TextView txtView12 = (TextView) findViewById(R.id.DescriptionTV6);
        txtView12.setText(Description6);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_schedule, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
