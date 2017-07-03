package com.animalhusbandry.petprofile;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.animalhusbandry.R;
import com.animalhusbandry.adapters.AdapterGetAllFavouritePetByUserId;
import com.animalhusbandry.dashboard.BaseFragment;
import com.animalhusbandry.model.GetAllFavouritePetByUserIdRequest;
import com.animalhusbandry.model.GetAllFavouritePetByUserIdResponse;
import com.animalhusbandry.retrofit.RetroUtils;
import com.animalhusbandry.utils.setToolbar;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;
import static com.animalhusbandry.R.id.nav_favourite_pet;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragAllFavouritePets.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragAllFavouritePets#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragAllFavouritePets extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ArrayList<GetAllFavouritePetByUserIdResponse.Result> userFavouritePetArrayList = new ArrayList<>();
    public RecyclerView recyclerView;
    public ProgressBar progressBar;
    private LinearLayoutManager layoutManager;
    private OnFragmentInteractionListener mListener;
    private View fragView;
    private String strUserId;
    private TextView etNoDataFound;
    public FragAllFavouritePets() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragAllFavouritePets.
     */
    // TODO: Rename and change types and number of parameters
    public static FragAllFavouritePets newInstance(String param1, String param2) {
        FragAllFavouritePets fragment = new FragAllFavouritePets();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
        activity.navigationView.setCheckedItem(nav_favourite_pet);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragView = inflater.inflate(R.layout.frag_all_favourite_pets, container, false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setToolbar.setToolbar(activity, "Your Favourite pets", false);
        }
        Toolbar toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
        TextView textView = (TextView) toolbar.findViewById(R.id.toolbar_title);
        ImageButton backBtn = (ImageButton) toolbar.findViewById(R.id.backBtn);
        backBtn.setEnabled(true);
        textView.setEnabled(true);
        ImageView ivSearch = (ImageView) toolbar.findViewById(R.id.ivSearch);
        ivSearch.setVisibility(View.VISIBLE);
        toolbar.setEnabled(true);
        etNoDataFound = (TextView) fragView.findViewById(R.id.etNoDataFound);
        progressBar = (ProgressBar) fragView.findViewById(R.id.progressBar_Ui);
        progressBar.setVisibility(View.VISIBLE);
        recyclerView = (RecyclerView) fragView.findViewById(R.id.recycler_View);
        layoutManager = new LinearLayoutManager(activity.getBaseContext());
        recyclerView.setLayoutManager(layoutManager);
        callServiceMethod();
        return fragView;
    }

    private void callServiceMethod() {
        final SharedPreferences sharedPreferences = getContext().getSharedPreferences("Options", MODE_PRIVATE);
        strUserId = sharedPreferences.getString("strUserId", "");
        GetAllFavouritePetByUserIdRequest getAllFavouritePetByUserIdRequest = new GetAllFavouritePetByUserIdRequest();
        getAllFavouritePetByUserIdRequest.setUserId(strUserId);
        doGetAllFavouritePetByUserId(getAllFavouritePetByUserIdRequest);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(activity.getBaseContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private void doGetAllFavouritePetByUserId(GetAllFavouritePetByUserIdRequest getAllFavouritePetByUserIdRequest) {
        RetroUtils retroUtils = new RetroUtils(activity.getBaseContext());
        retroUtils.getApiClient().GET_ALL_FAVOURITE_PET_BY_USER_ID_RESPONSE_CALL(getAllFavouritePetByUserIdRequest).enqueue(new Callback<GetAllFavouritePetByUserIdResponse>() {
            @Override
            public void onResponse(Call<GetAllFavouritePetByUserIdResponse> call, Response<GetAllFavouritePetByUserIdResponse> response) {
                progressBar.setVisibility(View.GONE);
                if (response == null || response.body() == null) {
                    Toast.makeText(getContext(), "Server Error", Toast.LENGTH_SHORT).show();
                } else if (response.body().getResponse().getCode().equals("200")) {
                    if ( response.body().getResponse().getResult().length <= 0) {
                        if (etNoDataFound.getVisibility() == View.GONE) {
                            recyclerView.setVisibility(View.GONE);
                            etNoDataFound.setVisibility(View.VISIBLE);
                        }}
                    else {
                        if (etNoDataFound.getVisibility() == View.VISIBLE) {
                            etNoDataFound.setVisibility(View.GONE);
                        }
                    userFavouritePetArrayList.addAll(Arrays.asList(response.body().getResponse().getResult()));
                    AdapterGetAllFavouritePetByUserId adapter = new AdapterGetAllFavouritePetByUserId(getActivity(), userFavouritePetArrayList);
                  /*  adapter.notifyDataSetChanged();*/
                        recyclerView.setVisibility(View.VISIBLE);
                    recyclerView.setAdapter(adapter);
                }} else if (response.body().getResponse().getCode().equals("401")) {
                    Toast.makeText(getContext(), "You are not authorized", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<GetAllFavouritePetByUserIdResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Service failure.Try again", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
      /*  if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}