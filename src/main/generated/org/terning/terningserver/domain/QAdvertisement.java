package org.terning.terningserver.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QAdvertisement is a Querydsl query type for Advertisement
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAdvertisement extends EntityPathBase<Advertisement> {

    private static final long serialVersionUID = 558022676L;

    public static final QAdvertisement advertisement = new QAdvertisement("advertisement");

    public final org.terning.terningserver.domain.common.QBaseTimeEntity _super = new org.terning.terningserver.domain.common.QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public QAdvertisement(String variable) {
        super(Advertisement.class, forVariable(variable));
    }

    public QAdvertisement(Path<? extends Advertisement> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAdvertisement(PathMetadata metadata) {
        super(Advertisement.class, metadata);
    }

}

