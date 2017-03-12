package com.example.rafae.rrss20;

import android.bluetooth.BluetoothAdapter;
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

import java.io.IOException;

public class BluetoothMode extends AppCompatActivity {


    private Button openDoorButton;
    private Button closeDoorButton;
    private Button onButton;
    private Button offButton;

    private BluetoothServer mBluetoothServer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth_mode);

        openDoorButton = (Button) findViewById(R.id.buttonOpenB);
        closeDoorButton = (Button) findViewById(R.id.buttonCloseB);
        onButton = (Button) findViewById(R.id.buttonOnB);
        offButton = (Button) findViewById(R.id.buttonOffB);

        mBluetoothServer = new BluetoothServer();
        mBluetoothServer.setListener(mBluetoothServerListener);

        try {
            mBluetoothServer.start();
        } catch (BluetoothServer.BluetoothServerException e) {
            e.printStackTrace();
            writeError(e.getMessage());
        }


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

            mBluetoothServer.stop();
            mBluetoothServer = null;
            finish();
            startActivity(new Intent(this, ProfileTabActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();

        mBluetoothServer.stop();
        mBluetoothServer = null;
    }

    /**
     * Bluetooth server events listener.
     */
    private BluetoothServer.IBluetoothServerListener mBluetoothServerListener =
            new BluetoothServer.IBluetoothServerListener() {
                @Override
                public void onStarted() {
                    writeMessage("waiting for client");
                    openDoorButton.setEnabled(false);
                    closeDoorButton.setEnabled(false);
                    onButton.setEnabled(false);
                    offButton.setEnabled(false);
                }

                @Override
                public void onConnected() {
                    writeMessage("connected");
                    openDoorButton.setEnabled(true);
                    closeDoorButton.setEnabled(true);
                    onButton.setEnabled(true);
                    offButton.setEnabled(true);
                }

                @Override
                public void onData(byte[] data) {
                    writeMessage(new String(data));
                }

                @Override
                public void onError(String message) {
                    writeError(message);
                }

                @Override
                public void onStopped() {
                    writeMessage("stopped");
                    openDoorButton.setEnabled(false);
                    closeDoorButton.setEnabled(false);
                    onButton.setEnabled(false);
                    offButton.setEnabled(false);
                }
            };

    public void btClick(View view){

        try {
            mBluetoothServer.start();
        } catch (BluetoothServer.BluetoothServerException e) {
            e.printStackTrace();
            writeError(e.getMessage());
        }

    }

    public void onClick(View view){

        String message = "on";
        try {
            mBluetoothServer.send(message.toString().getBytes());

        } catch (BluetoothServer.BluetoothServerException e) {
            e.printStackTrace();
            writeError(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            writeError(e.getMessage());
        }
    }

    public void offClick(View view){

        String message = "off";
        try {
            mBluetoothServer.send(message.toString().getBytes());

        } catch (BluetoothServer.BluetoothServerException e) {
            e.printStackTrace();
            writeError(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            writeError(e.getMessage());
        }
    }

    public void openClick(View view){

        String message = "open";
        try {
            mBluetoothServer.send(message.toString().getBytes());

        } catch (BluetoothServer.BluetoothServerException e) {
            e.printStackTrace();
            writeError(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            writeError(e.getMessage());
        }
    }

    public void closeClick(View view){

        String message = "close";
        try {
            mBluetoothServer.send(message.toString().getBytes());

        } catch (BluetoothServer.BluetoothServerException e) {
            e.printStackTrace();
            writeError(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            writeError(e.getMessage());
        }
    }



    private void writeMessage(String message){

        Toast.makeText(this, message , Toast.LENGTH_SHORT).show();

    }

    private void writeError(String message){
        writeMessage("ERROR: " + message);
    }
}
