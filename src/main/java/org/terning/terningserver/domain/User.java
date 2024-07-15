package org.terning.terningserver.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.terning.terningserver.domain.common.BaseTimeEntity;
import org.terning.terningserver.domain.enums.AuthType;
import org.terning.terningserver.domain.enums.State;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "Users")
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id; // 사용자 고유 ID

    @OneToOne(fetch = LAZY)
    @JoinColumn(name="filter_id", nullable = false)
    private Filter filter; // 사용자 필터 설정
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Scrap> scrapList = new ArrayList<>(); // 스크랩 공고

    @Column(length = 12)
    private String name; // 사용자 이름

//    private String email; //이메일

    private int profileImage;

    @Enumerated(STRING)
    private AuthType authType; // 인증 유형 (예: 카카오, 애플)

    @Column(length = 256)
    private String authId; // 인증 서비스에서 제공하는 고유 ID

    @Enumerated(STRING)
    private State state; // 사용자 상태 (예: 활성, 비활성, 정지)

}
