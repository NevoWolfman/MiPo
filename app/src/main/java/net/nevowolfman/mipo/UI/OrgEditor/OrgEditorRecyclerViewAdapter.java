package net.nevowolfman.mipo.UI.OrgEditor;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import net.nevowolfman.mipo.Model.Member;
import net.nevowolfman.mipo.Model.Team;
import net.nevowolfman.mipo.R;

public class OrgEditorRecyclerViewAdapter extends RecyclerView.Adapter<OrgEditorRecyclerViewAdapter.ViewHolder> {

    OrgEditorFragment fragment;
    Team team;
    int member_selected;

    public OrgEditorRecyclerViewAdapter(OrgEditorFragment fragment, Team team) {
        this.fragment = fragment;
        this.team = team;
        member_selected = -1;
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
    }

    @Override
    public int getItemCount() {
        return team.getMembers().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        public final TextView name, id;
        public Member member;
        public LinearLayout rootView;
        public ViewHolder(@NonNull View view) {
            super(view);
            rootView = (LinearLayout)view;
            view.setOnCreateContextMenuListener(this);
            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    member_selected = getBindingAdapterPosition();
                    return false;
                }
            });
            name = view.findViewById(R.id.MemberName);
            id = view.findViewById(R.id.MemberID);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            fragment.requireActivity().getMenuInflater().inflate(R.menu.member_edit, menu);
            if(getMemberSelected().hasUnderlings()) {
                menu.findItem(R.id.editTeam).setTitle("remove team");
            }
            else {
                menu.findItem(R.id.editTeam).setTitle("attach team");
            }
        }
    }

    public void removeMemberSelected()
    {
        team.getMembers().remove(member_selected);
        notifyItemRemoved(member_selected);
    }

    public Member getMember(int position)
    {
        return team.getMembers().get(position);
    }

    public void attachTeamToMemberSelected(Team team)
    {
        getMemberSelected().setUnderlings(team);
    }

    public void removeTeamFromMemberSelected()
    {
        getMemberSelected().setUnderlings(null);
    }

    public Member getMemberSelected()
    {
        return team.getMembers().get(member_selected);
    }
}
