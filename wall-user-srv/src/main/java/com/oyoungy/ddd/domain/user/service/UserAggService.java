package com.oyoungy.ddd.domain.user.service;

import com.oyoungy.ddd.domain.user.entity.UserAgg;
import com.oyoungy.ddd.domain.user.entity.User;

public interface UserAggService {

    UserAgg register(User user);

    User login(User user);
}
