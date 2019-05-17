package com.sydml.transaction.platform.data;


import com.sydml.transaction.platform.sign.Ignore;

public class SortCondition {

    @Ignore
    public static final String ASC = "ASC";

    @Ignore
    public static final String DESC = "DESC";

    @Ignore
    private String sortField;

    @Ignore
    private String direction;

    public static String getASC() {
        return ASC;
    }

    public static String getDESC() {
        return DESC;
    }

    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }
}
