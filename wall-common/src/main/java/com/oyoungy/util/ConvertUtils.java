package com.oyoungy.util;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

public class ConvertUtils {
    public static Byte IntegerToByte(Integer v){
        if(v == null){
            return null;
        }
        return v.byteValue();
    }

    public static Integer ByteToInteger(Byte v){
        if(v == null){
            return null;
        }
        return v.intValue();
    }

    public Timestamp DateToTimeStamp(LocalDateTime date){
        return Optional.ofNullable(date).
                map(Timestamp::valueOf).
                orElse(null);
    }

    public LocalDateTime TimeStampToLocalDateTime(Timestamp timestamp){
        return Optional.ofNullable(timestamp).
                map(Timestamp::toLocalDateTime).
                orElse(null);
    }

    public Date TimeStampToDate(Timestamp timestamp){
        return Optional.ofNullable(timestamp).
                map(t -> new Date(t.getTime())).
                orElse(null);
    }

    public Timestamp DateToTimeStamp(Date date){
        return Optional.ofNullable(date).
                map(t -> new Timestamp(t.getTime())).
                orElse(null);
    }
}
