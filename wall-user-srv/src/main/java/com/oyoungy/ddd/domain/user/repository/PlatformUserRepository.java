package com.oyoungy.ddd.domain.user.repository;

import com.oyoungy.ddd.domain.user.entity.PlatformUser;
import com.oyoungy.ddd.domain.user.entity.User;
import com.oyoungy.ddd.domain.user.vo.PlatformUserId;
import com.oyoungy.ddd.domain.repository.BaseRepository;

import java.util.Optional;

public interface PlatformUserRepository extends BaseRepository<PlatformUser, PlatformUserId> {
    Optional<User> findUser(PlatformUserId id);
}
