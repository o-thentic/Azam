package com.tanzakesho.azam;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;


/**
 * Created by othmankulindwa on 5/4/16.
 */
public class MessageReceiver extends BroadcastReceiver {

    public static final String THE_MESSAGE = "the_message";
    private static final int mID = 1;
    private int mDuration = 4000;
    private int mCounter = 0;

    @Override
    public void onReceive(final Context context, Intent intent) {

        new Thread(){
            @Override
            public void run() {
                super.run();
                while(mCounter < mDuration){
                    try {
                        sleep(100);
                        mCounter += 100;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        if(mCounter >= mDuration)
                            sendMessage(context);
                    }
                }

            }
        }.start();
    }

    public void sendMessage(Context context){
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context.getApplicationContext())
                .setSmallIcon(R.drawable.wallet)
                .setContentTitle("Azam Marine Receipt")
                .setContentText("Your Payment has been received\n" +
                        "thank you for your business")
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true);

        Intent mIntent = new Intent(context.getApplicationContext(), Messages.class);

        TaskStackBuilder tBuilder = TaskStackBuilder.create(context.getApplicationContext());
        tBuilder.addParentStack(Messages.class);
        tBuilder.addNextIntent(mIntent);
        PendingIntent pIntent = tBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        mBuilder.setContentIntent(pIntent);

        NotificationManager mManager =  (NotificationManager) context.getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        mManager.notify(mID, mBuilder.build());

        Log.i("TAG", "Broadcast Received");
    }
}
