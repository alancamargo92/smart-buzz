package com.ukdev.smartbuzz.activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.SparseBooleanArray;
import android.view.View;
import com.ukdev.smartbuzz.R;
import com.ukdev.smartbuzz.database.AlarmDao;
import com.ukdev.smartbuzz.fragments.*;
import com.ukdev.smartbuzz.misc.IntentExtra;
import com.ukdev.smartbuzz.model.Alarm;
import com.ukdev.smartbuzz.model.AlarmBuilder;
import com.ukdev.smartbuzz.model.Time;
import com.ukdev.smartbuzz.model.enums.SnoozeDuration;
import com.ukdev.smartbuzz.util.Utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * The activity where alarms are set
 *
 * @author Alan Camargo
 */
public class SetupActivity extends AppCompatActivity {

    private AlarmDao dao;
    private boolean editMode;
    private Context context;
    private TwoLinesEditText titleFragment;
    private TwoLinesTimePicker timePickerFragment;
    private TwoLinesDayOfTheWeek repetitionFragment;
    private TwoLinesRadioGroup snoozeDurationFragment;
    private TwoLinesRingtone ringtoneFragment;
    private TwoLinesSeekBar volumeFragment;
    private TwoLinesSwitch vibrationFragment;
    private TwoLinesMemo textFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);
        initialiseComponents();
    }

    @Override
    protected void onResume() {
        super.onResume();
        editMode = getIntent().getBooleanExtra(IntentExtra.EDIT_MODE.toString(), false);
        replaceFragmentPlaceholders();
        if (editMode)
            setFragmentValues();
    }

    private void initialiseComponents() {
        context = this;
        dao = AlarmDao.getInstance(context);
        setSaveButton();
        setTitleFragment();
        setTimePickerFragment();
        setRepetitionFragment();
        setSnoozeDurationFragment();
        setRingtoneFragment();
        setVolumeFragment();
        setVibrationFragment();
        setTextFragment();
    }

    private void replaceFragmentPlaceholders() {
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.placeholder_title, titleFragment);
        transaction.replace(R.id.placeholder_time_picker, timePickerFragment);
        transaction.replace(R.id.placeholder_repetition, repetitionFragment);
        transaction.replace(R.id.placeholder_snooze_duration, snoozeDurationFragment);
        transaction.replace(R.id.placeholder_ringtone, ringtoneFragment);
        transaction.replace(R.id.placeholder_volume, volumeFragment);
        transaction.replace(R.id.placeholder_vibrate, vibrationFragment);
        transaction.replace(R.id.placeholder_text, textFragment);
        transaction.commit();
    }

    private void setSaveButton() {
        FloatingActionButton saveButton = (FloatingActionButton) findViewById(R.id.fab_setup);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Alarm alarm = buildAlarm();
                boolean success;
                if (editMode)
                    success = dao.update(alarm);
                else
                    success = dao.insert(alarm);
                if (!success) {
                    Snackbar.make(view, R.string.error_save_alarm, Snackbar.LENGTH_LONG)
                            .setAction(R.string.retry, new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    if (editMode)
                                        dao.update(alarm);
                                    else
                                        dao.insert(alarm);
                                }
                            }).show();
                }
            }
        });
    }

    private void setTitleFragment() {
        titleFragment = new TwoLinesEditText();
        String title = getString(R.string.title);
        titleFragment.setTitle(title);
    }

    private void setTimePickerFragment() {
        timePickerFragment = new TwoLinesTimePicker();
        String title = getString(R.string.time);
        timePickerFragment.setTitle(title);
    }

    private void setRepetitionFragment() {
        repetitionFragment = new TwoLinesDayOfTheWeek();
        String title = getString(R.string.repeat);
        repetitionFragment.setTitle(title);
    }

    private void setSnoozeDurationFragment() {
        snoozeDurationFragment = new TwoLinesRadioGroup();
        String title = getString(R.string.snooze_duration);
        snoozeDurationFragment.setTitle(title);
    }

    private void setRingtoneFragment() {
        ringtoneFragment = new TwoLinesRingtone();
        String title = getString(R.string.ringtone);
        ringtoneFragment.setTitle(title);
    }

    private void setVolumeFragment() {
        volumeFragment = new TwoLinesSeekBar();
        String title = getString(R.string.volume);
        volumeFragment.setTitle(title);
    }

    private void setVibrationFragment() {
        vibrationFragment = new TwoLinesSwitch();
        String title = getString(R.string.vibrate);
        vibrationFragment.setTitle(title);
    }

    private void setTextFragment() {
        textFragment = new TwoLinesMemo();
        String title = getString(R.string.text);
        textFragment.setTitle(title);
        textFragment.setChangeListener(new TwoLinesDefaultFragment.TwoLinesChangeListener<String>() {
            @Override
            public void onChange(String newValue) {
                getActionBar().setTitle(newValue);
            }
        });
    }

    private Alarm buildAlarm() {
        String title = titleFragment.getValue();
        Time triggerTime = timePickerFragment.getValue();
        SparseBooleanArray array = repetitionFragment.getValue();
        List<Integer> days = new ArrayList<>();
        for (int i = Calendar.SUNDAY; i <= Calendar.SATURDAY; i++) {
            if (array.get(i)) {
                int index = i - 1;
                days.add(array.keyAt(index));
            }
        }
        int[] repetition = new int[days.size()];
        for (int i = 0; i < repetition.length; i++)
            repetition[i] = days.get(i);
        SnoozeDuration snoozeDuration = SnoozeDuration.valueOf(snoozeDurationFragment.getValue());
        Uri ringtoneUri = ringtoneFragment.getValue();
        int volume = volumeFragment.getValue();
        boolean vibrate = vibrationFragment.getValue();
        String text = textFragment.getValue();
        AlarmBuilder builder = new AlarmBuilder().setTitle(title)
                                                 .setTriggerTime(triggerTime)
                                                 .setRepetition(repetition)
                                                 .setSnoozeDuration(snoozeDuration)
                                                 .setRingtoneUri(ringtoneUri)
                                                 .setVolume(volume)
                                                 .setVibrate(vibrate)
                                                 .setText(text);
        return builder.build();
    }

    private void setFragmentValues() {
        int id = getIntent().getIntExtra(IntentExtra.ID.toString(), 0);
        Alarm alarm = dao.select(id);

        titleFragment.setSummary(alarm.getTitle());
        titleFragment.setValue(alarm.getTitle());

        timePickerFragment.setSummary(alarm.getTriggerTime().toString());
        timePickerFragment.setValue(alarm.getTriggerTime());

        repetitionFragment.setSummary(Utils.convertIntArrayToString(context, alarm.getRepetition()));
        // set value

        snoozeDurationFragment.setSummary(alarm.getSnoozeDuration().toString());
        snoozeDurationFragment.setValue(alarm.getSnoozeDuration().getValue());

        RingtoneManager manager = new RingtoneManager(context);
        int ringtonePosition = manager.getRingtonePosition(alarm.getRingtoneUri());
        Ringtone ringtone = manager.getRingtone(ringtonePosition);
        ringtoneFragment.setSummary(ringtone.getTitle(context));
        ringtoneFragment.setValue(alarm.getRingtoneUri());

        volumeFragment.setValue(alarm.getVolume());

        vibrationFragment.setValue(alarm.vibrates());

        textFragment.setSummary(alarm.getText());
        textFragment.setValue(alarm.getText());
    }

}
