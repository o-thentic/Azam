package com.tanzakesho.azam;

import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.tanzakesho.framework.FontCache;

import java.lang.reflect.Type;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentSubmittButton.OnSubmitInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentSubmittButton#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentSubmittButton extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
   
    // TODO: Rename and change types of parameters
    
    private OnSubmitInteractionListener mListener;

    public FragmentSubmittButton() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static FragmentSubmittButton newInstance() {
        FragmentSubmittButton fragment = new FragmentSubmittButton();
        Bundle args = new Bundle();
                fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
           
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_submitt_button, container, false);
        Button submitButton = (Button) view.findViewById(R.id.button_submit);
        ViewGroup.LayoutParams params = submitButton.getLayoutParams();

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        params.width = displayMetrics.widthPixels/2;
        params.height = displayMetrics.widthPixels/2;
        submitButton.setLayoutParams(params);

        Typeface typeface = FontCache.getTypeface("Roboto-Light.ttf", getActivity());
        submitButton.setTypeface(typeface);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onSubmitInteraction();
            }
        });

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onSubmitInteraction();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnSubmitInteractionListener) {
            mListener = (OnSubmitInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnSubmitInteractionListener");
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
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnSubmitInteractionListener {
        // TODO: Update argument type and name
        void onSubmitInteraction();
    }


}
