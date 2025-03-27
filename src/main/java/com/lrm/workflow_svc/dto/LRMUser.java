package com.lrm.workflow_svc.dto;

import com.lrm.workflow_svc.enums.LRMRole;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class LRMUser {
    private String soeId;
    private LRMRole role;
    private List<String> VWLRMClusterCds;
    private List<String> VWLRMLegalEntityCds;

    public LRMUser(String soeId, LRMRole role, List<String> VWLRMClusterCds, List<String> VWLRMLegalEntityCds) {
        this.soeId = soeId;
        this.role = role;
        this.VWLRMClusterCds = VWLRMClusterCds;
        this.VWLRMLegalEntityCds = VWLRMLegalEntityCds;
    }
}
