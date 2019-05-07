package com.example.dell.test01;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {
    EditText edtuser, edtpass;
    Button btndn, btndk;
    String url = "http://5cad3c9101a0b80014dcd399.mockapi.io/api/dat/accounts";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        anhxa();
        btndn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login(url);
            }
        });
        btndk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Register(url);
            }
        });
    }
    void anhxa(){
        edtuser = findViewById(R.id.edt_username);
        edtpass = findViewById(R.id.edt_pass);
        btndn = findViewById(R.id.btn_DN);
        btndk = findViewById(R.id.btn_DK);
    }
    void Login(String url){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try{
                    for(int i = 0; i<response.length(); i++){
                        JSONObject jsonObject = (JSONObject)response.get(i);
                        if (jsonObject.getString("username").toString().equals(edtuser.getText().toString()) &&
                                jsonObject.getString("password").toString().equals(edtpass.getText().toString())){
                            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                            startActivity(intent);
                            return;
                        }else {
                            Toast.makeText(LoginActivity.this, "Sai mat khau hoac ten", Toast.LENGTH_SHORT).show();

                        }
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }
    void Register(String url){
        if(edtuser.getText().toString().isEmpty() || edtpass.getText().toString().isEmpty()){
            Toast.makeText(this, "Khong bo trong", Toast.LENGTH_SHORT).show();
            return;
        }
        try{
            HashMap data = new HashMap();
            data.put("username",edtuser.getText().toString());
            data.put("password",edtpass.getText().toString());

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(data), new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Toast.makeText(LoginActivity.this, "DK thanh cong", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(LoginActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                }
            });
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(jsonObjectRequest);
        }catch (Exception e){
            e.printStackTrace();
        }
        //StringRequest stringRequest = new StringRequest(Request.Method.)
    }

}
