package animalhusbandry.android.com.animalhusbandry.Activities.Adapters;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import animalhusbandry.android.com.animalhusbandry.Activities.GetPetProfilesOfUserParams.GetPetProfilesOfUserResponse;
import animalhusbandry.android.com.animalhusbandry.R;

import static animalhusbandry.android.com.animalhusbandry.R.drawable.googleplus;

/**
 * Created by grewalshavneet on 6/7/2017.
 * Adapter for Pet List
 */

public class AdapterUserPetList extends RecyclerView.Adapter<AdapterUserPetList.ViewHolder> {

    private Context context;
    private ArrayList<GetPetProfilesOfUserResponse.Result> userPetArrayList;
    private AdapterView.OnItemClickListener clickListener;

    public AdapterUserPetList(FragmentActivity activity, ArrayList<GetPetProfilesOfUserResponse.Result> userPetArrayList) {
        this.context = context;
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
        Picasso.with(context).load(userPetArrayList.get(position).getImageUrl()).error(googleplus).fit().into(holder.petImage);


    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView petName;
        private TextView petBreed;
        private TextView petLocation;
        private ImageView petImage;

        public ViewHolder(View itemView) {
            super(itemView);
            petName = (TextView) itemView.findViewById(R.id.petName);
            petBreed = (TextView) itemView.findViewById(R.id.petBreed);
            petLocation = (TextView) itemView.findViewById(R.id.petLocation);
            petImage = (ImageView) itemView.findViewById(R.id.petImage);
        }
    }
}

