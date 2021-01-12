package com.example.demo.dto;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlanResDTO {
    private Long id;
    private String name;
    private Integer status;
    private Long siteId;
    private Integer adPositionId;
    private String formId;
    private Integer mediaId;
    private String accountId;
    private Integer firstSubjectId;
    private Integer secondSubjectId;
    private Integer thirdSubjectId;
    private String mark;
    private String source;
    private String originalUrl;
    private String planUrl;
    private Integer gradeId;
    private Integer businessId;
    private String createUser;
    private Long userId;
    private Long uqunFlowJobId;
    private Long uqunChannelId;
    private Date createTime;
    private Date updateTime;
    private String campaignId;
    private Integer saleTypeId;

}

