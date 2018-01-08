package net.zyxwv.ice;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.database.sqlite.SQLiteDatabase;


public class TextEdit extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_edit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Bundle extras = getIntent().getExtras();
        //mIntentString = extras != null ? extras.getString("myKey") : "nothing passed in";
        String sec = extras.getString("sec");

        TextView tx1 = (TextView)findViewById(R.id.tvHeader);
        tx1.setText(extras.getString("header"));
        TextView tx2 = (TextView)findViewById(R.id.etInput);
        String text = extras.getString("text");
        if (text.contentEquals("None")) text="";
        tx2.setText(text);

        Button bnC = (Button)findViewById(R.id.bnCancel);
        bnC.setOnClickListener(bnCancel_Click);
        Button bnS = (Button)findViewById(R.id.bnSave);
        bnS.setOnClickListener(bnSave_Click);

    }

    //---create an anonymous class to act as a button click listener---
    private OnClickListener bnCancel_Click = new OnClickListener() {
        public void onClick(View v){
            //setResult(RESULT_OK,intent);
            finish();
        }
    };

    //---create an anonymous class to act as a button click listener---
    private OnClickListener bnSave_Click = new OnClickListener() {
        public void onClick(View v){
            SQLiteDatabase myDB = null;
            try{
                String DbName = getResources().getString(R.string.dbName);
                String TableName = getResources().getString(R.string.dbTableName);
                myDB = v.getContext().openOrCreateDatabase(DbName, MODE_PRIVATE, null);

                Bundle extras = getIntent().getExtras();
                String sec = extras.getString("sec");
                String FieldName = null;
                TextView tx2 = null;
                //MsgPopup("'" + sec + "'");
                if (sec.contentEquals("A")) {
                    FieldName = "Allergies";
                    tx2 = (TextView)findViewById(R.id.etInput);
                    //MsgPopup("ran A");
                } else if (sec.contentEquals("C")) {
                    FieldName = "Conditions";
                    tx2 = (TextView)findViewById(R.id.etInput);
                } else if (sec.contentEquals("M")) {
                    FieldName = "Meds";
                    tx2 = (TextView)findViewById(R.id.etInput);
                } else if (sec.contentEquals("C1")) {
                    FieldName = "Contact1";
                    tx2 = (TextView)findViewById(R.id.etInput);
                } else if (sec.contentEquals("C2")) {
                    FieldName = "Contact2";
                    tx2 = (TextView)findViewById(R.id.etInput);
                } else if (sec.contentEquals("F")) {
                    FieldName = "PCFacility";
                    tx2 = (TextView)findViewById(R.id.etInput);
                } else if (sec.contentEquals("P1")) {
                    FieldName = "PCProvider";
                    tx2 = (TextView)findViewById(R.id.etInput);
                } else if (sec.contentEquals("P2")) {
                    FieldName = "SCProvider";
                    tx2 = (TextView)findViewById(R.id.etInput);
                } else if (sec.contentEquals("H")) {
                    FieldName = "HealthInsurance";
                    tx2 = (TextView)findViewById(R.id.etInput);
                } else if (sec.contentEquals("N")) {
                    FieldName = "Notes";
                    tx2 = (TextView)findViewById(R.id.etInput);
                }
                String sql = "UPDATE " + TableName + " SET " + FieldName + "='" + SqlFix(tx2.getText().toString()) + "',LastUpdated=DATETIME('NOW');";
                myDB.execSQL(sql);

                setResult(RESULT_OK,null);
                finish();
            } catch(Exception e) {
                Toast.makeText(getBaseContext(),e.getMessage(),Toast.LENGTH_LONG).show();
            } finally {
                if (myDB != null)
                    myDB.close();
            }
        }
    };

    private String SqlFix(String txt) {
        txt = txt.replace("'", "''");
        return txt;
    }

    private void MsgPopup(String Text){
        Toast.makeText(getBaseContext(),Text,Toast.LENGTH_LONG).show();
    }
}

