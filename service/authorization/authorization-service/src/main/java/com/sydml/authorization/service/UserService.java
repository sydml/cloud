package com.sydml.authorization.service;

import com.sydml.authorization.domain.User;
import com.sydml.common.api.dto.UserDTO;
import com.sydml.authorization.repository.UserRepository;
import com.sydml.authorization.util.BeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Liuym
 * @date 2019/3/25 0025
 */
@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BeanMapper beanMapper;


    @Override
//    @RequestMapping(value="user/find-by-username",method = RequestMethod.GET)
    public UserDTO findByUsername(String username) {
        return beanMapper.map(userRepository.findByUsername(username).get(0), UserDTO.class);
    }

    @Override
    public UserDTO save(UserDTO userDTO) {
        return beanMapper.map(userRepository.save(beanMapper.map(userDTO, User.class)), UserDTO.class);
    }

    @Override
    public UserDTO findById(Long id) {
        return beanMapper.map(userRepository.findById(id).orElseGet(null), UserDTO.class);
    }


}
