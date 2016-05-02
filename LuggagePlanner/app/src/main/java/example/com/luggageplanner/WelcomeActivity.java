package example.com.luggageplanner;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class WelcomeActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{
    ImageView imageView;
    TextView myLocation,condition,temperature;
    LocationManager locationManager;
    double lat, longi;
    String apiKey="cc8d0147336eb6d1dd595389e98a93ed";
    protected GoogleApiClient mgoogleapiclient;
    private static final LatLngBounds myBounds = new LatLngBounds(new LatLng(-0,0), new LatLng(0,0));
    EditText searchWidget;
    RecyclerView myRecycler;
    LinearLayoutManager myLinearlayoutmanager;
    MyAdapter myadapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        searchWidget=(EditText)findViewById(R.id.searchWidget);
        myadapter= new MyAdapter(this,R.layout.search_widget,mgoogleapiclient,myBounds,null);
        imageView=(ImageView)findViewById(R.id.tempImage);
        myLocation =(TextView)findViewById(R.id.location);
        condition=(TextView)findViewById(R.id.condition);
        temperature=(TextView)findViewById(R.id.temperature);
        locationManager= (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (checkCallingOrSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        lat= location.getLatitude();
        longi= location.getLongitude();
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(lat, longi, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String cityName = null;
        if (addresses != null) {
            cityName = addresses.get(0).getAddressLine(2);
        }
        myLocation.setText(cityName);
//        try {
//            geocoder.getFromLocationName(cityName,5);
//            addresses.get(0).getLatitude();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


        ErrorListener errorListener= new ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        };

        Response.Listener<JSONObject> listener= new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject current = response.getJSONObject("currently");
                    double temp = current.getDouble("temperature");
                    double temperature1 = (temp-32.0)/1.8;
                    temperature.setText(String.valueOf(temperature1));
                    String contionWeather= current.getString("summary");
                    condition.setText(contionWeather);
                    String iconImage= current.getString("icon");
                    imageView.setImageResource(Constants.iconMap.get(iconImage));

                } catch (JSONException e) {
                    Toast.makeText(WelcomeActivity.this, "JSON Exception", Toast.LENGTH_SHORT).show();
                }

            }
        };

       JsonObjectRequest currentWeather = new JsonObjectRequest(Method.GET, "https://api.forecast.io/forecast/" + apiKey + "/" + lat + "," + longi,"",listener,errorListener);
        Volley.newRequestQueue(this).add(currentWeather);


    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
