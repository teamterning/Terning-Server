package org.terning.terningserver.domain;

import jakarta.persistence.*;
import lombok.*;
import org.terning.terningserver.domain.common.BaseTimeEntity;
import org.terning.terningserver.domain.enums.AuthType;
import org.terning.terningserver.domain.enums.State;
import org.terning.terningserver.exception.CustomException;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static org.terning.terningserver.exception.enums.ErrorMessage.*;

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
    
    private Integer profileImage; //유저 아이콘

    @Enumerated(STRING)
    private AuthType authType; // 인증 유형 (예: 카카오, 애플)

    @Column(length = 256)
    private String authId; // 인증 서비스에서 제공하는 고유 ID

    @Column(length = 256)
    private String authAccessToken; // 엑세스 토큰

    @Column(length = 256)
    private String refreshToken; // 리프레시 토큰

    // TODO: User가 생기면 active default로 바꾸기
    @Enumerated(STRING)
    private State state; // 사용자 상태 (예: 활성, 비활성, 정지)


    public void updateRefreshToken(String authAccessToken, String refreshToken) {
        this.refreshToken = refreshToken;
        this.authAccessToken = authAccessToken;
    }

    public void resetRefreshToken() {
        try {
            this.refreshToken = null;
        } catch (Exception e) {
            throw new CustomException(FAILED_REFRESH_TOKEN_RESET);
        }
    }

    public void updateProfile(String name, Integer profileImage) {
        if (name != null && !name.isEmpty()) {
            this.name = name;
        }
        if (profileImage != null) {
            this.profileImage = profileImage;
        }
    }

    public void assignFilter(Filter filter) {
        this.filter = filter;
    }

    public void updateUser(AuthType authType, String authId, User user) {
        this.authType = authType;
        this.authId = authId;
        this.authAccessToken = user.getAuthAccessToken();
        this.refreshToken = user.getRefreshToken();
    }
}
