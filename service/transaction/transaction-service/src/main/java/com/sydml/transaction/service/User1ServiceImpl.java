package com.sydml.transaction.service;

import com.sydml.transaction.api.IUser1Service;
import com.sydml.transaction.domain.User1;
import com.sydml.transaction.repository.User1Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Liuym
 * @date 2019/3/13 0013
 */
@Service
public class User1ServiceImpl implements IUser1Service {
    @Autowired
    private User1Repository user1Repository;

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public void save(User1 user) {
        user1Repository.save(user);
    }
}
