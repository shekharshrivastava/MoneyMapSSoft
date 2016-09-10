package com.example.shasha.electrokart.Ui;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shasha.electrokart.Controller.RegistrationController;
import com.example.shasha.electrokart.Model.LoginModel;
import com.example.shasha.electrokart.R;
import com.example.shasha.electrokart.Utils.PasswordEncryption;
import com.example.shasha.electrokart.Model.RegistrationModel;
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


import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class SignupActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView emailOptionText;
    private TextView numberOptionText;
    private TextView checkingEncryption;
    private EditText editName;
    private byte[] encyptedText1;
    private TextView checkingDecrption;
    private EditText Signuppassword;
    private EditText Confirmpassword;
    private byte[] encyptedText2;
    private CoordinatorLayout coordinatorLayout;
    private EditText editEmailId;
    private TextView textInputName;
    private TextView textInputPassword;
    private TextView alreadyHaveAccountTv;
    private EditText textUserNumber;
    private Spinner spinnerCountryCode;
    private boolean isPasswordMatch;
    private LoginButton loginButton;
    private CallbackManager callbackManager;
    private String mfirstName;
    private String mLastName;
    private String mUserEmail;
    private TextView inputTextEmail;
    private UIHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this);

        setContentView(R.layout.registration_layout);
      /*  Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //noinspection ConstantConditions
        getSupportActionBar().setTitle("Sign Up");
        getSupportActionBar().setSubtitle("Money Map");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/
//        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        AppEventsLogger.activateApp(this);

        init();
    }


    public void init() {
        helper = new UIHelper();

        textInputName = (TextView) findViewById(R.id.textInputName);
        inputTextEmail = (TextView)findViewById(R.id.inputTextEmail);
//        TextInputLayout textInputNumber = (TextInputLayout) findViewById(R.id.textInputNumber);
        textInputPassword = (TextView) findViewById(R.id.inputTextPassword);
        editEmailId = (EditText) findViewById(R.id.editEmailId);
        editEmailId.addTextChangedListener(new MyTextWatcher(editEmailId));

        LinearLayout userContact = (LinearLayout) findViewById(R.id.userContact);
//        emailOptionText = (TextView) findViewById(R.id.emailOptionText);
//        numberOptionText = (TextView) findViewById(R.id.numberOptionText);
        editName = (EditText) findViewById(R.id.editName);
        editName.addTextChangedListener(new MyTextWatcher(editName));
        Signuppassword = (EditText) findViewById(R.id.Signuppassword);
        Signuppassword.addTextChangedListener(new MyTextWatcher(Signuppassword));
        Confirmpassword = (EditText) findViewById(R.id.Confirmpassword);
        alreadyHaveAccountTv = (TextView) findViewById(R.id.alreadyHaveAccount);
        alreadyHaveAccountTv.setOnClickListener(this);
        spinnerCountryCode = (Spinner) findViewById(R.id.spinnerCountryCode);

        ArrayAdapter<String> spinnerCountryCodesAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.CountryCodes));
        spinnerCountryCode.setAdapter(spinnerCountryCodesAdapter);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        textUserNumber = (EditText) findViewById(R.id.textUserNumber);
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
                        Bundle bFacebookData = getFacebookData(object);

                        mfirstName = bFacebookData.getString("first_name");
                        mLastName = bFacebookData.getString("last_name");
                        mUserEmail = bFacebookData.getString("email");
                    }
                });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id, first_name, last_name, email,gender, birthday, location"); // Parámetros que pedimos a facebook
                request.setParameters(parameters);
                request.executeAsync();


                Intent intent = new Intent(SignupActivity.this, HomeActivity.class);
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

    public void emailOption(View v) {
        numberOptionText.setVisibility(View.VISIBLE);
        emailOptionText.setVisibility(View.GONE);
    }

    public void numberOption(View v) {
        emailOptionText.setVisibility(View.VISIBLE);
        numberOptionText.setVisibility(View.GONE);
    }

    private boolean validateEmail() {
        if (editEmailId.getText().toString().trim().isEmpty()) {
            inputTextEmail.setText("*Email field can't be empty");
            return false;
        } else {
            inputTextEmail.setText("");
        }
        return true;
    }

    private boolean validatePassword() {
        if (Signuppassword.getText().toString().trim().isEmpty()) {
            textInputPassword.setText("*password is required");
            requestFocus(textInputPassword);
            return false;
        } else {
            textInputPassword.setText("");
        }

        return true;
    }

    private boolean validateName() {
        if (editName.getText().toString().trim().isEmpty()) {
            textInputName.setText("*name is required");
            requestFocus(textInputName);
            return false;
        } else {
            textInputName.setText("");
        }

        return true;
    }

    public void Continue(View v) {
        String userName = editName.getText().toString();
        String UserEmailId = editEmailId.getText().toString();
        String SignUpPassword = Signuppassword.getText().toString();
        String ConfirmPassword = Confirmpassword.getText().toString();
        String userPhoneNumber = textUserNumber.getText().toString();
        if (!TextUtils.isEmpty(userName) && !TextUtils.isEmpty(userPhoneNumber) && !TextUtils.isEmpty(UserEmailId)) {
            LoginModel.getInstance().setUserName(userName);
            LoginModel.getInstance().setUserNumber(userPhoneNumber);
            LoginModel.getInstance().setEmailId(UserEmailId);
        }else{
            LoginModel.getInstance().setUserName(mfirstName + " " + mLastName);
            LoginModel.getInstance().setEmailId(mUserEmail);
        }

        if (!validateEmail()) {
            return;
        } else {
            PasswordEncryption encryption = new PasswordEncryption();
            try {
                encyptedText1 = encryption.encrypt(SignUpPassword);
                encyptedText2 = encryption.encrypt(ConfirmPassword);

            } catch (Exception e) {
                e.printStackTrace();
            }
            String encyptedTextSignUpPassword = encryption.byteArrayToHexString(encyptedText1);
            String encyptedTextConfirmPassword = encryption.byteArrayToHexString(encyptedText2);

            if (encyptedTextSignUpPassword.equals(encyptedTextConfirmPassword)) {
                isPasswordMatch = true;
                RegistrationModel.getInstance().setPassword(encyptedTextSignUpPassword);

            } else {
                isPasswordMatch = false;
                helper.generateToast(getApplicationContext(), "Password does'nt match", TastyToast.LENGTH_LONG,TastyToast.INFO);
//                Toast.makeText(this, "Password does'nt match", Toast.LENGTH_SHORT).show();
            }
        }

        if (!(validateEmail())) {
            return;
        } else {
            if (UserEmailId.contains("@") && (!TextUtils.isEmpty(UserEmailId))) {
                RegistrationModel.getInstance().setEmailId(UserEmailId);
            } else {
                RegistrationModel.getInstance().setEmailId(mUserEmail);
            }

        }
        if (!validateName()) {
            return;
        } else {
            RegistrationModel.getInstance().setUserName(userName);
        }

        long date = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy");
        String dateString = sdf.format(date);
        try {
            Date loginDate = sdf.parse(dateString);
            RegistrationModel.getInstance().setLoginDate(dateString);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        RegistrationModel.getInstance().setUserNumber(spinnerCountryCode.getSelectedItem().toString() + userPhoneNumber);
        if (validateEmail() && validateName() && validatePassword() && isPasswordMatch) {

            RegistrationController controller = new RegistrationController(this);
            boolean response = controller.userRegistration();
            if (response == true && isPasswordMatch) {
                Intent intent = new Intent(this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        }

    }


    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == (android.R.id.home)) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();

        }
        return true;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.alreadyHaveAccount:
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;


        }
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
                case R.id.editEmailId:
                    validateEmail();
                    break;
                case R.id.Signuppassword:
                    validatePassword();
                    break;
                case R.id.editName:
                    validateName();
                    break;


            }
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

}