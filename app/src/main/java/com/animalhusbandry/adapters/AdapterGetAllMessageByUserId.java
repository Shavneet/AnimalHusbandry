package com.animalhusbandry.adapters;

import android.app.Activity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.animalhusbandry.R;
import com.animalhusbandry.model.GetAllMessageByUserIdResponse;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by grewalshavneet on 6/22/2017.
 */

public class AdapterGetAllMessageByUserId extends RecyclerView.Adapter<AdapterGetAllMessageByUserId.ViewHolder> {


    private CardView cardView;
    private Activity activity;
    private ArrayList<GetAllMessageByUserIdResponse.Result> getAllMessageByUserIdArrayList;

    public AdapterGetAllMessageByUserId(Activity activity, ArrayList<GetAllMessageByUserIdResponse.Result> getAllMessageByUserIdArrayList) {
        this.activity = activity;
        this.getAllMessageByUserIdArrayList = getAllMessageByUserIdArrayList;
    }

    @Override
    public AdapterGetAllMessageByUserId.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.getall_messageby_userid_card_view, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(AdapterGetAllMessageByUserId.ViewHolder holder, int position) {
        holder.petName.setText(getAllMessageByUserIdArrayList.get(position).getName());
        holder.comments.setText(getAllMessageByUserIdArrayList.get(position).getComment());

        String strMessageByPetId = getAllMessageByUserIdArrayList.get(position).getPetId();
        String strMessageByUserId = getAllMessageByUserIdArrayList.get(position).getUserId();
       /* holder.petLocation.setText(getAllMessageByUserIdArrayList.get(position).getLocation());*/
/*
        Picasso.with(activity.getBaseContext()).load(getAllMessageByUserIdArrayList.get(position).getImageUrl()).error(ic_1496341871_instagram).fit().into(holder.petImage);
*/

    }

    @Override
    public int getItemCount() {
        return getAllMessageByUserIdArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView petName;
        private TextView comments;
        private TextView petLocation;
        private CircleImageView petImage;

        public ViewHolder(View itemView) {
            super(itemView);
            petName = (TextView) itemView.findViewById(R.id.petName);
            cardView = (CardView) itemView.findViewById(R.id.card_view_pet_profile_ui);
            comments = (TextView) itemView.findViewById(R.id.comment);
           /* petLocation = (TextView) itemView.findViewById(R.id.petLocation);
            petImage = (CircleImageView) itemView.findViewById(R.id.petImage);*/
           /* cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   *//* SharedPreferences prefs = activity.getBaseContext().getSharedPreferences("Options", MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();*//*
                    int adapterPosotion = getAdapterPosition();
                    GetAllFavouritePetByUserIdResponse.Result result = getAllMessageByUserIdArrayList.get(adapterPosotion);
                    Intent intent = new Intent(activity.getBaseContext(), ShowFavouritePetProfileActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("petProfileUserClicked", (Serializable) result);
                *//*    String strPetId = result.getPetId();
                    editor.putString("PetId", strPetId);*//*
                    intent.putExtras(bundle);
                    activity.startActivity(intent);
                }
            });*/
        }
    }

}
