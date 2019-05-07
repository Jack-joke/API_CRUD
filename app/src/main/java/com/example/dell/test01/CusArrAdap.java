package com.example.dell.test01;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.text.SimpleDateFormat;
import java.util.List;

public class CusArrAdap extends BaseAdapter {
    private Context context;
    private int layout;
    private List<Projects> myList;
    String url = "http://5cad3c9101a0b80014dcd399.mockapi.io/api/dat/projects";

    public CusArrAdap(Context context, int layout, List<Projects> myList) {
        this.context = context;
        this.layout = layout;
        this.myList = myList;
    }
    private class ViewHolder{
        TextView tv1, tv2, tv3;
        ImageView imgDelete, imgEdit;
    }
    @Override
    public int getCount() {
        return myList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        ViewHolder holder;
        if(convertView == null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout,null);
            holder.tv1 = (TextView) convertView.findViewById(R.id.cus_tv1);
            holder.tv2 = (TextView) convertView.findViewById(R.id.cus_tv2);
            holder.tv3 = (TextView) convertView.findViewById(R.id.cus_tv3);
            holder.imgEdit = (ImageView) convertView.findViewById(R.id.cus_img1);
            holder.imgDelete = (ImageView) convertView.findViewById(R.id.cus_img2);
            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }
        final Projects projects = myList.get(position);
        holder.tv1.setText(projects.getName());
        holder.tv2.setText(sdf.format(projects.getDate()));
        holder.tv3.setText(sdf.format(projects.getFinishdate()));

        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,XuLyActivity.class);
                intent.putExtra("dataEmployee", projects);
                context.startActivity(intent);
            }
        });
        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConfirmDelete(projects.getName(), String.valueOf(projects.getId()));
            }
        });
        return convertView;
    }
    private void ConfirmDelete(String lastname, final String id){
        AlertDialog.Builder dialogDelete = new AlertDialog.Builder(context);
        dialogDelete.setMessage("Do you want to remove " + lastname);
        dialogDelete.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DeleteEmployee(url, id);
            }
        });
        dialogDelete.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialogDelete.show();

    }
    private void DeleteEmployee(String url, String id){
        StringRequest stringRequest = new StringRequest(Request.Method.DELETE, url +'/' + id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(context, "Succesfully delete!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context,MainActivity.class);
                context.startActivity(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Error make by Delete method!", Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

}
