package v1i9.com.medicalapp.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxSearchView;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.Provides;
import io.realm.Realm;
import io.realm.RealmResults;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import v1i9.com.medicalapp.Contacts;
import v1i9.com.medicalapp.ContactsListAdapter;
import v1i9.com.medicalapp.ContactsModel;
import v1i9.com.medicalapp.DI.Components.Modules.ContactsComponent;
import v1i9.com.medicalapp.DI.Components.Modules.ContactsModule;
import v1i9.com.medicalapp.DI.Components.Modules.DaggerContactsComponent;
import v1i9.com.medicalapp.R;
import v1i9.com.medicalapp.presenter.ContactsPresenter;
import v1i9.com.medicalapp.presenter.ContactsPresenterImpl;

public class MainActivity extends AppCompatActivity implements ContactsListAdapter.ContactsInterface,ContactsView {

    @BindView(R.id.fab)
    FloatingActionButton createContactButton;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.contactsList)
    RecyclerView contactsListRecyclerView;

    @BindView(R.id.autoCompleteTextView)
    SearchView search;

    @BindView(R.id.searchImageView)
    ImageView searchImageView;

    Realm realm;

    List<ContactsModel> contactsModelList ;

    ContactsListAdapter bookListAdapter;

    @Inject
    ContactsPresenter contactsPresenter ;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         realm = Realm.getDefaultInstance();
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        DaggerContactsComponent.builder().contactsModule(new ContactsModule(this)).build().inject(this);

        contactsModelList = new ArrayList<>();

        bookListAdapter = new ContactsListAdapter(this,contactsModelList);
        contactsListRecyclerView.setAdapter(bookListAdapter);

        contactsPresenter.setUpContactsList();

        contactsListRecyclerView.setHasFixedSize(true);
        contactsListRecyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).build());

        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false);
        contactsListRecyclerView.setLayoutManager(layoutManager);

        RxSearchView.queryTextChanges(search)

                .throttleLast(100, TimeUnit.MILLISECONDS)
                .debounce(200, TimeUnit.MILLISECONDS)
                .onBackpressureLatest()
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(throwable -> {
                    throwable.printStackTrace();
                    return Observable.empty();
                })
                .doOnError(error -> Log.d("Error","Error : "+error.getMessage()))
                .subscribe(searchQuery -> {
                    Log.d("Query","Query : "+searchQuery);
                    contactsPresenter.fetchQuery(String.valueOf(searchQuery));
                });




        RxView.clicks(createContactButton)
                .subscribe(aVoid -> {
                    contactsPresenter.addContactsClicked();
                });



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void contactsUpdated(List<ContactsModel> contactsModelList) {
        contactsPresenter.contactsUpdated(contactsModelList);
    }



    @Override
    public void displayAddContactsDialog() {

        boolean wrapInScrollView = true;
        new MaterialDialog.Builder(this)
                .title(R.string.contacts_title)
                .customView(R.layout.add_contact_layout, wrapInScrollView)
                .positiveText(R.string.positive)
                .negativeText(R.string.negative)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        String name = ((EditText) dialog.findViewById(R.id.nameEditText)).getText().toString();
                        String email = ((EditText) dialog.findViewById(R.id.emailEditText)).getText().toString();
                        String phoneNumber = ((EditText) dialog.findViewById(R.id.mobileEditText)).getText().toString();
                        contactsPresenter.addToContactsList(name,email,phoneNumber);
                        dialog.dismiss();
                    }
                })
                .show();

    }

    @Override
    public void updateContactsList(List<ContactsModel> listOfContacts) {
        bookListAdapter = new ContactsListAdapter(this, listOfContacts);
        contactsListRecyclerView.setAdapter(bookListAdapter);
        bookListAdapter.notifyDataSetChanged();
    }
}
