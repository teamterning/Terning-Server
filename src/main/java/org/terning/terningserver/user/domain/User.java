package org.terning.terningserver.user.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
import org.terning.terningserver.auth.dto.request.SignUpRequest;
import org.terning.terningserver.auth.jwt.exception.JwtErrorCode;
import org.terning.terningserver.auth.jwt.exception.JwtException;
import org.terning.terningserver.common.BaseTimeEntity;
import org.terning.terningserver.common.exception.CustomException;
import org.terning.terningserver.filter.domain.Filter;
import org.terning.terningserver.scrap.domain.Scrap;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.EnumType.STRING;
import static org.terning.terningserver.common.exception.enums.ErrorMessage.FAILED_REFRESH_TOKEN_RESET;

@Entity
@Table(name = "Users")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="filter_id")
    private Filter filter;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Scrap> scrapList = new ArrayList<>();

    @Column(length = 12)
    private String name;

    @Column(length = 256)
    private String authId;

    @Column(length = 256)
    private String refreshToken;

    @Enumerated(STRING)
    private ProfileImage profileImage;

    @Enumerated(STRING)
    private AuthType authType;

    @Setter
    @Enumerated(STRING)
    private PushNotificationStatus pushStatus;

    @Enumerated(STRING)
    private State state;

    public static User from(String authId, SignUpRequest request) {
        return User.builder()
                .authId(authId)
                .name(request.name())
                .authType(request.authType())
                .profileImage(ProfileImage.fromValue(request.profileImage()))
                .pushStatus(PushNotificationStatus.ENABLED)
                .state(State.ACTIVE)
                .build();
    }

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

    public void updateProfile(String name, ProfileImage profileImage){
        this.name = name;
        this.profileImage = profileImage;
    }

    public void validateRefreshToken(String providedToken) {
        if (this.refreshToken == null || !this.refreshToken.equals(providedToken)) {
            throw new JwtException(JwtErrorCode.INVALID_JWT_TOKEN);
        }
    }
}
