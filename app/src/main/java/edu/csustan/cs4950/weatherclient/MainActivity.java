package edu.csustan.cs4950.weatherclient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements Configuration{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void clickAll(View v) {
        TextView lblMsg = findViewById(R.id.lblMsg);

        Task task = new Task(lblMsg);
        task.execute(BUTTON_ALL, "", "");
    }

    public void clickCity(View v) {
        TextView lblMsg = findViewById(R.id.lblMsg);
        EditText inputZip = findViewById(R.id.inputZip);
        String zip = inputZip.getText().toString();

        Task task = new Task(lblMsg);
        task.execute(BUTTON_CITY, zip, "");
    }

    public void clickWarmerThan(View v) {
        TextView lblMsg = findViewById(R.id.lblMsg);
        EditText inputDegree = findViewById(R.id.inputDegree);
        String degreeStr = inputDegree.getText().toString();

        Task task = new Task(lblMsg);
        task.execute(BUTTON_WARMER_THAN, "", degreeStr);
    }


}
