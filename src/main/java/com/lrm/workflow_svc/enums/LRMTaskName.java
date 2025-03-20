package com.lrm.workflow_svc.enums;

public enum LRMTaskName {
    REVIEW_INITIATED(NodeType.START_NODE),
    PENDING_ENTITY_RISK_MGR_REVIEW(NodeType.HUMAN_TASK_NODE),
    PENDING_QR_REVIEW(NodeType.HUMAN_TASK_NODE),
    PENDING_REGIONAL_RISK_HEAD_REVIEW(NodeType.HUMAN_TASK_NODE),
    PENDING_GLRM_HEAD_REVIEW(NodeType.HUMAN_TASK_NODE),
    REVIEW_COMPLETED(NodeType.END_NODE);

    private final NodeType nodeType;

    LRMTaskName(NodeType nodeType) {
        this.nodeType = nodeType;
    }

    public NodeType getNodeType() {
        return nodeType;
    }
}
