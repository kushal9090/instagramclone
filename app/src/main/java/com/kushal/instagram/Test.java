package com.kushal.instagram;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Test extends AppCompatActivity {
   public static final  String MCC = "mcc";
    private EditText vet;
    private Button vbtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);


        vet  = (EditText) findViewById(R.id.vet);
        vbtn = (Button) findViewById(R.id.vbtn);

        vbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(vet.getText().toString().equals(MCC)){

                    Toast.makeText(Test.this, "valid", Toast.LENGTH_SHORT).show();
                }else
                {
                    Toast.makeText(Test.this, "not valid", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}
