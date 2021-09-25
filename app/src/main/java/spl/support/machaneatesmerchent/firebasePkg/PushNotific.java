package spl.support.machaneatesmerchent.firebasePkg;

import com.google.firebase.messaging.FirebaseMessaging;

public class PushNotific{

    //send to all
    public void send(){
        FirebaseMessaging.getInstance().subscribeToTopic("Thanks for joining to machan Eat !");
    }


    /*//send to specific user
    FirebaseMessaging.getInstance().send(
     new RemoteMessage.Builder(SENDER_ID + "@gcm.googleapis.com")
     .setMessageId(id)
     .addData("key", "value")
     .build());

     */
}

