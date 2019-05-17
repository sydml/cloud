package com.sydml.common.api.dto;

import com.sydml.common.basebean.enums.Status;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Liuym
 * @date 2019/3/25 0025
 */
public class UserDTO implements Serializable {

    private static final long serialVersionUID = -509696714197743874L;

    private Long id;

    private String username;

    private String password;

    private LocalDateTime createTime;

    private LocalDateTime modifyTime;

    private Status status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(LocalDateTime modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
