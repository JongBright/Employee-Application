public class Employee {

    private String fullName;
    private String email;
    private String phone;
    private String creationInfo;

    public Employee(){
        this.fullName = "";
        this.email = "";
        this.phone = "";
        this.creationInfo = "";
    }

    public Employee(String name, String eml, String tel, String ci){
        this.fullName = name;
        this.email = eml;
        this.phone = tel;
        this.creationInfo = ci;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCreationInfo() {
        return creationInfo;
    }

    public void setCreationInfo(String creationInfo) {
        this.creationInfo = creationInfo;
    }
}
