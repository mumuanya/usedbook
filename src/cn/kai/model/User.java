package cn.kai.model;

import java.util.Date;

public class User {
    private Integer id;

    private String neckname;

    private String mail;

    private String pic;

    private String password;

    private String token;

    private String phone;

    private String addr;

    private String district;

    private Date creatime;

    private Integer role;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNeckname() {
        return neckname;
    }

    public void setNeckname(String neckname) {
        this.neckname = neckname == null ? null : neckname.trim();
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail == null ? null : mail.trim();
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic == null ? null : pic.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token == null ? null : token.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr == null ? null : addr.trim();
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district == null ? null : district.trim();
    }

    public Date getCreatime() {
        return creatime;
    }

    public void setCreatime(Date creatime) {
        this.creatime = creatime;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

	@Override
	public String toString() {
		return "User [id=" + id + ", neckname=" + neckname + ", mail=" + mail + ", pic=" + pic + ", password="
				+ password + ", token=" + token + ", phone=" + phone + ", addr=" + addr + ", district=" + district
				+ ", creatime=" + creatime + ", role=" + role + "]";
	}
}