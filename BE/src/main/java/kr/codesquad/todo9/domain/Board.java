package kr.codesquad.todo9.domain;

import java.util.List;

public class Board {
    private Long id;
    private String name;
    private List<Column> columns;
    private List<Log> logs;

    public Board(long id, String name) {
        this.id = id;
        this.name = name;
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

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }

    public void setLogs(List<Log> logs) {
        this.logs = logs;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public List<Log> getLogs() {
        return logs;
    }
}
