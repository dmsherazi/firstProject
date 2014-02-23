package com.astrolabe.iremote;

/**
 * Created by Abu-Umar on 12/17/13.
 */

import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;


public class TCPClient {

    private String serverMessage;
    public static String SERVERIP = "192.168.0.18"; //your computer IP address
    public static int SERVERPORT = 20108;
    private OnMessageReceived mMessageListener = null;
    private boolean mRun = false;
    private boolean mKill = false;
    int testGit;
    PrintWriter out;
    public boolean isConnected = false;
    public BufferedReader in;
    public Socket socket;
    // Context ourContext;

    /**
     * Constructor of the class. OnMessagedReceived listens for the messages received from server
     */
    public void killTask(boolean bKill) {
        if (mKill) bKill = true;
        else bKill = false;
    }

    public TCPClient(OnMessageReceived listener) {
        mMessageListener = listener;
        //ourContext=context;
    }

    /**
     * Sends the message entered by client to the server
     *
     * @param message text entered by client
     */
    public void sendMessage(String message) {
        if (out != null && !out.checkError()) {
            out.println(message);
            Log.e("tcp client ", "message sending" + message);
            out.flush();
        }
    }

    public void stopClient() {
        if (socket != null) {
            try {

                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        mRun = false;
    }

    public int run(String siteNumber) {

        mRun = true;
        int returnVal = 0;

        try {
            //here you must put your computer's IP address.
            SERVERIP = "";
            Log.e("EEEEEE", siteNumber + "-o1.dyndns-ip.com");
            InetAddress serverAddr = InetAddress.getByName(siteNumber + "-o1.dyndns-ip.com");
            //.getHostAddress()
            isConnected = true;
            Log.e("TCP Client", "C: Connecting...");

            //create a socket to make the connection with the server
            SERVERPORT = 6666;
            socket = new Socket(serverAddr, 6666);

            try {

                //send the message to the server

                out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);

                //receive the message which the server sends back
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                //in this while the client listens for the messages sent by the server
                while (mRun) {

                    String total = "";
                    //BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));;
                    while (total.length() < 360 && (total.endsWith("!!") == false)) {
                        //Todo:  correct it after corrections KAS.cpp


                        // if the string is less then 160 chars long and not ending with !!
                        int c = in.read(); // read next char in buffer
                        if (c == -1)
                            break; // in.read() return -1 if the end of the buffer was reached
                        total += (char) c; // add char to string

                    }
                    serverMessage = total;
                    Log.e("tcp client ", "message rec" + serverMessage);
                    if (serverMessage != null && mMessageListener != null) {
                        //call the method messageReceived from MyActivity class
                        mMessageListener.messageReceived(serverMessage);
                    }
                    serverMessage = null;

                }


                Log.e("RESPONSE FROM SERVER", "S: Received Message: '" + serverMessage + "'");


            } catch (Exception e) {


            } finally {
                //the socket must be closed. It is not possible to reconnect to this socket
                // after it is closed, which means a new socket instance has to be created.
                socket.close();
            }

        } catch (UnknownHostException e) {
            Log.e("Connection", "" + e.toString());
            isConnected = false;
            returnVal = 1;

        } catch (IOException e) {
            Log.e("Connection", "" + e.toString());
            isConnected = false;
            returnVal = 1;

        } catch (Exception e) {
            Log.e("Connection", "" + e.toString());
            isConnected = false;
            returnVal = 1;


        }
        return returnVal;

    }

    //Declare the interface. The method messageReceived(String message) will must be implemented in the MyActivity
    //class at on asynckTask doInBackground
    public interface OnMessageReceived {
        public void messageReceived(String message);
    }
}