package kr.codesquad.todo9.repository;

import kr.codesquad.todo9.domain.Log;
import org.springframework.data.repository.CrudRepository;

public interface LogRepository extends CrudRepository<Log, Long> {}
