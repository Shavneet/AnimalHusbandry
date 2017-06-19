package com.animalhusbandry.adapters;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.animalhusbandry.R;
import com.animalhusbandry.model.GetPetProfilesOfUserResponse;
import com.animalhusbandry.userprofile.ShowUserPetProfileActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.animalhusbandry.R.drawable.ic_1496341871_instagram;


/**
 * Created by grewalshavneet on 6/7/2017.
 * Adapter for Pet List
 */

public class AdapterUserPetList extends RecyclerView.Adapter<AdapterUserPetList.ViewHolder> {
    public CardView cardView;
    private Activity activity;
    private ArrayList<GetPetProfilesOfUserResponse.Result> userPetArrayList;

    public AdapterUserPetList(Activity activity, ArrayList<GetPetProfilesOfUserResponse.Result> userPetArrayList) {
        this.activity = activity;
        this.userPetArrayList = userPetArrayList;
    }

    @Override
    public AdapterUserPetList.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pet_list_user_card_view, parent, false);

        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(AdapterUserPetList.ViewHolder holder, int position) {
        holder.petName.setText(userPetArrayList.get(position).getName());
        holder.petBreed.setText(userPetArrayList.get(position).getBreed());
        holder.petLocation.setText(userPetArrayList.get(position).getLocation());
        Picasso.with(activity.getBaseContext()).load(userPetArrayList.get(position).getImageUrl()).error(ic_1496341871_instagram).fit().into(holder.petImage);
    }

    @Override
    public int getItemCount() {
        return userPetArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView petName;
        private TextView petBreed;
        private TextView petLocation;
        private CircleImageView petImage;

        public ViewHolder(final View itemView) {
            super(itemView);
            petName = (TextView) itemView.findViewById(R.id.petName);
            petBreed = (TextView) itemView.findViewById(R.id.petBreed);
            petLocation = (TextView) itemView.findViewById(R.id.petLocation);
            petImage = (CircleImageView) itemView.findViewById(R.id.petImage);
            cardView = (CardView) itemView.findViewById(R.id.card_view);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int adapterPosition = getAdapterPosition();
                    GetPetProfilesOfUserResponse.Result result = userPetArrayList.get(adapterPosition);
                    Intent intent = new Intent(activity.getBaseContext(), ShowUserPetProfileActivity.class);

                    Bundle bundle = new Bundle();
                    /*bundle.putSerializable("petVaccinationsListOfUser", result.getPetVaccinationsList());*/
                    bundle.putSerializable("petVaccinationsListOfUser", result);
                    intent.putExtras(bundle);
                    activity.startActivity(intent);


                }

            });
        }
    }
}

