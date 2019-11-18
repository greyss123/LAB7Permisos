package com.example.permisolab7;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final int CODE_PERMISSION = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestPermision();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[]permissions,@NonNull int[] grantResults){
        if (requestCode == CODE_PERMISSION){
            if (grantResults.length > 0) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permiso de almacenamiento otorgado", Toast.LENGTH_SHORT).show();
                    initCall();

                } else {
                    //Funcionalidad desactivada
                }


                if (grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permiso de almacenamiento de llamadas otorgado", Toast.LENGTH_SHORT).show();
                    initStore();
                } else {
                    //Funcionalidad desactivada
                }
            }
        }
    }
    @Override
    public void onClick(View v){
        switch (v.getLayoutDirection()){
            case R.id.btncall:{
                callPhone();
                break;
            }
            case R.id.btnstore:{
                storeData();
                break;
            }
        }
    }
    public void callPhone () {
        TextView phone = findViewById(R.id.call);
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+ phone.getText()));
    }

    private void initStore (){
        ImageButton btn = findViewById(R.id.btnstore);
        btn.setOnClickListener(this);
    }
    private void initCall(){
        ImageButton btn = findViewById(R.id.btncall);
        btn.setOnClickListener(this);
    }

    public void storeData(){
        TextView data = findViewById(R.id.content);
        try{
            FileOutputStream files = openFileOutput("android.txt" , Activity.MODE_PRIVATE);
            OutputStreamWriter writer = new OutputStreamWriter(files);
            writer.write(data.getText().toString());
            writer.flush();
            writer.close();
            //OutputStreamWriter file - new OutputStreamWriter(openFileOutput());
        }catch (IOException e){
            e.printStackTrace();
        }
    }


    public Boolean checkPermission(String permission){
        int result = this.checkCallingOrSelfPermission(permission);
        return result == PackageManager.PERMISSION_GRANTED;
    }
    private void requestPermision(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.CALL_PHONE}, CODE_PERMISSION);
        }
        else {
            if (checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)&&
                    checkPermission(Manifest.permission.CALL_PHONE)){
                Toast.makeText(this,"Los permisos fueron otrosgados",Toast.LENGTH_LONG).show();
            }
        }
    }
}
