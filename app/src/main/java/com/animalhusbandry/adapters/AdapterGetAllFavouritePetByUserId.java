package com.animalhusbandry.adapters;

import android.app.Activity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.animalhusbandry.R;
import com.animalhusbandry.model.GetAllFavouritePetByUserIdResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.animalhusbandry.R.drawable.ic_1496341871_instagram;

/**
 * Created by grewalshavneet on 6/22/2017.
 */

public class AdapterGetAllFavouritePetByUserId extends RecyclerView.Adapter<AdapterGetAllFavouritePetByUserId.ViewHolder> {

    private CardView cardView;
    private Activity activity;
    private ArrayList<GetAllFavouritePetByUserIdResponse.Result> userFavouritePetArrayList;

    public AdapterGetAllFavouritePetByUserId(Activity activity, ArrayList<GetAllFavouritePetByUserIdResponse.Result> userFavouritePetArrayList) {
        this.activity = activity;
        this.userFavouritePetArrayList = userFavouritePetArrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favourite_pet_list_user_card_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterGetAllFavouritePetByUserId.ViewHolder holder, int position) {
        holder.petName.setText(userFavouritePetArrayList.get(position).getName());
        holder.petBreed.setText(userFavouritePetArrayList.get(position).getBreed());
        holder.petLocation.setText(userFavouritePetArrayList.get(position).getLocation());
        Picasso.with(activity.getBaseContext()).load(userFavouritePetArrayList.get(position).getImageUrl()).error(ic_1496341871_instagram).fit().into(holder.petImage);

    }


    @Override
    public int getItemCount() {
        return userFavouritePetArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView petName;
        private TextView petBreed;
        private TextView petLocation;
        private CircleImageView petImage;

        public ViewHolder(View itemView) {
            super(itemView);
            petName = (TextView) itemView.findViewById(R.id.petName);
            cardView = (CardView) itemView.findViewById(R.id.card_view_pet_profile_ui);
            petBreed = (TextView) itemView.findViewById(R.id.petBreed);
            petLocation = (TextView) itemView.findViewById(R.id.petLocation);
            petImage = (CircleImageView) itemView.findViewById(R.id.petImage);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   /* SharedPreferences prefs = activity.getBaseContext().getSharedPreferences("Options", MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();*/
                   /* int adapterPosotion = getAdapterPosition();
                    GetAllFavouritePetByUserIdResponse.Result result = userFavouritePetArrayList.get(adapterPosotion);
                    Intent intent = new Intent(activity.getBaseContext(), ShowFavouritePetProfileActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("petProfileUserClicked", (Serializable) result);
                *//*    String strPetId = result.getPetId();
                    editor.putString("PetId", strPetId);*//*
                    intent.putExtras(bundle);
                    activity.startActivity(intent);*/
                }
            });
        }
    }
}

