package com.oyoungy.ddd.domain.user.service;

import com.oyoungy.ddd.domain.permission.service.PermissionAggService;
import com.oyoungy.ddd.domain.user.entity.UserAgg;
import com.oyoungy.ddd.domain.user.entity.User;
import com.oyoungy.ddd.domain.user.entity.UserInfo;
import com.oyoungy.ddd.domain.user.repository.UserRepository;
import com.oyoungy.ddd.domain.permission.vo.RoleId;
import com.oyoungy.ddd.domain.user.vo.UserId;
import com.oyoungy.exceptions.WallBaseException;
import com.oyoungy.exceptions.WallNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
public class UserAggServiceImpl implements UserAggService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PermissionAggService permissionAggService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public UserAgg register(User user) {
        user.init();
        User newUser = userRepository.save(user);
        UserInfo userInfo = userRepository.findUserInfo(newUser.getId()).orElse(new UserInfo());
        if(userInfo.getId() != null){
            log.warn("清空旧的用户信息:{}", userInfo);
        }
        userInfo.init(newUser.getId());
        userRepository.save(userInfo);

        List<RoleId> roleIds = permissionAggService.defaultUserRole();
        permissionAggService.createUserRoleRelation(newUser.getId(), roleIds);
        UserAgg userAgg = new UserAgg();
        userAgg.setId(newUser.getId());
        userAgg.setRoles(roleIds);
        userAgg.setUser(newUser);
        userAgg.setUserInfo(userInfo);
        return userAgg;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public User login(User tmpUser){
        Optional<User> userOp = userRepository.findOne(tmpUser.getId());
        User user = userOp.orElseThrow(
                () -> new WallNotFoundException(MessageFormat.format("用户{0}不存在", tmpUser.getId())));
        if(!passwordEncoder.matches(tmpUser.getPassword(), user.getPassword())){
            throw new WallBaseException("密码错误");
        }
        user.login();
        userRepository.update4UserLogin(user);
        return user;
    }

    @Transactional(readOnly = true)
    @Override
    public UserAgg findOne(UserId id) {

        Optional<User> user = userRepository.findOne(id);
        Optional<UserAgg> resOp = user.map(u -> {
            UserAgg res = new UserAgg();
            res.setId(u.getId());
            res.setUser(u);
            return res;
        });
        resOp.ifPresent(u -> {
            userRepository.findUserInfo(id).ifPresent(u::setUserInfo);
            u.setRoles(permissionAggService.findUserRoles(u.getId()));
        });
        return resOp.orElseThrow(
                () -> new WallNotFoundException(MessageFormat.format("用户{0}不存在", id.getId())));
    }
}
