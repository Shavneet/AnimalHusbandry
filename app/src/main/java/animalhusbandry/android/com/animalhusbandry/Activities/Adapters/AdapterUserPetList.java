package animalhusbandry.android.com.animalhusbandry.Activities.Adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import animalhusbandry.android.com.animalhusbandry.Activities.GetPetProfilesOfUserParams.GetPetProfilesOfUserResponse;
import animalhusbandry.android.com.animalhusbandry.R;
import de.hdodenhof.circleimageview.CircleImageView;

import static animalhusbandry.android.com.animalhusbandry.R.drawable.ic_1496341871_instagram;

/**
 * Created by grewalshavneet on 6/7/2017.
 * Adapter for Pet List
 */

public class AdapterUserPetList extends RecyclerView.Adapter<AdapterUserPetList.ViewHolder> {

    private Activity activity;
    private ArrayList<GetPetProfilesOfUserResponse.Result> userPetArrayList;
    private AdapterView.OnItemClickListener clickListener;

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

        public ViewHolder(View itemView) {
            super(itemView);
            petName = (TextView) itemView.findViewById(R.id.petName);
            petBreed = (TextView) itemView.findViewById(R.id.petBreed);
            petLocation = (TextView) itemView.findViewById(R.id.petLocation);
            petImage = (CircleImageView) itemView.findViewById(R.id.petImage);
        }
    }
}

