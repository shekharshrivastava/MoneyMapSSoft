package com.example.shasha.electrokart.Ui;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.util.Linkify;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shasha.electrokart.Controller.LoginController;
import com.example.shasha.electrokart.Database.BackupDatabase;
import com.example.shasha.electrokart.Model.LoginModel;
import com.example.shasha.electrokart.R;
import com.example.shasha.electrokart.Utils.PasswordEncryption;
import com.example.shasha.electrokart.Utils.UIHelper;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.sdsmdg.tastytoast.TastyToast;
//import com.sdsmdg.tastytoast.TastyToast;

import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;


/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {


    private CoordinatorLayout coordinatorLayout;
    private TextView legalInfo;
    private EditText email;
    private EditText password;
    private TextView inputTextEmail;
    private TextView inputTextPassword;
    private static final String TAG = "LoginActivity";
    private Toolbar toolbar;
    private String getAuthEmailId;
    private CheckBox showPassword;
    private CheckBox keepMeLoggedIn;
    private LoginButton loginButton;
    private CallbackManager callbackManager;
    private String mfirstName;
    private String mLastName;
    private String mUserEmail;
    private Bundle bFacebookData;
    private UIHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this);
        setContentView(R.layout.activity_sign_in_new);
       /* toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Sign In");
        getSupportActionBar().setSubtitle("Money Map");*/
//        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
//        legalInfo = (TextView) findViewById(R.id.legalInfo);
//        Linkify.addLinks(legalInfo, Linkify.ALL);
         helper = new UIHelper();

        email = (EditText) findViewById(R.id.email);
        inputTextEmail = (TextView) findViewById(R.id.inputTextEmail);
        password = (EditText) findViewById(R.id.password);
        inputTextPassword = (TextView) findViewById(R.id.inputTextPassword);
        email.addTextChangedListener(new MyTextWatcher(email));
        password.addTextChangedListener(new MyTextWatcher(password));

        keepMeLoggedIn = (CheckBox) findViewById(R.id.keepMeLoggedIn);
        showPassword = (CheckBox) findViewById(R.id.showPassword);
        LoginController controller = new LoginController(this);
        getAuthEmailId = controller.getAuthentication();

        // Set up the login form.
        AppEventsLogger.activateApp(this);


        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("email");
        // If using in a fragment
        // Other app specific specialization

        // Callback registration
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                String accessToken = loginResult.getAccessToken().getToken();
                Log.i("accessToken", accessToken);
                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {


                @Override
                public void onCompleted(JSONObject object, GraphResponse response) {
                    Log.i("LoginActivity", response.toString());
                    // Get facebook data from login
                    bFacebookData = getFacebookData(object);
                    mfirstName = bFacebookData.getString("first_name");
                    mLastName = bFacebookData.getString("last_name");
                    mUserEmail = bFacebookData.getString("email");
                }
            });
            Bundle parameters = new Bundle();
            parameters.putString("fields", "id, first_name, last_name, email,gender, birthday, location"); // Parámetros que pedimos a facebook
            request.setParameters(parameters);
            request.executeAsync();

                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                LoginModel.getInstance().setUserName(mfirstName + " " + mLastName);
                LoginModel.getInstance().setEmailId(mUserEmail);

//                intent.putExtra("user_profile_picture", bFacebookData.getString("profile_pic"));
                startActivity(intent);
                finish();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }


        });

    }


        @Override
        protected void onResume () {
            super.onResume();
            LoginController controller = new LoginController(this);
            getAuthEmailId = controller.getAuthentication();

        }

    public void signIn(View v) throws Exception {
        String encyptedPassword = null;
        String emailId = email.getText().toString();
        String userPassword = password.getText().toString();
        if (userPassword != null) {
            PasswordEncryption encryption = new PasswordEncryption();
            byte[] encryptedPwd = encryption.encrypt(userPassword);
            encyptedPassword = encryption.byteArrayToHexString(encryptedPwd);
        }
        if (!TextUtils.isEmpty(getAuthEmailId)) {
            if (!validateEmail()) {
                return;
            } else {
                LoginModel.getInstance().setEmailId(emailId);
            }
            if (!validatePassword()) {
                return;
            } else {
                LoginController controller = new LoginController(this);
                controller.getUserDetails();
                if (encyptedPassword.equals(LoginModel.getInstance().getPassword()) && getAuthEmailId.equalsIgnoreCase(emailId)) {
                    controller.getAllDataFromUser();
                    Intent intent = new Intent(this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    helper.generateToast(getApplicationContext(),"Email or password is incorrect", TastyToast.LENGTH_LONG,TastyToast.WARNING);
                   // Toast.makeText(this, "Email or password is incorrect", Toast.LENGTH_SHORT).show();
                }
            }

        } else {
            helper.generateToast(getApplicationContext(), "You are not registered", TastyToast.LENGTH_LONG,TastyToast.ERROR);
//            Toast.makeText(this, "You are not registered", Toast.LENGTH_SHORT).show();
        }

        SharedPreferences pref =
                PreferenceManager.getDefaultSharedPreferences(this);

        SharedPreferences.Editor editor = pref.edit();

        editor.putBoolean("KEY_LOGIN_STATUS", keepMeLoggedIn.isChecked() ? true : false);

        editor.commit();
    }

    public void buttonCreateUser(View v) {
        if (getAuthEmailId.isEmpty()) {
            Intent intent = new Intent(this, SignupActivity.class);
            startActivity(intent);
//            overridePendingTransition(R.anim.slide_left, R.anim.fade_out);
            finish();
        } else {
            helper.generateToast(getApplicationContext(), "You are already registered", TastyToast.LENGTH_LONG,TastyToast.SUCCESS);

//            (Toast.makeText(this, "You are already registered", Toast.LENGTH_SHORT)).show();
        }


    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        }
    }

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    private boolean validateEmail() {
        if (email.getText().toString().trim().isEmpty()) {
            inputTextEmail.setText("*emailId  is required");
            requestFocus(inputTextEmail);
            return false;
        } else {
            inputTextEmail.setText("");
        }

        return true;
    }

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    private boolean validatePassword() {
        if (password.getText().toString().trim().isEmpty()) {
            inputTextPassword.setText("*password is required");
            requestFocus(password);
            return false;
        } else {
            inputTextPassword.setText("");
        }

        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == (R.id.menu_guest)) {
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);

        }
        return true;
    }

    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.email:
                    validateEmail();
                    break;
                case R.id.password:
                    validatePassword();
                    break;

            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private Bundle getFacebookData(JSONObject object) {
        Bundle bundle = new Bundle();

        try {
            String id = object.getString("id");

            try {
                URL profile_pic = new URL("https://graph.facebook.com/" + id + "/picture?width=200&height=150");
                Log.i("profile_pic", profile_pic + "");
                bundle.putString("profile_pic", profile_pic.toString());

            } catch (MalformedURLException e) {
                e.printStackTrace();
                return null;
            }

            bundle.putString("idFacebook", id);
            if (object.has("first_name"))
                bundle.putString("first_name", object.getString("first_name"));
            if (object.has("last_name"))
                bundle.putString("last_name", object.getString("last_name"));
            if (object.has("email"))
                bundle.putString("email", object.getString("email"));
            if (object.has("gender"))
                bundle.putString("gender", object.getString("gender"));
            if (object.has("birthday"))
                bundle.putString("birthday", object.getString("birthday"));
            if (object.has("location"))
                bundle.putString("location", object.getJSONObject("location").getString("name"));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return bundle;

    }
}

