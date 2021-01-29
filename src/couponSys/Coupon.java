package couponSys;

import java.sql.Date;

public class Coupon {
    private int m_id;
    private int m_companyID;
    private Category m_category;
    private String m_title;
    private String m_description;
    private Date m_startDate;
    private Date m_endDate;
    private int m_amount;
    private double m_price;
    private String m_image;

    public Coupon(int i_id, int i_companyID, Category i_category, String i_title, String i_description, Date i_startDate, Date i_endDate, int i_amount, double i_price, String i_image) {
        this.m_id = i_id;
        this.m_companyID = i_companyID;
        this.m_category = i_category;
        this.m_title = i_title;
        this.m_description = i_description;
        if(i_startDate.getYear()>1900) {
            this.m_startDate = new Date(i_startDate.getYear() - 1900, i_startDate.getMonth(), i_startDate.getDate());
            this.m_endDate = new Date(i_endDate.getYear() - 1900, i_endDate.getMonth(), i_endDate.getDate());
        }
        else
        {
            this.m_startDate = i_startDate;
            this.m_endDate = i_endDate;
        }

        this.m_amount = i_amount;
        this.m_price = i_price;
        this.m_image = i_image;
    }


    public int getId() {
        return m_id;
    }

    public void setId(int i_id) {
        this.m_id = i_id;
    }

    public int getCompanyID() {
        return m_companyID;
    }

    public void setCompanyID(int i_companyID) {
        this.m_companyID = i_companyID;
    }

    public Category getCategory() {
        return m_category;
    }

    public void setCategory(Category i_category) {
        this.m_category = i_category;
    }

    public String getDesciption() {
        return m_description;
    }
    public String getTitle() {
        return m_title;
    }

    public void setTitle(String i_title) {
        this.m_title = i_title;
    }
    public void setDesciption(String i_desciption) {
        this.m_description = i_desciption;
    }

    public Date getStartDate() {
        return m_startDate;
    }

    public void setStartDate(Date i_startDate) {
        this.m_startDate = i_startDate;
    }

    public Date getEndDate() {
        return m_endDate;
    }

    public void setEndDate(Date i_endDate) {
        this.m_endDate = i_endDate;
    }

    public int getAmount() {
        return m_amount;
    }

    public void setAmount(int i_amount) {
        this.m_amount = i_amount;
    }

    public double getPrice() {
        return m_price;
    }

    public void setPrice(double i_price) {
        this.m_price = i_price;
    }

    public String getImage() {
        return m_image;
    }

    public void setImage(String i_image) {
        this.m_image = i_image;
    }

    @Override
    public String toString() {
        return "\nCoupon id : " +m_id+
                "\n\tcompanyID=" + m_companyID +
                "\n\tcategory=" + m_category +
                "\n\tdesciption='" + m_description +
                "\n\tstartDate=" + m_startDate+//.getYear()) +"-"+(m_startDate.getMonth())+"-"+m_startDate.getDate() +
                "\n\tendDate=" + m_endDate+//.getYear()) +(m_endDate.getMonth())+"-"+m_endDate.getDate() +
                "\n\tamount=" + m_amount +
                "\n\tprice=" + m_price +
                "\n\timage=  \"" + m_image +"\"\n";
    }
}
