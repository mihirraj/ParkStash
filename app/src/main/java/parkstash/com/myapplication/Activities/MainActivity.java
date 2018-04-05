package parkstash.com.myapplication.Activities;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import parkstash.com.myapplication.Objects.Location;
import parkstash.com.myapplication.R;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback, GoogleMap.OnMapClickListener {

    FloatingActionButton fab;
    boolean tap = false;
    private GoogleMap mMap;
    SharedPreferences.Editor prefsEditor;
    SharedPreferences appSharedPrefs;
    List<Location> locations = new ArrayList<Location>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(this.getApplicationContext());
        String restoredText = appSharedPrefs.getString("MyLocations", null);
        if (restoredText != null) {
            String json = appSharedPrefs.getString("MyLocations", "");
            Gson gson = new Gson();
            Type type = new TypeToken<List<Location>>() {
            }.getType();
            locations = gson.fromJson(json, type);
        } else {
            locations.add(new Location(37.309788, -121.968433, "$35"));
            locations.add(new Location(38.232041, -121.043383, "$40"));
            locations.add(new Location(37.620224, -120.918139, "$54"));
            locations.add(new Location(37.726312, -121.706957, "$42"));
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        fab = (FloatingActionButton) findViewById(R.id.fab);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!tap) {
                    Snackbar.make(view, "Editing mode: enabled", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    tap = true;
                } else {
                    Snackbar.make(view, "Editing mode: disabled", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    tap = false;
                }

            }
        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.book_spot) {
            // Handle the click action
        } else if (id == R.id.list_spot) {

        } else if (id == R.id.past_bookings) {

        } else if (id == R.id.payments) {

        } else if (id == R.id.promos) {

        } else if (id == R.id.wallet) {

        } else if (id == R.id.my_profile) {

        } else if (id == R.id.ratings) {

        } else if (id == R.id.help) {

        } else if (id == R.id.legal) {

        } else if (id == R.id.logout) {

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        mMap.setOnMapClickListener(this);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(37.309788, -121.968433), 7));

        for (int i = 0; i < locations.size(); i++) {
            Log.d("ParkStash", "onMapReady: cost " + locations.get(i).getCost());
            googleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(locations.get(i).getLatitude(), locations.get(i).getLongitude()))
                    .icon(BitmapDescriptorFactory.fromBitmap(createTag(locations.get(i).getCost())))
                    // Specifies the anchor to be at a particular point in the marker image.
                    .anchor(0.5f, 1));
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    public Bitmap createTag(String tag) {
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.pin_bruh);
        Bitmap bmp1 = Bitmap.createScaledBitmap(bmp, 120, 120, false);
        Canvas canvas1 = new Canvas(bmp1);
        // paint defines the text color, stroke width and size
        Paint color = new Paint();
        color.setTextSize(35);
        color.setColor(Color.WHITE);
        // modify canvas
        canvas1.drawText(tag, 30, 40, color);
        return bmp1;
    }


    @Override
    public void onMapClick(LatLng latLng) {
        if (tap) {
            mMap.addMarker(new MarkerOptions().position(new LatLng(latLng.latitude, latLng.longitude)).icon(BitmapDescriptorFactory.fromBitmap(createTag("$30")))
                    // Specifies the anchor to be at a particular point in the marker image.
                    .anchor(0.5f, 1));
            locations.add(new Location(latLng.latitude, latLng.longitude, "$42"));
            Gson gson = new Gson();
            String json = gson.toJson(locations);
            prefsEditor = appSharedPrefs.edit();
            prefsEditor.putString("MyLocations", json);
            prefsEditor.apply();
        }
    }

}
