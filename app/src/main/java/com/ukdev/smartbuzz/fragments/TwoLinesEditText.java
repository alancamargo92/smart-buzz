package com.ukdev.smartbuzz.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.ukdev.smartbuzz.R;

/**
 * Fragment to enter single-line texts
 *
 * @author William Miranda
 */
public class TwoLinesEditText extends TwoLinesDefaultFragment<String> {

    private ViewGroup rootView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.two_lines_default, container, ATTACH_TO_ROOT);
        rootView = view.findViewById(R.id.rootView);
        if (onFragmentInflatedListener != null)
            onFragmentInflatedListener.onFragmentInflated(this);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogue(view.getContext());
            }
        });
    }

    /**
     * Sets the value
     *
     * @param value the value
     */
    @Override
    public void setValue(String value) {
        this.value = value;
        if (textSummary != null)
            textSummary.setText(value);
    }

    private void showDialogue(Context context) {
        AlertDialog.Builder dialogueBuilder = new AlertDialog.Builder(context);
        dialogueBuilder.setTitle(title);
        dialogueBuilder.setView(R.layout.dialogue_edit_text);
        dialogueBuilder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogueInterface, int which) {
                AlertDialog dialogue = (AlertDialog) dialogueInterface;
                EditText editText = dialogue.findViewById(R.id.dialogue_edit_text);
                if (editText != null) {
                    String value = editText.getText().toString();
                    setValue(value);
                    if (changeListener != null)
                        changeListener.onChange(value);
                }
            }
        });
        dialogueBuilder.setNegativeButton(R.string.cancel, null);
        Dialog dialogue = dialogueBuilder.show();
        if (value != null) {
            EditText editText = dialogue.findViewById(R.id.dialogue_edit_text);
            editText.setText(value);
        }
    }

}
