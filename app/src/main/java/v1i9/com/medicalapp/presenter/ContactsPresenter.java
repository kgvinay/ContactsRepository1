package v1i9.com.medicalapp.presenter;

import java.util.List;

import v1i9.com.medicalapp.ContactsModel;

/**
 * Created by Bmsils on 6/11/2016.
 */
public interface ContactsPresenter {

    public void addToContactsList(String name, String email, String phoneNumber);

    public void contactsUpdated(List<ContactsModel> contactsModelList);

    public void setUpContactsList();

    public void fetchQuery(String queryString);

    public void addContactsClicked();


}
