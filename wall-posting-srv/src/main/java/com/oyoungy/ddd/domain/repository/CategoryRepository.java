package com.oyoungy.ddd.domain.repository;

import com.oyoungy.ddd.domain.entity.Category;
import com.oyoungy.ddd.domain.vo.CategoryId;
import com.oyoungy.enums.StateEnum;

import java.util.List;

public interface CategoryRepository extends BaseRepository<Category, CategoryId> {
    List<Category> findNextPage(CategoryId lowerBound, Integer pageSize);

    List<Category> findPrevPage(CategoryId upperBound, Integer pageSize);

    boolean updateState(Category category);
}
