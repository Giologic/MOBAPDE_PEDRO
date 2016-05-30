package com.giologic.pedrosystem10;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.giologic.pedrosystem10.model.MySQLSSHConnector;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private int[] tabIcons = {
            R.mipmap.ic_home_white_24dp,
            R.mipmap.ic_get_app_white_24dp,
            R.mipmap.ic_help_white_24dp
    };

    public static final String dbUrl = "jdbc:mysql://localhost:5656/mobapde";
    public static final String username = "pedromax";
    public static final String password = "marcsanpedro";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/
        new InitSSH().execute();
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
    }

    private void setupTabIcons() {
        for(int i = 0; i < tabLayout.getTabCount(); i++) {
            tabLayout.getTabAt(i).setIcon(tabIcons[i]);
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new HomeFragment(), "Home");
        adapter.addFragment(new DownloadablesFragment(), "Downloads");
        adapter.addFragment(new AskCSGFragment(), "Ask CSG");
        viewPager.setAdapter(adapter);
    }

    class InitSSH extends AsyncTask<Void, Void, Void> {


        //        @Override
//        protected void onPreExecute() {
//            progressBar = (ProgressBar) getActivity().findViewById(R.id.progress_bar);
//            progressBar.setDrawingCacheBackgroundColor(Color.DKGRAY);
//            progressBar.setVisibility(View.VISIBLE);
//            progressBar.setProgress(0);
//        }
        @Override
        protected Void doInBackground(Void... params) {
            try{
                MySQLSSHConnector.getInstance().getConnection();
            }catch (Exception e) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(LoginPage.this);
//                builder.setTitle("Attention!");
//                builder.setMessage("Connection to Server failed.");
//                builder.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        InitSSH.execute();
//
//                    }
//                }

            }
            return null;
        }

//        @Override
//        protected void onProgressUpdate(Integer... progress){
//            System.out.print(progress);
//            if(Integer.parseInt(progress.toString()) != 100) {
//                int progressValue = Integer.parseInt(progress.toString());z
//                progressBar.setProgress(progress[progressValue]);
//            }else progressBar.setVisibility(View.GONE);
//
//
//        }


        @Override
        protected void onCancelled() {
            Toast toast = Toast.makeText(getBaseContext(),
                    "Error connecting to Server via SSH", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP, 25, 400);
            toast.show();

        }

    }

/*    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/
}
