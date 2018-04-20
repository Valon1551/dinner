package com.jolameva.app.runningdinner.creatematchroom;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.jolameva.app.runningdinner.R;
import com.jolameva.app.runningdinner.customview.WeekdaysWidget;

public class CreateMatchRoomActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_weekdays_widget);
    }

    public void weekdayWidget(View v)
    {
        WeekdaysWidget widget = (WeekdaysWidget) findViewById(R.id.widget_weekdays);
        Toast.makeText(this, "Widget Weekday\n"+widget.getSelectedDays()+"\n"
                +widget.getSelectedDaysText()+"\nAll days Selected ? - "+widget.isAllDaysSelected()
                +"\nNo day Selected ? - "+widget.isNoneSelected(), Toast.LENGTH_SHORT).show();
    }
}
