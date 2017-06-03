package com.example.andriod.parkingapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.andriod.parkingapp.data.user.User;
import com.example.andriod.parkingapp.service.UserAPIService;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";
    private static final String BASE_URL = "http://10.0.2.2:8080/Parking_saver/webapi/";
    private ProgressDialog pDialog;
    LoginActivity la = new LoginActivity();



    @Bind(R.id.input_name) EditText _nameText;
    @Bind(R.id.input_address) EditText _addressText;
    @Bind(R.id.input_email) EditText _emailText;
    @Bind(R.id.input_mobile) EditText _mobileText;
    @Bind(R.id.input_password) EditText _passwordText;
    @Bind(R.id.input_reEnterPassword) EditText _reEnterPasswordText;
    @Bind(R.id.btn_signup) Button _signupButton;
    @Bind(R.id.link_login) TextView _loginLink;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);

        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               newSignup();
            }
        });

        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }

    public void newSignup(){
        Log.d(TAG, "Signup");
        final String name = _nameText.getText().toString();
        final String address = _addressText.getText().toString();
        final String email = _emailText.getText().toString();
        final String mobile = _mobileText.getText().toString();
        final String password = _passwordText.getText().toString();
        final String reEnterPassword = _reEnterPasswordText.getText().toString();

        try{
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL).
                            addConverterFactory(GsonConverterFactory.create())
                    .build();

            UserAPIService service = retrofit.create(UserAPIService.class);
            Call<List<User>> call = service.getAllUserInfos();
            call.enqueue(new Callback<List<User>>() {
                @Override
                public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                    Log.d("onResponse", "" + response.code() +
                            "  response body " + response.body() +
                            " responseError " + response.errorBody() + " responseMessage " +
                            response.message());

                    System.out.println("start onResponse  "+ "================================");
                    List<User> userInfos = response.body();
                    boolean extistedUser = false;
                    for(int i =0;i< userInfos.size();i++){
                        if(userInfos.get(i).getEmail().equals(email) ){
                            extistedUser = true;
                            break;
                        }
                    }

                    //==============================================
                    if (name.isEmpty() || name.length() < 3) {
                        System.out.println("(name.isEmpty() || name.length() < 3)  "+" ================================");
                        _nameText.setError("At least 3 characters");
                        onSignupFailed1();
                    }
                   else if (address.isEmpty()) {
                        System.out.println("(address.isEmpty())  "+" ================================");
                        _addressText.setError("Enter a valid address");
                        onSignupFailed2();
                    }
                    else if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                        System.out.println("(email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())  "+" ================================");
                        _emailText.setError("Enter a valid email address");
                        onSignupFailed3();
                    }
                    else if (mobile.isEmpty() || mobile.length()!=10) {
                        System.out.println("(mobile.isEmpty() || mobile.length()!=10)   "+" ================================");
                        _mobileText.setError("Enter a valid 10digit mobile number");
                        onSignupFailed4();
                    }
                    else if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
                        System.out.println("(password.isEmpty() || password.length() < 4 || password.length() > 10)   "+" ================================");
                        _passwordText.setError("Password should be between 4 and 10 alphanumeric characters");
                        onSignupFailed5();
                    }
                    else if (reEnterPassword.isEmpty() || reEnterPassword.length() < 4 || reEnterPassword.length() > 10 || !(reEnterPassword.equals(password))) {
                        System.out.println("(reEnterPassword.isEmpty() || reEnterPassword.length() < 4 || reEnterPassword.length() > 10 || !(reEnterPassword.equals(password)))  "+" ================================");
                        _reEnterPasswordText.setError("Password do not match");
                        onSignupFailed6();
                    }
                    // check whether the User is already existed !!!
                    else if(response.isSuccessful() && extistedUser){
                        System.out.println("(response.isSuccessful() && extistedUser)  "+" ================================");
                        _emailText.setError("Email address is already existed!");
                        _signupButton.setEnabled(true);
                        onSignupFailed7();

                        // TODO: Implement your own authentication logic here.
                    }
                    else{
                        System.out.println("success sign up! "+" ================================");
                        // TODO POST the new User information
                          postNewUser();

                        System.out.println("postNewUser done "+" ================================");

                        final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this,
                                R.style.AppTheme_Dark_Dialog);
                        progressDialog.setIndeterminate(true);
                        progressDialog.setMessage("Signing up...");
                        progressDialog.show();
                        new android.os.Handler().postDelayed(
                                new Runnable() {
                                    public void run() {
                                        // On complete call either onLoginSuccess or onLoginFailed
                                        onSignupSuccess();
                                        progressDialog.dismiss();
                                    }
                                }, 3000);
                    }
                }

                @Override
                public void onFailure(Call<List<User>> call, Throwable t) {
                    Log.d("onFailure", t.toString());
                }
            });
        }catch (Exception e) {
            Log.d("onResponse", "There is an error");
            e.printStackTrace();
        }

    }

    public void onSignupSuccess() {
        _signupButton.setEnabled(true);
        setResult(RESULT_OK, null);
        finish();
    }

    public void onSignupFailed1() {
        Toast.makeText(getBaseContext(), "At least 3 characters", Toast.LENGTH_LONG).show();

        _signupButton.setEnabled(true);
    }
    public void onSignupFailed2() {
        Toast.makeText(getBaseContext(), "Enter a valid address", Toast.LENGTH_LONG).show();

        _signupButton.setEnabled(true);
    }
    public void onSignupFailed3() {
        Toast.makeText(getBaseContext(), "Enter a valid email address", Toast.LENGTH_LONG).show();

        _signupButton.setEnabled(true);
    }
    public void onSignupFailed4() {
        Toast.makeText(getBaseContext(), "Enter a valid 10-digit mobile number", Toast.LENGTH_LONG).show();

        _signupButton.setEnabled(true);
    }
    public void onSignupFailed5() {
        Toast.makeText(getBaseContext(), "Password should be between 4 and 10 alphanumeric characters", Toast.LENGTH_LONG).show();

        _signupButton.setEnabled(true);
    }
    public void onSignupFailed6() {
        Toast.makeText(getBaseContext(), "Password do not match", Toast.LENGTH_LONG).show();

        _signupButton.setEnabled(true);
    }
    public void onSignupFailed7() {
        Toast.makeText(getBaseContext(), "Email address is already existed!", Toast.LENGTH_LONG).show();

        _signupButton.setEnabled(true);
    }





    public void postNewUser() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/Parking_saver/webapi/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        UserAPIService api = retrofit.create(UserAPIService.class);

        // construct a new user object
        User user = new User();
        user.setPassword(_passwordText.getText().toString());
        user.setEmail(_emailText.getText().toString());
        System.out.println("in  postNewUser()"+ user.getEmail()+" ================================");
        System.out.println("in  postNewUser()"+ user.getPassword()+" ================================");

        la.getUserMap().put(1L,"Email: "+user.getEmail()+"\n"+"Password: "+user.getPassword()+"\n\n");

        System.out.println("post  la.getUserMap().size() := "+la.getUserMap().size());


       Call<User> postNewUserCall = api.postUserInfo(user);
        postNewUserCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()) {

                    Log.i(TAG, "post submitted to API." + response.body().toString());
                }
//                Log.d("onResponse", "" + response.code() +
//                        "  response body " + response.body() +
//                        " responseError " + response.errorBody() + " responseMessage " +
//                        response.message());

//                User user = response.body();
//                String detail  = "";
//                String email = user.getEmail();
//                String password=user.getPassword();
//                int id = user.getId();
//                //add to  LoginActivity private static Map<Long, String> users = new HashMap<>();
//                la.getUserMap().put(1L,"Email: "+email+"\n"+"Password: "+password+"\n\n");
//
//                System.out.println("post  la.getUserMap().size() := "+la.getUserMap().size());
            }
            //TODO  need to do ! Error
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error while posting new user", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }



}