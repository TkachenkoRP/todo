package com.my.todo.repository.specification;

import com.my.todo.dto.TaskFilter;
import com.my.todo.model.Task;
import com.my.todo.model.TaskPriority;
import com.my.todo.model.TaskStatus;
import com.my.todo.model.User;
import org.springframework.data.jpa.domain.Specification;

public interface TaskSpecification {
    static Specification<Task> withFilter(TaskFilter filter) {
        return Specification.where(byId(filter.id()))
                .and(byTitle(filter.title()))
                .and(byStatus(filter.status()))
                .and(byPriority(filter.priority()))
                .and(byAuthor(filter.authorId()))
                .and(byPerformer(filter.performerId()));
    }

    static Specification<Task> byId(Long id) {
        return (root, query, criteriaBuilder) -> {
            if (id == null) {
                return null;
            }
            return criteriaBuilder.equal(root.get(Task.Fields.id), id);
        };
    }

    static Specification<Task> byTitle(String title) {
        return (root, query, criteriaBuilder) -> {
            if (title == null) {
                return null;
            }
            return criteriaBuilder.like(
                    criteriaBuilder.lower(root.get(Task.Fields.title)),
                    "%" + title.toLowerCase() + "%"
            );
        };
    }

    static Specification<Task> byStatus(String status) {
        return (root, query, criteriaBuilder) -> {
            if (status == null) {
                return null;
            }
            return criteriaBuilder.equal(root.get(Task.Fields.status), Enum.valueOf(TaskStatus.class, status));
        };
    }

    static Specification<Task> byPriority(String priority) {
        return (root, query, criteriaBuilder) -> {
            if (priority == null) {
                return null;
            }
            return criteriaBuilder.equal(root.get(Task.Fields.priority), Enum.valueOf(TaskPriority.class, priority));
        };
    }

    static Specification<Task> byAuthor(Long authorId) {
        return (root, query, criteriaBuilder) -> {
            if (authorId == null) {
                return null;
            }
            return criteriaBuilder.equal(root.get(Task.Fields.author).get(User.Fields.id), authorId);
        };
    }

    static Specification<Task> byPerformer(Long performerId) {
        return (root, query, criteriaBuilder) -> {
            if (performerId == null) {
                return null;
            }
            return criteriaBuilder.equal(root.get(Task.Fields.performer).get(User.Fields.id), performerId);
        };
    }
}
