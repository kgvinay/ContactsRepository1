package v1i9.com.medicalapp;

/**
 * Created by Bmsils on 6/10/2016.
 */
public class ContactsModel {

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactEmailAddress() {
        return contactEmailAddress;
    }

    public void setContactEmailAddress(String contactEmailAddress) {
        this.contactEmailAddress = contactEmailAddress;
    }

    public String getContactPhoneNumber() {
        return contactPhoneNumber;
    }

    public void setContactPhoneNumber(String contactPhoneNumber) {
        this.contactPhoneNumber = contactPhoneNumber;
    }

    private String contactName;
    private String contactEmailAddress;
    private String contactPhoneNumber ;

    public ContactsModel(String contactName, String contactEmailAddress, String contactPhoneNumber) {
        this.contactPhoneNumber = contactPhoneNumber;
        this.contactEmailAddress = contactEmailAddress;
        this.contactName = contactName;
    }




}
