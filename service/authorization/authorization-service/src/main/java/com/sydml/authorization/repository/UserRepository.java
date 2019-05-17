package com.sydml.authorization.repository;

import com.sydml.authorization.domain.User;
import com.sydml.authorization.platform.data.JpaPartitionRepository;

import java.util.List;

/**
 * @author Liuym
 * @date 2019/3/25 0025
 */
public interface UserRepository extends JpaPartitionRepository<User, Long> {

    List<User> findByUsername(String username);
}
