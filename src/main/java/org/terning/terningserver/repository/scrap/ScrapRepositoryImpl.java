package org.terning.terningserver.repository.scrap;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.terning.terningserver.domain.InternshipAnnouncement;
import org.terning.terningserver.domain.Scrap;

import java.util.List;
import static org.terning.terningserver.domain.QScrap.scrap;


@RequiredArgsConstructor
public class ScrapRepositoryImpl implements ScrapRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Scrap> findAllByInternshipAndUserId(List<InternshipAnnouncement> internshipAnnouncements, Long userId) {
        return jpaQueryFactory
                .selectFrom(scrap)
                .where(scrap.internshipAnnouncement.in(internshipAnnouncements), scrap.user.id.eq(userId))
                .fetch();
    }
}
