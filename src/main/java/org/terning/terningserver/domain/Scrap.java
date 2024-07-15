package org.terning.terningserver.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.terning.terningserver.domain.common.BaseTimeEntity;
import org.terning.terningserver.domain.enums.Color;

import java.awt.*;

import static jakarta.persistence.EnumType.STRING;
import static lombok.AccessLevel.PROTECTED;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Scrap extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id; // 스크랩 고유 ID

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // 스크랩한 사용자

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "internship_announcement_id", nullable = false)
    private InternshipAnnouncement internshipAnnouncement; // 스크랩한 인턴십 공고

    @Enumerated(STRING)
    @Column(nullable = false)
    private Color color; // 스크랩 색상 (사용자가 지정)

    @Builder
    private Scrap(User user, InternshipAnnouncement internshipAnnouncement, Color color) {
        this.user = user;
        this.internshipAnnouncement = internshipAnnouncement;
        this.color = color;
    }

    public static Scrap create(User user, InternshipAnnouncement internshipAnnouncement, Color color) {
        return Scrap.builder()
                .user(user)
                .internshipAnnouncement(internshipAnnouncement)
                .color(color)
                .build();
    }
    
}
