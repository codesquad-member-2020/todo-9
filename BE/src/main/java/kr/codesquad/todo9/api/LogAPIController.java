package kr.codesquad.todo9.api;

import kr.codesquad.todo9.domain.Log;
import kr.codesquad.todo9.error.exception.BoardNotFoundException;
import kr.codesquad.todo9.error.exception.LogNotFoundException;
import kr.codesquad.todo9.repository.BoardRepository;
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

    private final BoardRepository boardRepository;

    public LogAPIController(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @GetMapping("/board/{boardId}/log/{boardKey}")
    public Log showLog(@PathVariable Long boardId, @PathVariable int boardKey) {
        List<Log> logs = boardRepository.findById(boardId).orElseThrow(BoardNotFoundException::new).getLogs();
        if (logs.size() < boardKey) {
            throw new LogNotFoundException();
        }
        return logs.get(boardKey - 1);
    }

    @GetMapping("/log/{boardKey}")
    public Log showLog(@PathVariable int boardKey) {
        return showLog(defaultBoardId, boardKey);
    }

    @GetMapping("/board/{boardId}/log/list")
    public List<Log> showLogList(@PathVariable Long boardId) {
        List<Log> logs = boardRepository.findById(boardId).orElseThrow(BoardNotFoundException::new).sortBoard().getLogs();
        log.debug("logs: {}", logs);
        return logs;
    }

    @GetMapping("/log/list")
    public List<Log> showLogList() {
        return showLogList(defaultBoardId);
    }
}
