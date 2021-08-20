package bloodlink0608app.com;

public class ItemsModel {
    //public String needtime,bloodgroup,postcreatername,needhospital;


    public String name,phone;

    public ItemsModel() {
    }

    public ItemsModel(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}


