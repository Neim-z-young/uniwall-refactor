package com.oyoungy.ddd.application.dto;

import com.oyoungy.ddd.domain.user.entity.PlatformUser;
import com.oyoungy.ddd.domain.user.entity.UserAgg;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class UserDetailDTO {
    private Long id;

    private String nickname;

    private String email;

    private String phoneNumber;

    private Integer online;

    private Integer status;

    private Date lastLogin;

    private Date gmtCreate;

    private Integer gender;
    private Integer growth;
    private Integer credit;

    private List<String> platforms;

    private List<String> roles;

    public static UserDetailDTO fromUserAgg(UserAgg userAgg){
        UserDetailDTO userDetailDTO = new UserDetailDTO();
        userDetailDTO.setId(userAgg.getId().getId());
        userDetailDTO.setNickname(userAgg.getUser().getNickname());
        userDetailDTO.setEmail(userAgg.getUser().getEmail());
        userDetailDTO.setPhoneNumber(userAgg.getUser().getPhoneNumber());
        userDetailDTO.setOnline(userAgg.getUser().getOnline());
        userDetailDTO.setStatus(userAgg.getUser().getStatus());
        userDetailDTO.setLastLogin(userAgg.getUser().getLastLogin());
        userDetailDTO.setGmtCreate(userAgg.getUser().getGmtCreate());
        userDetailDTO.setGender(userAgg.getUserInfo().getGender());
        userDetailDTO.setGrowth(userAgg.getUserInfo().getGrowth());
        userDetailDTO.setCredit(userAgg.getUserInfo().getCredit());

        List<String> platforms = new ArrayList<>();
        if(userAgg.getPlatformUsers() != null){
            for (PlatformUser u: userAgg.getPlatformUsers()
                 ) {
                platforms.add(u.getId().getPlatformType());
            }
        }
        userDetailDTO.setPlatforms(platforms);

        return userDetailDTO;
    }
}
