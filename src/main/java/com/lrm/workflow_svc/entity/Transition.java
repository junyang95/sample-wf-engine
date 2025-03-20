package com.lrm.workflow_svc.entity;

import com.lrm.workflow_svc.enums.TransitionAction;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;


@Entity
@Table(name = "transition", schema = "PUBLIC")
@Getter
@Setter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Transition {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transition_seq")
    @SequenceGenerator(name = "transition_seq", sequenceName = "transition_seq", allocationSize = 1)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "PROCESS_DEFINITION_ID_FK", referencedColumnName = "id")
    private ProcessDefinition processDefinition;


    // 来源节点
    @ManyToOne
    @JoinColumn(name = "FROM_TASK_DEFINITION_ID_FK", referencedColumnName = "id")
    private TaskDefinition fromTaskDefinition;

    // 目标节点
    @ManyToOne
    @JoinColumn(name = "TO_TASK_DEFINITION_ID_FK", referencedColumnName = "id")
    private TaskDefinition toTaskDefinition;

    // 操作类型，如 submit、rework
    @Column(name = "ACTION", nullable = true)
    @Enumerated(EnumType.STRING)
    private TransitionAction action;
    // 可选条件表达式
    // 用于条件判断，如 ${amount > 1000}
    // 之后可以用表达式引擎解析 或者 反射调用
    @Column(name = "CONDITION", nullable = true)
    private String condition;

    @CreatedDate
    @Column(name = "CREATED_AT", nullable = true)
    private Date createdAt;

    @LastModifiedDate
    @Column(name = "UPDATED_AT", nullable = true)
    private Date updatedAt;
}
