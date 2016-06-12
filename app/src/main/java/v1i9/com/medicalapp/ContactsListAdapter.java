package v1i9.com.medicalapp;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Bmsils on 6/10/2016.
 */
public class ContactsListAdapter extends RecyclerView.Adapter<ContactsListAdapter.ContactsListViewHolder> {

    private List<ContactsModel> contactsModelList  = new ArrayList<>();
    Activity mContext;

    public interface ContactsInterface{
         void contactsUpdated(List<ContactsModel> contactsModelList);
    }

    private ContactsInterface contactsInterface;

    public ContactsListAdapter(Activity activity,List<ContactsModel> contactsModelList){
        mContext = activity;
        this.contactsModelList = contactsModelList;

        contactsInterface = (ContactsInterface) activity;
    }


    @Override
    public ContactsListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.contacts_list_item, parent, false);
        ContactsListViewHolder bookListViewHolder = new ContactsListViewHolder(v);
        return bookListViewHolder;
    }

    @Override
    public void onBindViewHolder(final ContactsListViewHolder holder, final int position) {

        try {
            String name = contactsModelList.get(position).getContactName();
            holder.nameTextView.setText(name);
        }catch (Exception e){
            holder.nameTextView.setVisibility(View.GONE);
        }

        try {
            String email = contactsModelList.get(position).getContactEmailAddress();
            holder.emailTextView.setText(email);
        }catch (Exception e){
            holder.emailTextView.setVisibility(View.GONE);
        }

        try {
            String phoneNumber = contactsModelList.get(position).getContactPhoneNumber();
            holder.phoneTextView.setText(phoneNumber);
        }catch (Exception e){
            holder.phoneTextView.setVisibility(View.GONE);
        }

        holder.contactsLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                holder.clearCard.setVisibility(View.VISIBLE);
                notifyDataSetChanged();
            }
        });

        holder.clearCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contactsModelList.remove(position);
                contactsInterface.contactsUpdated(contactsModelList);
                notifyDataSetChanged();
                holder.clearCard.setVisibility(View.GONE);
            }
        });


    }


    @Override
    public int getItemCount() {
        return contactsModelList.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class ContactsListViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.nameTextView)
        TextView nameTextView;

        @BindView(R.id.emailTextView)
        TextView emailTextView;

        @BindView(R.id.phoneTextView)
        TextView phoneTextView;

        @BindView(R.id.clearCard)
        ImageView clearCard;

        @BindView(R.id.contactsLL)
        LinearLayout contactsLL;

    public ContactsListViewHolder(View itemView) {
        super(itemView);

        ButterKnife.bind(this, itemView);
    }
}
}
