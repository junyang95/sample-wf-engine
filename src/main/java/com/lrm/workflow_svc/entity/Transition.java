package com.lrm.workflow_svc.entity;

import com.lrm.workflow_svc.entity.converter.LRMRoleListConverter;
import com.lrm.workflow_svc.enums.LRMRole;
import com.lrm.workflow_svc.enums.TransitionAction;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;
import java.util.List;


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
    private TransitionAction action;//可以理解为我们的 action 字段相当于 jBPM 中 transition 的 decision name 或 outcome 属性，用来标识具体的流转分支

    @Column(name = "ALLOWED_ROLES", nullable = true)
    @Convert(converter = LRMRoleListConverter.class)
    private List<LRMRole> allowedRoles;
    // jbpm 里的 conditionExpression
    //       <conditionExpression xsi:type="tFormalExpression">
    //       <![CDATA[#{decision == 'reject' && currentUser == 'admin'}]]>
    //       </conditionExpression>
    // #{ conditionEvaluator.evaluate(myInput) }
    @Column(name = "CONDITION_EXPRESSION", nullable = true)
    private String conditionExpression;

    @CreatedDate
    @Column(name = "CREATED_AT", nullable = true)
    private Date createdAt;

    @LastModifiedDate
    @Column(name = "UPDATED_AT", nullable = true)
    private Date updatedAt;
}
