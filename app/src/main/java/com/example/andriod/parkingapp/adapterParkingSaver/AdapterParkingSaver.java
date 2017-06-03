package com.example.andriod.parkingapp.adapterParkingSaver;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.andriod.parkingapp.R;
import com.example.andriod.parkingapp.data.saver.TimeStatus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apple on 2017/5/31.
 */

public class AdapterParkingSaver extends RecyclerView.Adapter<AdapterParkingSaver.ParkingSaverHolder>{
    private List<TimeStatus> listData;
    private LayoutInflater inflater;
    //================about ItemClickCallback==========//
    private ItemClickCallback itemClickCallback;

    private static final String[] timeSection=
            {"0:00 - 6:00", "6:00 - 8:00", "8:00 - 10:00",
                    "10:00 - 12:00", "12:00 - 14:00", "14:00 - 16:00",
                    "16:00 - 18:00", "18:00 - 21:00", "21:00 - 24:00"};

    public AdapterParkingSaver(List<TimeStatus> listData, Context c) {
        this.listData = listData;
        this.inflater = LayoutInflater.from(c);

    }

    public interface ItemClickCallback {
        //  void onItemClick(int p);   //============可能需要九个button的
        void onItemClick1(int p);
        void onItemClick2(int p);
        void onItemClick3(int p);
        void onItemClick4(int p);
        void onItemClick5(int p);
        void onItemClick6(int p);
        void onItemClick7(int p);
        void onItemClick8(int p);
        void onItemClick9(int p);

    }

    //TODO: hier been added in order to add color
    public void setListData(ArrayList<TimeStatus> exerciseList) {
        this.listData.clear();
        this.listData.addAll(exerciseList);
    }

    public void setItemClickCallback(final ItemClickCallback itemClickCallback) {
        this.itemClickCallback = itemClickCallback;
    }

    @Override
    public ParkingSaverHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.activity_recyclerview_parkingsavers,parent,false);
        return new ParkingSaverHolder(view);
    }

    @Override
    public void onBindViewHolder(ParkingSaverHolder holder, int position) {

        System.out.println("onBindViewHolder============");

        TimeStatus item = listData.get(position);
        //TEST==============
        System.out.println("-------------------------------");
        System.out.println(item.get_0006());
        System.out.println(item.get_0608());
        System.out.println(item.get_0810());
        System.out.println(item.get_1012());
        System.out.println(item.get_1214());
        System.out.println(item.get_1416());
        System.out.println(item.get_1618());
        System.out.println(item.get_1821());
        System.out.println(item.get_2124());
        System.out.println("-------------------------------");
        //==================
     //   holder.tv_parkingSaverID.setText(item.getPs_ID());
        holder.tv_parkingSaverID.setText(""+ (position+1));
        //TODO
        if(item.get_0006().equals("true")) {
            holder.bt_Row_1_Col_1.setBackgroundColor(Color.parseColor("#E43F3F"));
        }
        if(item.get_0006().equals("false")) {
            holder.bt_Row_1_Col_1.setBackgroundColor(Color.parseColor("#B3E5FC"));
        }
        // handing yellow
        if(item.get_0006().equals("handling")){
            holder.bt_Row_1_Col_1.setBackgroundColor(Color.parseColor("#FFEB3B"));
        }
        holder.bt_Row_1_Col_1.setText(timeSection[0]);


        if(item.get_0608().equals("true")) {
            holder.bt_Row_1_Col_2.setBackgroundColor(Color.parseColor("#E43F3F"));
        }
        if(item.get_0608().equals("false")) {
            holder.bt_Row_1_Col_2.setBackgroundColor(Color.parseColor("#B3E5FC"));
        }
        if(item.get_0608().equals("handling")){
            holder.bt_Row_1_Col_2.setBackgroundColor(Color.parseColor("#FFEB3B"));
        }
        holder.bt_Row_1_Col_2.setText(timeSection[1]);

        if(item.get_0810().equals("true")) {
            holder.bt_Row_1_Col_3.setBackgroundColor(Color.parseColor("#E43F3F"));
        }
        if(item.get_0810().equals("false")) {
            holder.bt_Row_1_Col_3.setBackgroundColor(Color.parseColor("#B3E5FC"));
        }
        if(item.get_0810().equals("handling")){
            holder.bt_Row_1_Col_3.setBackgroundColor(Color.parseColor("#FFEB3B"));
        }
        holder.bt_Row_1_Col_3.setText(timeSection[2]);

        if(item.get_1012().equals("true")) {
            holder.bt_Row_2_Col_1.setBackgroundColor(Color.parseColor("#E43F3F"));
        }
        if(item.get_1012().equals("false")) {
            holder.bt_Row_2_Col_1.setBackgroundColor(Color.parseColor("#B3E5FC"));
        }
        if(item.get_1012().equals("handling")){
            holder.bt_Row_2_Col_1.setBackgroundColor(Color.parseColor("#FFEB3B"));
        }
        holder.bt_Row_2_Col_1.setText(timeSection[3]);


        if(item.get_1214().equals("true")) {
            holder.bt_Row_2_Col_2.setBackgroundColor(Color.parseColor("#E43F3F"));
        }
        if(item.get_1214().equals("false")) {
            holder.bt_Row_2_Col_2.setBackgroundColor(Color.parseColor("#B3E5FC"));
        }
        if(item.get_1214().equals("handling")){
            holder.bt_Row_2_Col_2.setBackgroundColor(Color.parseColor("#FFEB3B"));
        }
        holder.bt_Row_2_Col_2.setText(timeSection[4]);


        if(item.get_1416().equals("true")) {
            holder.bt_Row_2_Col_3.setBackgroundColor(Color.parseColor("#E43F3F"));
        }
        if(item.get_1416().equals("false")) {
            holder.bt_Row_2_Col_3.setBackgroundColor(Color.parseColor("#B3E5FC"));
        }
        if(item.get_1416().equals("handling")){
            holder.bt_Row_2_Col_3.setBackgroundColor(Color.parseColor("#FFEB3B"));
        }
        holder.bt_Row_2_Col_3.setText(timeSection[5]);


        if(item.get_1618().equals("true")) {
            holder.bt_Row_3_Col_1.setBackgroundColor(Color.parseColor("#E43F3F"));
        }
        if(item.get_1618().equals("false")) {
            holder.bt_Row_3_Col_1.setBackgroundColor(Color.parseColor("#B3E5FC"));
        }
        if(item.get_1618().equals("handling")){
            holder.bt_Row_3_Col_1.setBackgroundColor(Color.parseColor("#FFEB3B"));
        }
        holder.bt_Row_3_Col_1.setText(timeSection[6]);


        if(item.get_1821().equals("true")) {
            holder.bt_Row_3_Col_2.setBackgroundColor(Color.parseColor("#E43F3F"));
        }
        if(item.get_1821().equals("false")) {
            holder.bt_Row_3_Col_2.setBackgroundColor(Color.parseColor("#B3E5FC"));
        }
        if(item.get_1821().equals("handling")){
            holder.bt_Row_3_Col_2.setBackgroundColor(Color.parseColor("#FFEB3B"));
        }
        holder.bt_Row_3_Col_2.setText(timeSection[7]);


        if(item.get_2124().equals("true")) {
            holder.bt_Row_3_Col_3.setBackgroundColor(Color.parseColor("#E43F3F"));
        }
        if(item.get_2124().equals("false")) {
            holder.bt_Row_3_Col_3.setBackgroundColor(Color.parseColor("#B3E5FC"));
        }
        if(item.get_2124().equals("handling")){
            holder.bt_Row_3_Col_3.setBackgroundColor(Color.parseColor("#FFEB3B"));
        }
        holder.bt_Row_3_Col_3.setText(timeSection[8]);

    }

    @Override
    public int getItemCount() {
        return listData.size();
    }
    //========= inner class for view holder============//
    class ParkingSaverHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView tv_parkingSaverID;
        private Button bt_Row_1_Col_1;
        private Button bt_Row_1_Col_2;
        private Button bt_Row_1_Col_3;
        private Button bt_Row_2_Col_1;
        private Button bt_Row_2_Col_2;
        private Button bt_Row_2_Col_3;
        private Button bt_Row_3_Col_1;
        private Button bt_Row_3_Col_2;
        private Button bt_Row_3_Col_3;

        private View container;


        public ParkingSaverHolder(View itemView) {
            super(itemView);
            tv_parkingSaverID =(TextView)itemView.findViewById(R.id.parkingSaverID);

            bt_Row_1_Col_1 = (Button)itemView.findViewById(R.id.Row_1_Col_1);

            System.out.println("ParkingSaverHolder============");
            // TODO  need to set color  01.06 Hu Yue

            //bt_Row_1_Col_1.setBackgroundColor(Color.parseColor("#E43F3F"));

            bt_Row_1_Col_1.setOnClickListener(this);

            bt_Row_1_Col_2 = (Button)itemView.findViewById(R.id.Row_1_Col_2);
            bt_Row_1_Col_2.setOnClickListener(this);

            bt_Row_1_Col_3 = (Button)itemView.findViewById(R.id.Row_1_Col_3);
            bt_Row_1_Col_3.setOnClickListener(this);
            bt_Row_2_Col_1 = (Button)itemView.findViewById(R.id.Row_2_Col_1);
            bt_Row_2_Col_1.setOnClickListener(this);
            bt_Row_2_Col_2 = (Button)itemView.findViewById(R.id.Row_2_Col_2);
            bt_Row_2_Col_2.setOnClickListener(this);
            bt_Row_2_Col_3 = (Button)itemView.findViewById(R.id.Row_2_Col_3);
            bt_Row_2_Col_3.setOnClickListener(this);
            bt_Row_3_Col_1 = (Button)itemView.findViewById(R.id.Row_3_Col_1);
            bt_Row_3_Col_1.setOnClickListener(this);
            bt_Row_3_Col_2 = (Button)itemView.findViewById(R.id.Row_3_Col_2);
            bt_Row_3_Col_2.setOnClickListener(this);
            bt_Row_3_Col_3 = (Button)itemView.findViewById(R.id.Row_3_Col_3);
            bt_Row_3_Col_3.setOnClickListener(this);

            container = itemView.findViewById(R.id.container_items_root);


        }

        @Override
        public void onClick(View v) {

            if(v.getId() == R.id.Row_1_Col_1) {
                itemClickCallback.onItemClick1(getAdapterPosition());
            }
            if(v.getId() == R.id.Row_1_Col_2) {
                itemClickCallback.onItemClick2(getAdapterPosition());
            }
            if(v.getId() == R.id.Row_1_Col_3) {
                itemClickCallback.onItemClick3(getAdapterPosition());
            }
            if(v.getId() == R.id.Row_2_Col_1){
                itemClickCallback.onItemClick4(getAdapterPosition());
            }
            if(v.getId() == R.id.Row_2_Col_2) {
                itemClickCallback.onItemClick5(getAdapterPosition());
            }
            if(v.getId() == R.id.Row_2_Col_3){
                itemClickCallback.onItemClick6(getAdapterPosition());
            }
            if(v.getId() == R.id.Row_3_Col_1) {
                itemClickCallback.onItemClick7(getAdapterPosition());
            }
            if(v.getId() == R.id.Row_3_Col_2) {
                itemClickCallback.onItemClick8(getAdapterPosition());
            }
            if(v.getId() == R.id.Row_3_Col_3) {
                itemClickCallback.onItemClick9(getAdapterPosition());
            }


        }
    }
}