package v1i9.com.medicalapp.presenter;

import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import dagger.Provides;
import io.realm.Realm;
import io.realm.RealmResults;
import v1i9.com.medicalapp.Contacts;
import v1i9.com.medicalapp.ContactsListAdapter;
import v1i9.com.medicalapp.ContactsModel;
import v1i9.com.medicalapp.view.ContactsView;
import v1i9.com.medicalapp.view.MainActivity;

/**
 * Created by Bmsils on 6/11/2016.
 */
public class ContactsPresenterImpl implements ContactsPresenter {

    private ContactsView contactsView;

    Realm realm;

    List<ContactsModel> contactsModelList ;


    public ContactsPresenterImpl(ContactsView contactsView){
        this.contactsView = contactsView;
        realm = Realm.getDefaultInstance();
        contactsModelList = new ArrayList<>();

    }


    @Override
    public void addToContactsList(String name, String email, String phoneNumber) {

        ContactsModel contacts = new ContactsModel(name,email,phoneNumber);
        contactsModelList.add(contacts);
        Contacts contacts1 = new Contacts();
        contacts1.setName(name);
        contacts1.setEmail(email);
        contacts1.setPhoneNumber(phoneNumber);
        realm.beginTransaction();
        Contacts copyOfCountry2 = realm.copyToRealm(contacts1);
        realm.commitTransaction();
        contactsView.updateContactsList(contactsModelList);

    }

    @Override
    public void contactsUpdated(List<ContactsModel> contactsModelList) {
        this.contactsModelList = contactsModelList;
        realm.beginTransaction();
        realm.clear(Contacts.class);
        realm.commitTransaction();

        Log.d("SIZE ","Contacts : "+contactsModelList.size());
        for(int i = 0 ; i < contactsModelList.size(); i++){
            realm.beginTransaction();
            Contacts contacts1 = new Contacts();
            contacts1.setName(contactsModelList.get(i).getContactName());
            contacts1.setEmail(contactsModelList.get(i).getContactEmailAddress());
            contacts1.setPhoneNumber(contactsModelList.get(i).getContactPhoneNumber());
            realm.copyToRealmOrUpdate(contacts1);
            realm.commitTransaction();
        }
    }


    @Override
    public void setUpContactsList() {

        RealmResults<Contacts> results1 =
                realm.where(Contacts.class).findAll();

        if(results1.size() > 0 ) {
            for (Contacts c : results1) {
                Log.d("results1", c.getName());
                ContactsModel contactsModel = new ContactsModel(c.getName(),c.getEmail(),c.getPhoneNumber());
                contactsModelList.add(contactsModel);
            }
            contactsView.updateContactsList(contactsModelList);
        }
    }

    @Override
    public void fetchQuery(String query) {
        query = query.toLowerCase();
        if(contactsModelList.size() > 0 ) {

            if (query.length() > 0) {
                final List<ContactsModel> filteredList = new ArrayList<>();

                for (int i = 0; i < contactsModelList.size(); i++) {

                    final String text = contactsModelList.get(i).getContactName();
                    final String mobileNumber = contactsModelList.get(i).getContactPhoneNumber();
                    final String email = contactsModelList.get(i).getContactEmailAddress();
                    if (text.contains(query) || mobileNumber.contains(query) || email.contains(query)) {

                        filteredList.add(contactsModelList.get(i));
                    }
                }

                 contactsView.updateContactsList(filteredList);

            } else {
                contactsView.updateContactsList(contactsModelList);
            }
        }else{
            //Toast.makeText(activity,"No Results to display",Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public void addContactsClicked() {
        contactsView.displayAddContactsDialog();
    }


}
