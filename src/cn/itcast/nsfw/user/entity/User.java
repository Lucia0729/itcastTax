package cn.itcast.nsfw.user.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="USER")
public class User  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
    private String dept;	//部门
    private String account;	//账号
    private String name;	//用户名
    private String password;//密码
     
    private String headImg;//头像
    private boolean gender;//性别
    private String state;//状态
    private String mobile;//手机号
    private String email;//邮箱
    private Date birthday;//生日
    private String memo;//备注
    
    public static String USER_STATE_VALID = "1";//有效
    public static String USER_STATE_INVALID = "0";//无效
   
    private List<UserRole> userRoles;
    public User() {
	}
	public User(String id, String dept, String account, String name, String password, String headImg, boolean gender,
			String state, String mobile, String email, Date birthday, String memo) {
		this.id = id;
		this.dept = dept;
		this.account = account;
		this.name = name;
		this.password = password;
		this.headImg = headImg;
		this.gender = gender;
		this.state = state;
		this.mobile = mobile;
		this.email = email;
		this.birthday = birthday;
		this.memo = memo;
	}
	@Id
    @GenericGenerator(name="userid" , strategy="uuid")
    @GeneratedValue(generator="userid")
    @Column(name="id",length=32)
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@Column(name="dept",length=20,nullable=true)
	public String getDept() {
		return dept;
	}
	public void setDept(String dept) {
		this.dept = dept;
	}
	@Column(name="account",length=50,nullable=true)
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	@Column(name="name",length=20,nullable=true)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Column(name="password",length=50,nullable=true)
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Column(name="headImg",length=100)
	public String getHeadImg() {
		return headImg;
	}
	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}
	@Column(name="gender")
	public boolean isGender() {
		return gender;
	}
	public void setGender(boolean gender) {
		this.gender = gender;
	}
	@Column(name="state",length=1)
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	@Column(name="mobile",length=20)
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	@Column(name="email",length=50)
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Column(name="birthday",length=10)
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	@Column(name="memo",length=200)
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	@Transient
	public List<UserRole> getUserRoles() {
		return userRoles;
	}
	public void setUserRoles(List<UserRole> userRoles) {
		this.userRoles = userRoles;
	}
	
	
	
}
