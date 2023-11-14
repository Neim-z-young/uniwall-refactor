package com.oyoungy.ddd.application.domain.entity;

import com.oyoungy.enums.StateEnum;
import lombok.Data;

import java.util.Objects;

@Data
public abstract class ApprovableEntity<T> {
    private T id;
    private StateEnum state;
    public void deleted(){
        Objects.requireNonNull(id, "id为空");
        setState(StateEnum.DELETED);
    }

    public void approving(){
        Objects.requireNonNull(id, "id为空");
        setState(StateEnum.APPROVING);
    }

    public void created(){
        Objects.requireNonNull(id, "id为空");
        setState(StateEnum.USABLE);
    }

    public void approvedFailed(){
        Objects.requireNonNull(id, "id为空");
        setState(StateEnum.DENIED);
    }
}
