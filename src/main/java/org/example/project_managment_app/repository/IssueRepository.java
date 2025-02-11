package org.example.project_managment_app.repository;

import org.example.project_managment_app.entities.Issue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IssueRepository extends JpaRepository<Issue, Long> {
    public List<Issue> findByProjectId(Long projectId);
}
