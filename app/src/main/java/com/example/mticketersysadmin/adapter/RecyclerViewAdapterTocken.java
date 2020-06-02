package com.example.mticketersysadmin.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mticketersysadmin.R;
import com.example.mticketersysadmin.data.DataBaseHandler;
import com.example.mticketersysadmin.model.MovieData;
import com.example.mticketersysadmin.model.TockenData;

import java.text.MessageFormat;
import java.util.List;

public class RecyclerViewAdapterTocken extends RecyclerView.Adapter<RecyclerViewAdapterTocken.ViewHolderTocken> {

    private Context context;
    private List<TockenData> tockenDataList;


    public RecyclerViewAdapterTocken(Context context, List<TockenData> tockenDataList1) {
        this.context = context;
        this.tockenDataList = tockenDataList1;
    }


    @NonNull
    @Override
    public ViewHolderTocken onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.row_list_tocken,null);

        return new ViewHolderTocken(view,context);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderTocken holder, int position) {

        TockenData tockenData = tockenDataList.get(position);
        holder.Tocken_ID.setText(MessageFormat.format("Token ID : {0}",tockenData.getId()));
        holder.Tocken_Movie_ID.setText(MessageFormat.format("Movie ID : {0}", tockenData.getMovie_id()));
        holder.Tocken_User_ID.setText(MessageFormat.format("User ID : {0}", String.valueOf(tockenData.getUser_id())));
        holder.Tocken_Seat.setText(MessageFormat.format("Number of seats: {0}", String.valueOf(tockenData.getSeats())));




    }



    @Override
    public int getItemCount() {
        return tockenDataList.size();
    }

    public class ViewHolderTocken extends RecyclerView.ViewHolder{

        public TextView Tocken_ID;
        public TextView Tocken_Movie_ID;
        public TextView Tocken_User_ID;
        public TextView Tocken_Seat;

        private DataBaseHandler db;



        public ViewHolderTocken(@NonNull View itemView,Context ctx) {
            super(itemView);

            context = ctx;

            Tocken_ID = itemView.findViewById(R.id.tocken_id_row);
            Tocken_Movie_ID = itemView.findViewById(R.id.tocken_movie_id_row);
            Tocken_User_ID = itemView.findViewById(R.id.tocken_user_id_row);
            Tocken_Seat = itemView.findViewById(R.id.tocken_Seat_row);




        }




    }
}
