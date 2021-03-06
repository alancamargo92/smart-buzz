package com.ukdev.smartbuzz.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ukdev.smartbuzz.R;
import com.ukdev.smartbuzz.model.Time;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Fragment containing a {@code RadioGroup}
 *
 * @author William Miranda
 */
public class TwoLinesSnoozeDurationPicker extends TwoLinesDefaultFragment<Long> {

    public static final String ARG_OPTIONS_TEXT = "options_text";
    public static final String ARG_OPTIONS_VALUE = "options_value";

    private ViewGroup rootView;
    private Map<Long, String> mapOptionValue;
    private int selectedIndex = -1;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.two_lines_default, container, ATTACH_TO_ROOT);
        rootView = view.findViewById(R.id.rootView);
        if (getArguments() != null) {
            String[] texts = getArguments().getStringArray(ARG_OPTIONS_TEXT);
            long[] values = getArguments().getLongArray(ARG_OPTIONS_VALUE);
            mapOptionValue = new LinkedHashMap<>();
            if (texts != null) {
                for (int i = 0; i < texts.length; i++) {
                    if (values != null)
                        mapOptionValue.put(values[i], texts[i]);
                }
            }
        }
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

    @Override
    public void setValue(Long value) {
        this.value = value;
        if (textSummary != null)
            textSummary.setText(mapOptionValue.get(value));
    }

    /**
     * Gets the value
     *
     * @return the value
     */
    @Override
    public Long getValue() {
        if (value == null)
            value = Time.FIVE_MINUTES;
        return value;
    }

    public void setDefaultSelectedItem() {
        long item = Time.FIVE_MINUTES;
        if (mapOptionValue.containsKey(item))
            setValue(item);
    }

    private final DialogInterface.OnClickListener clickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogueInterface, int which) {
            selectedIndex = which;
        }
    };

    private void showDialogue(Context context) {
        AlertDialog.Builder dialogueBuilder = new AlertDialog.Builder(context);
        dialogueBuilder.setTitle(title);
        dialogueBuilder.setSingleChoiceItems(mapOptionValue.values().toArray(new String[0]),
                                             selectedIndex, clickListener);

        dialogueBuilder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogueInterface, int which) {
                int i = 0;
                for (Map.Entry<Long, String> entry : mapOptionValue.entrySet()) {
                    if (i == selectedIndex)
                        setValue(entry.getKey());
                    i++;
                }
            }
        });
        dialogueBuilder.setNegativeButton(R.string.cancel, null);
        dialogueBuilder.show();
    }

}
