public class Employee {

    private String fullName;
    private String email;
    private int phone;

    public Employee(){
        this.fullName = "";
        this.email = "";
        this.phone = 0;
    }

    public Employee(String name, String eml, int tel){
        this.fullName = name;
        this.email = eml;
        this.phone = tel;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }
}
