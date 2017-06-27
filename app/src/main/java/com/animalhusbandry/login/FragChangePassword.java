package com.animalhusbandry.login;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.animalhusbandry.R;
import com.animalhusbandry.dashboard.BaseFragment;
import com.animalhusbandry.dashboard.DashBoardFragment;
import com.animalhusbandry.model.ChangePasswordRequest;
import com.animalhusbandry.model.ChangePasswordResponse;
import com.animalhusbandry.retrofit.RetroUtils;
import com.animalhusbandry.utils.setToolbar;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragChangePassword.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragChangePassword#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragChangePassword extends BaseFragment {
    EditText etOldPassword, etSetPassword;
    ImageButton btnSubmitPassword;
    public ProgressDialog ringProgressDialog;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public FragChangePassword() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragChangePassword.
     */
    // TODO: Rename and change types and number of parameters
    public static FragChangePassword newInstance(String param1, String param2) {
        FragChangePassword fragment = new FragChangePassword();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View fragView = inflater.inflate(R.layout.frag_change_password, container, false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setToolbar.setToolbar( activity, "Change password", false);
        }
        Toolbar  toolbar = (Toolbar)activity. findViewById(R.id.toolbar);
        TextView textView = (TextView) toolbar.findViewById(R.id.toolbar_title);
        ImageButton backBtn= (ImageButton) toolbar.findViewById(R.id.backBtn);
        backBtn.setEnabled(true);
        textView.setEnabled(true);
        toolbar.setEnabled(true);
        etOldPassword = (EditText) fragView.findViewById(R.id.etOldPassword);
        etSetPassword = (EditText) fragView.findViewById(R.id.etSetPassword);
        btnSubmitPassword = (ImageButton) fragView.findViewById(R.id.btnSubmitPassword);
        btnSubmitPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etOldPassword.getText().toString().equals("")) {
                    etOldPassword.setError("Enter old password");
                    etOldPassword.requestFocus();
                } else if (etOldPassword.getText().toString().length() < 8 || etOldPassword.getText().toString().length() > 15) {
                    etOldPassword.setError("Password must be 8-15 characters");
                    etOldPassword.requestFocus();
                } else if (etSetPassword.getText().toString().equals("")) {
                    etSetPassword.setError("Enter new password");
                    etSetPassword.requestFocus();
                } else if (etSetPassword.getText().toString().length() < 8 || etSetPassword.getText().toString().length() > 15) {
                    etSetPassword.setError("Password must be 8-15 characters");
                    etSetPassword.requestFocus();
                } else if (!(isValidPassword(etSetPassword.getText().toString()))) {
                    etSetPassword.setError("Password should include atleast 1 special character(@,# etc) & 1 number(0-9)");
                    etSetPassword.requestFocus();
                } else if ((etOldPassword.getText().toString().equals(etSetPassword.getText().toString()))) {
                    Toast.makeText(getContext(), "Password is same, enter a new password ", Toast.LENGTH_SHORT).show();
                } else if (!(etOldPassword.getText().toString().equals(etSetPassword.getText().toString()))) {
                    ringProgressDialog = ProgressDialog.show(getContext(), "Please wait ...", "Changing password", true);
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
                    final SharedPreferences sharedPreferences = getContext().getSharedPreferences("Options", MODE_PRIVATE);
                    final String strUserId = sharedPreferences.getString("strUserId", "");
                    Log.e("^^^^^^^^^",strUserId+"");
                    String strOldPassword = etOldPassword.getText().toString();
                    String strSetPassword = etSetPassword.getText().toString();
                    ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest();
                    changePasswordRequest.setUserId(strUserId);
                    changePasswordRequest.setOldPassword(strOldPassword);
                    changePasswordRequest.setPassword(strSetPassword);
                    doChangePassword(changePasswordRequest);
                }
            }
        });
        return fragView;
    }
    private void doChangePassword(ChangePasswordRequest changePasswordRequest) {
        RetroUtils retroUtils = new RetroUtils(getContext());
        retroUtils.getApiClient().changeUserPassword(changePasswordRequest).enqueue(new Callback<ChangePasswordResponse>() {
            @Override
            public void onResponse(Call<ChangePasswordResponse> call, Response<ChangePasswordResponse> response) {
                if (response.body().getResponse().getCode()==200) {
                    Toast.makeText(getContext(), "Password changed sucessfully", Toast.LENGTH_SHORT).show();
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    DashBoardFragment baseFragment = new DashBoardFragment();
                    fragmentTransaction.replace(R.id.xmlFragment, baseFragment, "basefragmenttag");
                    fragmentTransaction.addToBackStack("baseFragmentFromChangePassword");
                    fragmentTransaction.commit();
                } else if (response.body().getResponse().getCode()==603) {
                    Toast.makeText(getContext(), "Old password doesn't matched", Toast.LENGTH_SHORT).show();
                } else if (response.body().getResponse().getCode()==401) {
                    Toast.makeText(getContext(), "Your session has been expired. Please login again", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ChangePasswordResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Something went wrong. Please try again", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean isValidPassword(String password) {
        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "((?=.*\\d)(?=.*[a-z])(?=.*[@#$%]).{8,15})";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);
        return matcher.matches();
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
       /* if (context instanceof OnFragmentInteractionListener) {
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
