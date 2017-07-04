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
import com.animalhusbandry.model.SearchPetUsingFiltersResponse;
import com.animalhusbandry.search.ShowFullPetProfileAfterSearchActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.animalhusbandry.R.drawable.ic_1496341871_instagram;

/**
 * Created by grewalshavneet on 6/23/2017.
 */

public class AdapterSearchPetUsingFilters extends RecyclerView.Adapter<AdapterSearchPetUsingFilters.ViewHolder> {

private CardView cardView;
private Activity activity;
private ArrayList<SearchPetUsingFiltersResponse.Result> searchPetUsingFiltersArrayList;


public AdapterSearchPetUsingFilters(Activity activity) {
        this.activity = activity;
        this.searchPetUsingFiltersArrayList = new ArrayList<>();
        }

public void setData(SearchPetUsingFiltersResponse.Result[] searchPetUsingFiltersArrayList) {
        this.searchPetUsingFiltersArrayList.clear();
        this.searchPetUsingFiltersArrayList.addAll(Arrays.asList(searchPetUsingFiltersArrayList));
        }

public void addData(SearchPetUsingFiltersResponse.Result[] searchPetUsingFiltersArrayList) {

        this.searchPetUsingFiltersArrayList.addAll(Arrays.asList(searchPetUsingFiltersArrayList));

        }

@Override
public AdapterSearchPetUsingFilters.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pet_list_user_card_view, parent, false);
        return new ViewHolder(view);
        }

@Override
public void onBindViewHolder(AdapterSearchPetUsingFilters.ViewHolder holder, int position) {
        holder.petName.setText(searchPetUsingFiltersArrayList.get(position).getName());
        holder.petBreed.setText(searchPetUsingFiltersArrayList.get(position).getBreed());
        holder.petLocation.setText(searchPetUsingFiltersArrayList.get(position).getLocation());
        Picasso.with(activity.getBaseContext()).load(searchPetUsingFiltersArrayList.get(position).getImageUrl()).placeholder(ic_1496341871_instagram).error(ic_1496341871_instagram).fit().into(holder.petImage);
        }


@Override
public int getItemCount() {
        return searchPetUsingFiltersArrayList.size();
        }

public class ViewHolder extends RecyclerView.ViewHolder {
    private TextView petName;
    private TextView petBreed;
    private TextView petLocation;
    private CircleImageView petImage;

    public ViewHolder(View itemView) {
        super(itemView);
        petName = (TextView) itemView.findViewById(R.id.petName);
        petBreed = (TextView) itemView.findViewById(R.id.petBreed);
        cardView = (CardView) itemView.findViewById(R.id.card_view_pet_profile_ui);
        petLocation = (TextView) itemView.findViewById(R.id.petLocation);
        petImage = (CircleImageView) itemView.findViewById(R.id.petImage);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                   /* SharedPreferences prefs = activity.getBaseContext().getSharedPreferences("Options", MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();*/
                int adapterPosotion = getAdapterPosition();
                SearchPetUsingFiltersResponse.Result result = searchPetUsingFiltersArrayList.get(adapterPosotion);
                Intent intent = new Intent(activity.getBaseContext(), ShowFullPetProfileAfterSearchActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("petProfileUserClicked", result);
                /*    String strPetId = result.getPetId();
                    editor.putString("PetId", strPetId);*/
                intent.putExtras(bundle);
                activity.startActivity(intent);
            }
        });
    }
}
}
