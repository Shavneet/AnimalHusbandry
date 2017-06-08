package animalhusbandry.android.com.animalhusbandry.Activities.Fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

import animalhusbandry.android.com.animalhusbandry.Activities.Adapters.AdapterUserPetList;
import animalhusbandry.android.com.animalhusbandry.Activities.CreatePetProfile;
import animalhusbandry.android.com.animalhusbandry.Activities.Dashboard;
import animalhusbandry.android.com.animalhusbandry.Activities.GetPetProfilesOfUserParams.GetPetProfilesOfUserRequest;
import animalhusbandry.android.com.animalhusbandry.Activities.GetPetProfilesOfUserParams.GetPetProfilesOfUserResponse;
import animalhusbandry.android.com.animalhusbandry.Activities.RetroFit.RetroUtils;
import animalhusbandry.android.com.animalhusbandry.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragAddNewPet.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragAddNewPet#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragAddNewPet extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ArrayList<GetPetProfilesOfUserResponse.Result> userPetArrayList = new ArrayList<>();
    public RecyclerView recyclerView;
    public ProgressBar progressBar;
    public ProgressDialog ringProgressDialog;    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    Activity a;

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
        if(getActivity()!=null){
            Dashboard activity= (Dashboard) getActivity();
            activity.setToolbarTitle("Add pet profile");
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragView = inflater.inflate(R.layout.frag_add_new_pet, container, false);
        progressBar = (ProgressBar) fragView.findViewById(R.id.progressBar_Ui);
        Toolbar toolbar = (Toolbar)getActivity().findViewById(R.id.toolbar);
        TextView textView = (TextView) toolbar.findViewById(R.id.toolbar_dashboard);
        textView.setText("Add pet profile");
     /*   ringProgressDialog = ProgressDialog.show(getContext(), "", "");
        ringProgressDialog.setCancelable(false);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(10000);
                } catch (Exception e) {
                }
                ringProgressDialog.dismiss();
            }
        }).start();*/
        progressBar.setVisibility(View.VISIBLE);
        recyclerView = (RecyclerView) fragView.findViewById(R.id.recycler_View);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        final SharedPreferences sharedPreferences = getContext().getSharedPreferences("Options", MODE_PRIVATE);
        final String strUserId = sharedPreferences.getString("strUserId", "");
        Log.e("^^^^^^^^^", strUserId + "");
        GetPetProfilesOfUserRequest getPetProfilesOfUserRequest = new GetPetProfilesOfUserRequest();
        getPetProfilesOfUserRequest.setUserId(strUserId);
        getPetProfilesOfUserRequest.setPage(0);
        getPetProfilesOfUserRequest.setSize(5);
        callRetrofitService(getPetProfilesOfUserRequest);

        FloatingActionButton btnFab = (FloatingActionButton) fragView.findViewById(R.id.btnAddNewPet);
        btnFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CreatePetProfile.class);
                startActivity(intent);

            }
        });

        return fragView;
    }

    private void callRetrofitService(GetPetProfilesOfUserRequest getPetProfilesOfUserRequest) {
        Log.e("!!!!calll", "");
        RetroUtils retroUtils = new RetroUtils(getContext());

        retroUtils.getApiClient().getPetProfilesOfUser(getPetProfilesOfUserRequest).enqueue(new Callback<GetPetProfilesOfUserResponse>() {
            @Override
            public void onResponse(Call<GetPetProfilesOfUserResponse> call, Response<GetPetProfilesOfUserResponse> response) {
                Log.e("!!!!!!!!", response.body().getResponse().getCode() + "");
               /* ringProgressDialog.dismiss();*/
                progressBar.setVisibility(View.GONE);
               /* GetUserDetailsResponse responseService= response.body().getResponse().getResult();*/
                userPetArrayList.addAll(Arrays.asList(response.body().getResponse().getResult()));
                AdapterUserPetList adapter = new AdapterUserPetList(getActivity(), userPetArrayList);
                recyclerView.setAdapter(adapter);


            }

            @Override
            public void onFailure(Call<GetPetProfilesOfUserResponse> call, Throwable t) {
                /*ringProgressDialog.dismiss();*/
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Service failure", Toast.LENGTH_SHORT).show();
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
        if (ringProgressDialog != null && ringProgressDialog.isShowing()) {
            ringProgressDialog.dismiss();
        }
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
