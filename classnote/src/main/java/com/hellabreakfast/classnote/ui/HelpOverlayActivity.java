package com.hellabreakfast.classnote.ui;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.hellabreakfast.classnote.model.Datamart;
import com.hellabreakfast.classnote.R;

/**
 * This activity shows the help overlay for the current screen in a transparent view over the screen.
 */
public class HelpOverlayActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_overlay);

        String title = getIntent().getStringExtra("title");
        if (title != null) setTitle(title);

        int currentScreen = Datamart.getInstance().getCurrentScreen();

        TextView textView1 = (TextView) findViewById(R.id.textView1);
        TextView textView2 = (TextView) findViewById(R.id.textView2);
        TextView textView3 = (TextView) findViewById(R.id.textView3);
        TextView textView4 = (TextView) findViewById(R.id.textView4);
        TextView textView5 = (TextView) findViewById(R.id.textView5);

        String[][] helpArray = {
                getResources().getStringArray(R.array.help_items_announcements),
                getResources().getStringArray(R.array.help_items_assignments),
                getResources().getStringArray(R.array.help_items_assignments),
                getResources().getStringArray(R.array.help_items_add_assignment)
        };
        textView1.setText( helpArray[currentScreen][0] );
        textView2.setText( helpArray[currentScreen][1] );
        textView3.setText( helpArray[currentScreen][2] );
        textView4.setText( helpArray[currentScreen][3] );
        textView5.setText( helpArray[currentScreen][4] );
    }

    /**
     * The user tapped to dismiss, so finish the activity.
     */
    public void understood(View view){
        finish();
    }
}