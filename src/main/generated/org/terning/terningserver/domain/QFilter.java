package org.terning.terningserver.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QFilter is a Querydsl query type for Filter
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFilter extends EntityPathBase<Filter> {

    private static final long serialVersionUID = -480837559L;

    public static final QFilter filter = new QFilter("filter");

    public final EnumPath<org.terning.terningserver.domain.enums.Grade> grade = createEnum("grade", org.terning.terningserver.domain.enums.Grade.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> startMonth = createNumber("startMonth", Integer.class);

    public final NumberPath<Integer> startYear = createNumber("startYear", Integer.class);

    public final EnumPath<org.terning.terningserver.domain.enums.WorkingPeriod> workingPeriod = createEnum("workingPeriod", org.terning.terningserver.domain.enums.WorkingPeriod.class);

    public QFilter(String variable) {
        super(Filter.class, forVariable(variable));
    }

    public QFilter(Path<? extends Filter> path) {
        super(path.getType(), path.getMetadata());
    }

    public QFilter(PathMetadata metadata) {
        super(Filter.class, metadata);
    }

}

