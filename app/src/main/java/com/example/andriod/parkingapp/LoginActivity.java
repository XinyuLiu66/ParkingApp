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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {
    private static final int REQUEST_SIGNUP = 0;
    private static final String BASE_URL = "http://10.0.2.2:8080/Parking_saver/webapi/";

    private static Map<Long, String> users = new HashMap<>();
    private String email;
    private String password;

    @Bind(R.id.input_email) EditText _emailText;
    @Bind(R.id.input_password) EditText _passwordText;
    @Bind(R.id.btn_login) Button _loginButton;
    @Bind(R.id.link_signup) TextView _signupLink;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        
        _loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newlogin();
            }
        });

        _signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }

    public void newlogin(){
        email = _emailText.getText().toString();
        password = _passwordText.getText().toString();
//        email = _emailText.getText().toString();
//        password = _passwordText.getText().toString();
        System.out.println("email: "+ email + "====================================");
        System.out.println("password: "+ password + "====================================");
        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("Enter a valid email address");
            onLoginFailed1();
            //TODO
        }
        else if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("Password shoud be between 4 and 10 alphanumeric characters");
            onLoginFailed2();
        }
        else{

            try {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL).
                                addConverterFactory(GsonConverterFactory.create())
                        .build();

                UserAPIService service = retrofit.create(UserAPIService.class);
                // get the authenticated User
                //  Call<List<User>> call = service.getUser(email,password);
                Call<List<User>> call = service.getAllUserInfos();
                call.enqueue(new Callback<List<User>>() {
                    @Override
                    public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                        System.out.println("start onResponse  "+ "================================");
                        List<User> userInfos = response.body();
                        //    System.out.println("userInfos size : "+userInfos.size()+ "================================");
                        boolean checkUser = false;
                        for(int i =0;i< userInfos.size();i++){
                            if(userInfos.get(i).getEmail().equals(email) && userInfos.get(i).getPassword().equals(password)){
                                checkUser = true;
                                break;
                            }
                        }

                        if(response.isSuccessful() && checkUser){
                            users.put(1L,"Email: "+email+"\n"+"Password: "+password+"\n\n");
                            System.out.println("response.isSuccessful()  "+ "================================");
                            _loginButton.setEnabled(false);
                            final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                                    R.style.AppTheme_Dark_Dialog);
                            progressDialog.setIndeterminate(true);
                            progressDialog.setMessage("Authenticating...");
                            progressDialog.show();

                            // TODO: Implement your own authentication logic here.

                            new android.os.Handler().postDelayed(
                                    new Runnable() {
                                        public void run() {
                                            // On complete call either onLoginSuccess or onLoginFailed
                                            onLoginSuccess();
                                            // onLoginFailed();
                                            progressDialog.dismiss();
                                        }
                                    }, 3000);
                        }
                        else{
                            _emailText.setError("Email or password you entered is incorrect,please try again!"); //TODO
                            onLoginFailed();
                        }
                    }
                    @Override
                    public void onFailure(Call<List<User>> call, Throwable t) {
                        Log.d("onFailure", t.toString());
                    }
                });

            } catch (Exception e) {
                Log.d("onResponse", "There is an error");
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        _loginButton.setEnabled(true);
        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Email or password you entered is incorrect,please try again!", Toast.LENGTH_LONG).show();

        _loginButton.setEnabled(true);
    }

    public void onLoginFailed1() {
        Toast.makeText(getBaseContext(), "Enter a valid email address!", Toast.LENGTH_LONG).show();

        _loginButton.setEnabled(true);
    }

    public void onLoginFailed2() {
        Toast.makeText(getBaseContext(), "Password shoud be between 4 and 10 alphanumeric characters!", Toast.LENGTH_LONG).show();

        _loginButton.setEnabled(true);
    }
    public static Map<Long, String> getUserMap() {
        return users;
    }
}
