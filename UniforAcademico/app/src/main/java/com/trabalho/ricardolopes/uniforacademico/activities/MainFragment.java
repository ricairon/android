package com.trabalho.ricardolopes.uniforacademico.activities;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.trabalho.ricardolopes.uniforacademico.R;

import java.util.TimerTask;

/**
 * O aplicativo principal é guiado por fragments.
 * Classe com o comportamento do conteúdo do fragment Main
 */
public class MainFragment extends Fragment {

    private Handler handler;
    private Runnable callback;


    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        return view;
    }

}
