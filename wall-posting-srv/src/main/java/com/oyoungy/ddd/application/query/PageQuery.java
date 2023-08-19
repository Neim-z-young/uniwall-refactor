package com.oyoungy.ddd.application.query;

import lombok.Data;

@Data
public class PageQuery<K> {
    private K lowerBound;
    private K upperBound;
    private Integer pageSize;
}
