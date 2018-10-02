package com.example.mughal.criminalcontent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Date;
import java.util.UUID;

import static android.widget.CompoundButton.*;

public class FragmentCrime extends Fragment {

    private static final String ARG_CRIME_ID = "crime_id";
    private static final String DIALOG_DATE = "DialogDate";
    private static final String DIALOG_CONFIRM = "DialogConfirm";

    private static final int REQUEST_DATE = 0;
    private static final int REQUEST_CONFIRM = 111;

    private Crime mCrime;
    EditText mTextField;
    Button mDateButton;
    CheckBox mSolvedCheckBox;

    public static FragmentCrime newInstance(UUID crimeId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_CRIME_ID, crimeId);

        FragmentCrime fragment = new FragmentCrime();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID crimeId = (UUID) getArguments().getSerializable(ARG_CRIME_ID);
        mCrime = CrimeLab.get(getActivity()).getCrime(crimeId);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_crime, container, false);

        mTextField = v.findViewById(R.id.crime_title);
        mTextField.setText(mCrime.getTitle());
        mDateButton = v.findViewById(R.id.crime_date);
        mSolvedCheckBox = v.findViewById(R.id.crime_solved);
        mSolvedCheckBox.setChecked(mCrime.isSolved());
        UpdateData();
        mDateButton.setEnabled(true);

        mDateButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getFragmentManager();
                DatePickerFragment dialog = DatePickerFragment.newInstance(mCrime.getDate());
                dialog.setTargetFragment(FragmentCrime.this, REQUEST_DATE);
                dialog.show(fm, DIALOG_DATE);
            }
        });


        mSolvedCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                FragmentManager fm = getFragmentManager();
                // Toast.makeText(getActivity(),String.valueOf(mSolvedCheckBox.isChecked()),Toast.LENGTH_SHORT).show();
                ConfirmDialog dialog = ConfirmDialog.newInstance(mSolvedCheckBox.isChecked());
                dialog.setTargetFragment(FragmentCrime.this,REQUEST_CONFIRM);
                dialog.show(fm,DIALOG_CONFIRM);

            }
        });

        mTextField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                mCrime.setTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        return v;
    }

    private void UpdateData() {
        mDateButton.setText(mCrime.getDate().toString());
        mSolvedCheckBox.setChecked(mCrime.isSolved());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_DATE) {
            Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mCrime.setDate(date);
            UpdateData();
        }
        if (requestCode == REQUEST_CONFIRM) {
            boolean isSolved = (boolean) data.getSerializableExtra(ConfirmDialog.EXTRA_CHECKED);
            mCrime.setSolved(isSolved);
            UpdateData();
        }

    }
}
