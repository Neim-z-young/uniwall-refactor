package com.oyoungy.controller;

import com.oyoungy.ddd.application.command.AddCategoryCommand;
import com.oyoungy.ddd.application.dto.CategoryDTO;
import com.oyoungy.ddd.application.dto.PageDTO;
import com.oyoungy.ddd.application.query.PageQuery;
import com.oyoungy.ddd.application.service.CategoryService;
import com.oyoungy.exceptions.WallBaseException;
import com.oyoungy.response.ResultDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/")
    ResultDTO<PageDTO<CategoryDTO, Long>> getCategories(
            @RequestParam(defaultValue = "0", name = "offset") Long minId,
            @RequestParam(defaultValue = "10") Integer pageSize){
        PageQuery<Long> pageQuery = new PageQuery<>();
        pageQuery.setLowerBound(minId);
        pageQuery.setPageSize(pageSize);
        return ResultDTO.success(categoryService.queryCategory(pageQuery));
    }

    @PostMapping("/")
    ResultDTO<CategoryDTO> addCategory(@RequestBody @Validated AddCategoryCommand addCategoryCommand){
        return ResultDTO.success(categoryService.addCategory(addCategoryCommand));
    }

    @DeleteMapping("/{categoryId}")
    ResultDTO<Void> deleteCategory(@PathVariable Long categoryId) throws WallBaseException {
        categoryService.deleteCategory(categoryId);
        return ResultDTO.success(null);
    }
}
