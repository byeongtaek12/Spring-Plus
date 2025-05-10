package org.example.expert.domain.todo.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.example.expert.domain.todo.entity.QTodo;
import org.example.expert.domain.todo.entity.Todo;

import java.util.Optional;

@RequiredArgsConstructor
public class TodoRepositoryImpl implements TodoQueryDSLRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    QTodo todo = QTodo.todo;


    @Override
    public Optional<Todo> findByIdWithUser(Long todoId) {
        Todo findTodo = queryFactory
                .select(todo)
                .from(todo)
                .join(todo.user)
                .fetchJoin()
                .where(todo.id.eq(todoId))
                .fetchOne();
        return Optional.ofNullable(findTodo);
    }
}
