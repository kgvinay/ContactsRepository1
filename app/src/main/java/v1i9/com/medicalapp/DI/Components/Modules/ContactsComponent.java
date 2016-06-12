package v1i9.com.medicalapp.DI.Components.Modules;

import dagger.Component;
import v1i9.com.medicalapp.view.MainActivity;

/**
 * Created by Bmsils on 6/12/2016.
 */

@Component(modules = ContactsModule.class)
public interface ContactsComponent {
    void inject(MainActivity mainActivity);
}
