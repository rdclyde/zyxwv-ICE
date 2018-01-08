package net.zyxwv.ice;

import android.content.ContentValues;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Intent;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class InfoEdit extends AppCompatActivity {
    String mId = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_infoedit);
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

            Intent i = getIntent();
            mId = i.getStringExtra("id");

            SQLiteDatabase myDB= null;
            String DbName = this.getString(R.string.dbName);
            String TableName = this.getString(R.string.dbTableName);
            myDB = this.openOrCreateDatabase(DbName, MODE_PRIVATE, null);
            // retrieve data from database
            Cursor crsr = myDB.rawQuery("SELECT * FROM " + TableName + " WHERE IceId='" + mId + "';" , null);
            crsr.moveToFirst();
            //display the data
            TextView tx1 = (TextView)findViewById(R.id.etName);
            tx1.setText(crsr.getString(1));
            TextView tx2 = (TextView)findViewById(R.id.etAddress);
            tx2.setText(crsr.getString(2));
            TextView tx3 = (TextView)findViewById(R.id.etCity);
            tx3.setText(crsr.getString(3));
            TextView tx4 = (TextView)findViewById(R.id.etState);
            tx4.setText(crsr.getString(4));
            TextView tx5 = (TextView)findViewById(R.id.etPostalCode);
            tx5.setText(crsr.getString(5));
            TextView tx6 = (TextView)findViewById(R.id.etCountry);
            tx6.setText(crsr.getString(6));
            TextView tx7 = (TextView)findViewById(R.id.etHomePhone);
            tx7.setText(crsr.getString(7));
            Button tx8 = (Button)findViewById(R.id.bnGetPhoto);
            tx8.setText(crsr.getString(8));
            PhotoLoad(crsr.getString(8));

            //set up the buttons
            Button bnP = (Button)findViewById(R.id.bnGetPhoto);
            bnP.setOnClickListener(bnGetPhoto_Click);
            Button bnC = (Button)findViewById(R.id.bnCancel);
            bnC.setOnClickListener(bnCancel_Click);
        } catch(Exception e) {
            MsgPopup(e.getMessage());
        } finally {

        }
    }

    //---create an anonymous class to act as a button click listener---
    private OnClickListener bnCancel_Click = new OnClickListener() {
        public void onClick(View v){
            //setResult(RESULT_OK,intent);
            finish();
        }
    };

    //---create an anonymous class to act as a button click listener---
    private OnClickListener bnGetPhoto_Click = new OnClickListener() {
        public void onClick(View v) {
            //MsgPopup("bnGetPhoto_Click");
            Intent i = new Intent(v.getContext(), Dlg_Open.class);
            i.putExtra("header",v.getContext().getString(R.string.dh_PhotoGet));
            String[] graphic = getResources().getStringArray(R.array.graphic);
            i.putExtra("fileType",graphic);
            i.putExtra("bnOkShow",false);
            startActivityForResult(i,2);
        }
    };

    // Listen for results.
    protected void onActivityResult(int requestCode, int resultCode, Intent i){
        // See which child activity is calling us back.
        //MsgPopup(requestCode + ", " + resultCode);
        switch (resultCode) {
            case RESULT_OK:
                if (resultCode == RESULT_OK){
                    Bundle extras = i.getExtras();
                    String FileName = extras.getString("FileName");
                    String FilePath = extras.getString("FilePath");
                    String srcPath=FilePath + "/" + FileName;
                    FileInputStream fis=null;
                    FileOutputStream fos=null;
                    try {
                        File rFile = new File(srcPath);
                        fis = new FileInputStream(rFile);
                        fos = openFileOutput(FileName,3);

                        byte[] buf = new byte[1024];
                        int len;
                        while ((len = fis.read(buf)) > 0) {
                            fos.write(buf, 0, len);
                        }
                        fis.close();
                        fos.flush();
                        fos.close();

                    } catch(Exception e) {
                        MsgPopup("Photo '" + srcPath + "' cannot be found. Please select another photo.\nInfo_Edit.onActivityResult\n" + e.getMessage());
                        srcPath="";
                    } finally {
                    }
                    Button bn1 = (Button)findViewById(R.id.bnGetPhoto);
                    bn1.setText(FileName);
                    PhotoLoad(FileName);
                }
            default:
                break;
        }
    }

    private void PhotoLoad(String FileName) {
        //photo insert goes here
        String PicPath = this.getString(R.string.FilePath);
        ImageView ivP = (ImageView)findViewById(R.id.ivPic);
        try {
            if (FileName.contentEquals("")) {
                ivP.setImageResource(R.drawable.sticky);
            } else {
                Bitmap bm = BitmapFactory.decodeFile(PicPath + FileName);
                if(bm==null) {
                    MsgPopup("File '" + PicPath + FileName + "' not found.");
                    ivP.setImageResource(R.drawable.sticky);
                } else {
                    ivP.setImageBitmap(bm);
                }
            }
        } catch(Exception e) {
            MsgPopup(e.getMessage());
            PicPath="";
        } finally {
        }
    }

    public void InfoSave(View v){
        //MsgPopup("Save button clicked");
        try {
            String TableName = getResources().getString(R.string.dbTableName);
            String dt = scHelpers.GetDateCurrentFormatted();

            TextView tx1 = (TextView)findViewById(R.id.etName);
            TextView tx2 = (TextView)findViewById(R.id.etAddress);
            TextView tx3 = (TextView)findViewById(R.id.etCity);
            TextView tx4 = (TextView)findViewById(R.id.etState);
            TextView tx5 = (TextView)findViewById(R.id.etPostalCode);
            TextView tx6 = (TextView)findViewById(R.id.etCountry);
            TextView tx7 = (TextView)findViewById(R.id.etHomePhone);
            Button tx8 = (Button)findViewById(R.id.bnGetPhoto);

            ContentValues data = new ContentValues(10);

            data.put("IceId", mId);
            data.put("Name", SqlFix(tx1.getText().toString()));
            data.put("Address", SqlFix(tx2.getText().toString()));
            data.put("City", SqlFix(tx3.getText().toString()));
            data.put("State", SqlFix(tx4.getText().toString()));
            data.put("PostalCode", SqlFix(tx5.getText().toString()));
            data.put("Country", SqlFix(tx6.getText().toString()));
            data.put("HomePhone", SqlFix(tx7.getText().toString()));
            data.put("Photo", SqlFix(tx8.getText().toString()));
            data.put("LastUpdated", dt);
            String where = "IceID='" + mId + "'";
            scDataAccess.SqlUpdate(TableName,data,where);

            setResult(RESULT_OK, null);
            finish();
        } catch(Exception e) {
            Toast.makeText(getBaseContext(),e.getMessage(),Toast.LENGTH_LONG).show();
        } finally {
        }
    }

    private String SqlFix(String txt) {
        txt = txt.replace("'", "''");
        return txt;
    }

    private void MsgPopup(String Text){
        Toast.makeText(getBaseContext(),Text,Toast.LENGTH_LONG).show();
    }


}
