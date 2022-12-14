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
    private ItemViewModel viewModel;
    private TextInputEditText infoEditText;
    private TextInputEditText medNameEditText;
    private String medName;
    private String information;

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
        // Inflate the layout for this fragment.
        Log.d(TAG, "_____onCreateView");
        View view = inflater.inflate(R.layout.fragment_med_info, container, false);
        TextInputLayout textInputMedName = view.findViewById(R.id.EditTextInputMedName);
        this.medNameEditText = view.findViewById(R.id.EditMedNameText);

        // Every time a new character is added to the TextInputEditText for medication name, the viewModel is updated.
        this.medNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.d(TAG, "_____beforeTextChanged");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                medName = Objects.requireNonNull(medNameEditText.getText()).toString();
                Log.d(TAG, "_____addTextChangedListener (onTextChanged): this.medName = " + medName);
                viewModel.setMedName(medName);
            }

            @Override
            public void afterTextChanged(Editable s) {
                medName = Objects.requireNonNull(medNameEditText.getText()).toString();
                Log.d(TAG, "_____addTextChangedListener (afterTextChanged): this.medName = " + medName);
                viewModel.setMedName(medName);
            }
        });

        this.infoEditText = view.findViewById(R.id.EditInfoEditText);

        // Every time a new character is added to the TextInputEditText for information, the viewModel is updated.
        this.infoEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.d(TAG, "_____beforeTextChanged");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                information = Objects.requireNonNull(infoEditText.getText()).toString();
                Log.d(TAG, "_____addTextChangedListener: this.information = " + information);
                viewModel.setInformation(information);
            }

            @Override
            public void afterTextChanged(Editable s) {
                information = Objects.requireNonNull(infoEditText.getText()).toString();
                Log.d(TAG, "_____addTextChangedListener: this.information = " + information);
                viewModel.setInformation(information);
            }
        });

        // Moving from onViewCreated to OnCreatedView.
        this.viewModel = new ViewModelProvider(requireActivity()).get(ItemViewModel.class);

        // Retrieve medKey selected from the yourMedication activity.
        AddMedicationActivity addMedicationActivity = (AddMedicationActivity) getActivity();
        assert addMedicationActivity != null;
        String editMedKey = addMedicationActivity.getEditMedKey();

        // Pre-fill fields is a existing medication is selected.
        if (editMedKey != null) {
            this.medNameEditText.setText(this.viewModel.getMedName().getValue());
            this.infoEditText.setText(this.viewModel.getInformation().getValue());
        }

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "_____onViewCreated");
        this.viewModel = new ViewModelProvider(requireActivity()).get(ItemViewModel.class); // comment out the next line with Elizabeth's alignment
    }
}