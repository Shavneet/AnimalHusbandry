package com.animalhusbandry.dashboard;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.animalhusbandry.R;
import com.animalhusbandry.adapters.AdapterGetAllPetProfiles;
import com.animalhusbandry.model.getallpetprofilesmodel.GetAllPetProfilesRequest;
import com.animalhusbandry.model.getallpetprofilesmodel.GetAllPetProfilesResponse;
import com.animalhusbandry.petprofile.CreatePetProfile;
import com.animalhusbandry.retrofit.RetroUtils;
import com.animalhusbandry.search.SearchActivity;
import com.animalhusbandry.utils.EndlessRecyclerViewScrollListenerImplementation;
import com.animalhusbandry.utils.setToolbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DashBoardFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DashBoardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DashBoardFragment extends BaseFragment implements EndlessRecyclerViewScrollListenerImplementation.OnScrollPageChangeListener {
    public boolean doubleBackToExitPressedOnce = false;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public RecyclerView recyclerView;
    public ProgressBar progressBar;
    private TextView etNoDataFound;
    private OnFragmentInteractionListener mListener;
    private static final int FIRST_PAGE = 0;
    private EndlessRecyclerViewScrollListenerImplementation endlessScrollListener;
    private AdapterGetAllPetProfiles adapter;
    private LinearLayoutManager layoutManager;
    private String strUserId;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DashBoardFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DashBoardFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DashBoardFragment newInstance(String param1, String param2) {
        DashBoardFragment fragment = new DashBoardFragment();
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
        adapter = new AdapterGetAllPetProfiles(getActivity());
        recyclerView.setAdapter(adapter);
        loadAllPetProfiles(FIRST_PAGE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragView = inflater.inflate(R.layout.dashboard_fragment, container, false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setToolbar.setToolbar(activity, "All pet profiles", false);
        }
        //Toolbar toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
        ImageView ivSearch = (ImageView) activity.toolbar.findViewById(R.id.ivSearch);
        ivSearch.setVisibility(View.VISIBLE);
       // TextView textView = (TextView) toolbar.findViewById(R.id.toolbar_title);
        ImageButton backBtn = (ImageButton) activity.toolbar.findViewById(R.id.backBtn);
        backBtn.setEnabled(true);

        activity.toolbar.setTitle("All Pet Profiles");
        //textView.setEnabled(true);
        activity.toolbar.setEnabled(true);
        progressBar = (ProgressBar) fragView.findViewById(R.id.progressBar_Ui);
        progressBar.setVisibility(View.VISIBLE);
        recyclerView = (RecyclerView) fragView.findViewById(R.id.recycler_View);
        etNoDataFound = (TextView) fragView.findViewById(R.id.etNoDataFound);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        FloatingActionButton floatingActionButton = (FloatingActionButton) fragView.findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CreatePetProfile.class);
                startActivity(intent);
            }
        });

        ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, SearchActivity.class);
                startActivity(intent);

            }
        });
        return fragView;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        activity.navigationView.setCheckedItem(R.id.nav_get_all_pet_profile);

    }

    public void initPaging() {
        if (endlessScrollListener == null)
            endlessScrollListener = new EndlessRecyclerViewScrollListenerImplementation(layoutManager, this);
        else
            endlessScrollListener.setmLayoutManager(layoutManager);
        recyclerView.addOnScrollListener(endlessScrollListener);
    }

    private void loadAllPetProfiles(int pageNumber) {
        GetAllPetProfilesRequest getAllPetProfilesRequest = new GetAllPetProfilesRequest();
        getAllPetProfilesRequest.setPage(pageNumber);
        getAllPetProfilesRequest.setSize(10);
        doGetAllPetProfiles(getAllPetProfilesRequest);
        initPaging();
    }

    private void doGetAllPetProfiles(GetAllPetProfilesRequest getAllPetProfilesRequest) {
        RetroUtils retroUtils = new RetroUtils(getContext());
        retroUtils.getApiClient().getAllPetProfiles(getAllPetProfilesRequest).enqueue(new Callback<GetAllPetProfilesResponse>() {
            @Override
            public void onResponse(Call<GetAllPetProfilesResponse> call, Response<GetAllPetProfilesResponse> response) {
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

                    if (endlessScrollListener.getCurrentPage() == 0) {
                        recyclerView.setVisibility(View.VISIBLE);
                        adapter.setData(response.body().getResponse().getResult());
                    } else {
                        recyclerView.setVisibility(View.VISIBLE);
                        adapter.addData(response.body().getResponse().getResult());
                    }
                    adapter.notifyDataSetChanged();
                }} else if (response.body().getResponse().getCode().equals("401")) {
                    Toast.makeText(getContext(), "You are not authorized", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetAllPetProfilesResponse> call, Throwable t) {
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

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
        loadAllPetProfiles(page);
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
