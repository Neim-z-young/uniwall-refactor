package com.oyoungy.ddd.domain.user.repository;

import com.oyoungy.ddd.domain.user.entity.User;
import com.oyoungy.ddd.domain.user.entity.UserInfo;
import com.oyoungy.ddd.domain.user.vo.UserId;
import com.oyoungy.ddd.domain.repository.BaseRepository;

import java.util.Optional;

public interface UserRepository extends BaseRepository<User, UserId> {
    UserInfo save(UserInfo userInfo);

    Optional<UserInfo> findUserInfo(UserId id);

    @Deprecated
    void updateUserInfo(UserInfo userInfo);

    void update4UserLogin(User user);
}
