package org.terning.terningserver.user.domain;

import jakarta.persistence.*;
import lombok.*;
import org.terning.terningserver.common.BaseTimeEntity;
import org.terning.terningserver.common.exception.CustomException;

import java.util.ArrayList;
import java.util.List;
import org.terning.terningserver.filter.domain.Filter;
import org.terning.terningserver.scrap.domain.Scrap;

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
    private Long id; // 사용자 고유 ID

    @OneToOne(fetch = LAZY)
    @JoinColumn(name="filter_id")
    private Filter filter; // 사용자 필터 설정
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Scrap> scrapList = new ArrayList<>(); // 스크랩 공고

    // TODO: 특수문자, 첫글자 , 12자리 이내
    @Column(length = 12)
    private String name; // 사용자 이름

    @Enumerated(STRING)
    private ProfileImage profileImage; //유저 아이콘

    @Enumerated(STRING)
    private AuthType authType; // 인증 유형 (예: 카카오, 애플)

    @Setter
    @Enumerated(EnumType.STRING)
    private PushNotificationStatus pushStatus;

    @Column(length = 256)
    private String authId; // 인증 서비스에서 제공하는 고유 ID

    @Column(length = 256)
    private String refreshToken; // 리프레시 토큰

    // TODO: User가 생기면 active default로 바꾸기
    @Enumerated(STRING)
    private State state; // 사용자 상태 (예: 활성, 비활성, 정지)

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

    //프로필 수정 메서드
    public void updateProfile(String name, ProfileImage profileImage){
        this.name = name;
        this.profileImage = profileImage;
    }
}
