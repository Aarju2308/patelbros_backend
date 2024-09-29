package com.patelbros.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "addresses")
@SuperBuilder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Address extends BaseEntity {
	
	private String firstName;
	
	private String lastName;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "userId")
	private User user;
	
	@Column(length = 600)
	private String address;
	
	@ManyToOne
	@JoinColumn(name = "cityId")
	private City city;
	
	@JsonIgnore
	@OneToMany(mappedBy = "address")
	private List<Order> orders;
	
}	
