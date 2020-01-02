package com.custom.pdf.vo;

import lombok.Data;

import java.util.List;

/**
 * @des:
 * @author: wsw
 * @email: 18683789594@163.com
 * @date: 2020/1/2 21:25
 */
@Data
public class ModelVo {

    private String userName;

    private String userAge;

    private String userJob;

    private String userHobby;

    private String billDate;

    private List<String> checkVosName;

    private List<CheckVo> checkVosValue;
}
