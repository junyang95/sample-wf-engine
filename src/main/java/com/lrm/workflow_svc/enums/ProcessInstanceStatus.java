package com.lrm.workflow_svc.enums;

public enum ProcessInstanceStatus {
    READY_TO_START,// 对应jbpm里的ProcessInstancelog表中的status字段 0
    STARTED,// 对应jbpm里的ProcessInstancelog表中的status字段 1
    COMPLETED,// 对应jbpm里的ProcessInstancelog表中的status字段 2
    ABORTED,// 对应jbpm里的ProcessInstancelog表中的status字段 3
}
