package com.lrm.workflow_svc.service;

import com.lrm.workflow_svc.enums.LRMRole;
import jakarta.annotation.PostConstruct;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WorkflowIdentityServiceImpl implements WorkflowIdentityService {
    private Map<String, LRMRole> map = new HashMap<>();

    @PostConstruct
    public void init() {
        map.put("USER_ADMIN_1", LRMRole.ADMIN);
        map.put("USER_ADMIN_2", LRMRole.ADMIN);
        map.put("USER_GLRM_TESTER_1", LRMRole.GLRM_TESTER);
        map.put("USER_GLRM_TESTER_2", LRMRole.GLRM_TESTER);
        map.put("USER_ERM_1", LRMRole.ENTITY_RISK_MANAGER);
        map.put("USER_ERM_2", LRMRole.ENTITY_RISK_MANAGER);
        map.put("USER_QR_1", LRMRole.QUALITY_REVIEWER);
        map.put("USER_QR_2", LRMRole.QUALITY_REVIEWER);
        map.put("USER_RRH_1", LRMRole.REGIONAL_RISK_HEAD);
        map.put("USER_RRH_2", LRMRole.REGIONAL_RISK_HEAD);
        map.put("USER_GLRM_HEAD_1", LRMRole.GLRM_HEAD);
        map.put("USER_GLRM_HEAD_2", LRMRole.GLRM_HEAD);
    }


    @Override
    public List<String> getUsersByRole(LRMRole role) {
        List<String> result = new ArrayList<>();
        this.map.forEach((user, lrmRole) -> {
            if (lrmRole == role) {
                result.add(user);
            }
        });
        return result;
    }

    @Override
    public LRMRole getRoleByUser(String user) {
        return this.map.get(user);
    }
}
