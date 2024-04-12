package com.example.scoutsapp.UI.Main;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.scoutsapp.Model.Organization;
import com.example.scoutsapp.databinding.FragmentOrgBinding;

import java.util.List;


public class OrgRecyclerViewAdapter extends RecyclerView.Adapter<OrgRecyclerViewAdapter.ViewHolder> {

    private final List<Organization> orgs;

    public OrgRecyclerViewAdapter(List<Organization> items) {
        orgs = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentOrgBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.org = orgs.get(position);
        holder.tvOrgName.setText(orgs.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return orgs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView tvOrgName;

        Organization org;

        public ViewHolder(FragmentOrgBinding binding) {
            super(binding.getRoot());
            tvOrgName = binding.tvOrgName;
        }
    }
}