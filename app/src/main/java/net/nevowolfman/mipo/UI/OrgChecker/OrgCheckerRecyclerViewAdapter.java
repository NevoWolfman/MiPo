package net.nevowolfman.mipo.UI.OrgChecker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import net.nevowolfman.mipo.Model.Member;
import net.nevowolfman.mipo.Model.Team;
import net.nevowolfman.mipo.R;
import net.nevowolfman.mipo.UI.OrgEditor.OrgEditorFragment;

public class OrgCheckerRecyclerViewAdapter extends RecyclerView.Adapter<OrgCheckerRecyclerViewAdapter.ViewHolder> {
    OrgCheckerFragment fragment;
    Team team;

    public OrgCheckerRecyclerViewAdapter(OrgCheckerFragment fragment, Team team) {
        this.fragment = fragment;
        this.team = team;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_member, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.member = team.getMembers().get(position);
        holder.name.setText(holder.member.getName());
        holder.id.setText(String.valueOf(holder.member.getId()));
        holder.cbCame.setChecked(holder.member.isCame());
    }

    @Override
    public int getItemCount() {
        return team.getMembers().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView name, id;
        public final CheckBox cbCame;
        public Member member;
        public LinearLayout rootView;
        public ViewHolder(@NonNull View view) {
            super(view);
            rootView = (LinearLayout) view;
            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if(member.hasUnderlings()) {
                        fragment.pushCurrentTeam();
                        fragment.switchCurrent_team(member.getUnderlings());
                    }
                    return true;
                }
            });
            name = view.findViewById(R.id.MemberName);
            id = view.findViewById(R.id.MemberID);
            cbCame = view.findViewById(R.id.cbCame);

            cbCame.setClickable(true);//we want to use it now, it isn't decoration anymore
            cbCame.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    member.setCame(b);
                }
            });
        }
    }
}
