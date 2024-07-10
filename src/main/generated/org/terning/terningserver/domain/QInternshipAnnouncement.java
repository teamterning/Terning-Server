package org.terning.terningserver.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QInternshipAnnouncement is a Querydsl query type for InternshipAnnouncement
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QInternshipAnnouncement extends EntityPathBase<InternshipAnnouncement> {

    private static final long serialVersionUID = -146716794L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QInternshipAnnouncement internshipAnnouncement = new QInternshipAnnouncement("internshipAnnouncement");

    public final org.terning.terningserver.domain.common.QBaseTimeEntity _super = new org.terning.terningserver.domain.common.QBaseTimeEntity(this);

    public final QCompany company;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final DateTimePath<java.util.Date> deadline = createDateTime("deadline", java.util.Date.class);

    public final StringPath detail = createString("detail");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath jobType = createString("jobType");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final StringPath qualifications = createString("qualifications");

    public final NumberPath<Integer> scrapCount = createNumber("scrapCount", Integer.class);

    public final ComparablePath<java.time.YearMonth> startDate = createComparable("startDate", java.time.YearMonth.class);

    public final StringPath title = createString("title");

    public final StringPath url = createString("url");

    public final NumberPath<Integer> viewCount = createNumber("viewCount", Integer.class);

    public final StringPath workingPeriod = createString("workingPeriod");

    public QInternshipAnnouncement(String variable) {
        this(InternshipAnnouncement.class, forVariable(variable), INITS);
    }

    public QInternshipAnnouncement(Path<? extends InternshipAnnouncement> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QInternshipAnnouncement(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QInternshipAnnouncement(PathMetadata metadata, PathInits inits) {
        this(InternshipAnnouncement.class, metadata, inits);
    }

    public QInternshipAnnouncement(Class<? extends InternshipAnnouncement> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.company = inits.isInitialized("company") ? new QCompany(forProperty("company")) : null;
    }

}

