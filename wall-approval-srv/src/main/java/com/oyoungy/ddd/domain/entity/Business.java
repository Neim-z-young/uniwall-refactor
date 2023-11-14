package com.oyoungy.ddd.domain.entity;

import com.oyoungy.enums.BusinessType;
import lombok.Data;

@Data
public class Business {
    private BusinessType type;
}
