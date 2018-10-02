package com.example.mughal.criminalcontent;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;

public class ConfirmDialog extends DialogFragment {

    public static final String SOLVED = "isSolved";
    public static final int REQUEST_CODE = 001;
    public static final String EXTRA_CHECKED = "criminal.solved";

    public static ConfirmDialog newInstance(boolean isSolved) {
        Bundle Args = new Bundle();
        Args.putSerializable(SOLVED, isSolved);

        ConfirmDialog confirmDialog = new ConfirmDialog();
        confirmDialog.setArguments(Args);
        return confirmDialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        final boolean isSolved = (boolean) getArguments().getSerializable(SOLVED);

        View v = LayoutInflater.from(getActivity()).inflate(R.layout.confirm_dialog, null);

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setPositiveButton("Yes I'm Sure", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        sendResult(Activity.RESULT_OK,isSolved);
                    }
                })
                .create();
    }

    private void sendResult(int resultCode , boolean isSolved) {
        if (getTargetFragment() == null) {
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(EXTRA_CHECKED, resultCode);

        getTargetFragment().onActivityResult(getTargetRequestCode(),resultCode,intent);
    }
}
