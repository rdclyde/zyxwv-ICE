package net.zyxwv.ice;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;


public class Main extends AppCompatActivity {
    private int GETPATHFILE = R.integer.getPathFile;
    private int GETPATHFOLDER = R.integer.getPathFolder;
    String mName = "";
    String mId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
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

            Intent i = getIntent();
            mName=i.getStringExtra("name");

        //Read in the data
        DataGet();
        //edit the data
        ImageButton bnI = (ImageButton)findViewById(R.id.bnMyInfoEdit);
        bnI.setOnClickListener(bnEdit_Click);
        ImageButton bnA = (ImageButton)findViewById(R.id.bnMyAllergiesEdit);
        bnA.setOnClickListener(bnEdit_Click);
        ImageButton bnC = (ImageButton)findViewById(R.id.bnMyConditionsEdit);
        bnC.setOnClickListener(bnEdit_Click);
        ImageButton bnM = (ImageButton)findViewById(R.id.bnMyMedsEdit);
        bnM.setOnClickListener(bnEdit_Click);
        ImageButton bnC1 = (ImageButton)findViewById(R.id.bn1ContactEdit);
        bnC1.setOnClickListener(bnEdit_Click);
        ImageButton bnC2 = (ImageButton)findViewById(R.id.bn2ContactEdit);
        bnC2.setOnClickListener(bnEdit_Click);
        ImageButton bnF = (ImageButton)findViewById(R.id.bnPCFEdit);
        bnF.setOnClickListener(bnEdit_Click);
        ImageButton bnP1 = (ImageButton)findViewById(R.id.bnPCPEdit);
        bnP1.setOnClickListener(bnEdit_Click);
        ImageButton bnP2 = (ImageButton)findViewById(R.id.bnSCPEdit);
        bnP2.setOnClickListener(bnEdit_Click);
        ImageButton bnH = (ImageButton)findViewById(R.id.bnHealthInsEdit);
        bnH.setOnClickListener(bnEdit_Click);
        ImageButton bnN = (ImageButton)findViewById(R.id.bnNotesEdit);
        bnN.setOnClickListener(bnEdit_Click);
    } catch(Exception e) {
        MsgPopup(e.getMessage());
    } finally {
    }
}

    @Override
    public void onResume(){
        super.onResume();
        //DataGet();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_delete) {
            PersonDelete();
            return true;
        }else if (id == R.id.action_email) {
            DataEmail();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //---create an anonymous class to act as a button click listener---
    private OnClickListener bnEdit_Click = new OnClickListener() {
        public void onClick(View v) {
            TextView tvI = (TextView)findViewById(R.id.tvMyInfo);
            TextView tvA = (TextView)findViewById(R.id.tvMyAllergies);
            TextView tvC = (TextView)findViewById(R.id.tvMyConditions);
            TextView tvM = (TextView)findViewById(R.id.tvMyMeds);
            TextView tvC1 = (TextView)findViewById(R.id.tv1Contact);
            TextView tvC2 = (TextView)findViewById(R.id.tv2Contact);
            TextView tvF = (TextView)findViewById(R.id.tvPCF);
            TextView tvP1 = (TextView)findViewById(R.id.tvPCP);
            TextView tvP2 = (TextView)findViewById(R.id.tvSCP);
            TextView tvH = (TextView)findViewById(R.id.tvHealthIns);
            TextView tvN = (TextView)findViewById(R.id.tvNotes);
            ImageButton bnI = (ImageButton)findViewById(R.id.bnMyInfoEdit);
            ImageButton bnA = (ImageButton)findViewById(R.id.bnMyAllergiesEdit);
            ImageButton bnC = (ImageButton)findViewById(R.id.bnMyConditionsEdit);
            ImageButton bnM = (ImageButton)findViewById(R.id.bnMyMedsEdit);
            ImageButton bnC1 = (ImageButton)findViewById(R.id.bn1ContactEdit);
            ImageButton bnC2 = (ImageButton)findViewById(R.id.bn2ContactEdit);
            ImageButton bnF = (ImageButton)findViewById(R.id.bnPCFEdit);
            ImageButton bnP1 = (ImageButton)findViewById(R.id.bnPCPEdit);
            ImageButton bnP2 = (ImageButton)findViewById(R.id.bnSCPEdit);
            ImageButton bnH = (ImageButton)findViewById(R.id.bnHealthInsEdit);
            ImageButton bnN = (ImageButton)findViewById(R.id.bnNotesEdit);

            if (v==bnA) {
                Intent i = new Intent(v.getContext(), TextEdit.class);
                i.putExtra("sec","A");
                i.putExtra("header",v.getContext().getString(R.string.sh_MyAllergies));
                i.putExtra("text",tvA.getText());
                startActivityForResult(i,0);
            } else if (v==bnI) {
                //MsgPopup("Info");
                Intent i = new Intent(v.getContext(), InfoEdit.class);
                i.putExtra("sec","I");
                i.putExtra("header",v.getContext().getString(R.string.sh_MyInfo));
                i.putExtra("text",tvI.getText());
                i.putExtra("id",mId);
                startActivityForResult(i, 1);
            } else if (v==bnC) {
                Intent i = new Intent(v.getContext(), TextEdit.class);
                i.putExtra("sec","C");
                i.putExtra("header",v.getContext().getString(R.string.sh_MyConditions));
                i.putExtra("text",tvC.getText());
                startActivityForResult(i,0);
            } else if (v==bnM) {
                Intent i = new Intent(v.getContext(), TextEdit.class);
                i.putExtra("sec","M");
                i.putExtra("header",v.getContext().getString(R.string.sh_MyMeds));
                i.putExtra("text",tvM.getText());
                startActivityForResult(i,0);
            } else if (v==bnC1) {
                Intent i = new Intent(v.getContext(), TextEdit.class);
                i.putExtra("sec","C1");
                i.putExtra("header",v.getContext().getString(R.string.sh_1Contact));
                i.putExtra("text",tvC1.getText());
                startActivityForResult(i,0);
            } else if (v==bnC2) {
                Intent i = new Intent(v.getContext(), TextEdit.class);
                i.putExtra("sec","C2");
                i.putExtra("header",v.getContext().getString(R.string.sh_2Contact));
                i.putExtra("text",tvC2.getText());
                startActivityForResult(i,0);
            } else if (v==bnF) {
                Intent i = new Intent(v.getContext(), TextEdit.class);
                i.putExtra("sec","F");
                i.putExtra("header",v.getContext().getString(R.string.sh_PCF));
                i.putExtra("text",tvF.getText());
                startActivityForResult(i,0);
            } else if (v==bnP1) {
                Intent i = new Intent(v.getContext(), TextEdit.class);
                i.putExtra("sec","P1");
                i.putExtra("header",v.getContext().getString(R.string.sh_PCP));
                i.putExtra("text",tvP1.getText());
                startActivityForResult(i,0);
            } else if (v==bnP2) {
                Intent i = new Intent(v.getContext(), TextEdit.class);
                i.putExtra("sec","P2");
                i.putExtra("header",v.getContext().getString(R.string.sh_SCP));
                i.putExtra("text",tvP2.getText());
                startActivityForResult(i,0);
            } else if (v==bnH) {
                Intent i = new Intent(v.getContext(), TextEdit.class);
                i.putExtra("sec","H");
                i.putExtra("header",v.getContext().getString(R.string.sh_HealthIns));
                i.putExtra("text",tvH.getText());
                startActivityForResult(i,0);
            } else if (v==bnN) {
                Intent i = new Intent(v.getContext(), TextEdit.class);
                i.putExtra("sec","N");
                i.putExtra("header",v.getContext().getString(R.string.sh_Notes));
                i.putExtra("text",tvN.getText());
                startActivityForResult(i,0);
            }
        };
    };

    // Listen for results.
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        // See which child activity is calling us back.
        //MsgPopup(requestCode + ", " + resultCode);
        switch (resultCode) {
            case RESULT_OK:
                if (resultCode == RESULT_OK){
                    DataGet();
                }
            default:
                break;
        }
    }

    private void DataGet(){
        String TableName = this.getString(R.string.dbTableName);

    	/* Create a Database. */
        try {
    		/* get a reference to the form objects */
            ImageView ivS = (ImageView)findViewById(R.id.ivSelf);
            TextView tvI = (TextView)findViewById(R.id.tvMyInfo);
            TextView tvA = (TextView)findViewById(R.id.tvMyAllergies);
            TextView tvC = (TextView)findViewById(R.id.tvMyConditions);
            TextView tvM = (TextView)findViewById(R.id.tvMyMeds);
            TextView tvC1 = (TextView)findViewById(R.id.tv1Contact);
            TextView tvC2 = (TextView)findViewById(R.id.tv2Contact);
            TextView tvF = (TextView)findViewById(R.id.tvPCF);
            TextView tvP1 = (TextView)findViewById(R.id.tvPCP);
            TextView tvP2 = (TextView)findViewById(R.id.tvSCP);
            TextView tvH = (TextView)findViewById(R.id.tvHealthIns);
            TextView tvN = (TextView)findViewById(R.id.tvNotes);

	        /* retrieve data from database */
            Cursor crsr = null;
            if(mId.contentEquals("")) {
                crsr = scDataAccess.SqlQryCrsr("SELECT * FROM " + TableName + " WHERE Name='" + mName + "';");
            }else{
                crsr = scDataAccess.SqlQryCrsr("SELECT * FROM " + TableName + " WHERE IceID='" + mId + "';");
            }
    		/* Insert test data to the Table   */
            //myDB.execSQL("DELETE FROM " + TableName + ";");
            //MsgPopup("crsr=" + crsr);
            if (crsr.getCount() == 0) {
                scDataAccess.SqlExec("INSERT INTO " + TableName
                        + " (Name,Address,City,State,PostalCode,Country,HomePhone,Photo,Allergies,Conditions,Meds,Contact1,Contact2,PCFacility,PCPhysician,SCPhysician,HealthInsurance,Notes)"
                        + " VALUES ('','','','','','','','','','','','','','','','','','');");
                //crsr = myDB.rawQuery("SELECT * FROM " + TableName + " ORDER BY IceID DESC;", null);
                crsr = scDataAccess.SqlQryCrsr("SELECT * FROM " + TableName + " ORDER BY IceID DESC;");
            }
            // Check if our result was valid.
            crsr.moveToFirst();
            if (crsr != null) {
                mId = crsr.getString(0);
                //photo insert goes here
                String PicPath = this.getString(R.string.FilePath);
                String FileName = crsr.getString(8);
                try {
                    if (FileName.contentEquals("")) {
                        ivS.setImageResource(R.drawable.sticky);
                    } else {
                        Bitmap bm = BitmapFactory.decodeFile(PicPath + FileName);
                        if(bm==null) {
                            MsgPopup("File '" + PicPath + FileName + "' not found.");
                            ivS.setImageResource(R.drawable.sticky);
                        } else {
                            ivS.setImageBitmap(bm);
                        }
                    }
                } catch(Exception e) {
                    MsgPopup(e.getMessage());
                    PicPath="";
                } finally {
                }

                //display the text data
                String MyInfo = crsr.getString(1) + "\n" + crsr.getString(2) + "\n" + crsr.getString(3) + ", " + crsr.getString(4)
                        + " " + crsr.getString(5) + "\n" + crsr.getString(6) + "\n" + crsr.getString(7) + "\nUpd: " + crsr.getString(19);
                MyInfo=MyInfo.replace("\n\n", "\n");
                if (MyInfo != "") tvI.setText(MyInfo); else tvI.setText("None");
                if (crsr.getString(9).contentEquals("")) tvA.setText("None"); else tvA.setText(crsr.getString(9));
                if (crsr.getString(10).contentEquals("")) tvC.setText("None"); else tvC.setText(crsr.getString(10));
                if (crsr.getString(11).contentEquals("")) tvM.setText("None"); else tvM.setText(crsr.getString(11));
                if (crsr.getString(12).contentEquals("")) tvC1.setText("None"); else tvC1.setText(crsr.getString(12));
                if (crsr.getString(13).contentEquals("")) tvC2.setText("None"); else tvC2.setText(crsr.getString(13));
                if (crsr.getString(14).contentEquals("")) tvF.setText("None"); else tvF.setText(crsr.getString(14));
                if (crsr.getString(15).contentEquals("")) tvP1.setText("None"); else tvP1.setText(crsr.getString(15));
                if (crsr.getString(16).contentEquals("")) tvP2.setText("None"); else tvP2.setText(crsr.getString(16));
                if (crsr.getString(17).contentEquals("")) tvH.setText("None"); else tvH.setText(crsr.getString(17));
                if (crsr.getString(18).contentEquals("")) tvN.setText("None"); else tvN.setText(crsr.getString(18));
            }
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
        }
    }

    private void PersonDelete(){
        //confirm delete
        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.action_delete))
                .setMessage(getString(R.string.action_delete_confirm))
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //MsgPopup("Yaay");
                        //perform delete
                        scDataAccess.PersonDelete(mId);
                        finish();
                    }})
                .setNegativeButton(android.R.string.no, null).show();
    }

    private void DataEmail(){
        //gather the data
        String txBody="";
        String div = "--------------------------\n";
        String shI = getString(R.string.sh_MyInfo);
        TextView tvI = (TextView)findViewById(R.id.tvMyInfo);
        String shA = getString(R.string.sh_MyAllergies);
        TextView tvA = (TextView)findViewById(R.id.tvMyAllergies);
        String shC = getString(R.string.sh_MyConditions);
        TextView tvC = (TextView)findViewById(R.id.tvMyConditions);
        String shM = getString(R.string.sh_MyMeds);
        TextView tvM = (TextView)findViewById(R.id.tvMyMeds);
        String shC1 = getString(R.string.sh_1Contact);
        TextView tvC1 = (TextView)findViewById(R.id.tv1Contact);
        String shC2 = getString(R.string.sh_2Contact);
        TextView tvC2 = (TextView)findViewById(R.id.tv2Contact);
        String shF = getString(R.string.sh_PCF);
        TextView tvF = (TextView)findViewById(R.id.tvPCF);
        String shP1 = getString(R.string.sh_PCP);
        TextView tvP1 = (TextView)findViewById(R.id.tvPCP);
        String shP2 = getString(R.string.sh_SCP);
        TextView tvP2 = (TextView)findViewById(R.id.tvSCP);
        String shH = getString(R.string.sh_HealthIns);
        TextView tvH = (TextView)findViewById(R.id.tvHealthIns);
        String shN = getString(R.string.sh_Notes);
        TextView tvN = (TextView)findViewById(R.id.tvNotes);

        txBody = txBody + shI.toString() + ": \n" + tvI.getText().toString() + "\n";
        txBody = txBody + div;
        txBody = txBody + shA.toString() + ": \n" + tvA.getText().toString() + "\n";
        txBody = txBody + div;
        txBody = txBody + shC.toString() + ": \n" + tvC.getText().toString() + "\n";
        txBody = txBody + div;
        txBody = txBody + shM.toString() + ": \n" + tvM.getText().toString() + "\n";
        txBody = txBody + div;
        txBody = txBody + shC1.toString() + ": \n" + tvC1.getText().toString() + "\n";
        txBody = txBody + div;
        txBody = txBody + shC2.toString() + ": \n" + tvC2.getText().toString() + "\n";
        txBody = txBody + div;
        txBody = txBody + shF.toString() + ": \n" + tvF.getText().toString() + "\n";
        txBody = txBody + div;
        txBody = txBody + shP1.toString() + ": \n" + tvP1.getText().toString() + "\n";
        txBody = txBody + div;
        txBody = txBody + shP2.toString() + ": \n" + tvP2.getText().toString() + "\n";
        txBody = txBody + div;
        txBody = txBody + shH.toString() + ": \n" + tvH.getText().toString() + "\n";
        txBody = txBody + div;
        txBody = txBody + shN.toString() + ": \n" + tvN.getText().toString() + "\n";
        //txBody = txBody + div;

        //open the email page
        String subjText = this.getString(R.string.email_subject_text);
        subjText = subjText.replace("[#Name#]",mName);
        Intent i = new Intent(App.getContext(), Email.class);
        i.putExtra("sendTo","");
        i.putExtra("sendFrom","");
        i.putExtra("subject", this.getString(R.string.email_subject) + " " + subjText);
        i.putExtra("body",txBody);
        startActivityForResult(i,0);

    }

    private void MsgPopup(String Text){
        Toast.makeText(getBaseContext(),Text,Toast.LENGTH_LONG).show();
    }
}

/* Loop through all Results
//int Column1 = crsr.getColumnIndex("Field1");
//int Column2 = crsr.getColumnIndex("Field2");

do {
	String Name = c.getString(Column1);
	int Age = c.getInt(Column2);

	Data =Data +Name+"/"+Age+"\n";
}
while(c.moveToNext()); */

//EditText et1 = (EditText)findViewById(R.id.etInput);

//TextView tx1 = (TextView)findViewById(R.id.tvName);
//tx1.setText(et1.getText().toString());


//Toast.makeText(getBaseContext(),
//		"You clicked " + "bnMyAllergiesEdit",
//        Toast.LENGTH_LONG).show();




