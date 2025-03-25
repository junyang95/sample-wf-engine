package com.lrm.workflow_svc.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LRMProcessParams {
    @JsonProperty(value = "app_name", defaultValue = "CitiControlsLRM")
    private String appName = "CitiControlsLRM";

    @JsonProperty(value = "domain", defaultValue = "LRM")
    private String domain = "LRM";

    @JsonProperty(value = "function_id", defaultValue = "lrm_group")
    private String functionId = "lrm_group";

    @JsonProperty(value = "vw_lrm_cluster")
    private String VWLRMCluster;

    @JsonProperty(value = "vw_lrm_legal_entity")
    private String VWLRMLegalEntity;

    @JsonProperty(value = "vw_lrm_region")
    private String VWLRMRegion;

    @JsonProperty(value = "vw_lrm_process")
    private String VWLRMProcess;

    @JsonProperty(value = "vw_lrm_country")
    private String VWLRMCountry;

    @JsonProperty(value = "assessment_year")
    private String assessmentYear;
}
