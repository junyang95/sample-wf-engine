package com.lrm.workflow_svc.entity;


import com.lrm.workflow_svc.entity.converter.StringArrayConverter;
import com.lrm.workflow_svc.enums.TaskInstanceStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

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

    @Column(name = "ASSIGNEES", nullable = true)
    @Convert(converter = StringArrayConverter.class)
    private String[] assignees;  // 当前处理人jw94700,vk47420

    @Column(name = "STATUS", nullable = true)
    @Enumerated(EnumType.STRING)
    private TaskInstanceStatus status;    // PENDING, COMPLETED, REASSIGNED, 等

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
