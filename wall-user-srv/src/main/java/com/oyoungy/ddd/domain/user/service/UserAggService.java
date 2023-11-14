package com.oyoungy.ddd.domain.user.service;

import com.oyoungy.ddd.domain.user.entity.UserAgg;
import com.oyoungy.ddd.domain.user.entity.User;
import com.oyoungy.ddd.domain.user.vo.UserId;
import com.oyoungy.exceptions.WallBaseException;
import com.oyoungy.exceptions.WallNotFoundException;

import java.util.Optional;

public interface UserAggService {

    UserAgg register(User user);

    User login(User user) throws WallBaseException;

    UserAgg findOne(UserId id) throws WallNotFoundException;
}
