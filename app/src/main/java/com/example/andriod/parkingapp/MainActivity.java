package com.example.andriod.parkingapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.andriod.parkingapp.Menu.MyInfosActivity;
import com.example.andriod.parkingapp.adapterParkingSaver.AdapterParkingSaver;
import com.example.andriod.parkingapp.data.saver.ReservationInfo;
import com.example.andriod.parkingapp.data.saver.TimeStatus;
import com.example.andriod.parkingapp.data.user.User;
import com.example.andriod.parkingapp.service.APIService;
import com.example.andriod.parkingapp.service.UserAPIService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity implements AdapterParkingSaver.ItemClickCallback{
  //  EditText editName;
   // TextView textDetails;
    Button btnGetData, btnReservation;
    private ProgressDialog pDialog;

    // for recycler view
    private RecyclerView recView;
    private AdapterParkingSaver adapter;
    Context c = this;
    AdapterParkingSaver.ItemClickCallback itemClickCallbackInMainActivity = this;
    public static List<ReservationInfo> list_totalParkingSavers = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ====set Recyclew view===
        recView = (RecyclerView)findViewById(R.id.rec_list);
      //Check out GridLayoutManager and StaggeredGridLayoutManager for more options
        recView.setLayoutManager(new LinearLayoutManager(this));

        //============================================
        System.out.println("Main start:======================================= " );
        //// TODO: 03.06.2017  
//       Intent intent = new Intent(this, LoginActivity.class);
//       startActivity(intent);

     //   textDetails = (TextView) findViewById(R.id.textDetails);
        btnGetData = (Button) findViewById(R.id.btnGetData);
        btnReservation = (Button) findViewById(R.id.btnReservation);
//        editName = (EditText) findViewById(R.id.editName);
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);

        btnGetData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getReservationInfoDetails();
              //todo  test post
             //   postIndividualParkingSaver();
                //todo test delete
          //      deleteUser();
            }
        });
        btnReservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
          //TODO
                int size  = list_totalParkingSavers.size();
                changeStatusForPut();
                for(int i =0;i<size;i++){
                    ReservationInfo tmp = list_totalParkingSavers.get(i);
                    putIndividualParkingSaver(i+1,tmp);
            }
         //  Toast.makeText(MainActivity.this, ""+"Need to implement PUT!!!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void changeStatusForPut(){
        int size  = list_totalParkingSavers.size();
        for(int i =0;i<size;i++){

            ReservationInfo tmp = list_totalParkingSavers.get(i);
            if(tmp.getTimeStatus().get_0006().equals("handling")){
                list_totalParkingSavers.get(i).getTimeStatus().set_0006("true");
            }
            if(tmp.getTimeStatus().get_0608().equals("handling")){
                list_totalParkingSavers.get(i).getTimeStatus().set_0608("true");
            }
            if(tmp.getTimeStatus().get_0810().equals("handling")){
                list_totalParkingSavers.get(i).getTimeStatus().set_0810("true");
            }
            if(tmp.getTimeStatus().get_1012().equals("handling")){
                list_totalParkingSavers.get(i).getTimeStatus().set_1012("true");
            }
            if(tmp.getTimeStatus().get_1214().equals("handling")){
                list_totalParkingSavers.get(i).getTimeStatus().set_1214("true");
            }
            if(tmp.getTimeStatus().get_1416().equals("handling")){
                list_totalParkingSavers.get(i).getTimeStatus().set_1416("true");
            }
            if(tmp.getTimeStatus().get_1618().equals("handling")){
                list_totalParkingSavers.get(i).getTimeStatus().set_1618("true");
            }
            if(tmp.getTimeStatus().get_1821().equals("handling")){
                list_totalParkingSavers.get(i).getTimeStatus().set_1821("true");
            }
            if(tmp.getTimeStatus().get_2124().equals("handling")){
                list_totalParkingSavers.get(i).getTimeStatus().set_2124("true");
            }

        }
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
        switch(item.getItemId()){
            case R.id.action_settings:
                Toast.makeText(MainActivity.this, ""+"Settings", Toast.LENGTH_SHORT).show();
                break;
            case R.id.my_infos:
                Toast.makeText(MainActivity.this, ""+"My Information", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), MyInfosActivity.class);
                startActivity(intent);
            //    finish();
                break;
            case R.id.parking_infos:
                Toast.makeText(MainActivity.this, ""+"My Parking Reservation", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * we oder the TimeStatus of every Parking-saver in a list
     * @return List<TimeStatus>
     */
    public static List<TimeStatus> getListData() {
        List<TimeStatus> list_totalDisplayData = new ArrayList<>();
        int size =list_totalParkingSavers.size();
        for(int i =0;i<size;i++){
            list_totalDisplayData.add(list_totalParkingSavers.get(i).getTimeStatus());
        }
//        System.out.println("list_totalParkingSavers.size() = "+ list_totalParkingSavers.size());
//        System.out.println("list_totalDisplayData.size() = "+ list_totalDisplayData.size());
        return list_totalDisplayData;

    }

    /**
     * GET Parking saver information
     */
    private void getReservationInfoDetails(){
        showpDialog();
        try {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://10.0.2.2:8080/Parking_saver/webapi/").
                            addConverterFactory(GsonConverterFactory.create())
                    .build();
            APIService service = retrofit.create(APIService.class);

           Call<List<ReservationInfo>> call = service.getAllReservationInfo();
            call.enqueue(new Callback<List<ReservationInfo>>() {
                @Override
                public void onResponse(Call<List<ReservationInfo>> call, Response<List<ReservationInfo>> response) {
                    List<ReservationInfo> reservationInfos = response.body();
                    System.out.println("get: reservationInfos.size = "+ reservationInfos.size());
                    // used for recyclerview
                    list_totalParkingSavers = reservationInfos;
                    //Toast.makeText(MainActivity.this, details, Toast.LENGTH_SHORT).show();
                    //textDetails.setText(details);
                    hidepDialog();
                    adapter = new AdapterParkingSaver(MainActivity.getListData(),c);
                    recView.setAdapter(adapter);
                    // set ItemCallback
                    adapter.setItemClickCallback(itemClickCallbackInMainActivity );
                }
                @Override
                public void onFailure(Call<List<ReservationInfo>> call, Throwable t) {
                    Log.d("onFailure", t.toString());
                    hidepDialog();
                }
            });
        } catch (Exception e) {
            Log.d("onResponse", "There is an error");
            e.printStackTrace();
            hidepDialog();
        }
    }

/*    private void putTest(){
        showpDialog();
        try {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://10.0.2.2:8080/Parking_saver/webapi/").
                            addConverterFactory(GsonConverterFactory.create())
                    .build();
            APIService service = retrofit.create(APIService.class);

            Call<ReservationInfo> call = service.putReservationInfo(3,list_totalParkingSavers.get(0));
            call.enqueue(new Callback<ReservationInfo>() {
                @Override
                public void onResponse(Call<ReservationInfo> call, Response<ReservationInfo> response) {
                    Toast.makeText(MainActivity.this, ""+"PUT DONE!!!", Toast.LENGTH_SHORT).show();
                    hidepDialog();
                }
                @Override
                public void onFailure(Call<ReservationInfo> call, Throwable t) {
                    Log.d("onFailure", t.toString());
                    hidepDialog();
                }
            });
        } catch (Exception e) {
            Log.d("onResponse", "There is an error");
            e.printStackTrace();
            hidepDialog();
        }
    }*/

    /**
     * PUT, Update the information of Parking-saver
     */
    //TODO
   public void putIndividualParkingSaver(int id,ReservationInfo psTochangeStatus) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/Parking_saver/webapi/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIService api = retrofit.create(APIService.class);

        Call<ReservationInfo> putIndividualParkingSaverCall = api.putReservationInfo(id,psTochangeStatus); // at this place: id from 0
        putIndividualParkingSaverCall.enqueue(new Callback<ReservationInfo>() {
            @Override
            public void onResponse(Call<ReservationInfo> call, Response<ReservationInfo> response) {
                Log.d("onResponse", "" + response.code() +
                        "  response body " + response.body() +
                        " responseError " + response.errorBody() + " responseMessage " +
                        response.message());
                // change yellow to red
                adapter = new AdapterParkingSaver(MainActivity.getListData(),c);
                recView.setAdapter(adapter);
                // set ItemCallback
                adapter.setItemClickCallback(itemClickCallbackInMainActivity );
                //=====================
                Toast.makeText(MainActivity.this, ""+"PUT DONE!!!", Toast.LENGTH_SHORT).show();
                hidepDialog();
            }
            @Override
            public void onFailure(Call<ReservationInfo> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error while fetching parkingSaver", Toast.LENGTH_SHORT).show();
            }
        });
    }
    // TODO DELETE   02.06.2017 09:40
    public void deleteUser() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/Parking_saver/webapi/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        UserAPIService api = retrofit.create(UserAPIService.class);
        //Test ID
        int id = 5;
        Call<User> deleteUserCall = api.deleteUserInfo(id);
        deleteUserCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Log.d("onResponse", "" + response.code() +
                        "  response body " + response.body() +
                        " responseError " + response.errorBody() + " responseMessage " +
                        response.message());
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error while fetching parkingSaver", Toast.LENGTH_SHORT).show();
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

    @Override
    public void onItemClick1(int p) {
        String item0006 = list_totalParkingSavers.get(p).getTimeStatus().get_0006();
        switch (item0006) {
            case "true":
                //    item.getTimeSectionFrom0().setIfReservation("false");
                Toast.makeText(getApplicationContext(), "This time space is already reserved, please try again!", Toast.LENGTH_SHORT).show();
                //    adapter.setListData((ArrayList<Item>) Data_ParkingSaver.getListData());
                //    adapter.notifyDataSetChanged();
                break;
            // user choosing the park-time
            case "handling": {
                list_totalParkingSavers.get(p).getTimeStatus().set_0006("false");
                Toast.makeText(getApplicationContext(), "Oh I see, you don't want this one!", Toast.LENGTH_SHORT).show();
                List<TimeStatus> testList = MainActivity.getListData();
                adapter.setListData((ArrayList<TimeStatus>) testList);
                adapter.notifyDataSetChanged();
                break;
            }
            // false => handling
            default: {
                list_totalParkingSavers.get(p).getTimeStatus().set_0006("handling");
                Toast.makeText(getApplicationContext(), "Are you sure?", Toast.LENGTH_SHORT).show();
                List<TimeStatus> testList = MainActivity.getListData();
                adapter.setListData((ArrayList<TimeStatus>) testList);
                adapter.notifyDataSetChanged();
                break;
            }
        }
    }

    @Override
    public void onItemClick2(int p) {
        String item0608 = list_totalParkingSavers.get(p).getTimeStatus().get_0608();
        switch (item0608) {
            case "true":
                //    item.getTimeSectionFrom0().setIfReservation("false");
                Toast.makeText(getApplicationContext(), "This time space is already reserved, please try again!", Toast.LENGTH_SHORT).show();
                //    adapter.setListData((ArrayList<Item>) Data_ParkingSaver.getListData());
                //    adapter.notifyDataSetChanged();
                break;
            // user choosing the park-time
            case "handling": {
                list_totalParkingSavers.get(p).getTimeStatus().set_0608("false");
                Toast.makeText(getApplicationContext(), "Oh I see, you don't want this one!", Toast.LENGTH_SHORT).show();
                List<TimeStatus> testList = MainActivity.getListData();
                adapter.setListData((ArrayList<TimeStatus>) testList);
                adapter.notifyDataSetChanged();
                break;
            }
            // false => handling
            default: {
                list_totalParkingSavers.get(p).getTimeStatus().set_0608("handling");
                Toast.makeText(getApplicationContext(), "Are you sure?", Toast.LENGTH_SHORT).show();
                List<TimeStatus> testList = MainActivity.getListData();
                adapter.setListData((ArrayList<TimeStatus>) testList);
                adapter.notifyDataSetChanged();
                break;
            }
        }
    }

    @Override
    public void onItemClick3(int p) {
        String item0810 = list_totalParkingSavers.get(p).getTimeStatus().get_0810();
        switch (item0810) {
            case "true":
                //    item.getTimeSectionFrom0().setIfReservation("false");
                Toast.makeText(getApplicationContext(), "This time space is already reserved, please try again!", Toast.LENGTH_SHORT).show();
                //    adapter.setListData((ArrayList<Item>) Data_ParkingSaver.getListData());
                //    adapter.notifyDataSetChanged();
                break;
            // user choosing the park-time
            case "handling": {
                list_totalParkingSavers.get(p).getTimeStatus().set_0810("false");
                Toast.makeText(getApplicationContext(), "Oh I see, you don't want this one!", Toast.LENGTH_SHORT).show();
                List<TimeStatus> testList = MainActivity.getListData();
                adapter.setListData((ArrayList<TimeStatus>) testList);
                adapter.notifyDataSetChanged();
                break;
            }
            // false => handling
            default: {
                list_totalParkingSavers.get(p).getTimeStatus().set_0810("handling");
                Toast.makeText(getApplicationContext(), "Are you sure?", Toast.LENGTH_SHORT).show();
                List<TimeStatus> testList = MainActivity.getListData();
                adapter.setListData((ArrayList<TimeStatus>) testList);
                adapter.notifyDataSetChanged();
                break;
            }
        }
    }

    @Override
    public void onItemClick4(int p) {
        String item1012 = list_totalParkingSavers.get(p).getTimeStatus().get_1012();
        switch (item1012) {
            case "true":
                //    item.getTimeSectionFrom0().setIfReservation("false");
                Toast.makeText(getApplicationContext(), "This time space is already reserved, please try again!", Toast.LENGTH_SHORT).show();
                //    adapter.setListData((ArrayList<Item>) Data_ParkingSaver.getListData());
                //    adapter.notifyDataSetChanged();
                break;
            // user choosing the park-time
            case "handling": {
                list_totalParkingSavers.get(p).getTimeStatus().set_1012("false");
                Toast.makeText(getApplicationContext(), "Oh I see, you don't want this one!", Toast.LENGTH_SHORT).show();
                List<TimeStatus> testList = MainActivity.getListData();
                adapter.setListData((ArrayList<TimeStatus>) testList);
                adapter.notifyDataSetChanged();
                break;
            }
            // false => handling
            default: {
                list_totalParkingSavers.get(p).getTimeStatus().set_1012("handling");
                Toast.makeText(getApplicationContext(), "Are you sure?", Toast.LENGTH_SHORT).show();
                List<TimeStatus> testList = MainActivity.getListData();
                adapter.setListData((ArrayList<TimeStatus>) testList);
                adapter.notifyDataSetChanged();
                break;
            }
        }
    }

    @Override
    public void onItemClick5(int p) {
        String item1214 = list_totalParkingSavers.get(p).getTimeStatus().get_1214();
        switch (item1214) {
            case "true":
                //    item.getTimeSectionFrom0().setIfReservation("false");
                Toast.makeText(getApplicationContext(), "This time space is already reserved, please try again!", Toast.LENGTH_SHORT).show();
                //    adapter.setListData((ArrayList<Item>) Data_ParkingSaver.getListData());
                //    adapter.notifyDataSetChanged();
                break;
            // user choosing the park-time
            case "handling": {
                list_totalParkingSavers.get(p).getTimeStatus().set_1214("false");
                Toast.makeText(getApplicationContext(), "Oh I see, you don't want this one!", Toast.LENGTH_SHORT).show();
                List<TimeStatus> testList = MainActivity.getListData();
                adapter.setListData((ArrayList<TimeStatus>) testList);
                adapter.notifyDataSetChanged();
                break;
            }
            // false => handling
            default: {
                list_totalParkingSavers.get(p).getTimeStatus().set_1214("handling");
                Toast.makeText(getApplicationContext(), "Are you sure?", Toast.LENGTH_SHORT).show();
                List<TimeStatus> testList = MainActivity.getListData();
                adapter.setListData((ArrayList<TimeStatus>) testList);
                adapter.notifyDataSetChanged();
                break;
            }
        }
    }

    @Override
    public void onItemClick6(int p) {
        String item1416 = list_totalParkingSavers.get(p).getTimeStatus().get_1416();
        switch (item1416) {
            case "true":
                //    item.getTimeSectionFrom0().setIfReservation("false");
                Toast.makeText(getApplicationContext(), "This time space is already reserved, please try again!", Toast.LENGTH_SHORT).show();
                //    adapter.setListData((ArrayList<Item>) Data_ParkingSaver.getListData());
                //    adapter.notifyDataSetChanged();
                break;
            // user choosing the park-time
            case "handling": {
                list_totalParkingSavers.get(p).getTimeStatus().set_1416("false");
                Toast.makeText(getApplicationContext(), "Oh I see, you don't want this one!", Toast.LENGTH_SHORT).show();
                List<TimeStatus> testList = MainActivity.getListData();
                adapter.setListData((ArrayList<TimeStatus>) testList);
                adapter.notifyDataSetChanged();
                break;
            }
            // false => handling
            default: {
                list_totalParkingSavers.get(p).getTimeStatus().set_1416("handling");
                Toast.makeText(getApplicationContext(), "Are you sure?", Toast.LENGTH_SHORT).show();
                List<TimeStatus> testList = MainActivity.getListData();
                adapter.setListData((ArrayList<TimeStatus>) testList);
                adapter.notifyDataSetChanged();
                break;
            }
        }
    }

    @Override
    public void onItemClick7(int p) {
        String item1618 = list_totalParkingSavers.get(p).getTimeStatus().get_1618();
        switch (item1618) {
            case "true":
                //    item.getTimeSectionFrom0().setIfReservation("false");
                Toast.makeText(getApplicationContext(), "This time space is already reserved, please try again!", Toast.LENGTH_SHORT).show();
                //    adapter.setListData((ArrayList<Item>) Data_ParkingSaver.getListData());
                //    adapter.notifyDataSetChanged();
                break;
            // user choosing the park-time
            case "handling": {
                list_totalParkingSavers.get(p).getTimeStatus().set_1618("false");
                Toast.makeText(getApplicationContext(), "Oh I see, you don't want this one!", Toast.LENGTH_SHORT).show();
                List<TimeStatus> testList = MainActivity.getListData();
                adapter.setListData((ArrayList<TimeStatus>) testList);
                adapter.notifyDataSetChanged();
                break;
            }
            // false => handling
            default: {
                list_totalParkingSavers.get(p).getTimeStatus().set_1618("handling");
                Toast.makeText(getApplicationContext(), "Are you sure?", Toast.LENGTH_SHORT).show();
                List<TimeStatus> testList = MainActivity.getListData();
                adapter.setListData((ArrayList<TimeStatus>) testList);
                adapter.notifyDataSetChanged();
                break;
            }
        }
    }

    @Override
    public void onItemClick8(int p) {
        String item1821 = list_totalParkingSavers.get(p).getTimeStatus().get_1821();
        switch (item1821) {
            case "true":
                //    item.getTimeSectionFrom0().setIfReservation("false");
                Toast.makeText(getApplicationContext(), "This time space is already reserved, please try again!", Toast.LENGTH_SHORT).show();
                //    adapter.setListData((ArrayList<Item>) Data_ParkingSaver.getListData());
                //    adapter.notifyDataSetChanged();
                break;
            // user choosing the park-time
            case "handling": {
                list_totalParkingSavers.get(p).getTimeStatus().set_1821("false");
                Toast.makeText(getApplicationContext(), "Oh I see, you don't want this one!", Toast.LENGTH_SHORT).show();
                List<TimeStatus> testList = MainActivity.getListData();
                adapter.setListData((ArrayList<TimeStatus>) testList);
                adapter.notifyDataSetChanged();
                break;
            }
            // false => handling
            default: {
                list_totalParkingSavers.get(p).getTimeStatus().set_1821("handling");
                Toast.makeText(getApplicationContext(), "Are you sure?", Toast.LENGTH_SHORT).show();
                List<TimeStatus> testList = MainActivity.getListData();
                adapter.setListData((ArrayList<TimeStatus>) testList);
                adapter.notifyDataSetChanged();
                break;
            }
        }
    }

    @Override
    public void onItemClick9(int p) {
        String item2124 = list_totalParkingSavers.get(p).getTimeStatus().get_2124();
        switch (item2124) {
            case "true":
                //    item.getTimeSectionFrom0().setIfReservation("false");
                Toast.makeText(getApplicationContext(), "This time space is already reserved, please try again!", Toast.LENGTH_SHORT).show();
                //    adapter.setListData((ArrayList<Item>) Data_ParkingSaver.getListData());
                //    adapter.notifyDataSetChanged();
                break;
            // user choosing the park-time
            case "handling": {
                list_totalParkingSavers.get(p).getTimeStatus().set_2124("false");
                Toast.makeText(getApplicationContext(), "Oh I see, you don't want this one!", Toast.LENGTH_SHORT).show();
                List<TimeStatus> testList = MainActivity.getListData();
                adapter.setListData((ArrayList<TimeStatus>) testList);
                adapter.notifyDataSetChanged();
                break;
            }
            // false => handling
            default: {
                list_totalParkingSavers.get(p).getTimeStatus().set_2124("handling");
                Toast.makeText(getApplicationContext(), "Are you sure?", Toast.LENGTH_SHORT).show();
                List<TimeStatus> testList = MainActivity.getListData();
                adapter.setListData((ArrayList<TimeStatus>) testList);
                adapter.notifyDataSetChanged();
                break;
            }
        }
    }

}
