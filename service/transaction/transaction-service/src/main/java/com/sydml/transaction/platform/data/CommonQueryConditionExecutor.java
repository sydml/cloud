package com.sydml.transaction.platform.data;

import com.sydml.common.utils.Nullable;
import com.sydml.common.utils.StringUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public final class CommonQueryConditionExecutor {

    private CommonQueryConditionExecutor() {
        //do nothing
    }

    public static <T> Page<T> queryByCondition(PageCondition condition, JpaSpecificationExecutor<T> specificationExecutor) {
        Specification<T> specification = SpecificationBuilder.buildSpecification(condition);
        if (condition.getPage() != PageCondition.PAGE_DEFAULTVALUE) {
            return StringUtil.isEmpty(condition.getDirection())
                    ? specificationExecutor.findAll(specification, PageRequest.of(condition.getPage() - 1, condition.getLimit()))
                    : specificationExecutor.findAll(specification, PageRequest.of(condition.getPage() - 1, condition.getLimit(),
                    buildSortByCondition(condition)));
        } else {
            List<T> tasks = StringUtil.isEmpty(condition.getDirection()) ? specificationExecutor.findAll(specification) :
                    specificationExecutor.findAll(specification, buildSortByCondition(condition));
            return new PageImpl<>(tasks);
        }
    }

    public static Sort buildSortByCondition(SortCondition sortCondition) {
        return new Sort(Sort.Direction.fromString(sortCondition.getDirection()), Nullable.of(sortCondition.getSortField()).orElse(
                "createInstant"));
    }
}
