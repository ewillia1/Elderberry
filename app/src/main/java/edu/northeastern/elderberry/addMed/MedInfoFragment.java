package edu.northeastern.elderberry.addMed;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethod;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

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
// TODO: When keyboard is closed lose focus on Medication Name TextInputEditText.
public class MedInfoFragment extends Fragment {
    private static final String TAG = "MedInfoFragment";
    private ItemViewModel viewModel;
    private TextInputEditText medNameEditText;
    private TextInputEditText infoEditText;
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
        // Inflate the layout for this fragment
        Log.d(TAG, "_____onCreateView");
        View view = inflater.inflate(R.layout.fragment_med_info, container, false);
        TextInputLayout textInputMedName = view.findViewById(R.id.textInputMedName);
        this.medNameEditText = view.findViewById(R.id.medNameEditText);

        this.medNameEditText.setOnFocusChangeListener((v, hasFocus) -> {
            this.medName = Objects.requireNonNull(this.medNameEditText.getText()).toString();
            Log.d(TAG, "_____setOnFocusChangeListener: this.medName = " + this.medName);
            this.viewModel.setMedName(this.medName);

//            if(!hasFocus) {
//                InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
//                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
//            }
        });

        this.infoEditText = view.findViewById(R.id.infoEditText);

        this.infoEditText.setOnFocusChangeListener((v, hasFocus) -> {
            this.information = Objects.requireNonNull(this.infoEditText.getText()).toString();
            Log.d(TAG, "setOnFocusChangeListener: this.information = " + this.information);
            this.viewModel.setInformation(this.information);
        });

        this.infoEditText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                this.information = Objects.requireNonNull(this.infoEditText.getText()).toString();
                Log.d(TAG, "_____setOnFocusChangeListener: this.information = " + this.information);
                this.viewModel.setInformation(this.information);
            }
            return false;
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "_____onViewCreated");
        this.viewModel = new ViewModelProvider(requireActivity()).get(ItemViewModel.class);
    }

//    @Override
//    public void onConfigurationChanged(@NonNull Configuration newConfig) {
//        super.onConfigurationChanged(newConfig);
//        Log.d(TAG, "_____onConfigurationChanged");
//
//        // Checks whether a hardware keyboard is available
//        if (newConfig.hardKeyboardHidden == Configuration.HARDKEYBOARDHIDDEN_NO) {
//            Toast.makeText(getContext(), "keyboard visible", Toast.LENGTH_SHORT).show();
//        } else if (newConfig.hardKeyboardHidden == Configuration.HARDKEYBOARDHIDDEN_YES) {
//            Toast.makeText(getContext(), "keyboard hidden", Toast.LENGTH_SHORT).show();
//        }
//    }
}