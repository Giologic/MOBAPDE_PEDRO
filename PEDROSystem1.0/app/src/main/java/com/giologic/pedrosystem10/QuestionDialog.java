package com.giologic.pedrosystem10;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

/**
 * Created by MSI LEOPARD on 4/18/2016.
 */
public class QuestionDialog extends DialogFragment {
    View v;
    EditText etQuestion;
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        v = LayoutInflater
                .from(getActivity())
                .inflate(R.layout.dialog_ask, null);
        etQuestion = (EditText) v.findViewById(R.id.et_question);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                .setTitle("Ask CSG")
                .setView(v)
                .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String value = etQuestion.getText().toString();
                        ((AskCSGFragment) getTargetFragment()).addQuestion(value);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                    }
                });
        return builder.create();
    }

    @Override
    public void onStart() {
        super.onStart();
        etQuestion.getBackground().setColorFilter(ContextCompat.getColor(getContext(), R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
    }
}
