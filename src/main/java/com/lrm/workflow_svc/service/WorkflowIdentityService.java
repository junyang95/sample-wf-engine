package com.lrm.workflow_svc.service;

import com.lrm.workflow_svc.dto.LRMUser;
import com.lrm.workflow_svc.enums.LRMRole;

import java.util.List;

public interface WorkflowIdentityService {
    /**
     * 根据角色获取用户标识列表
     *
     * @param role 角色名称
     * @return 符合该角色的用户标识集合
     */
    List<LRMUser> getUsersByRole(LRMRole role);

    LRMRole getRoleByUser(String soeId);

    LRMUser getUserBySoeId(String soeId);
}
