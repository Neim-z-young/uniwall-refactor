package com.oyoungy.controller;

import com.oyoungy.ddd.application.command.AddPostingCommand;
import com.oyoungy.ddd.application.dto.*;
import com.oyoungy.ddd.application.query.PageQuery;
import com.oyoungy.ddd.application.service.PostingService;
import com.oyoungy.response.ResultDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;

@RestController
@RequestMapping("/posting")
public class PostingController {
    @Autowired
    private PostingService postingService;

    @GetMapping("/hello")
    public ResultDTO<String> helloWorld(){
        return ResultDTO.success("hello world !!");
    }

    @GetMapping("/{categoryId}")
    ResultDTO<PageDTO<PostingBriefDTO, BigInteger>> getPostings(
            @PathVariable Long categoryId,
            @RequestParam(defaultValue = "0", name = "offset") BigInteger minId,
            @RequestParam(defaultValue = "10") Integer pageSize){
        PageQuery<BigInteger> pageQuery = new PageQuery<>();
        pageQuery.setLowerBound(minId);
        pageQuery.setPageSize(pageSize);
        return ResultDTO.success(postingService.queryPostingByCategory(categoryId, pageQuery));
    }

    @GetMapping("/")
    ResultDTO<PostingQueryDTO> getPosting(@RequestParam BigInteger postingId){
        return ResultDTO.success(postingService.queryPostingById(postingId));
    }

    @PostMapping("/")
    ResultDTO<PostingStateDTO> addPosting(@RequestBody @Validated AddPostingCommand addPostingCommand){
        return ResultDTO.success(postingService.addPosting(addPostingCommand));
    }

    @DeleteMapping("/{postingId}")
    ResultDTO<Void> deletePosting(@PathVariable BigInteger postingId){
        postingService.deletePosting(postingId);
        return ResultDTO.success(null);
    }


}
