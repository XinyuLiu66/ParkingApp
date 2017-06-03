package com.example.andriod.parkingapp.Menu;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.andriod.parkingapp.LoginActivity;
import com.example.andriod.parkingapp.R;

import java.util.Map;

/**
 * Created by dell on 31.05.2017.
 */

public class MyInfosActivity extends AppCompatActivity {

    LoginActivity la = new LoginActivity();
    TextView my_Infos;
    private ProgressDialog pDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myinfos);
        my_Infos = (TextView) findViewById(R.id.my_infos);
       // show personal information
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);
    //    String email = la.getEmail();
    //  getUserInfoDetail("=======================");
//        String email =  la.getEmail();
//        String password =  la.getPassword();
        Map<Long,String> map = la.getUserMap();
        String details = "";
        details += map.get(1L) +"\n\n";
        my_Infos.setText(details);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
        return super.onOptionsItemSelected(item);
    }


    private void getUserInfoDetail(String InputEmail){

        showpDialog();


//        try {
//            Retrofit retrofit = new Retrofit.Builder()
//                    .baseUrl("http://10.0.2.2:8080/Parking_saver/webapi/").
//                            addConverterFactory(GsonConverterFactory.create())
//                    .build();
//
//            UserAPIService service = retrofit.create(UserAPIService.class);

         //   Call<List<User>> call = service.getUserInfo();

//            call.enqueue(new Callback<List<User>>() {
//                @Override
//                public void onResponse(Call<List<User>> call, Response<List<User>> response) {
//                    List<User> userInfos = response.body();
//                    String details = "";
//                    for (int i = 0; i < userInfos.size(); i++) {
//                        Integer id = userInfos.get(i).getId();
//                        String email = userInfos.get(i).getEmail();
//                        String password = userInfos.get(i).getPassword();
//
//                        details += "ID: " + id + "\n" +
//                                "Email: " + email +"\n"+
//                        "Password: " + password + "\n\n";
//
//                    }
//                    //Toast.makeText(MainActivity.this, details, Toast.LENGTH_SHORT).show();
//                    my_Infos.setText(details);
//                    hidepDialog();
//                }
//
//                @Override
//                public void onFailure(Call<List<User>> call, Throwable t) {
//                    Log.d("onFailure", t.toString());
//                    hidepDialog();
//                }
//            });
//        } catch (Exception e) {
//            Log.d("onResponse", "There is an error");
//            e.printStackTrace();
//            hidepDialog();
//        }
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
