package com.example.hambre.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import com.facebook.FacebookSdk;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.login.widget.LoginButton;
import com.facebook.model.GraphUser;
import com.facebook.widget.ProfilePictureView;

import java.util.Arrays;

public class EntradaActivity extends HambreApp {

    private UiycleHelper uiHelper;
    private Session.StatusCallback callback = new Session.StatusCallback() {
        @Override
        public void call(Session session, SessionState state, Exception exception) {
            onSessionStateChanged(session, state, exception);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrada);

        uiHelper = new UiLifecycleHelper(this, callback);
        uiHelper.onCreate(savedInstanceState);

        LoginButton lb = (LoginButton) findViewById(R.id.fbLogin);
        lb.setPublishPermissions(Arrays.asList("email", "public_profile", "user_friends"));

    }

    protected void onResume() {
        super.onResume();

        Session session = Session.getActiveSession();
        if (session != null && (session.isClosed() || session.isOpened())) {

            onSessionStateChanged(session, session.getState(), null);

        }

        uiHelper.onResume();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        uiHelper.onDestroy();

        //
    }

    @Override
    public void onPause() {
        super.onPause();
        uiHelper.onPause();

    }

    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        uiHelper.onSaveInstanceState(bundle);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        uiHelper.onActivityResult(requestCode, resultCode, data);
    }

    //Methods Facebook

    public void onSessionStateChanged(final Session session, SessionState state, Exception exception){
        if(session != null && session.isOpened()){
            Log.i("Script", "Usu�rio conectado");
            Request.newMeRequest(session, new Request.GraphUserCallback() {
                @Override
                public void onCompleted(GraphUser user, Response response) {
                    if(user != null){
                        TextView tv = (TextView) findViewById(R.id.name);
                        tv.setText(user.getFirstName()+" "+user.getLastName());

                        tv = (TextView) findViewById(R.id.email);
                        tv.setText(user.getProperty("email").toString());

                        tv = (TextView) findViewById(R.id.id);
                        tv.setText(user.getId());

                        ProfilePictureView ppv = (ProfilePictureView) findViewById(R.id.fbImg);
                        ppv.setProfileId(user.getId());

                        getFriends(session);
                    }
                }
            }).executeAsync();
        }
        else{
            Log.i("Script", "Usu�rio n�o conectado");
        }
    }

    public void getFriends(Session session){
        Request.newMyFriendsRequest(session, new Request.GraphUserListCallback() {
            @Override
            public void onCompleted(List<GraphUser> users, Response response) {
                if(users != null){
                    Log.i("Script", "Friends: "+users.size());
                }
                Log.i("Script", "response: "+response);
            }
        }).executeAsync();
    }



};
