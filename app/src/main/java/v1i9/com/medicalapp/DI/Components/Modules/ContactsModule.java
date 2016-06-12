package v1i9.com.medicalapp.DI.Components.Modules;

import dagger.Module;
import dagger.Provides;
import v1i9.com.medicalapp.presenter.ContactsPresenter;
import v1i9.com.medicalapp.presenter.ContactsPresenterImpl;
import v1i9.com.medicalapp.view.ContactsView;

/**
 * Created by Bmsils on 6/12/2016.
 */
@Module
public class ContactsModule {


    private ContactsView contactsView;

    public ContactsModule(ContactsView contactsView){
        this.contactsView = contactsView;
    }

    @Provides ContactsView provideContactsView(){
        return contactsView;
    }

    @Provides
    ContactsPresenter provideRegisterPresenter(ContactsView contactsView){
        return new ContactsPresenterImpl(contactsView);
    }
}
