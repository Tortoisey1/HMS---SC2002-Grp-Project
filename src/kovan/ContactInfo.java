public class ContactInfo {
    private String email;
    private String phoneNumber;

    ContactInfo(String e, String p){
        this.email = e;
        this.phoneNumber = p;
    }


    public String getEmail() {
        return email;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
