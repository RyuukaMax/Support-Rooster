package com.mock.fathi.supportrooster.tools;

import com.mock.fathi.supportrooster.R;
import com.mock.fathi.supportrooster.models.Employees;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.ViewHolder> {
    private List<Employees.Engineers> engineers;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View priceView;

        public ViewHolder(View v) {
            super(v);
            priceView = v;
        }
    }

    public EmployeeAdapter(List<Employees.Engineers> engineersList) {
        this.engineers = engineersList;
    }

    @Override
    public EmployeeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater;
        View view;

        layoutInflater = LayoutInflater.from(parent.getContext());
        view = layoutInflater.inflate(R.layout.list_engineer, parent, false);
        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TextView nameView = holder.priceView.findViewById(R.id.eng_name);
        nameView.setText(String.valueOf(engineers.get(position).getName()));
    }

    @Override
    public int getItemCount() {
        return engineers.size();
    }
}
