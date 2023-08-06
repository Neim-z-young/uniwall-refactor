package com.oyoungy.util;

import com.oyoungy.enums.GenderEnum;
import com.oyoungy.enums.OnlineEnum;
import com.oyoungy.enums.StateEnum;
import com.oyoungy.enums.StatusEnum;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

public class ConvertUtils {
    public static Byte integerToByte(Integer v){
        if(v == null){
            return null;
        }
        return v.byteValue();
    }

    public static Integer byteToInteger(Byte v){
        if(v == null){
            return null;
        }
        return v.intValue();
    }

    public Timestamp dateToTimeStamp(LocalDateTime date){
        return Optional.ofNullable(date).
                map(Timestamp::valueOf).
                orElse(null);
    }

    public LocalDateTime timeStampToLocalDateTime(Timestamp timestamp){
        return Optional.ofNullable(timestamp).
                map(Timestamp::toLocalDateTime).
                orElse(null);
    }

    public Date timeStampToDate(Timestamp timestamp){
        return Optional.ofNullable(timestamp).
                map(t -> new Date(t.getTime())).
                orElse(null);
    }

    public Timestamp dateToTimeStamp(Date date){
        return Optional.ofNullable(date).
                map(t -> new Timestamp(t.getTime())).
                orElse(null);
    }

    public GenderEnum integerToGenderEnum(Integer gender){
        return GenderEnum.of(gender);
    }

    public Integer genderEnumToInteger(GenderEnum gender){
        return gender.getValue();
    }

    public OnlineEnum integerToOnlineEnum(Integer v){
        return OnlineEnum.of(v);
    }

    public Integer onlineEnumToInteger(OnlineEnum v){
        return v.getValue();
    }

    public StateEnum integerToStateEnum(Integer state){
        return StateEnum.of(state);
    }

    public Integer stateEnumToInteger(StateEnum state){
        return state.getValue();
    }

    public StatusEnum integerToStatusEnum(Integer status){
        return StatusEnum.of(status);
    }

    public Integer statusEnumToInteger(StatusEnum status){
        return status.getValue();
    }
}
