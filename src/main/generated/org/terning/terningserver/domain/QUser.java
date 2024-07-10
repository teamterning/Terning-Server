package org.terning.terningserver.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = 612245724L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUser user = new QUser("user");

    public final org.terning.terningserver.domain.common.QBaseTimeEntity _super = new org.terning.terningserver.domain.common.QBaseTimeEntity(this);

    public final StringPath authId = createString("authId");

    public final EnumPath<org.terning.terningserver.domain.enums.AuthType> authType = createEnum("authType", org.terning.terningserver.domain.enums.AuthType.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final QFilter filter;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final StringPath name = createString("name");

    public final StringPath refreshToken = createString("refreshToken");

    public final ListPath<Scrap, QScrap> scrapList = this.<Scrap, QScrap>createList("scrapList", Scrap.class, QScrap.class, PathInits.DIRECT2);

    public final EnumPath<org.terning.terningserver.domain.enums.State> state = createEnum("state", org.terning.terningserver.domain.enums.State.class);

    public QUser(String variable) {
        this(User.class, forVariable(variable), INITS);
    }

    public QUser(Path<? extends User> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUser(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUser(PathMetadata metadata, PathInits inits) {
        this(User.class, metadata, inits);
    }

    public QUser(Class<? extends User> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.filter = inits.isInitialized("filter") ? new QFilter(forProperty("filter")) : null;
    }

}

