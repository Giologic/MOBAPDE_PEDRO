package com.giologic.pedrosystem10;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.giologic.pedrosystem10.model.AcademicAnnouncement;
import com.giologic.pedrosystem10.model.MySQLSSHConnector;
import com.giologic.pedrosystem10.model.Organization;
import com.giologic.pedrosystem10.model.Post;
import com.giologic.pedrosystem10.service.AAService;
import com.giologic.pedrosystem10.service.OrgService;
import com.giologic.pedrosystem10.service.PostService;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;

import java.io.InputStream;
import java.net.URL;
import java.security.SecureRandom;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    private ProgressBar progressBar;

    private RecyclerView recyclerView;
    private HomeHeadersAdapter adapter;
    private OnFragmentInteractionListener mListener;
    protected SwipeRefreshLayout mSwipeRefreshLayout;



    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_home);
        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        adapter = new HomeHeadersAdapter();

        new PostSSH().execute();

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        StickyRecyclerHeadersDecoration headersDecor = new StickyRecyclerHeadersDecoration(adapter);
        recyclerView.addItemDecoration(headersDecor);
        mSwipeRefreshLayout.setRefreshing(true);
        progressBar.setVisibility(View.VISIBLE);



        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshContent();
            }
        });

    }

    private void refreshContent() {
        new Handler().post(new Runnable() {

            @Override
            public void run() {
//                recyclerView.setAdapter(null);
//                recyclerView.setAdapter(adapter);

                new PostSSH().execute();
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


    class PostSSH extends AsyncTask<Void, Void, Collection<Post>> {

        @Override
        protected Collection<Post> doInBackground(Void... params) {
            try{
                PostService pService = new PostService(MainActivity.dbUrl, MainActivity.username, MainActivity.password);

                Collection<Post> posts = pService.findAll();

                for(Post p : posts) {
                    URL url = new URL(p.getImg());
                    InputStream is = (InputStream) url.getContent();
                    Drawable d = Drawable.createFromStream(is, null);
                    p.setDrawablePub(d);

                    url = new URL(p.getOrg().getLogo());
                    is = (InputStream) url.getContent();
                    d = Drawable.createFromStream(is, null);
                    p.setDrawableLogo(d);
                }

                return posts;
            } catch (Exception e){

            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mSwipeRefreshLayout.setRefreshing(true);

        }

        @Override
        protected void onPostExecute(Collection<Post> posts){
            mSwipeRefreshLayout.setRefreshing(false);
            progressBar.setVisibility(View.GONE);
            adapter.removeAll();
            for(Post p : posts) {
                adapter.add(p);
            }
        }
    }
}
