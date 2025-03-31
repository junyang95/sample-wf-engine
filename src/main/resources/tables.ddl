create sequence PUBLIC.PROCESS_DEFINITION_SEQ;
create sequence PUBLIC.PROCESS_INSTANCE_SEQ;
create sequence PUBLIC.TASK_DEFINITION_SEQ;
create sequence PUBLIC.TASK_INSTANCE_SEQ;
create sequence PUBLIC.TRANSITION_SEQ;


CREATE CACHED TABLE "PUBLIC"."PROCESS_DEFINITION"(
    "VERSION" INTEGER,
    "CREATED_AT" TIMESTAMP(6),
    "ID" BIGINT NOT NULL,
    "UPDATED_AT" TIMESTAMP(6),
    "DESCRIPTION" CHARACTER VARYING(255),
    "NAME" CHARACTER VARYING(255)
)

CREATE CACHED TABLE "PUBLIC"."TASK_DEFINITION"(
    "CREATED_AT" TIMESTAMP(6),
    "ID" BIGINT NOT NULL,
    "PROCESS_DEFINITION_ID_FK" BIGINT,
    "UPDATED_AT" TIMESTAMP(6),
    "ALLOWED_ROLES" CHARACTER VARYING(255),
    "RESOURCE_ASSIGNMENT_EXPRESSION" CHARACTER VARYING(255),
    "NAME" ENUM('PENDING_ENTITY_RISK_MGR_REVIEW', 'PENDING_GLRM_HEAD_REVIEW', 'PENDING_QR_REVIEW', 'PENDING_REGIONAL_RISK_HEAD_REVIEW', 'REVIEW_COMPLETED', 'REVIEW_INITIATED'),
    "NODE_TYPE" ENUM('END_NODE', 'HUMAN_TASK_NODE', 'JOIN', 'SPLIT', 'START_NODE')
)

 CREATE CACHED TABLE "PUBLIC"."TASK_DEFINITION"(
    "CREATED_AT" TIMESTAMP(6),
    "ID" BIGINT NOT NULL,
    "PROCESS_DEFINITION_ID_FK" BIGINT,
    "UPDATED_AT" TIMESTAMP(6),
    "ALLOWED_ROLES" CHARACTER VARYING(255),
    "RESOURCE_ASSIGNMENT_EXPRESSION" CHARACTER VARYING(255),
    "NAME" ENUM('PENDING_ENTITY_RISK_MGR_REVIEW', 'PENDING_GLRM_HEAD_REVIEW', 'PENDING_QR_REVIEW', 'PENDING_REGIONAL_RISK_HEAD_REVIEW', 'REVIEW_COMPLETED', 'REVIEW_INITIATED'),
    "NODE_TYPE" ENUM('END_NODE', 'HUMAN_TASK_NODE', 'JOIN', 'SPLIT', 'START_NODE')
)

CREATE CACHED TABLE "PUBLIC"."TASK_INSTANCE"(
    "COMPLETED_AT" TIMESTAMP(6),
    "CREATED_AT" TIMESTAMP(6),
    "ID" BIGINT NOT NULL,
    "PROCESS_DEFINITION_ID_FK" BIGINT,
    "STARTED_AT" TIMESTAMP(6),
    "TASK_DEFINITION_ID_FK" BIGINT,
    "UPDATED_AT" TIMESTAMP(6),
    "ACTUAL_OWNER_ID" CHARACTER VARYING(255),
    "COMMENTS" CHARACTER VARYING(255),
    "NOMINATE_ASSIGNEES" CHARACTER VARYING(255),
    "STATUS" ENUM('COMPLETED', 'CREATED', 'EXITED', 'IN_PROGRESS', 'READY', 'RESERVED')
)


CREATE CACHED TABLE "PUBLIC"."TRANSITION"(
    "CREATED_AT" TIMESTAMP(6),
    "FROM_TASK_DEFINITION_ID_FK" BIGINT,
    "ID" BIGINT NOT NULL,
    "PROCESS_DEFINITION_ID_FK" BIGINT,
    "TO_TASK_DEFINITION_ID_FK" BIGINT,
    "UPDATED_AT" TIMESTAMP(6),
    "ALLOWED_ROLES" CHARACTER VARYING(255),
    "CONDITION_EXPRESSION" CHARACTER VARYING(255),
    "ACTION" ENUM('APPROVE', 'REJECT')
)

