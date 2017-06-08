package animalhusbandry.android.com.animalhusbandry.Activities.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import animalhusbandry.android.com.animalhusbandry.Activities.Fragments.DeleteUserProfileParams.DeleteUserProfileRequest;
import animalhusbandry.android.com.animalhusbandry.Activities.Fragments.DeleteUserProfileParams.DeleteUserProfileResponse;
import animalhusbandry.android.com.animalhusbandry.Activities.Login;
import animalhusbandry.android.com.animalhusbandry.Activities.RetroFit.RetroUtils;
import animalhusbandry.android.com.animalhusbandry.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragAccountSettings.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragAccountSettings#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragAccountSettings extends Fragment {

    public Button btnChangePassword, btnDeleteAccount, btnEditUserProfile;
    public ProgressDialog ringProgressDialog;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public FragAccountSettings() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragAccountSettings.
     */
    // TODO: Rename and change types and number of parameters
    public static FragAccountSettings newInstance(String param1, String param2) {
        FragAccountSettings fragment = new FragAccountSettings();
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
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        final View fragView = inflater.inflate(R.layout.fragment_account_settings, container, false);
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        TextView textView=(TextView)toolbar.findViewById(R.id.toolbar_dashboard);
        textView.setText("Account settings");
        btnChangePassword = (Button) fragView.findViewById(R.id.btnChangePassword);
        btnDeleteAccount = (Button) fragView.findViewById(R.id.btnDeleteAccount);
        btnEditUserProfile = (Button) fragView.findViewById(R.id.btnEditUserProfile);
        btnEditUserProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                FragEditUserProfile fragmentEditUserProfile = new FragEditUserProfile();
                fragmentTransaction.replace(R.id.xmlFragment, fragmentEditUserProfile, "fragmentEditUserProfileTag");
                fragmentTransaction.addToBackStack("fragmentEditUserProfile");
                fragmentTransaction.commit();
            }
        });
        btnDeleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                LayoutInflater factory = LayoutInflater.from(getActivity());
                final View dialogView = factory.inflate(R.layout.alert_dialog_delete, null);
             /*   final View dialogView = layoutInflater.from(R.layout.alert_dialog_delete, null);*/
                builder.setView(dialogView);
                builder.setCancelable(true);
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ringProgressDialog = ProgressDialog.show(getActivity(), "Please wait ...", "Deleting your account", true);
                        ringProgressDialog.setCancelable(true);

                        new Thread(new Runnable() {

                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(10000);
                                } catch (Exception e) {

                                    ringProgressDialog.dismiss();
                                }
                            }
                        }).start();

                        final SharedPreferences sharedPreferences = getContext().getSharedPreferences("Options", Context.MODE_APPEND);
                        final String strUserId = sharedPreferences.getString("strUserId", "");
                        DeleteUserProfileRequest deleteUserProfileRequest = new DeleteUserProfileRequest();
                        deleteUserProfileRequest.setUserId(strUserId);
                        doDeleteUserProfile(deleteUserProfileRequest);
                    }

                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder.show();
            }


        });

        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                FragChangePassword fragChangePassword = new FragChangePassword();
                fragmentTransaction.replace(R.id.xmlFragment, fragChangePassword, "fragChangePasswordTag");
                fragmentTransaction.addToBackStack("fragChangePassword");
                fragmentTransaction.commit();
            }
        });
        return fragView;}
    private void doDeleteUserProfile(DeleteUserProfileRequest deleteUserProfileRequest) {
        RetroUtils retroUtils = new RetroUtils(getContext());
        retroUtils.getApiClient().deleteUserProfile(deleteUserProfileRequest).enqueue(new Callback<DeleteUserProfileResponse>() {
            @Override
            public void onResponse(Call<DeleteUserProfileResponse> call, Response<DeleteUserProfileResponse> response) {
                ringProgressDialog.dismiss();
                if (response.body().getResponse().getCode().equals("200")) {
                    Toast.makeText(getContext(), "Account deleted sucessfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity(), Login.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<DeleteUserProfileResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Something went wrong try again later ", Toast.LENGTH_SHORT).show();
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
