package com.oyoungy.ddd.infra.impl.domain.repository;

import com.oyoungy.ddd.domain.user.entity.User;
import com.oyoungy.ddd.domain.user.entity.UserInfo;
import com.oyoungy.ddd.domain.user.repository.UserRepository;
import com.oyoungy.ddd.domain.user.vo.UserId;
import com.oyoungy.ddd.infra.converter.DoConverter;
import com.oyoungy.ddd.infra.dao.UserDAO;
import com.oyoungy.ddd.infra.dao.UserInfoDAO;
import com.oyoungy.ddd.infra.database.UserDO;
import com.oyoungy.ddd.infra.database.UserInformationDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private DoConverter doConverter = DoConverter.INSTANCE;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private UserInfoDAO userInfoDAO;

    @Override
    public User save(User user) {
        UserDO userDO = doConverter.toUserDO(user);
        userDO = userDAO.save(userDO);
        return doConverter.toUser(userDO);
    }

    @Override
    public Optional<User> findOne(UserId id) {
        Optional<UserDO> userDOOptional = userDAO.findById(id.getId());
        return userDOOptional.map(doConverter::toUser);
    }

    @Override
    public UserInfo save(UserInfo userInfo) {
        UserInformationDO userInformationDO = doConverter.toUserInformationDO(userInfo);
        userInformationDO = userInfoDAO.save(userInformationDO);
        return doConverter.toUserInfo(userInformationDO);
    }

    @Override
    public Optional<UserInfo> findUserInfo(UserId id) {
        return userInfoDAO.findById(id.getId()).map(doConverter::toUserInfo);
    }

    @Override
    @Transactional
    public void updateUserInfo(UserInfo userInfo) {
        userInfoDAO.update4CreateUser(userInfo.getId().getId(),
                userInfo.getGender().getValue(),
                userInfo.getGrowth(), userInfo.getCredit(), userInfo.getGmtModified(), userInfo.getGmtCreate());
        userInfoDAO.flush();
    }

    @Override
    public void update4UserLogin(User user) {
        userDAO.update4Login(user.getId().getId(), user.getOnline().getValue(), user.getLastLogin());
    }
}
