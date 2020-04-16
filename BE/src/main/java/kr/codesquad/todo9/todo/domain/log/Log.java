package kr.codesquad.todo9.todo.domain.log;

import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

public class Log {

    private @Id Long id;
    private String action;
    private String type;
    private String beforeCardContents;
    private String afterCardContents;
    private Long beforeCardId;
    private Long afterCardId;
    private Long fromColumnId;
    private Long toColumnId;
    private LocalDateTime actionedAt;
    private Long userId;

    public Log() {}

    public Log(String action,
               String type,
               String beforeCardContents,
               String afterCardContents,
               Long beforeCardId,
               Long afterCardId,
               Long fromColumnId,
               Long toColumnId,
               LocalDateTime actionedAt,
               Long userId) {
        this.action = action;
        this.type = type;
        this.beforeCardContents = beforeCardContents;
        this.afterCardContents = afterCardContents;
        this.beforeCardId = beforeCardId;
        this.afterCardId = afterCardId;
        this.fromColumnId = fromColumnId;
        this.toColumnId = toColumnId;
        this.actionedAt = actionedAt;
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBeforeCardContents() {
        return beforeCardContents;
    }

    public void setBeforeCardContents(String beforeCardContents) {
        this.beforeCardContents = beforeCardContents;
    }

    public String getAfterCardContents() {
        return afterCardContents;
    }

    public void setAfterCardContents(String afterCardContents) {
        this.afterCardContents = afterCardContents;
    }

    public Long getBeforeCardId() {
        return beforeCardId;
    }

    public void setBeforeCardId(Long beforeCardId) {
        this.beforeCardId = beforeCardId;
    }

    public Long getAfterCardId() {
        return afterCardId;
    }

    public void setAfterCardId(Long afterCardId) {
        this.afterCardId = afterCardId;
    }

    public Long getFromColumnId() {
        return fromColumnId;
    }

    public void setFromColumnId(Long fromColumnId) {
        this.fromColumnId = fromColumnId;
    }

    public Long getToColumnId() {
        return toColumnId;
    }

    public void setToColumnId(Long toColumnId) {
        this.toColumnId = toColumnId;
    }

    public LocalDateTime getActionedAt() {
        return actionedAt;
    }

    public void setActionedAt(LocalDateTime actionedAt) {
        this.actionedAt = actionedAt;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
