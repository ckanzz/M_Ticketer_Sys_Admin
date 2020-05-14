package com.example.mticketersysadmin.controler;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import com.example.mticketersysadmin.data.DataBaseHandler;
import com.example.mticketersysadmin.model.MovieData;
import com.example.mticketersysadmin.model.TockenData;
import com.example.mticketersysadmin.model.UserData;

import java.util.List;

public class ReceiveSms extends BroadcastReceiver {


    private DataBaseHandler dataBaseHandler;

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context,"Rec",Toast.LENGTH_SHORT).show();

        dataBaseHandler = new DataBaseHandler(context);

        //When msg app broadcasts it automatically picks up intent and then performs the db tasks as it acts like a listner
        if(intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")){

            Bundle bundle = intent.getExtras();
            SmsMessage[] msgs = null;
            String msg_from;
            if(bundle != null){
                try{
                    //Creating pdu (Protocol Data Unit> as we operate on 2 modes GPRS/GSM and storing it into a msg from.
                    Object[] pdus = (Object[]) bundle.get("pdus");
                    msgs = new SmsMessage[pdus.length];

                    for(int i = 0; i < msgs.length ; i++){

                        //Storing the content of the msg
                        msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
                        msg_from = msgs[i].getOriginatingAddress();
                        String msgBody = msgs[i].getMessageBody();
                        Log.d("body", "onReceive: " + msgBody);
                        //Spliting msg body using space
                        String[] msgParts = msgBody.split(" ");

                        SmsManager smsManager = SmsManager.getDefault();
//                        smsManager.sendTextMessage(msg_from,null,"Incorrect Fromat Send For Registration or Movie info:\n1 Your Area code" +
//                                        "\nFor Booking send : \n2 MovieId NumberofSeats\nput 1 space between each number",
//                                null,null);

                        //Giving Automated Response based on the reply

                        //registring the user
                        if("1".equals(msgParts[0])){
                            smsManager = SmsManager.getDefault();

                            //Setting up retriving the msg items
                            UserData userData = new UserData();
                            userData.setPhone_Number(msg_from);
                            String AreaCode = msgParts[1];
                            userData.setArea_Code(Integer.parseInt(AreaCode));


                            //checking if users added
                            List<UserData> us = dataBaseHandler.getAllUsers();
                            for(UserData u : us){
                                Log.d("VALCHECK", "onReceive: " + u.getPhone_Number());
                            }

                            //Sending confrimation msg back and adding to db if new user
                            if(dataBaseHandler.user_reg(msg_from)) {
                                //int Ac = dataBaseHandler.user_area(msg_from);
                                List<String> Movie_Info_Msg_list = dataBaseHandler.getMovieInArea(Integer.parseInt(AreaCode));
                                for(String msgsend : Movie_Info_Msg_list){
                                    smsManager.sendTextMessage(msg_from, null, msgsend , null, null);
                                }

                            }
                            else{
                                dataBaseHandler.add_user(userData);
                                smsManager.sendTextMessage(msg_from, null, "Regitered To M-Tickter", null, null);
                            }

                            Toast.makeText(context,"Msg Sent",Toast.LENGTH_SHORT).show();
                            //dataBaseHandler.add_user(userData);
                        }

                        //sending back movie data to the user according to the are code
                        else if("2".equals(msgParts[0])){
                            smsManager = SmsManager.getDefault();
                            if(dataBaseHandler.user_reg(msg_from)){

                                int SelectedOption = Integer.parseInt(msgParts[1]);
                                int SelectedSeats = Integer.parseInt(msgParts[2]);
                                Log.d("MOVIE", "onReceive: " + SelectedOption + " " + SelectedSeats);

                                MovieData movieDataChosen = dataBaseHandler.getMovie(SelectedOption);
                                int Availseats = movieDataChosen.getSeats_Available();
                                Log.d("MOVIE1", "onReceive: " + SelectedOption + " " + SelectedSeats);
//                                Log.d("PLSWORK", "onReceive: " + movieDataChosen.getHall_Name());
                                int userDataBooking = dataBaseHandler.getUserID(msg_from);
                                Log.d("MOVIE2", "onReceive: " + SelectedOption + " " + userDataBooking);

                                if(SelectedSeats > dataBaseHandler.MovieSeatCount(SelectedOption)){
                                    smsManager.sendTextMessage(msg_from, null, "These many seats Unavailable Reduce Number", null, null);
                                }else{

                                    TockenData tockenData = new TockenData();

                                    tockenData.setMovie_id(SelectedOption);
                                    tockenData.setUser_id(userDataBooking);
                                    tockenData.setSeats(SelectedSeats);

                                    long tid = dataBaseHandler.add_tocken(tockenData);
                                    dataBaseHandler.MovieSeatUpdate(SelectedOption,Availseats - SelectedSeats);


                                    smsManager.sendTextMessage(msg_from, null, "Tocken Id :" + tid +
                                            "\nTickets Have been Reserved Reach 15 minutes before show time for confirmation", null, null);

                                    smsManager.sendTextMessage(movieDataChosen.getPhone_Number(), null,
                                            "Booking Done By : " + msg_from + "\nTocken ID : "
                                            + tid + "\nNumber of Seats : " + SelectedSeats + "\nMovie name : " + movieDataChosen.getMovie_name()
                                            + "\n Time : " + movieDataChosen.getTiming(), null, null);
                                }
                            }
                            else{
                                smsManager.sendTextMessage(msg_from, null, "Please Register Send:\n1 Area_Code", null, null);
                            }
                        }
                        else {
                            Log.d("WRONG", "onReceive: Wrong");
                            smsManager = SmsManager.getDefault();
                            smsManager.sendTextMessage(msg_from,null,"Incorrect msg For Registration or Movie info send:\n1 Your Area code" +
                                    "\nFor Booking send : \n2 Movie_Id Number_of_Seats\nUse 1 space between each number",
                                    null,null);
                        }


                        Toast.makeText(context,"Msg Sent",Toast.LENGTH_SHORT).show();
                        Toast.makeText(context,"From : " + msg_from + "\nBody : " + msgBody,Toast.LENGTH_SHORT).show();
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
}
