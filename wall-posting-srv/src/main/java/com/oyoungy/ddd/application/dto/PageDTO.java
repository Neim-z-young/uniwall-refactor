package com.oyoungy.ddd.application.dto;

import lombok.Data;

import java.util.List;

@Data
public class PageDTO<T, K> {
    private List<T> objects;
    private K upperBound;
    private K lowerBound;
    private Integer pageSize;
}
