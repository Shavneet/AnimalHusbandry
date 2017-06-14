package animalhusbandry.android.com.animalhusbandry.Activities.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

import animalhusbandry.android.com.animalhusbandry.Activities.Adapters.AdapterGetAllPetProfiles;
import animalhusbandry.android.com.animalhusbandry.Activities.CreatePetProfile;
import animalhusbandry.android.com.animalhusbandry.Activities.GetAllPetProfilesParams.GetAllPetProfilesRequest;
import animalhusbandry.android.com.animalhusbandry.Activities.GetAllPetProfilesParams.GetAllPetProfilesResponse;
import animalhusbandry.android.com.animalhusbandry.Activities.RetroFit.RetroUtils;
import animalhusbandry.android.com.animalhusbandry.Activities.utils.EndlessRecyclerViewScrollListenerImplementation;
import animalhusbandry.android.com.animalhusbandry.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BaseFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BaseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BaseFragment extends Fragment implements EndlessRecyclerViewScrollListenerImplementation.OnScrollPageChangeListener {
    public boolean doubleBackToExitPressedOnce = false;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ArrayList<GetAllPetProfilesResponse.Result> allPetProfilesArrayList = new ArrayList<>();
    public RecyclerView recyclerView;
    public ProgressBar progressBar;
    private static final int FIRST_PAGE = 0;
    private OnFragmentInteractionListener mListener;
    private EndlessRecyclerViewScrollListenerImplementation endlessScrollListener;
    private LinearLayoutManager layoutManager;
    private String strUserId;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BaseFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BaseFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BaseFragment newInstance(String param1, String param2) {
        BaseFragment fragment = new BaseFragment();
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
      /*  if (getActivity() != null) {
            Dashboard activity = (Dashboard) getActivity();
            activity.setToolbarTitle("Dashboard");
        }*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        TextView textView=(TextView)toolbar.findViewById(R.id.toolbar_dashboard);
        textView.setText("Dashboard");
        View fragView = inflater.inflate(R.layout.fragment_base, container, false);
        progressBar = (ProgressBar) fragView.findViewById(R.id.progressBar_Ui);
        progressBar.setVisibility(View.VISIBLE);
        recyclerView = (RecyclerView) fragView.findViewById(R.id.recycler_View);
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
        loadAllPetProfiles(FIRST_PAGE);
        return fragView;
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
                allPetProfilesArrayList.addAll(Arrays.asList(response.body().getResponse().getResult()));
                AdapterGetAllPetProfiles adapter = new AdapterGetAllPetProfiles(getActivity(), allPetProfilesArrayList);
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<GetAllPetProfilesResponse> call, Throwable t) {

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
