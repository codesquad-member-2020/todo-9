package kr.codesquad.todo9.todo.service.log;

import kr.codesquad.todo9.common.error.exception.LogNotFoundException;
import kr.codesquad.todo9.todo.domain.log.LogDTO;
import kr.codesquad.todo9.todo.domain.log.LogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogServiceImpl implements LogService {

    private static final Logger log = LoggerFactory.getLogger(LogServiceImpl.class);

    private final LogRepository logRepository;

    public LogServiceImpl(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    @Override
    public LogDTO getLog(Long boardId, int boardKey) {
        List<LogDTO> logs = logRepository.getLogDTOList(boardId);
        if (logs.size() < boardKey) {
            throw new LogNotFoundException();
        }
        return logs.get(boardKey - 1);
    }

    @Override
    public List<LogDTO> getLogList(Long boardId) {
        List<LogDTO> logs = logRepository.getLogDTOList(boardId);
        log.debug("logs: {}", logs);
        return logs;
    }

}
