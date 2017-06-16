package animalhusbandry.android.com.animalhusbandry.Activities.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import animalhusbandry.android.com.animalhusbandry.Activities.GetPetProfilesOfUserParams.PetVaccinationsList;
import animalhusbandry.android.com.animalhusbandry.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragEditPetProfile.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragEditPetProfile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragEditPetProfile extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int CAMERA_REQUEST = 1100;
    private static final int GALERY_REQUEST = 2700;
    private static final int RESULT_CROP = 2900;
    EditText etPetName, etBloodline, etRegistration, etAge, etColor, etLocation, etBreed, etAnyOther;
    Button btnCreateProfile;
    CheckBox checkboxMale, checkboxFemale, checkboxDHPP, checkboxRabies, checkboxParvoVirus, checkboxNone;
    String strPetName, strPetBloodline, strPetRegistration, strPetAge, strPetColor, strPetLocation, strPetBreed, strPetAnyOther, strGender;
    public ProgressDialog ringProgressDialog;
    public AlertDialog cameraDialog;
    ImageView ivBtnAddImage;
    public Uri fileUri;
    public Bitmap bitmap;
    public String encodedImage;
    private static final int REQUEST_READ_PERMISSION = 7860;
    private static final int REQUEST_CAMERA_PERMISSION = 1888;
    public Bitmap circularBitmap;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public FragEditPetProfile() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragEditPetProfile.
     */
    // TODO: Rename and change types and number of parameters
    public static FragEditPetProfile newInstance(String param1, String param2) {
        FragEditPetProfile fragment = new FragEditPetProfile();
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
      View fragView=inflater.inflate(R.layout.frag_edit_pet_profile, container, false);
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        TextView textView=(TextView)toolbar.findViewById(R.id.toolbar_dashboard);
        textView.setText("Edit pet profile");
        ivBtnAddImage = (ImageView)fragView. findViewById(R.id.ivCircularImageView);
        etPetName = (EditText)fragView. findViewById(R.id.etPetName);
        etBloodline = (EditText) fragView.findViewById(R.id.etBloodline);
        etRegistration = (EditText)fragView. findViewById(R.id.etRegistration);
        etAge = (EditText)fragView. findViewById(R.id.etAge);
        etColor = (EditText) fragView.findViewById(R.id.etColor);
        etLocation = (EditText) fragView.findViewById(R.id.etLocation);
        etBreed = (EditText)fragView. findViewById(R.id.etBreed);
        etAnyOther = (EditText)fragView. findViewById(R.id.etAnyOtherEditable);
        checkboxMale = (CheckBox) fragView.findViewById(R.id.checkboxMale);
        checkboxFemale = (CheckBox) fragView.findViewById(R.id.checkboxFemale);
        checkboxDHPP = (CheckBox) fragView.findViewById(R.id.checkboxDHPP);
        checkboxRabies = (CheckBox)fragView. findViewById(R.id.checkboxRabies);
        checkboxParvoVirus = (CheckBox)fragView. findViewById(R.id.checkboxParvoVirus);
        btnCreateProfile = (Button)fragView. findViewById(R.id.btnCreateProfile);
        checkboxNone = (CheckBox) fragView.findViewById(R.id.checkboxNone);
        Toolbar toolbarTop = (Toolbar)fragView. findViewById(R.id.toolbar1);
        final ArrayList<PetVaccinationsList> arrrayListVaccination = new ArrayList<PetVaccinationsList>();


        btnCreateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return fragView;
    }

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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        /*if(getActivity()!=null){
            Dashboard activity= (Dashboard) getActivity();
            activity.setToolbarTitle("Edit pet profile");
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
