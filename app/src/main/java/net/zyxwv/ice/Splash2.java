package net.zyxwv.ice;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Splash2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash2);

        PopulatePeopleList();
    }

    @Override
    public void onResume(){
        super.onResume();
        PopulatePeopleList();
    }

    private void PopulatePeopleList(){
        Spinner spN = (Spinner)findViewById(R.id.sp_people);
        List<String>list = DataGet();
        // Check if our result was valid.
        if (list != null) {
            //fill the dropdown
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.item_spinner, list);
            //dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spN.setAdapter(dataAdapter);
        }

    }

    private List<String> DataGet(){
        String TableName = this.getString(R.string.dbTableName);
        List<String> list = new ArrayList<>();
    	/* Create a Database. */
        try {
            scDataAccess.SqlCreateTables();

	        /* retrieve data from database */
            list = scDataAccess.SqlQryList("SELECT Name FROM " + TableName + " ORDER BY Name;");
    		/* Insert test data to the Table   */
            //myDB.execSQL("DELETE FROM " + TableName + ";");
            //MsgPopup("crsr=" + crsr);
            /*if (list == null) {
                scDataAccess.SqlExec("INSERT INTO " + TableName
                        + " (IceID,Name,Address,City,State,PostalCode,Country,HomePhone,Photo,Allergies,Conditions,Meds,Contact1,Contact2,PCFacility,PCPhysician,SCPhysician,HealthInsurance,Notes)"
                        + " VALUES (1,'','','','','','','','','','','','','','','','','','');");
                list = scDataAccess.SqlQryList("SELECT Name FROM " + TableName + " ORDER BY Name;");
            }*/
        }
        catch(Exception e) {
            //Log.e("Error", "Error", e);
            MsgPopup("Error: " + e.getMessage());
            //showAlert("zyxwv ICE", e.getMessage(), "OK", false);
            //Intent itMsg = new Intent(ICE.this, Message.class);
            //itMsg.putExtra("text",e.getMessage());
            //startActivity(itMsg);
        }
        finally {
            list.add("new person");
            return list;
        }
    }

    public void MainGo(View v){
        //MsgPopup("Button clicked.");
        //go to Main, passing the selected person
        Spinner spN = (Spinner)findViewById(R.id.sp_people);
        String strPerson = spN.getSelectedItem().toString();
        Intent i = new Intent(this, Main.class);
        i.putExtra("name",strPerson);
        startActivity(i);
    }

    private void MsgPopup(String Text){
        Toast.makeText(getBaseContext(), Text, Toast.LENGTH_LONG).show();
    }

}
