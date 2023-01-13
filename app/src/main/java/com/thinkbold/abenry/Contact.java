package com.thinkbold.abenry;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class Contact extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText cName, cEmail, cPhone, cFulltext;
    Spinner spinner;

    String name, email, phone, messagef, practice, npractice, toSendValue, finalvalue, message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        cName = findViewById(R.id.names);
        cEmail = findViewById(R.id.email);
        cPhone = findViewById(R.id.phone);
        cFulltext = findViewById(R.id.fullText);

        spinner = findViewById(R.id.spinner);
        String[] practicearena = getResources().getStringArray(R.array.practices);
        ArrayAdapter<String> nadapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, practicearena);
        nadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(nadapter);
        spinner.setOnItemSelectedListener(this);

        BottomNavigationView bottomNavigationView = findViewById(R.id.menu_nav);
        //setting the Home selected
        bottomNavigationView.setSelectedItemId(R.id.contact_navigation);
        //performing item selection
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.contact_navigation:
                        return true;
                    case R.id.praatice_navigation:
                        startActivity(new Intent(getApplicationContext(), Services.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.home_navigation:
                        startActivity(new Intent(getApplicationContext(), Home.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.news_navigation:
                        startActivity(new Intent(getApplicationContext(), News.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.about_navigation:
                        startActivity(new Intent(getApplicationContext(), AboutUs.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });

        ImageView imageBack=findViewById(R.id.imageBack);
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Button buttonSubmit = findViewById(R.id.buttonSubmit);
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = cName.getText().toString();
                phone = cPhone.getText().toString();
                email = cEmail.getText().toString();
                messagef = cFulltext.getText().toString();
                npractice = practice;

                toSendValue = "Full name: " + name + "\n Phone number: "+ phone + "\n Email address: "+ email +"\n User Message: "+ messagef + "\n Selected practice: "+npractice;
                Log.d("ToSendValue2211", toSendValue);
                //toSendValue = "Full name: " + name + "\n Phone number: "+ phone + "\n Email address: "+ email +"\n User Message: "+ messagef;
                finalvalue = toSendValue;
                Log.d("finalValue22222", finalvalue);

                if (haveNetworkConnection()){
                    new sendMessage().execute();
                }else {
                    Toast.makeText(Contact.this, "No Internet Connection. Please check your network settings!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        practice = parent.getItemAtPosition(position).toString();
        //Toast.makeText(this, practice, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    public void buttonClicked(View view) {
        switch (view.getId()){
            case R.id.buttonCall:
                String phone = "+2552129461";
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                startActivity(intent);
                break;
            case R.id.buttonEmail:
                String[] emails = {"info@abenry.co.tz"};
                Intent intentFeedback = new Intent(Intent.ACTION_SEND);
                intentFeedback.setType("rfc/822");
                intentFeedback.putExtra(Intent.EXTRA_EMAIL, emails);
                intentFeedback.putExtra(Intent.EXTRA_SUBJECT, "");
                intentFeedback.putExtra(Intent.EXTRA_TEXT, "");
                startActivity(Intent.createChooser(intentFeedback, "Send Email With:"));
                break;
            case R.id.buttonLocation:
                String region = "Daresalaam";
                String district = "Ilala";
                String street = "Ohio / Kibo";
                String road = "Ohio";
                String localtion = region + "," + district + "," + street + "," + road;
                Uri mapUri = Uri.parse("geo:0,0?q=" + Uri.encode(localtion));
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, mapUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
                String destinationLatitude = "-6.813672163290962";
                String destinationLongitude = "39.289083169314594";

                String uri = "http://maps.google.com/maps?daddr=" + destinationLatitude + "," + destinationLongitude + " (" + "LOCATION NAME HERE" + ")";
                Intent intentb = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                intentb.setPackage("com.google.android.apps.maps");
                try {
                    startActivity(intentb);
                } catch (ActivityNotFoundException ex) {
                    try {
                        Intent unrestrictedIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                        startActivity(unrestrictedIntent);
                    } catch (ActivityNotFoundException innerEx) {
                        Toast.makeText(this, "Please install a maps application", Toast.LENGTH_LONG).show();
                    }
                }
                break;
            case R.id.imageInstagram:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/thinkboldi/")));
                break;
            case R.id.imageYoutube:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/channel/UCE96gPS6rJ5XQg-hRknlGGg/videos")));
                break;
            case R.id.imageWhatsap:
                String contact = "+255652079542"; // use country code with your phone number
                String url = "https://api.whatsapp.com/send?phone=" + contact;
                try {
                    PackageManager pm = Contact.this.getPackageManager();
                    pm.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES);
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                } catch (PackageManager.NameNotFoundException e) {
                    Toast.makeText(Contact.this, "Whatsapp app not installed in your phone", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
                break;
            default:
        }
    }

    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        @SuppressLint("MissingPermission") NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }



    class sendMessage extends AsyncTask {

        ProgressDialog progressDialog;
        JSONObject jObject;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(Contact.this);
            progressDialog.setMessage("Sending message...");
            progressDialog.setCancelable(false);
            progressDialog.setIndeterminate(false);
            progressDialog.show();
        }

        @Override
        protected Object doInBackground(Object[] objects) {

            try {

                DefaultHttpClient httpclient = new DefaultHttpClient();

                HttpPost httppost = new HttpPost("https://thinkbold.africa/abenry/email.php");

                ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);

                nameValuePairs.add(new BasicNameValuePair("message", finalvalue));

                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = httpclient.execute(httppost);
                InputStream inputStream = response.getEntity().getContent();

                BufferedReader rd = new BufferedReader(new InputStreamReader(inputStream), 4096);
                String line;
                StringBuilder sb = new StringBuilder();
                while ((line = rd.readLine()) != null) {
                    sb.append(line);
                }
                rd.close();
                message = sb.toString();
                inputStream.close();

            } catch (Exception e) {
                Toast.makeText(getApplicationContext(),"Try Again", Toast.LENGTH_LONG).show();
            }
            return message;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            progressDialog.dismiss();
            if (message.equals("Success")) {
                //Register beneficiary activity
                Toast.makeText(Contact.this, "The message has been sent, thank you..", Toast.LENGTH_SHORT).show();
                finish();
            } else{
                Toast.makeText(Contact.this, "Please try again..", Toast.LENGTH_SHORT).show();
            }
        }
    }
}