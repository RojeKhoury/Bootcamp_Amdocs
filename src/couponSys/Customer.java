package couponSys;

import java.util.ArrayList;

public class Customer {
    private int m_id;
    private String m_firstName;
    private String m_lastName;
    private String m_email;
    private String m_password;
    private ArrayList<Coupon> m_coupons;

    public int getId() {
        return m_id;
    }

    public void setId(int i_id) {
        this.m_id = i_id;
    }

    public String getFirstName() {
        return m_firstName;
    }

    public void setFirstName(String i_firstName) {
        this.m_firstName = i_firstName;
    }

    public String getLastName() {
        return m_lastName;
    }

    public void setLastName(String i_lastName) {
        this.m_lastName = i_lastName;
    }

    public String getEmail() {
        return m_email;
    }

    public void setEmail(String i_email) {
        this.m_email = i_email;
    }

    public String getPassword() {
        return m_password;
    }

    public void setPassword(String i_password) {
        this.m_password = i_password;
    }

    public ArrayList<Coupon> getCoupons() {
        return m_coupons;
    }

    public void setCoupons(ArrayList<Coupon> i_coupons) {
        this.m_coupons = i_coupons;
    }

    public Customer(int i_id, String i_firstName, String i_lastName, String i_email, String i_password, ArrayList<Coupon> i_coupons) {
        this.m_id = i_id;
        this.m_firstName = i_firstName;
        this.m_lastName = i_lastName;
        this.m_email = i_email;
        this.m_password = i_password;
        this.m_coupons = i_coupons;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + m_id +
                ", firstName='" + m_firstName + '\'' +
                ", lastName='" + m_lastName + '\'' +
                ", email='" + m_email + '\'' +
                ", password='" + m_password + '\'' +
                ", coupons=" + m_coupons +
                '}';
    }
}
