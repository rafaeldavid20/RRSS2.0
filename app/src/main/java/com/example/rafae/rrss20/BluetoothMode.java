package com.example.rafae.rrss20;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Set;

public class BluetoothMode extends AppCompatActivity {


    private Button openDoorButton;
    private Button closeDoorButton;
    private Button onButton;
    private Button offButton;;
    private static BluetoothDevice btDevice = null;
    private static ConnectThread btconnect;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth_mode);

        openDoorButton = (Button) findViewById(R.id.buttonOpenB);
        closeDoorButton = (Button) findViewById(R.id.buttonCloseB);
        onButton = (Button) findViewById(R.id.buttonOnB);
        offButton = (Button) findViewById(R.id.buttonOffB);

        openDoorButton.setEnabled(false);
        closeDoorButton.setEnabled(false);
        onButton.setEnabled(false);
        offButton.setEnabled(false);

        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            // Device does not support Bluetooth
        }



        Set<BluetoothDevice> bondedDevices = mBluetoothAdapter.getBondedDevices();

        for (BluetoothDevice dev : bondedDevices) {
            if (dev.getName().equals("raspberrypi")) {
                btDevice = dev;
            }
        }

        btconnect = new ConnectThread(btDevice);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_bluetooth_mode, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.bt_action_settings) {

            finish();
            startActivity(new Intent(this, ProfileTabActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart(){
        super.onStart();



    }


    @Override
    protected void onResume(){
        super.onResume();

        try {
            btconnect.mmSocket.connect();
            writeMessage("Conectado");
            openDoorButton.setEnabled(true);
            closeDoorButton.setEnabled(true);
            onButton.setEnabled(true);
            offButton.setEnabled(true);
        } catch (IOException e) {
            e.printStackTrace();
            writeMessage("No fue posible la coneccion");
            try {
                btconnect.mmSocket.close();
            } catch (IOException closeException) { }
            return;
        }

    }



    @Override
    protected void onDestroy() {
        super.onDestroy();

        try {
            btconnect.mmSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void btClick(View view){

        try {
            btconnect.mmSocket.connect();
            writeMessage("Conectado");
            openDoorButton.setEnabled(true);
            closeDoorButton.setEnabled(true);
            onButton.setEnabled(true);
            offButton.setEnabled(true);
        } catch (IOException e) {
            e.printStackTrace();
            writeMessage("No fue posible la coneccion");
            try {
                btconnect.mmSocket.close();
            } catch (IOException closeException) { }
            return;
        }


    }

    public void onClick(View view){

        String message = "on";
        try {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(btconnect.mmSocket.getOutputStream(), "ASCII"));
            writer.write(message);
            writer.flush();
            writeMessage("motor encendido");
        } catch (IOException ex) {
            return;
        }
    }

    public void offClick(View view){

        String message = "off";
        try {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(btconnect.mmSocket.getOutputStream(), "ASCII"));
            writer.write(message);
            writer.flush();
            writeMessage("motor apagado");
        } catch (IOException ex) {
            return;
        }
    }

    public void openClick(View view){

        String message = "open";
        try {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(btconnect.mmSocket.getOutputStream(), "ASCII"));
            writer.write(message);
            writer.flush();
            writeMessage("puertas abiertas");
        } catch (IOException ex) {
            return;
        }
    }

    public void closeClick(View view){

        String message = "close";
        try {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(btconnect.mmSocket.getOutputStream(), "ASCII"));
            writer.write(message);
            writer.flush();
            writeMessage("puertas cerradas");
        } catch (IOException ex) {
            return;
        }
    }



    private void writeMessage(String message){

        Toast.makeText(this, message , Toast.LENGTH_SHORT).show();

    }

}
