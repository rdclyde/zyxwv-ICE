package net.zyxwv.ice;

import android.os.Bundle;
import android.view.View;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Environment;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class Dlg_Open extends ListActivity {
    private String STORAGE_PATH = Environment.getExternalStorageDirectory().getPath();
    private int GETPATHFILE = R.integer.getPathFile;
    private int GETPATHFOLDER = R.integer.getPathFolder;
    private List<String> files = new ArrayList<String>();
    private List<String> fileTypes = new ArrayList<String>();
    private List<String> fileTypesC = new ArrayList<String>();
    private String[] fileExt = null;
    private int pathType = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dlg_open);
        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/

        Bundle extras = getIntent().getExtras();
        TextView tvH = (TextView)findViewById(R.id.tvHeader);
        tvH.setText(extras.getString("header"));
        fileExt = extras.getStringArray("fileType");
        int bnOkShow = View.INVISIBLE;
        if(extras.getBoolean("bnOkShow")==false) bnOkShow = View.INVISIBLE;
        pathType = extras.getInt("retFileFolder");
        if(pathType==GETPATHFILE) {
            bnOkShow = View.VISIBLE;
        } else if(pathType==GETPATHFOLDER) {
            bnOkShow = View.INVISIBLE;
        }
        //MsgPopup("rff=" + pathType + ", bnOkShow=" + bnOkShow);
        updateList();

        Button bnU = (Button)findViewById(R.id.bnUp);
        bnU.setOnClickListener(bnUp_Click);
        Button bnK = (Button)findViewById(R.id.bnOK);
        bnK.setVisibility(bnOkShow);
        bnK.setOnClickListener(bnOK_Click);
    }

    //list the folders and files in the current directory
    public void updateList() {
        TextView tv1 = (TextView)findViewById(R.id.tvPath);
        tv1.setText(STORAGE_PATH);
        File home = new File(STORAGE_PATH);
        files.clear();
        fileTypes.clear();
        fileTypesC.clear();
        if (home.listFiles().length > 0) {
            for (File file : home.listFiles()) {
                if(file.isDirectory()) {
                    files.add(file.getName());
                    fileTypes.add("d");
                    fileTypesC.add("d   " + file.getName());
                } else {
                    for(String type : fileExt) {
                        if(file.getName().endsWith("." + type)) {
                            files.add(file.getName());
                            fileTypes.add("f");
                            fileTypesC.add("f   " + file.getName());
                            //exit for
                            break;
                        }
                    }
                }
            }
        }
        ArrayAdapter<String> fileList = new ArrayAdapter<String>(this, R.layout.file_item, fileTypesC);
        setListAdapter(fileList);
    }

    //a list item was clicked
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        String FileName = files.get(position);
        //MsgPopup("FileName=" + FileName + ", FileType=" + fileTypes.get(position));
        if(fileTypes.get(position)=="d"){
            //go into a directory
            STORAGE_PATH=STORAGE_PATH + "/" + FileName;
            //MsgPopup(STORAGE_PATH + ", " + position);
            updateList();
        } else if(fileTypes.get(position)=="f"){
            //return the name of the selected file
            //MsgPopup("fileType=f, FileName=" + FileName);
            Intent i = new Intent();
            i.putExtra("FileName",FileName);
            i.putExtra("FilePath", STORAGE_PATH);
            i.putExtra("retFileFolder", GETPATHFILE);
            setResult(RESULT_OK,i);
            finish();
        }
    }

    //go up a directory
    //---create an anonymous class to act as a button click listener---
    private OnClickListener bnUp_Click = new OnClickListener() {
        public void onClick(View v){
            //MsgPopup("SP='" + STORAGE_PATH + "'\nEv='" + Environment.getExternalStorageDirectory().getPath() +"'");
            if(!STORAGE_PATH.contentEquals(Environment.getExternalStorageDirectory().getPath())) {
                int I = STORAGE_PATH.lastIndexOf("/");
                STORAGE_PATH=STORAGE_PATH.substring(0, I);
                updateList();
            }
        }
    };
    //---create an anonymous class to act as a button click listener---
    private OnClickListener bnOK_Click = new OnClickListener() {
        public void onClick(View v){
            //return the selected path
            Intent i = new Intent();
            i.putExtra("Type","Folder");
            i.putExtra("FileName","");
            i.putExtra("FilePath", STORAGE_PATH);
            i.putExtra("retFileFolder", pathType);
            setResult(RESULT_OK,i);
            finish();
        }
    };

    private void MsgPopup(String Text){
        Toast.makeText(getBaseContext(),Text,Toast.LENGTH_LONG).show();
    }


}
