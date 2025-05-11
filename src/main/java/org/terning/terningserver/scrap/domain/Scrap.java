package org.terning.terningserver.scrap.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.terning.terningserver.common.BaseTimeEntity;
import org.terning.terningserver.user.domain.User;
import org.terning.terningserver.internshipAnnouncement.domain.InternshipAnnouncement;

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
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "internship_announcement_id", nullable = false)
    private InternshipAnnouncement internshipAnnouncement;

    @Enumerated(STRING)
    @Column(nullable = false)
    private Color color;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "synced"))
    private SyncStatus syncStatus;

    private Scrap(User user, InternshipAnnouncement internshipAnnouncement, Color color) {
        this.user = user;
        this.internshipAnnouncement = internshipAnnouncement;
        this.color = color;
        this.syncStatus = SyncStatus.notSynced();
    }

    public static Scrap create(User user, InternshipAnnouncement internshipAnnouncement, Color color) {
        return new Scrap(user, internshipAnnouncement, color);
    }

    public void updateColor(Color color) {
        this.color = color;
    }

    public String getColorToHexValue() {
        return this.color.getColorValue();
    }

    public void markSynced() {
        this.syncStatus = SyncStatus.synced();
    }
}

