package net.nevowolfman.mipo.UI.Main;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import net.nevowolfman.mipo.Model.EventDate;
import net.nevowolfman.mipo.Model.Organization;
import net.nevowolfman.mipo.R;
import net.nevowolfman.mipo.UI.OrgEditor.OrgEditorFragment;

public class AddOrganizationDialog extends DialogFragment implements View.OnClickListener{
    private MainActivity activity;

    private EditText etName;
    private Spinner spDay1, spDay2;
    private Button btnPick1,btnPick2;

    EventDate event1 = new EventDate(), event2 = new EventDate();

    public AddOrganizationDialog(MainActivity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        View view = getLayoutInflater().inflate(R.layout.new_org_dialog, null);
        etName = view.findViewById(R.id.etName);
        spDay1 = view.findViewById(R.id.spDay1);
        spDay2 = view.findViewById(R.id.spDay2);
        btnPick1 = view.findViewById(R.id.btnPick1);
        btnPick2 = view.findViewById(R.id.btnPick2);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                activity,
                R.array.week_array,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spDay1.setAdapter(adapter);
        spDay2.setAdapter(adapter);

        btnPick1.setOnClickListener(this);
        btnPick2.setOnClickListener(this);

        spDay1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                event1.setDay(i+1);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}});
        spDay2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                event2.setDay(i+1);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}});



        builder.setView(view).setPositiveButton(R.string.Add, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = etName.getText().toString();

                //TODO: make the errors not pop out from the dialog
                if(name.isEmpty())
                {
                    Toast.makeText(activity, "Please fill out name", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(event1.getMinute() == 60) {
                    Toast.makeText(activity, "Please fill out time of first event", Toast.LENGTH_SHORT).show();
                    return;
                }

                Organization org = new Organization(name, event1);
                activity.setAlarm(event1);
                if(event2.getMinute() != 60){
                    org.getEventDates().add(1, event2);
                    activity.setAlarm(event2);
                }
                activity.addOrg(org);
                activity.swapFragments(R.id.fragOrg, new OrgEditorFragment());
            }
        }).setNegativeButton(R.string.Cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //close the dialog
            }
        });

        return builder.create();
    }

    @Override
    public void onClick(View view) {
        EventDate eventDate;
        if(view == btnPick1) {
            eventDate = event1;
        }
        else {
            eventDate = event2;
        }
        TimePickerDialog timePickerDialog = new TimePickerDialog(activity, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                eventDate.setHour(hour);
                eventDate.setMinute(minute);
            }
        }, eventDate.getHour(), eventDate.getMinute(), true);
        timePickerDialog.show();
    }
}
