package org.terning.terningserver.user.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.terning.terningserver.common.BaseTimeEntity;
import org.terning.terningserver.common.exception.CustomException;
import org.terning.terningserver.filter.domain.Filter;
import org.terning.terningserver.scrap.domain.Scrap;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static org.terning.terningserver.common.exception.enums.ErrorMessage.FAILED_REFRESH_TOKEN_RESET;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "Users")
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "filter_id")
    private Filter filter;

    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Scrap> scrapList = new ArrayList<>();

    @Column(length = 12)
    private String name;

    @Enumerated(STRING)
    private ProfileImage profileImage;

    @Enumerated(STRING)
    private AuthType authType;

    @Setter
    @Enumerated(EnumType.STRING)
    private PushNotificationStatus pushStatus;

    @Column(length = 256)
    private String authId;

    @Column(length = 256)
    private String refreshToken;

    @Enumerated(STRING)
    private State state;

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void resetRefreshToken() {
        try {
            this.refreshToken = null;
        } catch (Exception e) {
            throw new CustomException(FAILED_REFRESH_TOKEN_RESET);
        }
    }

    public void assignFilter(Filter filter) {
        this.filter = filter;
    }

    public void updateProfile(String name, ProfileImage profileImage) {
        this.name = name;
        this.profileImage = profileImage;
    }
}
