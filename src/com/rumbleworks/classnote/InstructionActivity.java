package com.rumbleworks.classnote;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

/**
 * Created by Oliver on 3/2/14.
 */
public class InstructionActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruction);
    }

    public void closeInstructions( View view ) {

        finish();

    }
}


