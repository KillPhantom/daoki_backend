package com.daoki.basic.VO.response;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(value = "the quoted topic")
public class QuoteTopicVO {

    private String title;

    private String link;

    private String topicId;

    private String score;

}
