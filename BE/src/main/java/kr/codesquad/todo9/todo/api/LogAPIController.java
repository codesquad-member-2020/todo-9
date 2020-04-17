package kr.codesquad.todo9.todo.api;

import kr.codesquad.todo9.todo.domain.log.LogDTO;
import kr.codesquad.todo9.todo.service.log.LogService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LogAPIController {

    private static final Long defaultBoardId = 1L;

    private final LogService logService;

    public LogAPIController(LogService logService) {this.logService = logService;}

    @GetMapping("/board/{boardId}/log/{boardKey}")
    public LogDTO showLog(@PathVariable Long boardId, @PathVariable int boardKey) {
        return logService.getLog(boardId, boardKey);
    }

    @GetMapping("/log/{boardKey}")
    public LogDTO showLog(@PathVariable int boardKey) {
        return showLog(defaultBoardId, boardKey);
    }

    @GetMapping("/board/{boardId}/log/list")
    public List<LogDTO> showLogList(@PathVariable Long boardId) {
        return logService.getLogList(boardId);
    }

    @GetMapping("/log/list")
    public List<LogDTO> showLogList() {
        return showLogList(defaultBoardId);
    }

}
