package com.rectus29.beertender.entities;

/**
 * User: rectus_29
 * Date: 27 mai 2009
 */

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SuppressWarnings("serial")
@Entity
@Table(name = "role", indexes = {
		@Index(name = "uniqueId", columnList = "uniqueId", unique = true)}
)
public class Role extends BasicGenericEntity<Role> {

	@Column
	private String name;

	@Column
	private String description;

	@Column
	private int weight = 100;

	@ManyToMany(cascade = { CascadeType.PERSIST })
	private Set<Permission> permissions;

	@OneToMany(mappedBy = "role")
	private Set<User> users = new HashSet<User>();

	@Column
	private Boolean isAdmin = false;

	public Role() {

		this.permissions = new HashSet<Permission>();
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(Set<Permission> permissions) {
		this.permissions = permissions;
	}

	public List<Permission> getContainerPermissions() {
		List<Permission> out = new ArrayList<Permission>();
		for (Permission temp : this.permissions) {
			if (temp.getCodeString().startsWith("container:"))
				out.add(temp);
		}
		return out;
	}

	public List<Permission> getActivityPermissions() {
		List<Permission> out = new ArrayList<Permission>();
		for (Permission temp : this.permissions) {
			if (temp.getCodeString().startsWith("activity:"))
				out.add(temp);
		}
		return out;
	}

	public void addPermission(Permission perm) {
		if (!this.permissions.contains(perm))
			this.permissions.add(perm);
	}

	public void removePermission(Permission perm) {
		this.permissions.remove(perm);
	}

	public Boolean getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	@Override
	public int compareTo(Role object) {
		return defaultCompareTo(object);
	}
}
