package com.lrm.workflow_svc.entity;


import com.lrm.workflow_svc.entity.converter.StringArrayConverter;
import com.lrm.workflow_svc.entity.converter.StringListConverter;
import com.lrm.workflow_svc.enums.TaskInstanceStatus;
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
@Table(name = "task_instance", schema = "PUBLIC")
@Getter
@Setter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class TaskInstance {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "task_instance_seq")
    @SequenceGenerator(name = "task_instance_seq", sequenceName = "task_instance_seq", allocationSize = 1)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "PROCESS_DEFINITION_ID_FK", referencedColumnName = "id")
    private ProcessInstance processInstance;

    @ManyToOne
    @JoinColumn(name = "TASK_DEFINITION_ID_FK", referencedColumnName = "id")
    private TaskDefinition taskDefinition;

    @Column(name = "NOMINATE_ASSIGNEES", nullable = true)
    @Convert(converter = StringListConverter.class)
    private List<String> nominateAssignees;  // soeid of [jw94700,vk47420]

    @Column(name = "ACTUAL_OWNER_ID", nullable = true)
    private String actualOwnerId; // soeid of jw94700, who completed the task

    @Column(name = "STATUS", nullable = true)
    @Enumerated(EnumType.STRING)
    private TaskInstanceStatus status;  // CREATED, READY, IN_PROGRESS, COMPLETED, EXITED, RESERVED, 有点像JBPM里NODEINSTANCELOG.TYPE

    @Column(name = "STARTED_AT", nullable = true)
    private Date startedAt;

    @Column(name = "COMPLETED_AT", nullable = true)
    private Date completedAt;

    @Column(name = "COMMENTS", nullable = true)
    private String comments;

    @CreatedDate
    @Column(name = "CREATED_AT", nullable = true)
    private Date createdAt;

    @LastModifiedDate
    @Column(name = "UPDATED_AT", nullable = true)
    private Date updatedAt;
}
