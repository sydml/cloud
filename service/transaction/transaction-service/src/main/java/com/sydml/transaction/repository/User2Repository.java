package com.sydml.transaction.repository;

import com.sydml.transaction.domain.User2;
import com.sydml.transaction.platform.JpaPartitionRepository;

import java.util.List;

/**
 * @author Liuym
 * @date 2019/3/13 0013
 */
public interface User2Repository extends JpaPartitionRepository<User2, Long> {
    List<User2> findByName(Long id);

    void deleteByName(Long id);
}
