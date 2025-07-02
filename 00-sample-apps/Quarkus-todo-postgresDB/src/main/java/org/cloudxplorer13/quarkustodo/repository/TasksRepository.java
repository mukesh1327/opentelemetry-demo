package org.cloudxplorer13.quarkustodo.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.cloudxplorer13.quarkustodo.model.entity.Tasks;

@ApplicationScoped
public class TasksRepository implements PanacheRepository<Tasks> {
    public boolean existsByName(String name) {
        return count("name", name) > 0;
    }
}