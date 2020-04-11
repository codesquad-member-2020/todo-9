package kr.codesquad.todo9.repository;

import kr.codesquad.todo9.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {}
