package com.sydml.authorization.platform.data;

import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Liuym
 * @date 2018/12/15 0015
 */
@NoRepositoryBean
public interface JpaPartitionRepository<ET, IDT extends Serializable> extends JpaRepository<ET, IDT> {

    int PARTITION_COUNT_1000 = 1000;
    int MAX_COUNT = 20000;
    Logger LOGGER = LoggerFactory.getLogger(JpaPartitionRepository.class);


    /**
     * 按照传入的函数来进行1000个元素的分组查询
     *
     * @param function
     * @param idList
     * @return
     */
    default <PT1> List<ET> findByPartition1000(Function<List<PT1>, List<ET>> function, List<PT1> idList) {
        checkMaxCount(idList, function);
        return Lists.partition(idList, PARTITION_COUNT_1000).stream().map(function).flatMap(List::stream).collect(Collectors.toList());
    }

    /**
     * 按照传入的函数和状态来进行1000个元素的分组查询
     *
     * @param function
     * @param idList
     * @param status
     * @return
     */
    default <PT1, PT2> List<ET> findByPartition1000(BiFunction<List<PT1>, PT2, List<ET>> function, List<PT1> idList, PT2 status) {
        checkMaxCount(idList, function);
        return Lists.partition(idList, PARTITION_COUNT_1000).stream().map(ids -> function.apply(ids, status)).flatMap(List::stream)
                .collect(Collectors.toList());
    }

    /**
     * 检查是否超过最大数量;
     *
     * @param list
     * @param function
     * @return
     */
    default boolean checkMaxCount(List list, Object function) {
        if (list.size() > MAX_COUNT) {
            LOGGER.warn("The query count is greater than " + MAX_COUNT + ", repository is :" + function.getClass() + ", function is : " +
                    function + ", pls check your code.");
            return false;
        }
        return true;
    }
}
