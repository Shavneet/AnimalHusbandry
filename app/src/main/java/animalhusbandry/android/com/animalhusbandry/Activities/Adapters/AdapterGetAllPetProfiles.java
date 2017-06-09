package animalhusbandry.android.com.animalhusbandry.Activities.Adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import animalhusbandry.android.com.animalhusbandry.Activities.GetAllPetProfilesParams.GetAllPetProfilesResponse;
import animalhusbandry.android.com.animalhusbandry.R;
import de.hdodenhof.circleimageview.CircleImageView;

import static animalhusbandry.android.com.animalhusbandry.R.drawable.ic_1496341871_instagram;

/**
 * Created by grewalshavneet on 6/9/2017.
 */

public class AdapterGetAllPetProfiles extends RecyclerView.Adapter<AdapterGetAllPetProfiles.ViewHolder> {

    private Activity activity;
    private ArrayList<GetAllPetProfilesResponse.Result> allPetProfilesArrayList;


    public AdapterGetAllPetProfiles(Activity activity, ArrayList<GetAllPetProfilesResponse.Result> allPetProfilesArrayList) {
        this.activity = activity;
        this.allPetProfilesArrayList = allPetProfilesArrayList;
    }

    @Override
    public AdapterGetAllPetProfiles.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pet_list_user_card_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterGetAllPetProfiles.ViewHolder holder, int position) {
        holder.petName.setText(allPetProfilesArrayList.get(position).getName());
        holder.petBreed.setText(allPetProfilesArrayList.get(position).getBreed());
        holder.petLocation.setText(allPetProfilesArrayList.get(position).getLocation());
        Picasso.with(activity.getBaseContext()).load(allPetProfilesArrayList.get(position).getImageUrl()).error(ic_1496341871_instagram).fit().into(holder.petImage);
    }


    @Override
    public int getItemCount() {
        return allPetProfilesArrayList.size();
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
            petLocation = (TextView) itemView.findViewById(R.id.petLocation);
            petImage = (CircleImageView) itemView.findViewById(R.id.petImage);
        }
    }
}