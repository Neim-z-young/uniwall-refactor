package com.oyoungy.ddd.infra.impl.domain.repository;

import com.oyoungy.ddd.domain.user.entity.PlatformUser;
import com.oyoungy.ddd.domain.user.entity.User;
import com.oyoungy.ddd.domain.user.repository.PlatformUserRepository;
import com.oyoungy.ddd.domain.user.vo.PlatformUserId;
import com.oyoungy.ddd.infra.converter.DoConverter;
import com.oyoungy.ddd.infra.dao.PlatformUserDAO;
import com.oyoungy.ddd.infra.dao.UserDAO;
import com.oyoungy.ddd.infra.database.PlatformUserDO;
import com.oyoungy.ddd.infra.database.PlatformUserKeys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class PlatformUserRepositoryImpl implements PlatformUserRepository {
    private DoConverter doConverter = DoConverter.INSTANCE;

    @Autowired
    private PlatformUserDAO platformUserDAO;

    @Autowired
    private UserDAO userDAO;


    @Override
    public PlatformUser save(PlatformUser user) {
        PlatformUserDO platformUserDO = doConverter.toPlatformUserDO(user);
        platformUserDO = platformUserDAO.save(platformUserDO);
        return doConverter.toPlatformUser(platformUserDO);
    }

    @Override
    public Optional<User> findUser(PlatformUserId id) {
        PlatformUserKeys platformUserKeys = doConverter.toPlatformUserKeys(id);
        Optional<User> user =
        platformUserDAO.findById(platformUserKeys).
                flatMap(platformUserDO -> userDAO.findById(platformUserDO.getUserId())).
                map(doConverter::toUser);
        return user;
    }
}
