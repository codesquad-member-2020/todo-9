package kr.codesquad.todo9.repository;

import kr.codesquad.todo9.domain.Board;
import org.springframework.data.repository.CrudRepository;

public interface BoardRepository extends CrudRepository<Board, Long> {}
