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
import android.widget.EditText;
import android.widget.Toast;

import com.example.andriod.parkingapp.Menu.MyInfosActivity;
import com.example.andriod.parkingapp.adapterParkingSaver.AdapterParkingSaver;
import com.example.andriod.parkingapp.data.reservationInfo.Entry;
import com.example.andriod.parkingapp.data.reservationInfo.ReservationInfo;
import com.example.andriod.parkingapp.data.reservationInfo.TimeStatus;
import com.example.andriod.parkingapp.data.userInfo.User;
import com.example.andriod.parkingapp.model.Data_ParkingSaver;
import com.example.andriod.parkingapp.model.Item;
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
    EditText editName;
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
//        Intent intent = new Intent(this, LoginActivity.class);
//        startActivity(intent);

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
           Toast.makeText(MainActivity.this, ""+"Need to implement PUT!!!", Toast.LENGTH_SHORT).show();
            }
        });

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


    private void getReservationInfoDetails(){

        showpDialog();
        try {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://10.0.2.2:8080/Parking_saver/webapi/").
                            addConverterFactory(GsonConverterFactory.create())
                    .build();

            APIService service = retrofit.create(APIService.class);

           Call<List<ReservationInfo>> call = service.getAllReservationInfo();
        //      Call<List<ReservationInfo>> call = (Call<List<ReservationInfo>>) service.getAllReservationInfo();

            call.enqueue(new Callback<List<ReservationInfo>>() {
                @Override
                public void onResponse(Call<List<ReservationInfo>> call, Response<List<ReservationInfo>> response) {
                    List<ReservationInfo> reservationInfos = response.body();
                    // used for recyclerview
                    list_totalParkingSavers = reservationInfos;

//                    String details = "";
//                    for (int i = 0; i < reservationInfos.size(); i++) {
//                        Integer id = reservationInfos.get(i).getId();
//                        String coordinate = reservationInfos.get(i).getCoordinate();
//                        TimeStatus ts = reservationInfos.get(i).getTimeStatus();
//
//                        details += "ID: " + id + "\n" +
//                                "coordinate: " + coordinate +"\n"+
//                                "TimeStatus: " + ts.getEntry().get(0).getKey()+"  "+ ts.getEntry().get(0).getValue() + "\n\n";
//
//
//                    }
                    //Toast.makeText(MainActivity.this, details, Toast.LENGTH_SHORT).show();
                    //textDetails.setText(details);
                    hidepDialog();
                    adapter = new AdapterParkingSaver(Data_ParkingSaver.getListData(),c);
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

    // TODO POST
    public void postIndividualParkingSaver() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/Parking_saver/webapi/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIService api = retrofit.create(APIService.class);

        // construct a new parkingSaver object
        ReservationInfo reservationInfo = new ReservationInfo();
        Entry entry1 = new Entry();
        Entry entry2 = new Entry();
        Entry entry3 = new Entry();
        Entry entry4 = new Entry();
        Entry entry5 = new Entry();
        Entry entry6 = new Entry();

        entry1.setKey("00-08");
        entry1.setValue("open");

        entry2.setKey("08-10");
        entry2.setValue("open");


        ArrayList<Entry> entries = new ArrayList<Entry>() ;
        entries.add(entry1);
        entries.add(entry2);

        TimeStatus ts = new TimeStatus();
        ts.setEntry(entries);
        reservationInfo.setTimeStatus(ts);;
        reservationInfo.setCoordinate("(5,5)");
        reservationInfo.setId(5);

        Call<ReservationInfo> postIndividualParkingSaverCall = api.postReservationInfo(reservationInfo);

        postIndividualParkingSaverCall.enqueue(new Callback<ReservationInfo>() {
            @Override
            public void onResponse(Call<ReservationInfo> call, Response<ReservationInfo> response) {
                Log.d("onResponse", "" + response.code() +
                        "  response body " + response.body() +
                        " responseError " + response.errorBody() + " responseMessage " +
                        response.message());

                ReservationInfo parkingSaver = response.body();
                String detail  = "";
             //   String timeStatus = parkingSaver.getTimeStatus();
                TimeStatus timeStatus = parkingSaver.getTimeStatus();
                String coordination=parkingSaver.getCoordinate();
                int id = parkingSaver.getId();

                detail += "ID: "+ id + "\n" + "Coordination: " + coordination + "\n"
                        + "TimeStatus: " + timeStatus.getEntry().get(0) + "\n" + "\n\n";
                //textDetails.setText(detail);
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

           //     User user = response.body();
//                String detail  = "";
//                detail += "ID: "+ user.getId() + "\n" + "Emil: " + user.getEmail() + "\n" + "\n\n";
//                //textDetails.setText(detail);
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

        Item item = (Item) Data_ParkingSaver.getListData().get(p);
        if (Boolean.valueOf(item.getTimeSectionFrom0().isIfReservation())){
            item.getTimeSectionFrom0().setIfReservation("false");
            Toast.makeText(getApplicationContext(),"This time space is already reserved, please try again!",Toast.LENGTH_SHORT).show();
        }  else {

            item.getTimeSectionFrom0().setIfReservation("true");
            Toast.makeText(getApplicationContext(),"Reservation success",Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onItemClick2(int p) {

        Item item = (Item) Data_ParkingSaver.getListData().get(p);
        if (Boolean.valueOf(item.getTimeSectionFrom6().isIfReservation())){
            item.getTimeSectionFrom6().setIfReservation("false");
            Toast.makeText(getApplicationContext(),"This time space is already reserved, please try again!",Toast.LENGTH_SHORT).show();
        }  else {
            item.getTimeSectionFrom6().setIfReservation("true");
            Toast.makeText(getApplicationContext(),"Reservarion success",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onItemClick3(int p) {
        Item item = (Item) Data_ParkingSaver.getListData().get(p);
        if (Boolean.valueOf(item.getTimeSectionFrom8().isIfReservation())){
            item.getTimeSectionFrom8().setIfReservation("false");
            Toast.makeText(getApplicationContext(),"This time space is already reserved, please try again!",Toast.LENGTH_SHORT).show();
        }  else {
            item.getTimeSectionFrom8().setIfReservation("true");
            Toast.makeText(getApplicationContext(),"Reservarion success",Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onItemClick4(int p) {
        Item item = (Item) Data_ParkingSaver.getListData().get(p);
        if (Boolean.valueOf(item.getTimeSectionFrom10().isIfReservation())){
            item.getTimeSectionFrom10().setIfReservation("false");
            Toast.makeText(getApplicationContext(),"This time space is already reserved, please try again!",Toast.LENGTH_SHORT).show();
        }  else {
            item.getTimeSectionFrom10().setIfReservation("true");
            Toast.makeText(getApplicationContext(),"Reservarion success",Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onItemClick5(int p) {
        Item item = (Item) Data_ParkingSaver.getListData().get(p);
        if (Boolean.valueOf(item.getTimeSectionFrom12().isIfReservation())){
            item.getTimeSectionFrom12().setIfReservation("false");
            Toast.makeText(getApplicationContext(),"This time space is already reserved, please try again!",Toast.LENGTH_SHORT).show();
        }  else {
            item.getTimeSectionFrom12().setIfReservation("true");
            Toast.makeText(getApplicationContext(),"Reservarion success",Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onItemClick6(int p) {
        Item item = (Item) Data_ParkingSaver.getListData().get(p);
        if (Boolean.valueOf(item.getTimeSectionFrom14().isIfReservation())){
            item.getTimeSectionFrom14().setIfReservation("false");
            Toast.makeText(getApplicationContext(),"This time space is already reserved, please try again!",Toast.LENGTH_SHORT).show();
        }  else {
            item.getTimeSectionFrom14().setIfReservation("true");
            Toast.makeText(getApplicationContext(),"Reservarion success",Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onItemClick7(int p) {
        Item item = (Item) Data_ParkingSaver.getListData().get(p);
        if (Boolean.valueOf(item.getTimeSectionFrom16().isIfReservation())){
            item.getTimeSectionFrom16().setIfReservation("false");
            Toast.makeText(getApplicationContext(),"This time space is already reserved, please try again!",Toast.LENGTH_SHORT).show();
        }  else {
            item.getTimeSectionFrom16().setIfReservation("true");
            Toast.makeText(getApplicationContext(),"Reservarion success",Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onItemClick8(int p) {
        Item item = (Item) Data_ParkingSaver.getListData().get(p);
        if (Boolean.valueOf(item.getTimeSectionFrom18().isIfReservation())){
            item.getTimeSectionFrom18().setIfReservation("false");
            Toast.makeText(getApplicationContext(),"This time space is already reserved, please try again!",Toast.LENGTH_SHORT).show();
        }  else {
            item.getTimeSectionFrom18().setIfReservation("true");
            Toast.makeText(getApplicationContext(),"Reservarion success",Toast.LENGTH_SHORT).show();
        }

    }
    @Override
    public void onItemClick9(int p) {

        Item item = (Item) Data_ParkingSaver.getListData().get(p);
        if (Boolean.valueOf(item.getTimeSectionFrom21().isIfReservation())){
            item.getTimeSectionFrom21().setIfReservation("false");
            Toast.makeText(getApplicationContext(),"This time space is already reserved, please try again!",Toast.LENGTH_SHORT).show();
        }  else {
            item.getTimeSectionFrom21().setIfReservation("true");
            Toast.makeText(getApplicationContext(),"Reservarion success",Toast.LENGTH_SHORT).show();
        }

    }


}
