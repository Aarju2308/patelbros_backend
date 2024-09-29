package com.patelbros.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
@SuperBuilder
@Table(name = "sub_categories")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SubCategory extends BaseEntity {
	
	private String subCategory;
	
	private boolean active;
	
	@ManyToOne
	@JoinColumn(name = "catId")
	private Category category;
	
	@OneToMany(mappedBy = "subCategory")
	@JsonIgnore
	private List<ThirdCategory> thirdCategories;
}
