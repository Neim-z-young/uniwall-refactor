package com.oyoungy.ddd.infra.impl.domain.repository;

import com.oyoungy.ddd.domain.entity.Posting;
import com.oyoungy.ddd.domain.repository.PostingRepository;
import com.oyoungy.ddd.domain.vo.PostingId;
import com.oyoungy.ddd.infra.convertor.DoConverter;
import com.oyoungy.ddd.infra.dao.PostingDAO;
import com.oyoungy.enums.StateEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class PostingRepositoryImpl implements PostingRepository {
    private DoConverter converter = DoConverter.INSTANCE;

    @Autowired
    private PostingDAO postingDAO;

    @Override
    public List<Posting> findNextPage(PostingId lowerBound, Integer pageSize) {
        return postingDAO.findByIdGreaterThanAndState(lowerBound.getId(), StateEnum.USABLE.getValue(), Pageable.ofSize(pageSize))
                .stream().map(converter::toPosting).collect(Collectors.toList());
    }

    @Override
    public List<Posting> findPrevPage(PostingId upperBound, Integer pageSize) {
        return postingDAO.findByIdLessThanAndState(upperBound.getId(), StateEnum.USABLE.getValue(), Pageable.ofSize(pageSize))
                .stream().map(converter::toPosting).collect(Collectors.toList());
    }

    @Override
    public boolean updateState(Posting posting) {
        return postingDAO.updateState(posting.getId().getId(), posting.getState().getValue()) > 0;
    }

    @Override
    public Posting save(Posting posting) {
        return converter.toPosting(postingDAO.save(converter.toPostingDO(posting)));
    }

    @Override
    public Optional<Posting> findOne(PostingId keyId) {
        return postingDAO.findById(keyId.getId()).map(converter::toPosting);
    }
}
