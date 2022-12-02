package edu.northeastern.elderberry.editMed;

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
    private static final String TAG = "EditMedInfoFragment";
    private ItemViewModel viewModel;
    private TextInputEditText editInfoEditText;
    private TextInputEditText editMedNameEditText;
    private String medName;
    private String information;
    private String editMedKey;

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
        // moved from onViewCreated to here to retrieve data might work?
        this.viewModel = new ViewModelProvider(requireActivity()).get(ItemViewModel.class);

        // retrieve MedKey selected from the yourMedication activity
        EditMedicationActivity editMedActivity = (EditMedicationActivity) getActivity();
        editMedKey = editMedActivity.getEditMedKey();
        // we will check if editMedKey is present, if yes, we will populate a textInputView
        Log.d(TAG, "onCreate: edit MedKey is " + editMedKey);
        if (editMedKey != null) {
            Log.d(TAG, "onCreate: medName from viewModel is " + this.viewModel.getMedName().getValue());
            editMedNameEditText.setText(this.viewModel.getMedName().getValue());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d(TAG, "_____onCreateView");
        View view = inflater.inflate(R.layout.fragment_edit_med_info, container, false);
        //TextInputLayout textInputMedName = view.findViewById(R.id.EditTextInputMedName);
        editMedNameEditText = view.findViewById(R.id.EditMedNameText);

        // Every time a new character is added to the TextInputEditText for medication name, the viewModel is updated.
        editMedNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                medName = Objects.requireNonNull(editMedNameEditText.getText()).toString();
                Log.d(TAG, "_____setOnFocusChangeListener: this.medName = " + medName);
                viewModel.setMedName(medName);
            }

            @Override
            public void afterTextChanged(Editable s) {
                medName = Objects.requireNonNull(editMedNameEditText.getText()).toString();
                Log.d(TAG, "_____setOnFocusChangeListener: this.medName = " + medName);
                viewModel.setMedName(medName);
            }
        });

        this.editInfoEditText = view.findViewById(R.id.EditInfoEditText);

        // Every time a new character is added to the TextInputEditText for information, the viewModel is updated.
        this.editInfoEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                information = Objects.requireNonNull(editInfoEditText.getText()).toString();
                Log.d(TAG, "setOnFocusChangeListener: this.information = " + information);
                viewModel.setInformation(information);
            }

            @Override
            public void afterTextChanged(Editable s) {
                information = Objects.requireNonNull(editInfoEditText.getText()).toString();
                Log.d(TAG, "setOnFocusChangeListener: this.information = " + information);
                viewModel.setInformation(information);
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "_____onViewCreated");
        //this.viewModel = new ViewModelProvider(requireActivity()).get(ItemViewModel.class);
    }
}