package com.ydh.redsheep.api.zkClient.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @description:
 * @author: yangdehong
 * @version: 2017/8/30.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable{

    private Integer id;
    private String name;

}
