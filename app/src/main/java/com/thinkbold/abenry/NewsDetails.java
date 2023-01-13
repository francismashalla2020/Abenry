package com.thinkbold.abenry;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

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
import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class NewsDetails extends AppCompatActivity {

    TextView date, title, content, category, ratingScale;
    ImageView rate, share, imageCorps;
    String nTittle, nDate, nImage, nDetails, finalvalue, message, nCat;

    EditText moreRating;
    RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);

        Bundle bundle = getIntent().getExtras();
        nTittle=   bundle.getString("getTitle");
        nDate= bundle.getString("getDate");
        nImage= bundle.getString("getImage");
        nDetails = bundle.getString("getDesc");
        nCat = bundle.getString("getCat");

        title = findViewById(R.id.title);
        content = findViewById(R.id.content);
        date = findViewById(R.id.date);
        category = findViewById(R.id.category);
        rate = findViewById(R.id.rate);
        share = findViewById(R.id.share);
        imageCorps = findViewById(R.id.imageCorps);

        title.setText(nTittle);
        content.setText(nDetails);
        date.setText(nDate);
        category.setText(nCat);

        Picasso.get().load(nImage).into(imageCorps);


        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView = findViewById(R.id.title);
                String sharetitle = textView.getText().toString();
                String app_downloadlink = "https://play.google.com/store/apps/details?id=com.thinkbold.abenry";
                Intent sharePost = new Intent(Intent.ACTION_SEND);
                sharePost.setType("text/plain");
                sharePost.putExtra(Intent.EXTRA_TEXT, "Read this news: " + sharetitle + "\n from Abenry Advocates, download it via \n" + app_downloadlink);
                startActivity(Intent.createChooser(sharePost, "Share this news"));
            }
        });

        rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCustomDialog();
            }
        });

        ImageView imageBack=findViewById(R.id.imageBack);
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    public void showCustomDialog(){

        final Dialog dialog = new Dialog(NewsDetails.this);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.activity_rating);

        moreRating = dialog.findViewById(R.id.moreRating);
        final Button sendRating = dialog.findViewById(R.id.sendRating);
        ratingScale = dialog.findViewById(R.id.tvRatingScale);
        ratingBar = dialog.findViewById(R.id.ratingBar);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                ratingScale.setText(String.valueOf(rating));
                switch((int) ratingBar.getRating()){
                    case 1:
                        ratingScale.setText(getResources().getString(R.string.star1));
                        break;
                    case 2:
                        ratingScale.setText(getResources().getString(R.string.star2));
                        break;
                    case 3:
                        ratingScale.setText(getResources().getString(R.string.star3));
                        break;
                    case 4:
                        ratingScale.setText(getResources().getString(R.string.star4));
                        break;
                    case 5:
                        ratingScale.setText(getResources().getString(R.string.star5));
                        break;
                    default:
                }
            }
        });

        sendRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ratingValue = moreRating.getText().toString();
                String scale = ratingScale.getText().toString();
                float lue = ratingBar.getRating();
                String value = String.valueOf(lue);
                TextView textView = findViewById(R.id.title);
                String title = textView.getText().toString();

                String toSendValue;

                toSendValue = "Rating value " + value + "\n Scale: "+ scale + "\n Ratings: "+ ratingValue +"\n Title: "+ title;
                Log.d("ToSendNesde5666", toSendValue);

                finalvalue = toSendValue;
                if (haveNetworkConnection()){
                    new sendMessage().execute();
                }else {
                    Toast.makeText(NewsDetails.this, "No Internet Connection. Please check your network settings!", Toast.LENGTH_SHORT).show();
                }

            }
        });
        dialog.show();
    }



    class sendMessage extends AsyncTask {

        ProgressDialog progressDialog;
        JSONObject jObject;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(NewsDetails.this);
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
                Toast.makeText(NewsDetails.this, "The message has been sent, thank you..", Toast.LENGTH_SHORT).show();
                finish();
            } else{
                Toast.makeText(NewsDetails.this, "Please try again..", Toast.LENGTH_SHORT).show();
            }
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

}