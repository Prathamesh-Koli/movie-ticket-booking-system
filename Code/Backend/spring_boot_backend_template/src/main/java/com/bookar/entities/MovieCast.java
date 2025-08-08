package com.bookar.entities;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class MovieCast {
    private String name;
    private String photoUrl;
}
