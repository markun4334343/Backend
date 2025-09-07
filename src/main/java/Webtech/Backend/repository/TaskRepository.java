package Webtech.Backend.repository;

import Webtech.Backend.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    // Spring Data JPA provides basic CRUD operations automatically
    // findAll(), findById(), save(), deleteById() are available
}
