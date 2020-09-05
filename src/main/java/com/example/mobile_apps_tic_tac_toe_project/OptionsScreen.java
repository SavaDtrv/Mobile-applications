package com.example.mobile_apps_tic_tac_toe_project;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

class OptionsScreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.options_screen_view);

        //Defining buttons
        Button singleplayerbutton = findViewById(R.id.singleplayerbutton);
        Button twoplayerbutton = findViewById(R.id.twoplayerbutton);

        //Setting on click listener for the buttons
        singleplayerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DisplaySinglePlayerAlert();
            }
        });

        twoplayerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DisplayTwoPlayerAlert();
            }
        });
    }

    // Alert Box for button clicks
    public void DisplaySinglePlayerAlert(){
        LayoutInflater inflater = getLayoutInflater();
        View SinglePlayerLayout = inflater.inflate(R.layout.layout_single_player_options, null);
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(SinglePlayerLayout);
        dialog.getWindow().setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.alertbackground));
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT);
        dialog.setCancelable(true);

        // setting buttons for single player options
        final Button superEasy =  SinglePlayerLayout.findViewById(R.id.supereasybutton);
        final Button easy =  SinglePlayerLayout.findViewById(R.id.easybutton);

        // setting on click listener for button
        superEasy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //change to game activity
                Intent intent = new Intent(OptionsScreen.this, PlayerVSPlayerSameDeviceOrVsCPU.class);
                Bundle b = new Bundle();
                b.putString("key", superEasy.getText().toString()); //"Super easy mode"
                intent.putExtras(b);
                startActivity(intent);

//                //CLose Menu
//                OptionsScreen.this.finish();
//                dialog.cancel();
            }
        });

        easy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //change to game activity
                Intent intent = new Intent(OptionsScreen.this, PlayerVSPlayerSameDeviceOrVsCPU.class);
                Bundle b = new Bundle();
                b.putString("key", easy.getText().toString()); //"Easy mode"
                intent.putExtras(b);
                startActivity(intent);

//                //CLose Menu
//                OptionsScreen.this.finish();
//                dialog.cancel();
            }
        });

        dialog.show();
    }

    public void DisplayTwoPlayerAlert(){
        LayoutInflater inflater = getLayoutInflater();
        View TwoPlayerLayout = inflater.inflate(R.layout.layout_two_player_options, null);
        final Dialog dialog = new Dialog(this);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(TwoPlayerLayout);
        dialog.getWindow().setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.alertbackground));
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT);
        dialog.setCancelable(true);

        // setting up buttons
        Button bluetoothbutton = TwoPlayerLayout.findViewById(R.id.wifibutton);
        Button samedevicebutton = TwoPlayerLayout.findViewById(R.id.samedevicebutton);

        // setting on click listener
        // Alert Dialog if device does not support bluetooth
        final android.app.AlertDialog.Builder noBluetoothAlertBuilder = new android.app.AlertDialog.Builder(this);

        bluetoothbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Launch wifi game
                dialog.cancel();
                BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                if(mBluetoothAdapter==null){
                    noBluetoothAlertBuilder.setMessage("Device does not support bluetooth");
                    noBluetoothAlertBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    android.app.AlertDialog noBluetoothAlert = noBluetoothAlertBuilder.create();
                    noBluetoothAlert.show();
                }
                else{

                    startActivity(new Intent(OptionsScreen.this, BluetoothProcessHandling.class));
                    //OptionsScreen.this.finish();
                }
            }
        });

        samedevicebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //switch to game activity
                startActivity(new Intent(OptionsScreen.this, PlayerVSPlayerSameDeviceOrVsCPU.class));
                //Close Menu when opening 2p game
//                OptionsScreen.this.finish();
//                dialog.cancel();
            }
        });

        dialog.show();
    }
}
