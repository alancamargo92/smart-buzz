package com.ukdev.smartbuzz.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ukdev.smartbuzz.R;

/**
 * Fragment for the dismiss operation
 *
 * @author Alan Camargo
 */
public class DismissFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dismiss, container, false);
    }

}
