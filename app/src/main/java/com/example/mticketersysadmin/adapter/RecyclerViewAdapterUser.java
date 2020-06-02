package com.example.mticketersysadmin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mticketersysadmin.R;
import com.example.mticketersysadmin.data.DataBaseHandler;
import com.example.mticketersysadmin.model.TockenData;
import com.example.mticketersysadmin.model.UserData;

import java.text.MessageFormat;
import java.util.List;

public class RecyclerViewAdapterUser extends RecyclerView.Adapter<RecyclerViewAdapterUser.ViewHolderUser> {

    private Context context;
    private List<UserData> userDataList;


    public RecyclerViewAdapterUser(Context context, List<UserData> userDataList1) {
        this.context = context;
        this.userDataList = userDataList1;
    }


    @NonNull
    @Override
    public ViewHolderUser onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.row_list_users,null);

        return new ViewHolderUser(view,context);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderUser holder, int position) {

        UserData userData = userDataList.get(position);

        holder.User_ID.setText(MessageFormat.format("User ID : {0}",userData.getId()));
        holder.User_Area.setText(MessageFormat.format("User Area Code : {0}", String.valueOf(userData.getArea_Code())));
        holder.User_Phone.setText(MessageFormat.format("User  Phone Number : \n{0}", userData.getPhone_Number()));

    }



    @Override
    public int getItemCount() {
        return userDataList.size();
    }

    public class ViewHolderUser extends RecyclerView.ViewHolder{

        public TextView User_ID;
        public TextView User_Area;
        public TextView User_Phone;

        private DataBaseHandler db;



        public ViewHolderUser(@NonNull View itemView,Context ctx) {
            super(itemView);

            context = ctx;

            User_ID = itemView.findViewById(R.id.user_id_row);
            User_Area = itemView.findViewById(R.id.user_area_row);
            User_Phone = itemView.findViewById(R.id.user_phone_row);

        }




    }
}
