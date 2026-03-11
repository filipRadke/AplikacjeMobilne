package com.example.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
public class FirstFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first, container, false);
        Button button = view.findViewById(R.id.btn);
        button.setOnClickListener(v -> {
            FragmentActivity activity = getActivity();
            assert activity != null;
            EditText edit = activity.findViewById(R.id.editText);
            Toast.makeText(activity, edit.getText().toString(), Toast.LENGTH_SHORT).show();
        });
        return view;
    }
}
