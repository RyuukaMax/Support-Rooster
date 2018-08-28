package com.mock.fathi.supportrooster.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.mock.fathi.supportrooster.R;
import com.mock.fathi.supportrooster.models.Employees;
import com.mock.fathi.supportrooster.tools.EmployeeAdapter;
import com.mock.fathi.supportrooster.tools.RetrofitCallbacks;
import com.mock.fathi.supportrooster.tools.RetrofitWrapper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ListEngineer extends AppCompatActivity {
    private List<Employees.Engineers> engineersList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private EmployeeAdapter empAdapter;
    private View listView;
    private View progressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_engineer);

        listView = findViewById(R.id.list_view);
        progressView = findViewById(R.id.progress_view);
        
        mRecyclerView = findViewById(R.id.list_recycler_view);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        empAdapter = new EmployeeAdapter(engineersList);
        mRecyclerView.setAdapter(empAdapter);

        final SwipeRefreshLayout refreshPull = findViewById(R.id.list_refresh_swipe);
        refreshPull.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchEngineers();
                refreshPull.setRefreshing(false);
            }
        });

        fetchEngineers();

        Button generateScheduleBtn = findViewById(R.id.generate_schedule_btn);
        generateScheduleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListEngineer.this, Schedule.class);
                intent.putExtra("engineers", (Serializable) engineersList);
                startActivity(intent);
            }
        });
    }

    private void fetchEngineers() {
        showProgress(true);
        RetrofitCallbacks callbacks = new RetrofitCallbacks();
        RetrofitWrapper resp = new RetrofitWrapper() {
            @Override
            public void success(Object obj) {
                Employees emp = (Employees) obj;
                engineersList.clear();
                engineersList = emp.getEngineersList();

                empAdapter = new EmployeeAdapter(engineersList);
                mRecyclerView.setAdapter(empAdapter);

                showProgress(false);
            }

            @Override
            public void failure(String message) {
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                showProgress(false);
            }
        };
        callbacks.getEngineersList(resp);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= 16) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            listView.setVisibility(show ? View.GONE : View.VISIBLE);
            listView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    listView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            progressView.setVisibility(show ? View.VISIBLE : View.GONE);
            progressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    progressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            progressView.setVisibility(show ? View.VISIBLE : View.GONE);
            listView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }
}