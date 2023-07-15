package com.oyoungy.ddd.infra.convertor;

import cn.hutool.core.lang.Assert;
import com.oyoungy.ddd.domain.entity.Category;
import com.oyoungy.ddd.infra.database.CategoryDO;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class DoConverterTest {

    @org.junit.jupiter.api.Test
    void toCategory() {
        DoConverter doConverter = DoConverter.INSTANCE;
        CategoryDO categoryDO = new CategoryDO();
        categoryDO.setCategory("11");
        categoryDO.setCreateUserId(12L);
        categoryDO.setGmtCreate(new Date());
        categoryDO.setId(123L);
        categoryDO.setTopPostings("12345;1236;555");
        Category category = doConverter.toCategory(categoryDO);
        System.out.println(category);
        CategoryDO categoryDO1 = doConverter.toCategoryDO(category);
        assertEquals(categoryDO, categoryDO1);

        categoryDO.setTopPostings("-12345;1234;asdasf");
        category = doConverter.toCategory(categoryDO);
        System.out.println(category);
        category.setTopPostings(null);
        categoryDO1 = doConverter.toCategoryDO(category);
        assertEquals("", categoryDO1.getTopPostings());
    }
}