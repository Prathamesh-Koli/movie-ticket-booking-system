package com.bookar.entities;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Table(name="movies")
@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Movie {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="movie_id")
	private Long movieId;
	
	@Column(name ="title" , nullable =false)
	private String title;
	
	@Column(name="description" , columnDefinition = "TEXT")
	private String description;
	
	@Column(name = "language" , nullable =false)
    private String language;

	@Enumerated(EnumType.STRING)
	@Column(name = "genre")
	private Genre genre;

	@Column(name = "duration", nullable = false)
	private String duration;

    @Column(name = "release_date", nullable =false)
    private LocalDate releaseDate;

    @Column(name = "poster_url")
    private String posterUrl;
    
    @OneToMany(mappedBy = "movie")
    private List<Show> shows;

    
    @CreationTimestamp
	@Column(name="created_at")
	private LocalDate createdAt;
    
    
	
}
