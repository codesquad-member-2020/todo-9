package kr.codesquad.todo9.todo.service.log;

import kr.codesquad.todo9.todo.domain.log.LogDTO;

import java.util.List;

public interface LogService {

    LogDTO getLog(Long boardId, int boardKey);

    List<LogDTO> getLogList(Long boardId);

}
