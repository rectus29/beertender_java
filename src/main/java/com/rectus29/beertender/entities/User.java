package com.rectus29.beertender.entities;

import com.rectus29.beertender.entities.resource.impl.AvatarResource;
import com.rectus29.beertender.enums.UserAuthentificationType;
import com.rectus29.beertender.tools.StringUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

@SuppressWarnings("serial")
@Entity
@Table(name = "users", indexes = {
		@Index(name = "uniqueId", columnList = "uniqueId", unique = true)}
)
public class User extends BasicGenericEntity<User> implements IDecorableElement {

	@Column(nullable = false)
	private String password = RandomStringUtils.random(64);
	@Column(nullable = false, unique = true, length = 36)
	private String uuid = UUID.randomUUID().toString();
	@Column(nullable = false, unique = true)
	private String email;
	@Column
	private String firstName;
	@Column
	private String lastName;
	@Column
	private String salt = new SecureRandomNumberGenerator().nextBytes(64).toBase64();
	@ManyToOne(cascade = {CascadeType.PERSIST})
	private Role role;
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date restoreSessionDate;
	@Column
	private String restoreSession;
	@OneToOne(cascade = CascadeType.ALL)
	private AvatarResource avatarImage;
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastLogin;
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private UserAuthentificationType userAuthentificationType = UserAuthentificationType.NONE;
	@Column
	@Type(type = "com.rectus29.beertender.hibernate.types.LocaleUserType")
	private Locale userLocale = Locale.FRANCE ;

	public User() {
	}

	@Override
	public String getFormattedName() {
		return ((StringUtils.isNotBlank(firstName)) ? firstName : "") + " " + ((StringUtils.isNotBlank(lastName)) ? lastName.toUpperCase() : "");
	}

	@Override
	public AvatarResource getDecoration() {
		return getAvatarImage();
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public Role getRole() {
		return role;
	}

	public User setRole(Role role) {
		this.role = role;
		return this;
	}

	public boolean hasRole(String roleName) {
		return role.getName().equals(roleName);
	}

	public Date getRestoreSessionDate() {
		return restoreSessionDate;
	}

	public void setRestoreSessionDate(Date restoreSessionDate) {
		this.restoreSessionDate = restoreSessionDate;
	}

	public String getRestoreSession() {
		return restoreSession;
	}

	public void setRestoreSession(String restoreSession) {
		this.restoreSession = restoreSession;
	}

	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	public String getAvatarPath() {
		return null;
	}

	public boolean isAdmin() {
		if (this.role != null) {
			return this.role.getIsAdmin();
		}
		return false;
	}

	public UserAuthentificationType getUserAuthentificationType() {
		return userAuthentificationType;
	}

	public void setUserAuthentificationType(UserAuthentificationType userAuthentificationType) {
		this.userAuthentificationType = userAuthentificationType;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public AvatarResource getAvatarImage() {
		return avatarImage;
	}

	public User setAvatarImage(AvatarResource avatarImage) {
		this.avatarImage = avatarImage;
		return this;
	}

	public Locale getUserLocale() {
		return userLocale;
	}

	public User setUserLocale(Locale userLocale) {
		this.userLocale = userLocale;
		return this;
	}

	@Override
	public int compareTo(User object) {
		return defaultCompareTo(object);
	}
}
