package com.sydml.transaction.repository;

import com.sydml.transaction.domain.User1;
import com.sydml.transaction.platform.JpaPartitionRepository;

import java.util.List;

/**
 * @author Liuym
 * @date 2019/3/13 0013
 */
public interface User1Repository extends JpaPartitionRepository<User1, Long> {
    List<User1> findByName(Long id);

    void deleteByName(Long id);
}
