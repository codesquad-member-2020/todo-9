package kr.codesquad.todo9.todo.api;

import kr.codesquad.todo9.common.error.exception.LogNotFoundException;
import kr.codesquad.todo9.todo.domain.log.LogDTO;
import kr.codesquad.todo9.todo.domain.log.LogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LogAPIController {

    private static final Logger log = LoggerFactory.getLogger(LogAPIController.class);
    private static final Long defaultBoardId = 1L;

    private final LogRepository logRepository;

    public LogAPIController(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    @GetMapping("/board/{boardId}/log/{boardKey}")
    public LogDTO showLog(@PathVariable Long boardId, @PathVariable int boardKey) {
        List<LogDTO> logs = logRepository.getLogDTOList(boardId);
        if (logs.size() < boardKey) {
            throw new LogNotFoundException();
        }
        return logs.get(boardKey - 1);
    }

    @GetMapping("/log/{boardKey}")
    public LogDTO showLog(@PathVariable int boardKey) {
        return showLog(defaultBoardId, boardKey);
    }

    @GetMapping("/board/{boardId}/log/list")
    public List<LogDTO> showLogList(@PathVariable Long boardId) {
        List<LogDTO> logs = logRepository.getLogDTOList(boardId);
        log.debug("logs: {}", logs);
        return logs;
    }

    @GetMapping("/log/list")
    public List<LogDTO> showLogList() {
        return showLogList(defaultBoardId);
    }
}
