package com.oyoungy.ddd.application.Service;

import com.mysql.cj.exceptions.WrongArgumentException;
import com.oyoungy.ddd.application.assembler.UserAssembler;
import com.oyoungy.ddd.application.command.TokenRefreshCommand;
import com.oyoungy.ddd.application.command.UserLoginCommand;
import com.oyoungy.ddd.application.command.UserRegisterCommand;
import com.oyoungy.ddd.application.dto.TokenDTO;
import com.oyoungy.ddd.application.dto.UserDTO;
import com.oyoungy.ddd.domain.user.entity.User;
import com.oyoungy.ddd.domain.user.entity.UserAgg;
import com.oyoungy.ddd.domain.user.repository.UserRepository;
import com.oyoungy.ddd.domain.user.service.UserAggService;
import com.oyoungy.ddd.domain.user.vo.UserId;
import com.oyoungy.exceptions.WallBaseException;
import com.oyoungy.exceptions.WallNotFoundException;
import com.oyoungy.tool.JwtTool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Optional;

@Service
@Slf4j
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserAggService userAggDomainService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTool jwtTool;

    private UserAssembler userAssembler = UserAssembler.INSTANCE;

    public UserDTO queryUser(Long id){
        UserId userId = userAssembler.toUserId(id);
        Optional<User> user = userRepository.findOne(userId);
        return userAssembler.toUserDTO(
                user.orElseThrow(() ->
                        new WallBaseException(MessageFormat.format("用户{0}不存在", id))));
    }

    public UserDTO registerUser(UserRegisterCommand userRegisterCommand){
        User user = userAssembler.toUser(userRegisterCommand);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        UserAgg userAgg = userAggDomainService.register(user);
        return userAssembler.toUserDTO(userAgg.getUser());
    }

    public TokenDTO login(UserLoginCommand userLoginCommand){
        User tmpUser = userAssembler.toUser(userLoginCommand);
        User user = userAggDomainService.login(tmpUser);

        TokenDTO tokenDTO = new TokenDTO();
        tokenDTO.setRefreshToken(jwtTool.generateRefreshToken(user.getId().getId(), jwtTool.getJwtConf().getUserRole()));
        tokenDTO.setToken(jwtTool.generateAccessToken(user.getId().getId(), jwtTool.getJwtConf().getUserRole()));
        tokenDTO.setUserId(user.getId().getId());
        log.info("secret:{}", jwtTool.getJwtConf().getSecret());
        return tokenDTO;
    }

    public TokenDTO refresh(TokenRefreshCommand refreshCommand){
        Long userId = jwtTool.getUserIdFromToken(refreshCommand.getRefreshToken());
        TokenDTO tokenDTO = new TokenDTO();
        tokenDTO.setToken(jwtTool.refreshToken(refreshCommand.getRefreshToken()));
        tokenDTO.setUserId(userId);
        return tokenDTO;
    }
}
