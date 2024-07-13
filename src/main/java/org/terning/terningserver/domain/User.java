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

    @Column(nullable = false, length = 12)
    private String name; // 사용자 이름

    @Column(nullable = false)
    private int profileImage; //유저 아이콘

    @Enumerated(STRING)
    @Column(nullable = false)
    private AuthType authType; // 인증 유형 (예: 카카오, 애플)

    @Column(nullable = false, length = 256)
    private String authId; // 인증 서비스에서 제공하는 고유 ID

    @Enumerated(STRING)
    @Column(nullable = false)
    private State state; // 사용자 상태 (예: 활성, 비활성, 정지)

}
