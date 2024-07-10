package org.terning.terningserver.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCompany is a Querydsl query type for Company
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QCompany extends BeanPath<Company> {

    private static final long serialVersionUID = -216030772L;

    public static final QCompany company = new QCompany("company");

    public final EnumPath<org.terning.terningserver.domain.enums.CompanyCategory> companyCategory = createEnum("companyCategory", org.terning.terningserver.domain.enums.CompanyCategory.class);

    public final StringPath companyImage = createString("companyImage");

    public final StringPath companyInfo = createString("companyInfo");

    public QCompany(String variable) {
        super(Company.class, forVariable(variable));
    }

    public QCompany(Path<? extends Company> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCompany(PathMetadata metadata) {
        super(Company.class, metadata);
    }

}

