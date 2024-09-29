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
@Table(name = "third_categories")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ThirdCategory extends BaseEntity {
	
	private String thirdCategory;
	
	private boolean active;
	
	@ManyToOne
	@JoinColumn(name = "subCatID")
	private SubCategory subCategory;
	
	@OneToMany(mappedBy = "thirdCategory")
	@JsonIgnore
	private List<Product> products;
}
