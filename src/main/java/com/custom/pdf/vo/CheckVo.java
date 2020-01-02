package com.custom.pdf.vo;

import lombok.Data;

/**
 * @des:
 * @author: wsw
 * @email: 18683789594@163.com
 * @date: 2020/1/2 21:26
 */
@Data
public class CheckVo {

    private String checkName;
    private String checkIsOne;
    private String checkIsTwo;
    private String checkThree;

    public CheckVo(String checkName, String checkIsOne, String checkIsTwo, String checkThree) {
        this.checkName = checkName;
        this.checkIsOne = checkIsOne;
        this.checkIsTwo = checkIsTwo;
        this.checkThree = checkThree;
    }
}
