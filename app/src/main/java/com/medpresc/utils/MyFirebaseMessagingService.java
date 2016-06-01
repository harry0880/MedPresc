/**
 * Copyright 2016 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.medpresc.utils;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.medpresc.DbHandler;
import com.medpresc.R;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";
    public static final String ACTION_1 = "action_1";
    DbHandler db;
    String messageBody,patientid,DocId,appId;
    static int Unique_Integer=0;

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // TODO(developer): Handle FCM messages here.
        // If the application is in the foreground handle both data and notification messages here.
        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
       /* Log.d(TAG, "From: " + remoteMessage.getFrom());
        Log.d(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());*/
        sendNotification(remoteMessage);
    }
    // [END receive_message]

    /**
     * Create and show a simple notification containing the received FCM message.
     *

     */
    private void sendNotification(RemoteMessage remoteMessage) {
        db=new DbHandler(getApplicationContext());
         messageBody=remoteMessage.getData().get("message");
      DocId=remoteMessage.getData().get("docotrid");
         patientid=remoteMessage.getData().get("patientId");
         appId=remoteMessage.getData().get("AppoinmentNumber");
        Intent intent = new Intent();
        intent.setAction(ACTION_1);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getService(this, 0,
                intent, PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_stat_ic_notification)
                .setContentTitle("Medical")
                .setContentText("New Appointment")
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .addAction(R.drawable.ic_check,"Accept",pendingIntent)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(messageBody))
                ;

// Moves the expanded layout object into the notification object.


        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(Unique_Integer /* ID of notification */, notificationBuilder.build());
        Unique_Integer++;
    }

    public  class NotificationActionService extends IntentService {

        public NotificationActionService() {
            super(NotificationActionService.class.getSimpleName());
        }

        @Override
        protected void onHandleIntent(Intent intent) {

            String action = intent.getAction();
            NotificationManagerCompat.from(this).cancel(Unique_Integer);
            if (ACTION_1.equals(action)) {
         /*   db.CallWebService_AppointmentConfirm(DocId,patientid,appId,"Yes");*/
                NotificationManagerCompat.from(this).cancel(Unique_Integer);
                // TODO: handle action 1.
                // If you want to cancel the notification: NotificationManagerCompat.from(this).cancel(NOTIFICATION_ID);
            }
        }
    }
}
