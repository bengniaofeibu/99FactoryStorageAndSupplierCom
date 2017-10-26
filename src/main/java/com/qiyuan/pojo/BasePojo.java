package com.qiyuan.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class BasePojo implements Serializable {

    private static final long serialVersionUID = 1461967774682435314L;
}
