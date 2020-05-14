package com.example.mticketersysadmin.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mticketersysadmin.Movie_List;
import com.example.mticketersysadmin.R;
import com.example.mticketersysadmin.data.DataBaseHandler;
import com.example.mticketersysadmin.model.MovieData;

import java.text.MessageFormat;
import java.util.List;

public class RecyclerViewAdapterMovie extends RecyclerView.Adapter<RecyclerViewAdapterMovie.ViewHolderMovie> {

    private Context context;
    private List<MovieData> movie_lists;
    private AlertDialog.Builder builder;
    private AlertDialog alertDialog;

    public RecyclerViewAdapterMovie(Context context, List<MovieData> movie_lists) {
        this.context = context;
        this.movie_lists = movie_lists;
    }


    @NonNull
    @Override
    public ViewHolderMovie onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.row_list_movie,null);

        return new ViewHolderMovie(view,context);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderMovie holder, int position) {

        MovieData movieData = movie_lists.get(position);
        holder.Movie_ID.setText(MessageFormat.format("Movie ID : {0}",movieData.getId()));
        holder.Movie_Name.setText(MessageFormat.format("Movie Name : {0}", movieData.getMovie_name()));
        holder.Movie_Area_Code.setText(MessageFormat.format("Area Code : {0}", String.valueOf(movieData.getArea_Code())));
        holder.Movie_Time_Slot.setText(MessageFormat.format("Time Slot : {0}", String.valueOf(movieData.getTime_Slot())));
        holder.Movie_Seats.setText(MessageFormat.format("Seats Available : {0}", String.valueOf(movieData.getSeats_Available())));
        holder.Movie_Phone_Number.setText(MessageFormat.format("Phone Number : {0}", movieData.getPhone_Number()));
        holder.Movie_Timings.setText(MessageFormat.format("Timings : {0}", movieData.getTiming()));
        holder.Movie_Hall.setText(MessageFormat.format("Theater Name : {0}", movieData.getHall_Name()));



    }



    @Override
    public int getItemCount() {
        return movie_lists.size();
    }

    public class ViewHolderMovie extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView Movie_ID;
        public TextView Movie_Name;
        public TextView Movie_Area_Code;
        public TextView Movie_Time_Slot;
        public TextView Movie_Seats;
        public TextView Movie_Phone_Number;
        public TextView Movie_Timings;
        public TextView Movie_Hall;
        public Button Del_Button;
        public Button Edit_Button;
        private DataBaseHandler db;
        public int id;


        public ViewHolderMovie(@NonNull View itemView,Context ctx) {
            super(itemView);

            context = ctx;

            Movie_ID = itemView.findViewById(R.id.movie_id_row);
            Movie_Name = itemView.findViewById(R.id.movie_name_row);
            Movie_Area_Code = itemView.findViewById(R.id.movie_area_code_row);
            Movie_Time_Slot = itemView.findViewById(R.id.movie_time_slot_row);
            Movie_Seats = itemView.findViewById(R.id.movie_seats_row);
            Movie_Phone_Number = itemView.findViewById(R.id.movie_phone_row);
            Movie_Timings = itemView.findViewById(R.id.movie_timing_row);
            Movie_Hall = itemView.findViewById(R.id.movie_hall_name_row);
            Del_Button = itemView.findViewById(R.id.del_Button);
            Edit_Button = itemView.findViewById(R.id.edit_Button);

            Del_Button.setOnClickListener(this);
            Edit_Button.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {

            int pos = getAdapterPosition();
            switch (view.getId()){
                case R.id.del_Button :  pos = getAdapterPosition();
                                        MovieData movieData = movie_lists.get(pos);
                                        id = movieData.getId();
                                        delMovie(id);
                                        break;
                case R.id.edit_Button : pos = getAdapterPosition();
                                        movieData = movie_lists.get(pos);
                                        EditMovie(movieData);
                    break;

            }

        }

        private void EditMovie(final MovieData movieData) {

            builder = new AlertDialog.Builder(context);
            View view2 = LayoutInflater.from(context).inflate(R.layout.pop_up,null);

            final EditText MovieName = view2.findViewById(R.id.movie_name);
            final EditText MovieArea = view2.findViewById(R.id.area_code);
            final EditText MovieTime = view2.findViewById(R.id.slot_time);
            final EditText MovieSeats = view2.findViewById(R.id.seats);
            final EditText MoviePhoneNumber = view2.findViewById(R.id.phone_number);
            final EditText MovieTimings = view2.findViewById(R.id.timing);
            final EditText MovieHall = view2.findViewById(R.id.hall_name);
            TextView Title = view2.findViewById(R.id.title);
            Button EditButton = view2.findViewById(R.id.save_Button);
            Title.setText("Update Movie Details");
            EditButton.setText("Update");

            MovieName.setText(movieData.getMovie_name());
            MovieArea.setText(String.valueOf(movieData.getArea_Code()));
            MovieTime.setText(String.valueOf(movieData.getTime_Slot()));
            MovieSeats.setText(String.valueOf(movieData.getSeats_Available()));
            MoviePhoneNumber.setText(movieData.getPhone_Number());
            MovieTimings.setText(movieData.getTiming());
            MovieHall.setText(movieData.getHall_Name());

            builder.setView(view2);
            alertDialog = builder.create();
            alertDialog.show();

            EditButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DataBaseHandler db = new DataBaseHandler(context);
                    movieData.setMovie_name(MovieName.getText().toString().trim());
                    movieData.setArea_Code(Integer.parseInt(MovieArea.getText().toString().trim()));
                    movieData.setTime_Slot(Integer.parseInt(MovieTime.getText().toString().trim()));
                    movieData.setSeats_Available(Integer.parseInt(MovieSeats.getText().toString().trim()));
                    movieData.setPhone_Number(MoviePhoneNumber.getText().toString().trim());
                    movieData.setTiming(MovieTimings.getText().toString().trim());
                    movieData.setHall_Name(MovieHall.getText().toString().trim());

                    db.updateMovie(movieData);
                    notifyItemChanged(getAdapterPosition(),movieData);
                    alertDialog.dismiss();

                }
            });


        }

        private void delMovie(final int id) {
            builder = new AlertDialog.Builder(context);
            View view1 = LayoutInflater.from(context).inflate(R.layout.confirmation,null);

            Button noButton = view1.findViewById(R.id.no_button);
            Button yesButton = view1.findViewById(R.id.yes_button);

            builder.setView(view1);
            alertDialog = builder.create();
            alertDialog.show();

            noButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    alertDialog.dismiss();
                }
            });

            yesButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    DataBaseHandler db = new DataBaseHandler(context);
                    db.deleteMovie(id);
                    movie_lists.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());

                    alertDialog.dismiss();

                }
            });
        }
    }
}
