package com.giologic.pedrosystem10;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.hardware.camera2.TotalCaptureResult;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.giologic.pedrosystem10.model.Question;
import com.giologic.pedrosystem10.service.QuestionService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AskCSGFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AskCSGFragment extends Fragment {
    private OnFragmentInteractionListener mListener;

//    private TabLayout tabLayout;

    //List of Questions
    private ArrayList<Question> questionList = new ArrayList<Question>();
    private RecyclerView recyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private AnswersAdapter adapter;
    private ProgressBar progressBar;
    private ProgressDialog progress;

   //Question Upper Part
    private TextView txtOpenFragment;

    public AskCSGFragment() {
        // Required empty public constructor
    }

    public static AskCSGFragment newInstance() {
        AskCSGFragment fragment = new AskCSGFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


//        tabLayout = (TabLayout) getActivity().findViewById(R.id.tabsAskCSG);
        txtOpenFragment = (TextView) getActivity().findViewById(R.id.tv_question);
        txtOpenFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment d = new QuestionDialog();
                d.show(getFragmentManager(), "");
                d.setTargetFragment(AskCSGFragment.this, 0);
            }
        });

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_answers);
        //progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        mSwipeRefreshLayout.setRefreshing(true);
        adapter = new AnswersAdapter();
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshContent();
            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar_askcsg);
        progressBar.setVisibility(View.VISIBLE);

        new QuestionSSH().execute();
    }

    public void addQuestion(String question) {
        new AddQuestionSSH().execute(question);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ask_csg, container, false);
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

    public void refreshContent() {
        new Handler().post(new Runnable() {

            @Override
            public void run() {
//                recyclerView.setAdapter(null);
//                recyclerView.setAdapter(adapter);

                new QuestionSSH().execute();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    class AddQuestionSSH extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress = ProgressDialog.show(getContext(), "",
                    "Submitting question...", true);
        }

        @Override
        protected Void doInBackground(String... params) {
            String value = params[0];
            try {
                System.out.println("Question Added: " + value);
                QuestionService questionService = new QuestionService(MainActivity.dbUrl, MainActivity.username, MainActivity.password);
                questionService.addQuestion(new Question(value, Calendar.getInstance().getTime()));
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            progressBar.setVisibility(View.GONE);
            progress.dismiss();
            Toast.makeText(getContext(), "Question successfully submitted!", Toast.LENGTH_LONG).show();
            refreshContent();
        }
    }

    class QuestionSSH extends AsyncTask<Void, Void, Collection<Question>> {

        @Override
        protected Collection<Question> doInBackground(Void... params) {
            try{
                QuestionService qService = new QuestionService(MainActivity.dbUrl, MainActivity.username, MainActivity.password);
                return qService.findAll();
            } catch (Exception e){

            }
            return null;
        }

        @Override
        protected void onPreExecute() {
//
            mSwipeRefreshLayout.setRefreshing(true);
        }

        @Override
        protected void onPostExecute(Collection<Question> questions) {


            mSwipeRefreshLayout.setRefreshing(false);
            progressBar.setVisibility(View.GONE);
            adapter.removeAll();
            for(Question q : questions) {
                adapter.add(q);
            }

        }
    }




}
