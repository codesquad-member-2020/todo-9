package kr.codesquad.todo9.todo.domain.board;

import kr.codesquad.todo9.todo.domain.column.ColumnDTO;
import kr.codesquad.todo9.todo.domain.log.LogDTO;

import java.util.ArrayList;
import java.util.List;

public class BoardDTO {

    private Long id;
    private String name;
    private List<ColumnDTO> columns = new ArrayList<>();
    private List<LogDTO> logs = new ArrayList<>();

    public BoardDTO() {}

    public BoardDTO(Board board, List<ColumnDTO> columns, List<LogDTO> logs) {
        this.id = board.getId();
        this.name = board.getName();
        this.columns = columns;
        this.logs = logs;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ColumnDTO> getColumns() {
        return columns;
    }

    public void setColumns(List<ColumnDTO> columns) {
        this.columns = columns;
    }

    public List<LogDTO> getLogs() {
        return logs;
    }

    public void setLogs(List<LogDTO> logs) {
        this.logs = logs;
    }
}
