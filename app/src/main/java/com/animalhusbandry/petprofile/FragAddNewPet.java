package com.animalhusbandry.petprofile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.animalhusbandry.R;
import com.animalhusbandry.adapters.AdapterUserPetProfiles;
import com.animalhusbandry.dashboard.BaseFragment;
import com.animalhusbandry.model.getpetprofilesofusermodel.GetPetProfilesOfUserRequest;
import com.animalhusbandry.model.getpetprofilesofusermodel.GetPetProfilesOfUserResponse;
import com.animalhusbandry.retrofit.RetroUtils;
import com.animalhusbandry.utils.EndlessRecyclerViewScrollListenerImplementation;
import com.animalhusbandry.utils.setToolbar;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;
import static com.animalhusbandry.R.id.nav_add_pet;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragAddNewPet.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragAddNewPet#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragAddNewPet extends BaseFragment implements EndlessRecyclerViewScrollListenerImplementation.OnScrollPageChangeListener {

    private String mParam1;
    private String mParam2;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ArrayList<GetPetProfilesOfUserResponse.Result> userPetArrayList = new ArrayList<>();
    public RecyclerView recyclerView;
    public ProgressBar progressBar;
    private TextView etNoDataFound;
    private static final int FIRST_PAGE = 0;
    private OnFragmentInteractionListener mListener;
    private EndlessRecyclerViewScrollListenerImplementation endlessScrollListener;
    private LinearLayoutManager layoutManager;
    private String strUserId;


    public FragAddNewPet() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragAddNewPet.
     */
    // TODO: Rename and change types and number of parameters
    public static FragAddNewPet newInstance(String param1, String param2) {
        FragAddNewPet fragment = new FragAddNewPet();
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
        activity.navigationView.setCheckedItem(nav_add_pet);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragView = inflater.inflate(R.layout.frag_add_new_pet, container, false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setToolbar.setToolbar( activity, "Your pets", false);
        }
        Toolbar  toolbar = (Toolbar)activity. findViewById(R.id.toolbar);
        TextView textView = (TextView) toolbar.findViewById(R.id.toolbar_title);
        ImageButton backBtn= (ImageButton) toolbar.findViewById(R.id.backBtn);
        backBtn.setEnabled(true);
        ImageView ivSearch = (ImageView) toolbar.findViewById(R.id.ivSearch);
        ivSearch.setVisibility(View.VISIBLE);
        textView.setEnabled(true);
        toolbar.setEnabled(true);
        etNoDataFound = (TextView) fragView.findViewById(R.id.etNoDataFound);
        progressBar = (ProgressBar) fragView.findViewById(R.id.progressBar_Ui);
        progressBar.setVisibility(View.VISIBLE);
        recyclerView = (RecyclerView) fragView.findViewById(R.id.recycler_View);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        final SharedPreferences sharedPreferences = getContext().getSharedPreferences("Options", MODE_PRIVATE);
        strUserId = sharedPreferences.getString("strUserId", "");
        FloatingActionButton btnFab = (FloatingActionButton) fragView.findViewById(R.id.btnAddNewPet);
        btnFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CreatePetProfile.class);
                startActivity(intent);
            }
        });
        loadData(FIRST_PAGE);
        return fragView;
    }

    public void initPaging() {
        if (endlessScrollListener == null)
            endlessScrollListener = new EndlessRecyclerViewScrollListenerImplementation(layoutManager, this);
        else
            endlessScrollListener.setmLayoutManager(layoutManager);
        recyclerView.addOnScrollListener(endlessScrollListener);
    }


    public void loadData(int pageNo) {
        GetPetProfilesOfUserRequest getPetProfilesOfUserRequest = new GetPetProfilesOfUserRequest();
        getPetProfilesOfUserRequest.setUserId(strUserId);
        getPetProfilesOfUserRequest.setPage(pageNo);
        getPetProfilesOfUserRequest.setSize(5);
        doGetAllPetProfilesOfUser(getPetProfilesOfUserRequest);
        initPaging();
    }

    private void doGetAllPetProfilesOfUser(GetPetProfilesOfUserRequest getPetProfilesOfUserRequest) {
        Log.e("!!!!calll", "");
        RetroUtils retroUtils = new RetroUtils(getContext());
        retroUtils.getApiClient().getPetProfilesOfUserId(getPetProfilesOfUserRequest).enqueue(new Callback<GetPetProfilesOfUserResponse>() {
            @Override
            public void onResponse(Call<GetPetProfilesOfUserResponse> call, Response<GetPetProfilesOfUserResponse> response) {
                Log.e("!!!!!!!!", response.body().getResponse().getCode() + "");
                progressBar.setVisibility(View.GONE);
                if (response.body().getResponse().getCode().equals("200")) {
                    if (endlessScrollListener.getCurrentPage() == 0 && response.body().getResponse().getResult().length <= 0) {
                        if (etNoDataFound.getVisibility() == View.GONE) {
                            recyclerView.setVisibility(View.GONE);
                            etNoDataFound.setVisibility(View.VISIBLE);
                        }}
                    else {
                        if (etNoDataFound.getVisibility() == View.VISIBLE) {
                            etNoDataFound.setVisibility(View.GONE);
                        }

                    userPetArrayList.addAll(Arrays.asList(response.body().getResponse().getResult()));
                    AdapterUserPetProfiles adapter = new AdapterUserPetProfiles(getActivity(), userPetArrayList);
                  /*  adapter.notifyDataSetChanged();*/
                        recyclerView.setVisibility(View.VISIBLE);
                    recyclerView.setAdapter(adapter);
                }} else if (response.body().getResponse().getCode().equals("401")) {
                    Toast.makeText(getContext(), "You are not authorized", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetPetProfilesOfUserResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Service failure.Try again", Toast.LENGTH_SHORT).show();
            }
        });
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
        /*if (context instanceof OnFragmentInteractionListener) {
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

    @Override
    public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
        loadData(page);

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
