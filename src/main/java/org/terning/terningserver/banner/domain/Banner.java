package org.terning.terningserver.banner.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Banner {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String imageUrl;

    private String link;

    private int priority;

}
