package com.lrm.workflow_svc.enums;

// 这个类似于jbpm里的Task表中的status字段
public enum TaskInstanceStatus {
    CREATED, READY, IN_PROGRESS, COMPLETED, EXITED, RESERVED,
}
