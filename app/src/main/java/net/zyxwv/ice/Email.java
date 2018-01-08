package net.zyxwv.ice;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

public class Email extends AppCompatActivity {

    String sendTo = "";
    String sendFrom = "";
    String subject = "";
    String body = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent i = getIntent();
        //sendTo = i.getStringExtra("sendTo");
        sendTo="rdclyde@deltakron.com";
        sendFrom = i.getStringExtra("sendFrom");
        subject = i.getStringExtra("subject");
        body = i.getStringExtra("body");

        EditText etTo = (EditText)findViewById(R.id.etTo);
        EditText etSubject = (EditText)findViewById(R.id.etSubject);
        EditText etBody = (EditText)findViewById(R.id.etBody);
        etTo.setText(sendTo);
        etSubject.setText(subject);
        etBody.setText(body);
    }

    public void EmailSend(View v){
        EditText etTo = (EditText)findViewById(R.id.etTo);
        EditText etSubject = (EditText)findViewById(R.id.etSubject);
        EditText etBody = (EditText)findViewById(R.id.etBody);

        Intent em = new Intent(Intent.ACTION_SEND);
        em.setData(Uri.parse("mailto:"));
        em.setType("text/plain");
        em.putExtra(Intent.EXTRA_EMAIL,new String[]{etTo.getText().toString()});
        em.putExtra(Intent.EXTRA_SUBJECT, etSubject.getText().toString());
        em.putExtra(Intent.EXTRA_TEXT, etBody.getText().toString());
        //em.setType("message/rfc822");
        startActivity(Intent.createChooser(em,"Choose an email client:"));

        finish();
    }

    public void EmailCancel(View v){
            //setResult(RESULT_OK,intent);
            finish();
        }



}
