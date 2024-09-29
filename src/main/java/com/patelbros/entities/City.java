package com.patelbros.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="cities")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class City{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String pinCode;

    @Column(nullable = false)
    private Double estimatedShippingCost;

    @ManyToOne
    @JoinColumn(name = "state_id", nullable = false)
    private State state;
    
    @JsonIgnore
    @OneToMany(mappedBy = "city")
    private List<Address> addresses;
    
    private boolean active;
    
    
}
