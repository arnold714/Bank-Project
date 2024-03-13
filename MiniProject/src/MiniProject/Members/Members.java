package MiniProject.Members;

import java.sql.Date;

//vo
public class Members { //id, pwd, name, phone, auth, signdate
	private String id;
	private String pwd;
	private String name;
	private String phone;
	private Boolean auth;
	private Date signdate;
	

	public Members() {
	}

	public Members(String id, String pwd, String name, String phone,Boolean auth, Date signdate) {
		super();
		this.id = id;
		this.pwd = pwd;
		this.name = name;
		this.phone = phone;
		this.auth = auth;
		this.signdate = signdate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
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

	public Date getSigndate() {
		return signdate;
	}

	public void setSigndate(Date signdate) {
		this.signdate = signdate;
	}

	public Boolean getAuth() {
		return auth;
	}

	public void setAuth(Boolean auth) {
		this.auth = auth;
	}

	@Override
	public String toString() {
		return "Members [id=" + id + ", pwd=" + pwd + ", name=" + name + ", phone=" + phone + ", auth=" + auth
				+ ", signdate=" + signdate + "]";
	}

	


}