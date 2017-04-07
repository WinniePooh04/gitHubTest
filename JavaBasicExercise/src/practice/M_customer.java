package practice;

import java.util.Date;

public class M_customer {
    private Integer customer_id;
    private String customer_cd;
    private String customer_name;
    private String address;
    private String tel;
    private Date create_dt;
    private Integer sum_qty;
    private String staff_cd;
    private String part_cd;
    
    public String getStaff_cd() {
		return staff_cd;
	}

	public void setStaff_cd(String staff_cd) {
		this.staff_cd = staff_cd;
	}

	public String getPart_cd() {
		return part_cd;
	}

	public void setPart_cd(String part_cd) {
		this.part_cd = part_cd;
	}

	public Integer getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(Integer customer_id) {
		this.customer_id = customer_id;
	}

	public Integer getSum_qty() {
		return sum_qty;
	}

	public void setSum_qty(Integer sum_qty) {
		this.sum_qty = sum_qty;
	}

	public void setCustomerId(Integer customer_id){
    	this.customer_id = customer_id;
    }

    public Integer getCustomerId(){
    	return customer_id;
    }
	public String getCustomer_cd() {
		return customer_cd;
	}

	public void setCustomer_cd(String customer_cd) {
		this.customer_cd = customer_cd;
	}

	public String getCustomer_name() {
		return customer_name;
	}

	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public Date getCreate_dt() {
		return create_dt;
	}

	public void setCreate_dt(Date create_dt) {
		this.create_dt = create_dt;
	}
	
	public String getCusInfo() {
		return customer_name + '\n' + address;
	}
}