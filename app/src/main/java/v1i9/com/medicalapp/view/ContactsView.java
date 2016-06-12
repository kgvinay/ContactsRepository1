package v1i9.com.medicalapp.view;

import java.util.List;

import v1i9.com.medicalapp.ContactsModel;

/**
 * Created by Bmsils on 6/11/2016.
 */
public interface ContactsView {




    public void displayAddContactsDialog();

    public void updateContactsList(List<ContactsModel> contactsList);

}
