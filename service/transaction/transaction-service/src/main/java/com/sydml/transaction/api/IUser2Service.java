package com.sydml.transaction.api;

import com.sydml.transaction.domain.User1;
import com.sydml.transaction.domain.User2;

import java.util.List;

/**
 * @author Liuym
 * @date 2019/3/13 0013
 */
public interface IUser2Service {
    void save(User2 user);

    void batchSave(List<User2> user2List);

    void saveUserWithTrx(User2 user2);

    User2 saveUserWithoutTrx(User2 user2);

//    User1 saveUser2AndUser1(User2 user2);

    void testBaseTypeRequest(String a, int b, Boolean c, Long d, User1 user1);

    void test1();
}
