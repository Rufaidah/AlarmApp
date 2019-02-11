package com.fai.alarmapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView tvOneTimeDate, tvOneTimeTime, tvRepeatingTime;
    private EditText edtOneTimeNote, edtRepeatingNote;
    private Button btnOneTimeDate, btnOneTimeTime, btnOneTime, btnRepeatingTime, btnRepeating, btnCancelRepeatingAlarm;
    private Calendar calOneTimeDate, calOneTimeTime, calRepeatingTime;
    private AlarmPreference alarmPreference;
    private AlarmReceiver alarmReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvOneTimeDate = (TextView)findViewById(R.id.tv_one_time_alarm_date);
        tvOneTimeTime = (TextView)findViewById(R.id.tv_one_time_alarm_time);
        tvRepeatingTime = (TextView)findViewById(R.id.tv_repeating_alarm_time);
        edtOneTimeNote = (EditText)findViewById(R.id.edt_one_time_alarm_note);
        edtRepeatingNote = (EditText)findViewById(R.id.edt_repeating_alarm_note);
        btnOneTimeDate = (Button)findViewById(R.id.btn_one_time_alarm_date);
        btnOneTimeTime = (Button)findViewById(R.id.btn_one_time_alarm_time);
        btnOneTime = (Button)findViewById(R.id.btn_set_one_time_alarm);
        btnRepeatingTime = (Button)findViewById(R.id.btn_repeating_alarm_time);
        btnRepeating = (Button)findViewById(R.id.btn_set_repeating_alarm);
        btnCancelRepeatingAlarm = (Button)findViewById(R.id.btn_cancel_repeating_alarm);

        btnOneTimeDate.setOnClickListener(this);
        btnOneTimeTime.setOnClickListener(this);
        btnOneTime.setOnClickListener(this);
        btnRepeatingTime.setOnClickListener(this);
        btnRepeating.setOnClickListener(this);
        btnCancelRepeatingAlarm.setOnClickListener(this);

        calOneTimeDate = Calendar.getInstance();
        calOneTimeTime = Calendar.getInstance();
        calRepeatingTime = Calendar.getInstance();

        alarmPreference = new AlarmPreference(this);
        alarmReceiver = new AlarmReceiver();

        if (!TextUtils.isEmpty(alarmPreference.getOneTimeDate())){
            setOneTimeText();
        }

        if (!TextUtils.isEmpty(alarmPreference.getRepeatingTime())){
            setRepeatingText();
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_one_time_alarm_date) {
            final Calendar currentDate = Calendar.getInstance();
            new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    calOneTimeDate.set(year, month, dayOfMonth);
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    tvOneTimeDate.setText(dateFormat.format(calOneTimeDate.getTime()));
                }
            }, currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DAY_OF_MONTH)).show();
        } else if (view.getId() == R.id.btn_one_time_alarm_time) {
            final Calendar currentDate = Calendar.getInstance();
            new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    calOneTimeTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    calOneTimeTime.set(Calendar.MINUTE, minute);
                    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
                    tvOneTimeTime.setText(timeFormat.format(calOneTimeTime.getTime()));
                }
            }, currentDate.get(Calendar.HOUR_OF_DAY), currentDate.get(Calendar.MINUTE), true).show();
        } else if (view.getId() == R.id.btn_repeating_alarm_time) {
            final Calendar currentDate = Calendar.getInstance();
            new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    calRepeatingTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    calRepeatingTime.set(Calendar.MINUTE, minute);
                    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
                    tvRepeatingTime.setText(timeFormat.format(calRepeatingTime.getTime()));
                }
            }, currentDate.get(Calendar.HOUR_OF_DAY), currentDate.get(Calendar.MINUTE), true).show();
        } else if (view.getId() == R.id.btn_set_one_time_alarm) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String oneTimeDate = dateFormat.format(calOneTimeDate.getTime());
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
            String oneTimeTime = timeFormat.format(calOneTimeTime.getTime());
            String oneTimeNote = edtOneTimeNote.getText().toString();

            alarmPreference.setOneTimeDate(oneTimeDate);
            alarmPreference.setOneTimeTime(oneTimeTime);
            alarmPreference.setOneTimeNote(oneTimeNote);

            alarmReceiver.setOneTimeAlarm(this, AlarmReceiver.TYPE_ONE_TIME,
                    alarmPreference.getOneTimeDate(),
                    alarmPreference.getOneTimeTime(),
                    alarmPreference.getOneTimeNote());
        } else if (view.getId() == R.id.btn_set_repeating_alarm) {
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
            String repeatingTime = timeFormat.format(calRepeatingTime.getTime());
            String repeatingNote = edtRepeatingNote.getText().toString();

            alarmPreference.setRepeatingTime(repeatingTime);
            alarmPreference.setRepeatingNote(repeatingNote);

            alarmReceiver.setRepeatingAlarm(this, AlarmReceiver.TYPE_REPEATING,
                    alarmPreference.getRepeatingTime(),
                    alarmPreference.getRepeatingNote());
        } else if (view.getId() == R.id.btn_cancel_repeating_alarm) {
            alarmReceiver.cancelAlarm(this, AlarmReceiver.TYPE_REPEATING);
        }
    }

    private void setOneTimeText() {
        tvOneTimeDate.setText(alarmPreference.getOneTimeDate());
        tvOneTimeTime.setText(alarmPreference.getOneTimeTime());
        edtOneTimeNote.setText(alarmPreference.getOneTimeNote());
    }

    private void setRepeatingText() {
        tvRepeatingTime.setText(alarmPreference.getRepeatingTime());
        edtRepeatingNote.setText(alarmPreference.getRepeatingNote());
    }

}
