package com.lrm.workflow_svc.repo;

import com.lrm.workflow_svc.entity.ProcessDefinition;
import com.lrm.workflow_svc.entity.ProcessInstance;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProcessInstanceRepository extends CrudRepository<ProcessInstance, Long> {
}
