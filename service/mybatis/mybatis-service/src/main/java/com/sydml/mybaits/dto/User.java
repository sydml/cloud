package com.sydml.mybaits.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author Liuym
 * @date 2019/3/23 0023
 */
public class User {

    private Long id;

    private String name;

    private LocalDateTime createInstant;

    private LocalDateTime dateTimeTest;

    private LocalDate dateTest;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getCreateInstant() {
        return createInstant;
    }

    public void setCreateInstant(LocalDateTime createInstant) {
        this.createInstant = createInstant;
    }

    public LocalDateTime getDateTimeTest() {
        return dateTimeTest;
    }

    public void setDateTimeTest(LocalDateTime dateTimeTest) {
        this.dateTimeTest = dateTimeTest;
    }

    public LocalDate getDateTest() {
        return dateTest;
    }

    public void setDateTest(LocalDate dateTest) {
        this.dateTest = dateTest;
    }
}
