package com.coworking.model;

import com.coworking.enums.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users", uniqueConstraints = { @UniqueConstraint(name = "uk_user_email", columnNames = "email") })
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class User extends BaseEntity {

	@Column(nullable = false, length = 100)
	private String firstName;

	@Column(nullable = false, length = 100)
	private String lastName;

	@Column(nullable = false, length = 150)
	private String email;

	@Column(name = "password_hash", nullable = false, length = 255)
	private String passwordHash;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	private Role role;

	@Builder.Default
	@Column(nullable = false)
	private Boolean enabled = true;
	
	public String getFullName() {
	    return firstName + " " + lastName;
	}

}
