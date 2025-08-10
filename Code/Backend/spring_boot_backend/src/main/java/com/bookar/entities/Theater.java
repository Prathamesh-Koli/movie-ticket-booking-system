package com.bookar.entities;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Table(name="theaters")
@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Theater {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="theater_id")
	private Long theaterId;
	
	@Column(name="theater_name", nullable =false)
	private String theaterName;
	
	@Column(name="theater_location", nullable =false)
	private String theaterLocation;
	
	@Column(name="theater_address")
	private String theaterAddress;
	
	@OneToMany(mappedBy = "theater")
	private List<Screen> screens;
	
	@Enumerated(EnumType.STRING)
	@Column(name="status", length=20)
	private TheatreStatus status;
	
	@CreationTimestamp
	@Column(name="created_at")
	private LocalDate createdAt;
	
	@ManyToOne
	@JoinColumn(name = "owner_id")
	private User owner;

}