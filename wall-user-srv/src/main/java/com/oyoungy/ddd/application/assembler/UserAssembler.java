package com.oyoungy.ddd.application.assembler;

import com.oyoungy.ddd.application.command.UserLoginCommand;
import com.oyoungy.ddd.application.dto.UserDTO;
import com.oyoungy.ddd.application.command.UserRegisterCommand;
import com.oyoungy.ddd.domain.user.entity.User;
import com.oyoungy.ddd.domain.user.vo.UserId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserAssembler {
    UserAssembler INSTANCE = Mappers.getMapper(UserAssembler.class);

    @Mapping(source = "id.id", target = "id")
    UserDTO toUserDTO(User user);

    UserId toUserId(Long id);

    User toUser(UserRegisterCommand userRegisterCommand);

    @Mapping(source = "id", target = "id.id")
    User toUser(UserLoginCommand userLoginCommand);


}
