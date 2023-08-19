package com.oyoungy.ddd.infra.impl.domain.repository;

import com.oyoungy.ddd.domain.entity.Category;
import com.oyoungy.ddd.domain.repository.CategoryRepository;
import com.oyoungy.ddd.domain.vo.CategoryId;
import com.oyoungy.ddd.infra.convertor.DoConverter;
import com.oyoungy.ddd.infra.dao.CategoryDAO;
import com.oyoungy.ddd.infra.database.CategoryDO;
import com.oyoungy.enums.StateEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class CategoryRepositoryImpl implements CategoryRepository {
    private DoConverter converter = DoConverter.INSTANCE;

    @Autowired
    private CategoryDAO categoryDAO;

    @Override
    public Category save(Category category){
        CategoryDO categoryDO = categoryDAO.save(converter.toCategoryDO(category));
        return converter.toCategory(categoryDO);
    }

    @Override
    public Optional<Category> findOne(CategoryId id){
        return categoryDAO.findById(id.getId()).map(converter::toCategory);
    }

    @Override
    public List<Category> findNextPage(CategoryId lowerBound, Integer pageSize){
        return categoryDAO.findByIdGreaterThanAndState(lowerBound.getId(), StateEnum.USABLE.getValue(), Pageable.ofSize(pageSize))
                .stream().map(converter::toCategory).collect(Collectors.toList());
    }

    @Override
    public List<Category> findPrevPage(CategoryId upperBound, Integer pageSize) {
        return categoryDAO.findByIdLessThanAndState(upperBound.getId(), StateEnum.USABLE.getValue(), Pageable.ofSize(pageSize))
                .stream().map(converter::toCategory).collect(Collectors.toList());
    }

    @Override
    public boolean updateState(Category category) {
        return categoryDAO.updateState(category.getId().getId(), category.getState().getValue()) > 0;
    }
}
