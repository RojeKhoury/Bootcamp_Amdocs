package couponSys;

import java.util.ArrayList;

public class Company {
    private int m_id;
    private String m_name;
    private String m_email;
    private String m_password;
    private ArrayList<Coupon> m_coupons;

    public Company(int i_id, String i_name, String i_email, String i_password, ArrayList<Coupon> i_coupons) {
        this.m_id = i_id;
        this.m_name = i_name;
        this.m_email = i_email;
        this.m_password = i_password;
        this.m_coupons = i_coupons;
    }

    public int getId() {
        return m_id;
    }

    public void setId(int i_id) {
        this.m_id = i_id;
    }

    public String getName() {
        return m_name;
    }

    public void setName(String i_name) {
        this.m_name = i_name;
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

    @Override
    public String toString() {
        return "Company{" +
                "id=" + m_id +
                ", name='" + m_name + '\'' +
                ", email='" + m_email + '\'' +
                ", password='" + m_password + '\'' +
                ", coupons=" + m_coupons +
                '}';
    }
}
