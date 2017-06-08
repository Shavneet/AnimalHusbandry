package animalhusbandry.android.com.animalhusbandry.Activities.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import animalhusbandry.android.com.animalhusbandry.Activities.Fragments.GetUserDetailsParams.GetUserDetailsRequest;
import animalhusbandry.android.com.animalhusbandry.Activities.Fragments.GetUserDetailsParams.GetUserDetailsResponse;
import animalhusbandry.android.com.animalhusbandry.Activities.Fragments.UpdateUserDetailsParams.UpdateUserDetailsRequest;
import animalhusbandry.android.com.animalhusbandry.Activities.Fragments.UpdateUserDetailsParams.UpdateUserDetailsResponse;
import animalhusbandry.android.com.animalhusbandry.Activities.RetroFit.RetroUtils;
import animalhusbandry.android.com.animalhusbandry.Activities.utils.VectorDrawableUtils;
import animalhusbandry.android.com.animalhusbandry.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragEditUserProfile.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragEditUserProfile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragEditUserProfile extends Fragment {
    EditText etFName, etLName, etContact;
    Button btnSubmitChanges;
    public ProgressDialog ringProgressDialog;
    public SharedPreferences.Editor editor;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public FragEditUserProfile() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragEditUserProfile.
     */
    // TODO: Rename and change types and number of parameters
    public static FragEditUserProfile newInstance(String param1, String param2) {
        FragEditUserProfile fragment = new FragEditUserProfile();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        TextView textView=(TextView)toolbar.findViewById(R.id.toolbar_dashboard);
        textView.setText("Edit your profile");
        View fragView = inflater.inflate(R.layout.frag_edit_user_profile, container, false);
        etFName = (EditText) fragView.findViewById(R.id.etFName);
        etLName = (EditText) fragView.findViewById(R.id.etLName);
        etContact = (EditText) fragView.findViewById(R.id.etContact);
        SharedPreferences pref = getContext().getSharedPreferences("Options", MODE_PRIVATE);
        editor = pref.edit();
        ringProgressDialog = ProgressDialog.show(getContext(), "Please wait ...", "Fetching your details", true);
        ringProgressDialog.setCancelable(true);

        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                }
                ringProgressDialog.dismiss();
            }
        }).start();
        setIconsEditUserDetails();
        btnSubmitChanges = (Button) fragView.findViewById(R.id.btnSubmitChanges);
        final SharedPreferences sharedPreferences = getContext().getSharedPreferences("Options", MODE_PRIVATE);
        final String strUserId = sharedPreferences.getString("strUserId", "");
        GetUserDetailsRequest getUserDetailRequest = new GetUserDetailsRequest();
        getUserDetailRequest.setUserId(strUserId);
        doGetUserDetails(getUserDetailRequest);
        btnSubmitChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strEtFNameUpdate = etFName.getText().toString();
                String strEtLNameUpdate = etLName.getText().toString();
                String strEtConatctUpdate = etContact.getText().toString();
                UpdateUserDetailsRequest updateUserDetailsRequest = new UpdateUserDetailsRequest();
                updateUserDetailsRequest.setUserId(strUserId);
                updateUserDetailsRequest.setFirstName(strEtFNameUpdate);
                updateUserDetailsRequest.setLastName(strEtLNameUpdate);
                updateUserDetailsRequest.setPhone(strEtConatctUpdate);
                doUpdateUserDetails(updateUserDetailsRequest);

        }
        });
        return fragView;
    }

    private void setIconsEditUserDetails() {
        Drawable FName = VectorDrawableUtils.getDrawable(getContext(), R.drawable.ic_004_user_3);
        Drawable LNmae = VectorDrawableUtils.getDrawable(getContext(), R.drawable.ic_011_user_2);
        Drawable contact = VectorDrawableUtils.getDrawable(getContext(), R.drawable.ic_009_phone_with_numbers_of_the_buttons);
        etFName.setCompoundDrawablesWithIntrinsicBounds(null, null, FName, null);
        etLName.setCompoundDrawablesWithIntrinsicBounds(null, null, LNmae, null);
        etContact.setCompoundDrawablesWithIntrinsicBounds(null, null, contact, null);

    }

    private void doUpdateUserDetails(UpdateUserDetailsRequest updateUserDetailsRequest) {
        RetroUtils retroUtils = new RetroUtils(getContext());
        retroUtils.getApiClient().updateUserDetails(updateUserDetailsRequest).enqueue(new Callback<UpdateUserDetailsResponse>() {
            @Override
            public void onResponse(Call<UpdateUserDetailsResponse> call, Response<UpdateUserDetailsResponse> response) {
                ringProgressDialog.dismiss();
                if (response.body().getResponse().getCode().equals("200")) {
                    String strUserFullName = response.body().getResponse().getResult().getUserFullName();
                    String strUserEmail = response.body().getResponse().getResult().getEmail();
                    editor.putString("strUserFullName", strUserFullName);
                    editor.putString("strUserEmail", strUserEmail);
                    editor.commit();
                    Toast.makeText(getContext(), "Details updated sucessfully", Toast.LENGTH_SHORT).show();
                    FragmentManager fragmentmanager = getFragmentManager();
                    FragmentTransaction fragmentTranscation = fragmentmanager.beginTransaction();
                    BaseFragment baseFragment = new BaseFragment();
                    fragmentTranscation.replace(R.id.xmlFragment, baseFragment, "gf");
                    fragmentTranscation.addToBackStack("baseFragmentFromEditUser");
                    fragmentTranscation.commit();
                }
            }
            @Override
            public void onFailure(Call<UpdateUserDetailsResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Something went wrong. Please try again ", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void doGetUserDetails(GetUserDetailsRequest getUserDetailRequest) {
        RetroUtils retroUtils = new RetroUtils(getContext());
        retroUtils.getApiClient().getUserDetails(getUserDetailRequest).enqueue(new Callback<GetUserDetailsResponse>() {
            @Override
            public void onResponse(Call<GetUserDetailsResponse> call, Response<GetUserDetailsResponse> response) {

                Log.e("*********", response.body().getResponse().getResult().getFirstName() + "");
                if (response.body().getResponse().getCode().equals("200")) {
                    etFName.setText(response.body().getResponse().getResult().getFirstName());
                    etLName.setText(response.body().getResponse().getResult().getLastName());
                    etContact.setText(response.body().getResponse().getResult().getPhone());
                } else {
                    Toast.makeText(getContext(), "Server down please enter your details manually", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetUserDetailsResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Something went wrong. Please try again", Toast.LENGTH_SHORT).show();

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
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
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

    private void setIcons() {
        Drawable FName = VectorDrawableUtils.getDrawable(getContext(), R.drawable.ic_004_user_3);
        Drawable LName = VectorDrawableUtils.getDrawable(getContext(), R.drawable.ic_011_user_2);
        Drawable contact = VectorDrawableUtils.getDrawable(getContext(), R.drawable.ic_009_phone_with_numbers_of_the_buttons);
        etFName.setCompoundDrawablesWithIntrinsicBounds(FName, null, null, null);
        etLName.setCompoundDrawablesWithIntrinsicBounds(LName, null, null, null);
        etContact.setCompoundDrawablesWithIntrinsicBounds(contact, null, null, null);

    }
}
