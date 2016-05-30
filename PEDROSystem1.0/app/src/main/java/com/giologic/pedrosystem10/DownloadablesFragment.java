package com.giologic.pedrosystem10;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.giologic.pedrosystem10.model.CourseOffering;
import com.giologic.pedrosystem10.model.Flowchart;
import com.giologic.pedrosystem10.service.FilesService;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DownloadablesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DownloadablesFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    private ProgressBar progressBar;
    private ProgressDialog dialog;
    private Spinner spinnerID;
    private Spinner spinnerCourse;
    private ImageButton btnFlowchart;
    private ImageButton btnCourseOfferings;
    private LinearLayout downloadsLayout;

    private Collection<Flowchart> flowcharts;

    private CourseOffering courseOffering;

    public DownloadablesFragment() {
        // Required empty public constructor
    }

    public static DownloadablesFragment newInstance() {
        DownloadablesFragment fragment = new DownloadablesFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_downloadables, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar_downloads);
        spinnerID = (Spinner) view.findViewById(R.id.spinner_id);
        spinnerCourse = (Spinner) view.findViewById(R.id.spinner_course);
        downloadsLayout = (LinearLayout) view.findViewById(R.id.layout_downloads);
        downloadsLayout.setVisibility(View.GONE);
        new UITask().execute();

        btnFlowchart = (ImageButton) view.findViewById(R.id.btn_flowchart);
        btnFlowchart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = spinnerID.getSelectedItem().toString();
                String course = spinnerCourse.getSelectedItem().toString();
                for(Flowchart f : flowcharts) {
                    if(f.getIdNumber().equals(id) && f.getSpecialization().equals(course)) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(f.getFilepath()));
                        startActivity(browserIntent);
                    }
                }
            }
        });

        btnCourseOfferings = (ImageButton) view.findViewById(R.id.btn_course_offerings);
        btnCourseOfferings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(courseOffering.getFilepath()));
                startActivity(browserIntent);
            }
        });
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    void hideProgressIndicator(){
        dialog.dismiss();
    }

    private static class Downloads {
        private String idNumbers[];
        private String courses[];

        public String[] getIdNumbers() {
            return idNumbers;
        }

        public void setIdNumbers(String[] idNumbers) {
            this.idNumbers = idNumbers;
        }

        public String[] getCourses() {
            return courses;
        }

        public void setCourses(String[] courses) {
            this.courses = courses;
        }
    }

    class UITask extends AsyncTask<Void, Void, Downloads> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Downloads doInBackground(Void... params) {

            try {
                FilesService service = new FilesService(MainActivity.dbUrl, MainActivity.username, MainActivity.password);
                courseOffering = service.getCourseOffering();
                Downloads downloads = new Downloads();
                flowcharts = service.findAllFlowcharts();

                List<String> idNumberList = new ArrayList<String>();
                for(Flowchart f : flowcharts) {
                    if(!idNumberList.contains(f.getIdNumber())) {
                        idNumberList.add(f.getIdNumber());
                    }
                }
                String idNumbers[] = new String[idNumberList.size()];
                for(int i = 0; i < idNumbers.length; i++) {
                    idNumbers[i] = idNumberList.get(i);
                }
                downloads.setIdNumbers(idNumbers);

                List<String> courseList = new ArrayList<String>();
                for(Flowchart f : flowcharts) {
                    if(!courseList.contains(f.getSpecialization())) {
                        courseList.add(f.getSpecialization());
                    }
                }
                String courses[] = new String[courseList.size()];
                for(int i = 0; i < courses.length; i++) {
                    courses[i] = courseList.get(i);
                }
                downloads.setCourses(courses);

                return downloads;
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Downloads downloads) {
            super.onPostExecute(downloads);
            progressBar.setVisibility(View.GONE);
            downloadsLayout.setVisibility(View.VISIBLE);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item);
            adapter.addAll(downloads.getIdNumbers());
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerID.setAdapter(adapter);

            adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item);
            adapter.addAll(downloads.getCourses());
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerCourse.setAdapter(adapter);
        }
    }
}
