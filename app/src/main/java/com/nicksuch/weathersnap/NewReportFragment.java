package com.nicksuch.weathersnap;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseUser;
import com.parse.SaveCallback;


/*
 * This fragment manages the data entry for a
 * new Report object. It lets the user input a
 * report name, describe the conditions, and take a
 * photo. If there is already a photo associated
 * with this report, it will be displayed in the
 * preview at the bottom, which is a standalone
 * ParseImageView.
 */
public class NewReportFragment extends Fragment {

    private ImageButton photoButton;
    private Button saveButton;
    private Button cancelButton;
    private TextView reportName;
    private Spinner reportConditions;
    private ParseImageView reportPreview;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent,
                             Bundle SavedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_new_report, parent, false);

        reportName = ((EditText) v.findViewById(R.id.report_name));

        // The reportConditions spinner lets people assign conditions to reports they're
        // making.
        reportConditions = ((Spinner) v.findViewById(R.id.conditions_spinner));
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter
                .createFromResource(getActivity(), R.array.conditions_array,
                        android.R.layout.simple_spinner_dropdown_item);
        reportConditions.setAdapter(spinnerAdapter);

        photoButton = ((ImageButton) v.findViewById(R.id.photo_button));
        photoButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getActivity()
                        .getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(reportName.getWindowToken(), 0);
                startCamera();
            }
        });

        saveButton = ((Button) v.findViewById(R.id.save_button));
        saveButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Report report = ((PhotoActivity) getActivity()).getCurrentReport();

                // When the user clicks "Save," upload the report to Parse
                // Add data to the report object:
                report.setTitle(reportName.getText().toString());

                // Associate the report with the current user
                report.setAuthor(ParseUser.getCurrentUser());

                // Add the conditions
                report.setConditions(reportConditions.getSelectedItem().toString());

                // If the user added a photo, that data will be
                // added in the CameraFragment

                // Save the report and return
                report.saveInBackground(new SaveCallback() {

                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            getActivity().setResult(Activity.RESULT_OK);
                            getActivity().finish();
                        } else {
                            Toast.makeText(
                                    getActivity().getApplicationContext(),
                                    "Error saving: " + e.getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }

                });

            }
        });

        cancelButton = ((Button) v.findViewById(R.id.cancel_button));
        cancelButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                getActivity().setResult(Activity.RESULT_CANCELED);
                getActivity().finish();
            }
        });

        // Until the user has taken a photo, hide the preview
        reportPreview = (ParseImageView) v.findViewById(R.id.report_preview_image);
        reportPreview.setVisibility(View.INVISIBLE);

        return v;
    }

    /*
     * All data entry about a Report object is managed from the NewReportActivity.
     * When the user wants to add a photo, we'll start up a custom
     * CameraFragment that will let them take the photo and save it to the Report
     * object owned by the NewReportActivity. Create a new CameraFragment, swap
     * the contents of the fragmentContainer (see activity_new_report.xml), then
     * add the NewReportFragment to the back stack so we can return to it when the
     * camera is finished.
     */
    public void startCamera() {
        Fragment cameraFragment = new CameraFragment();
        FragmentTransaction transaction = getActivity().getFragmentManager()
                .beginTransaction();
        transaction.replace(R.id.fragmentContainer, cameraFragment);
        transaction.addToBackStack("NewReportFragment");
        transaction.commit();
    }

    /*
     * On resume, check and see if a report photo has been set from the
     * CameraFragment. If it has, load the image in this fragment and make the
     * preview image visible.
     */
    @Override
    public void onResume() {
        super.onResume();
        ParseFile photoFile = ((PhotoActivity) getActivity())
                .getCurrentReport().getPhotoFile();
        if (photoFile != null) {
            reportPreview.setParseFile(photoFile);
            reportPreview.loadInBackground(new GetDataCallback() {
                @Override
                public void done(byte[] data, ParseException e) {
                    reportPreview.setVisibility(View.VISIBLE);
                }
            });
        }
    }

}

