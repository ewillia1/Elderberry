package edu.northeastern.elderberry.addMed;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

import edu.northeastern.elderberry.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MedInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class MedInfoFragment extends Fragment {
    private static final String TAG = "MedInfoFragment";
    private ItemViewModel medNameViewModel;
    private TextInputEditText medNameEditText;
    private String medName;

    public MedInfoFragment() {
        Log.d(TAG, "_____MedInfoFragment");
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MedInfoFragment.
     */
    public static MedInfoFragment newInstance() {
        Log.d(TAG, "_____newInstance");
        return new MedInfoFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "_____onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d(TAG, "_____onCreateView");
        View view = inflater.inflate(R.layout.fragment_med_info, container, false);
        TextInputLayout textInputMedName = view.findViewById(R.id.textInputMedName);
        this.medNameEditText = view.findViewById(R.id.medNameEditText);

        // TODO: Figure out what listener I need to use here! Only want the listener to be called when leaving the edit text (so long as there is a non-empty string inside).

//        this.textInputMedName.addOnEditTextAttachedListener(textInputLayout -> {
//            this.medName = this.medNameEditText.getText().toString();
//            Log.d(TAG, "_____onEditTextAttached: this.medName = " + this.medName);
//            this.viewModel.selectItem(this.medName);
//        });

//        this.medNameEditText.setOnClickListener(v -> {
//            this.medName = this.medNameEditText.getText().toString();
//            Log.d(TAG, "_____onClick: this.medName = " + this.medName);
//            this.viewModel.selectItem(this.medName);
//        });

        this.medNameEditText.setOnFocusChangeListener((v, hasFocus) -> {
            this.medName = Objects.requireNonNull(this.medNameEditText.getText()).toString();
            Log.d(TAG, "_____setOnFocusChangeListener: this.medName = " + this.medName);
            this.medNameViewModel.setItem(this.medName);
        });

//        this.medNameEditText.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                medName = Objects.requireNonNull(medNameEditText.getText()).toString();
//                Log.d(TAG, "_____setOnFocusChangeListener: this.medName = " + medName);
//                medNameViewModel.setItem(medName);
//            }
//        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "_____onViewCreated");
        this.medNameViewModel = new ViewModelProvider(requireActivity()).get(ItemViewModel.class);
    }
}