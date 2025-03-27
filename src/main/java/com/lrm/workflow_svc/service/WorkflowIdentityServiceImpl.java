package com.lrm.workflow_svc.service;

import com.lrm.workflow_svc.dto.LRMUser;
import com.lrm.workflow_svc.enums.LRMRole;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WorkflowIdentityServiceImpl implements WorkflowIdentityService {
    private Map<String, LRMUser> map = new HashMap<>();

    @PostConstruct
    public void init() {
        map.put("USER_ADMIN_1", new LRMUser("USER_ADMIN_1", LRMRole.ADMIN, List.of(), List.of()));
        map.put("USER_ADMIN_2", new LRMUser("USER_ADMIN_2", LRMRole.ADMIN, List.of(), List.of()));
        map.put("USER_GLRM_TESTER_1", new LRMUser("USER_GLRM_TESTER_1", LRMRole.GLRM_TESTER, List.of(), List.of("HK", "CN", "IN")));
        map.put("USER_GLRM_TESTER_2", new LRMUser("USER_GLRM_TESTER_2", LRMRole.GLRM_TESTER, List.of(), List.of("HK")));
        map.put("USER_ERM_1", new LRMUser("USER_ERM_1", LRMRole.ENTITY_RISK_MANAGER, List.of(), List.of("HK", "CN", "IN")));
        map.put("USER_ERM_2", new LRMUser("USER_ERM_2", LRMRole.ENTITY_RISK_MANAGER, List.of(), List.of("HK")));
        map.put("USER_QR_1", new LRMUser("USER_QR_1", LRMRole.QUALITY_REVIEWER, List.of(), List.of("HK", "CN", "IN")));
        map.put("USER_QR_2", new LRMUser("USER_QR_2", LRMRole.QUALITY_REVIEWER, List.of(), List.of("HK")));
        map.put("USER_RRH_1", new LRMUser("USER_RRH_1", LRMRole.REGIONAL_RISK_HEAD, List.of("ASIA_S", "ASIA_N"), List.of()));
        map.put("USER_RRH_2", new LRMUser("USER_RRH_2", LRMRole.REGIONAL_RISK_HEAD, List.of("NAM", "UK"), List.of()));
        map.put("USER_GLRM_HEAD_1", new LRMUser("USER_GLRM_HEAD_1", LRMRole.GLRM_HEAD, List.of(), List.of()));
        map.put("USER_GLRM_HEAD_2", new LRMUser("USER_GLRM_HEAD_2", LRMRole.GLRM_HEAD, List.of(), List.of()));
        map.put("READ_ONLY_1", new LRMUser("READ_ONLY_1", LRMRole.READ_ONLY, List.of(), List.of()));
    }


    @Override
    public List<LRMUser> getUsersByRole(LRMRole role) {
        List<LRMUser> users = new ArrayList<>();
        for (LRMUser user : this.map.values()) {
            if (user.getRole() == role) {
                users.add(user);
            }
        }
        return users;
    }

    @Override
    public LRMRole getRoleByUser(String soeId) {
        return this.map.get(soeId).getRole();
    }

    @Override
    public LRMUser getUserBySoeId(String soeId) {
        return this.map.get(soeId);
    }
}
