package com.thinkbold.abenry;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.Calendar;

public class Services extends AppCompatActivity {

    TextView tapconnect, greetings;
    LinearLayout quicklayout;
    CardView corporateLayout, iplayout, litlayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);

        corporateLayout = findViewById(R.id.corporateLayout);
        corporateLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(Services.this);
                dialog.setContentView(R.layout.corporate_content);
                dialog.getWindow().setLayout(android.app.ActionBar.LayoutParams.MATCH_PARENT, android.app.ActionBar.LayoutParams.WRAP_CONTENT);
                Button buttonSubmit;
                buttonSubmit =  dialog.findViewById(R.id.buttonSubmit);
                buttonSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Services.this, Contact.class);
                        startActivity(intent);
                    }
                });
                dialog.show();
            }
        });
        iplayout = findViewById(R.id.iplayout);
        iplayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(Services.this);
                dialog.setContentView(R.layout.ip_content);
                dialog.getWindow().setLayout(android.app.ActionBar.LayoutParams.MATCH_PARENT, android.app.ActionBar.LayoutParams.WRAP_CONTENT);
                Button buttonSubmit;
                buttonSubmit =  dialog.findViewById(R.id.buttonSubmit);
                buttonSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Services.this, Contact.class);
                        startActivity(intent);
                    }
                });
                dialog.show();
            }
        });
        litlayout = findViewById(R.id.litlayout);
        litlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(Services.this);
                dialog.setContentView(R.layout.litigation_content);
                dialog.getWindow().setLayout(android.app.ActionBar.LayoutParams.MATCH_PARENT, android.app.ActionBar.LayoutParams.WRAP_CONTENT);
                Button buttonSubmit;
                buttonSubmit =  dialog.findViewById(R.id.buttonSubmit);
                buttonSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Services.this, Contact.class);
                        startActivity(intent);
                    }
                });
                dialog.show();
            }
        });

        tapconnect = findViewById(R.id.tapConnect);
        greetings = findViewById(R.id.greetings);
        quicklayout = findViewById(R.id.quicklayout);

        tapconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottomSheetDialog();
            }
        });

        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);

        if(timeOfDay >= 0 && timeOfDay < 12){
            greetings.setText(R.string.serve);
        }else if(timeOfDay >= 12 && timeOfDay < 16){
            greetings.setText(R.string.goodafternoon);
        }else if(timeOfDay >= 16 && timeOfDay < 21){
            greetings.setText(R.string.goodevening);
        }else if(timeOfDay >= 21 && timeOfDay < 24){
            greetings.setText(R.string.goodnight);
        }


        BottomNavigationView bottomNavigationView = findViewById(R.id.menu_nav);
        //setting the Home selected
        bottomNavigationView.setSelectedItemId(R.id.praatice_navigation);
        //performing item selection
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.praatice_navigation:
                        return true;
                    case R.id.home_navigation:
                        startActivity(new Intent(getApplicationContext(), Home.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.news_navigation:
                        startActivity(new Intent(getApplicationContext(), News.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.contact_navigation:
                        startActivity(new Intent(getApplicationContext(), Contact.class));
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
                intentFeedback.putExtra(Intent.EXTRA_SUBJECT, "Hello Abenry");
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
                        Toast.makeText(getApplicationContext(), "Please install a maps application", Toast.LENGTH_LONG).show();
                    }
                }
                break;
            default:

        }
    }

    private void showBottomSheetDialog() {

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this, R.style.SheetDialog);
        bottomSheetDialog.setContentView(R.layout.quickass);

        //R.style.SheetDialog

        LinearLayout calling = bottomSheetDialog.findViewById(R.id.calling);

        calling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = "+2552129461";
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                startActivity(intent);
                bottomSheetDialog.dismiss();
            }
        });

        LinearLayout emailing = bottomSheetDialog.findViewById(R.id.emailing);
        emailing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] emails = {"info@abenry.co.tz"};
                Intent intentFeedback = new Intent(Intent.ACTION_SEND);
                intentFeedback.setType("rfc/822");
                intentFeedback.putExtra(Intent.EXTRA_EMAIL, emails);
                intentFeedback.putExtra(Intent.EXTRA_SUBJECT, "Hello Abenry");
                intentFeedback.putExtra(Intent.EXTRA_TEXT, "");
                startActivity(Intent.createChooser(intentFeedback, "Send Email With:"));
                bottomSheetDialog.dismiss();
            }
        });
        LinearLayout locating = bottomSheetDialog.findViewById(R.id.emailing);
        locating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                        Toast.makeText(getApplicationContext(), "Please install a maps application", Toast.LENGTH_LONG).show();
                    }
                }
                bottomSheetDialog.dismiss();
            }
        });


        bottomSheetDialog.show();
        bottomSheetDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        bottomSheetDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

    }

    //For handling practice content

}