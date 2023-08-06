package com.oyoungy.ddd.domain.user.entity;

import com.oyoungy.ddd.domain.user.vo.UserId;
import com.oyoungy.enums.GenderEnum;
import lombok.Data;

import java.text.MessageFormat;
import java.util.Date;

@Data
public class UserInfo {
    private UserId id;
    private GenderEnum gender;
    private Integer growth;
    private Integer credit;
    private Date gmtCreate;
    private Date gmtModified;

    public void init(UserId id){
        setId(id);
        setCredit(0);
        setGender(GenderEnum.NONE);
        setGrowth(0);
        setGmtCreate(new Date());
        setGmtModified(new Date());
    }

    public void incrementGrowth(Integer add){
        if(add < 0){
            throw new IllegalArgumentException(MessageFormat.format("新增成长值{0}异常", add));
        }

        if(growth + add < 0){
            throw new IllegalStateException(
                    MessageFormat.format("成长值溢出，原成长值:{0}, 新增成长值:{1}", growth, add));
        }

        growth += add;
    }
}
