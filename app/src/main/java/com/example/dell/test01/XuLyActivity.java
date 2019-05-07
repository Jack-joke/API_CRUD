package com.example.dell.test01;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class XuLyActivity extends AppCompatActivity implements View.OnClickListener {
    EditText edtname, edtdate, edtfinish;
    Button btnOk, btnCancel;
    String id;
    String url = "http://5cad3c9101a0b80014dcd399.mockapi.io/api/dat/projects";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xuly);
        anhxa();

        Intent intent = getIntent();
        if (intent.getSerializableExtra("dataEmployee")!=null){
            Projects projects = (Projects) intent.getSerializableExtra("dataEmployee");
            id = String.valueOf(projects.getId());
            edtname.setText(projects.getName());
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            edtdate.setText(sdf.format(projects.getDate()));
            edtfinish.setText(sdf.format(projects.getFinishdate()));
            btnOk.setText("Update");
        }
        edtdate.setOnClickListener(this);
        edtfinish.setOnClickListener(this);
        btnOk.setOnClickListener(this);
        btnCancel.setOnClickListener(this);

    }
    void anhxa(){
        edtname = findViewById(R.id.edt_name);
        edtdate = findViewById(R.id.edt_date);
        edtfinish = findViewById(R.id.edt_finish);
        btnOk = findViewById(R.id.btn_Ok);
        btnCancel = findViewById(R.id.btn_Cancel);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_Ok:
                if (btnOk.getText().equals("OK")) {
                    AddEmployee(url);
                } else {
                    UpdateEmployee(url, id);
                }
                break;
            case R.id.btn_Cancel:
                finish();
                break;
            case R.id.edt_date:
                showDatePickerDialog(edtdate);
                break;
            case R.id.edt_finish:
                showDatePickerDialog(edtfinish);
                break;
        }
    }
    private void AddEmployee(String url){
        if (edtname.getText().toString().isEmpty()
                || edtdate.getText().toString().isEmpty()
                || edtfinish.getText().toString().isEmpty()) {
            Toast.makeText(XuLyActivity.this, "Không được để rỗng", Toast.LENGTH_LONG).show();
            return;
        }
        String strStartDate = edtdate.getText().toString();
        String strFinishDate = edtfinish.getText().toString();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date startDate = sdf.parse(strStartDate);
            Date finishDate = sdf.parse(strFinishDate);
            if (finishDate.getTime() >= startDate.getTime()) {
                HashMap data = new HashMap();
                data.put("projectname", edtname.getText().toString());
                data.put("date", convertDateToTimeStamp(edtdate.getText().toString()));
                data.put("finishdate", convertDateToTimeStamp(edtfinish.getText().toString()));

                RequestQueue queue = Volley.newRequestQueue(this);
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url,
                        new JSONObject(data), new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(XuLyActivity.this, "Thêm Thành Công", Toast.LENGTH_SHORT).show();
                        startActivity( new Intent(XuLyActivity.this, MainActivity.class));
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(XuLyActivity.this, "Thêm Không Thành Công"+error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
                queue.add(jsonObjectRequest);
            } else {
                Toast.makeText(XuLyActivity.this, "FinishDay Phải Lớn Hơn StartDay", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            Toast.makeText(this, "Không Định Dạng Được Kiểu Ngày", Toast.LENGTH_SHORT).show();
        }
    }
    private void UpdateEmployee(String url, String id){
        if (edtname.getText().toString().isEmpty()
                || edtdate.getText().toString().isEmpty()
                || edtfinish.getText().toString().isEmpty()) {
            Toast.makeText(XuLyActivity.this, "Không được để rỗng", Toast.LENGTH_LONG).show();
            return;
        }
        String strStartDate = edtdate.getText().toString();
        String strFinishDate = edtfinish.getText().toString();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date startDate = sdf.parse(strStartDate);
            Date finishDate = sdf.parse(strFinishDate);
            if (finishDate.getTime() >= startDate.getTime()) {
                HashMap data = new HashMap();
                data.put("projectname", edtname.getText().toString());
                data.put("date", convertDateToTimeStamp(edtdate.getText().toString()));
                data.put("finishdate", convertDateToTimeStamp(edtfinish.getText().toString()));

                RequestQueue queue = Volley.newRequestQueue(this);
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, url +"/"+id,
                        new JSONObject(data), new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(XuLyActivity.this, "Sua Thành Công", Toast.LENGTH_SHORT).show();
                        startActivity( new Intent(XuLyActivity.this, MainActivity.class));
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(XuLyActivity.this, "Sua Không Thành Công"+error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
                queue.add(jsonObjectRequest);
            } else {
                Toast.makeText(XuLyActivity.this, "FinishDay Phải Lớn Hơn StartDay", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            Toast.makeText(this, "Không Định Dạng Được Kiểu Ngày", Toast.LENGTH_SHORT).show();
        }
    }
    public long convertDateToTimeStamp(String strDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date date =  sdf.parse(strDate);
            long timestamp = date.getTime()/1000L;
            return timestamp;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }
    public void showDatePickerDialog(final EditText edt){
        DatePickerDialog.OnDateSetListener callback = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                if (dayOfMonth >= 10) {
                    if (month >= 9) {
                        edt.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    } else {
                        edt.setText(dayOfMonth + "/0" + (month + 1) + "/" + year);
                    }
                } else {
                    if (month >= 9) {
                        edt.setText("0"+dayOfMonth + (month + 1) + "/" + year);
                    } else {
                        edt.setText("0"+dayOfMonth + "/0" + (month + 1) + "/" + year);
                    }
                }
            }
        };
        String date = "";
        if (!edt.getText().toString().isEmpty()) {
            date = edt.getText().toString();

        } else {
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            date = sdf.format(cal.getTime());
        }
        String strArrtmp[] = date.split("/");
        int year = Integer.parseInt(strArrtmp[2]);
        int month = Integer.parseInt(strArrtmp[1])-1;
        int day = Integer.parseInt(strArrtmp[0]);
        DatePickerDialog pic = new DatePickerDialog(XuLyActivity.this,
                callback,year,month,day);
        pic.show();
    }

}
