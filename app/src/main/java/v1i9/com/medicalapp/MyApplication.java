package v1i9.com.medicalapp;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import v1i9.com.medicalapp.DI.Components.Modules.ContactsComponent;
import v1i9.com.medicalapp.DI.Components.Modules.DaggerContactsComponent;

/**
 * Created by Bmsils on 6/10/2016.
 */
public class MyApplication extends Application {

    private static MyApplication instance;


    @Override
    public void onCreate() {
        super.onCreate();

        RealmConfiguration config = new RealmConfiguration.Builder(getApplicationContext()).
                setModules(new ContactsDatabase()).build();
        Realm.setDefaultConfiguration(config);

    }


    public static MyApplication getInstance() {
        return instance;
    }
}
