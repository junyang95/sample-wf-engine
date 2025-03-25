package com.lrm.workflow_svc.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lrm.workflow_svc.enums.TransitionAction;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LRMTaskParams extends LRMProcessParams{

    @JsonProperty(value = "operator")
    private String operator;

    @JsonProperty(value = "action")
    private TransitionAction action;    //APPROVE,REJECT,
}
