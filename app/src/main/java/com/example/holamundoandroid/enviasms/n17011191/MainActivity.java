package com.example.holamundoandroid.enviasms.n17011191;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText editTextTelefono;
    private EditText editTextMensaje;
    private Button buttonEnviar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        checkSMSStatePermission();

        setContentView(R.layout.activity_main);

        editTextTelefono=findViewById(R.id.editTextTelefono);
        editTextMensaje=findViewById(R.id.editTextMensaje);
        buttonEnviar=findViewById(R.id.buttonEnviar);

        buttonEnviar.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String tel = editTextTelefono.getText().toString();
                        String men = editTextMensaje.getText().toString();
                        enviarSMS(tel, men);

                        //Mandar notifiación de mensaje enviado
                        AlertDialog.Builder alerta = new AlertDialog.Builder(MainActivity.this);
                        alerta.setMessage("¡Mensaje Enviado!")
                                .setCancelable(true)
                                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.cancel();
                                    }
                                })
                                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.cancel();
                                    }
                                });
                        alerta.setTitle("Aviso");
                        alerta.show();

                    }
                }
        );
    }

    private void enviarSMS(String tel, String men){
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(tel, null, men, null, null);

    }

    private void checkSMSStatePermission(){
        int permissionCheck = ContextCompat.checkSelfPermission(
                this, Manifest.permission.SEND_SMS);
        if(permissionCheck != PackageManager.PERMISSION_GRANTED){
            Log.i("mensaje", "No tienes permiso para recibir SMS");
            ActivityCompat.requestPermissions(this,new String [] {Manifest.permission.SEND_SMS},225);
        }else{
            Log.i("mensaje","Si tienes permiso para recibir SMS");
        }
    }

}