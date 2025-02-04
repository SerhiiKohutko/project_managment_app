package org.example.project_managment_app.repository;

import org.example.project_managment_app.entities.Project;
import org.example.project_managment_app.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {

//    List<Project> findByOwner(User user);

//    @Query("SELECT p from Project p join p.team t where t=:user")
//    List<Project> findProjectByTeam(@Param("user") User user);

    List<Project> findByNameContainingAndTeamContains(String partialName, User user);

    List<Project> findByTeamContainingOrOwner(User user, User owner);

}
