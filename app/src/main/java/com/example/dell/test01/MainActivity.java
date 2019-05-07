package com.example.dell.test01;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {
    ListView ls;
    String url = "http://5cad3c9101a0b80014dcd399.mockapi.io/api/dat/projects";
    CusArrAdap arrAdap;
    ArrayList<Projects> arr;
    String vitri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ls = findViewById(R.id.lv_item);

        arr = new ArrayList<Projects>();
        arrAdap = new CusArrAdap(this,R.layout.cus_layout, arr);
        ls.setAdapter(arrAdap);

        GetData(url);
    }
    private void GetData(String url){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for(int i = 0; i < response.length(); i++){
                    try{
                        JSONObject object = response.getJSONObject(i);
                        arr.add(new Projects(
                                object.getInt("id"),
                                object.getString("projectname"),
                                convertTimeStampToDate(object.getLong("date")),
                                convertTimeStampToDate(object.getLong("finishdate"))
                                ));
                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                }
                arrAdap.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mymenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.mnu_Add){
            startActivity(new Intent(MainActivity.this, XuLyActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
    public Date convertTimeStampToDate(long time){
        Calendar cal = Calendar.getInstance();
        TimeZone timeZone = cal.getTimeZone();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        simpleDateFormat.setTimeZone(timeZone);
        String localTime = simpleDateFormat.format(new Date(time * 1000));
        Date date = new Date();
        try {
            date = simpleDateFormat.parse(localTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

}
