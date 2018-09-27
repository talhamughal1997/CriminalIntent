package com.example.mughal.criminalcontent;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.UUID;

public class CrimeActivity extends SingleFragmentActivity {


    public static final String EXTRA_CRIME_ID = "CriminalContent.crime_id";

    public static Intent newIntent(Context packageContext, UUID crime_id) {
        Intent intent = new Intent(packageContext, CrimeActivity.class);
        intent.putExtra(EXTRA_CRIME_ID, crime_id);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
       UUID crimeId = (UUID) getIntent().getSerializableExtra(EXTRA_CRIME_ID);
       return FragmentCrime.newInstance(crimeId);
    }
}
