package kr.codesquad.todo9.domain;

import java.time.ZoneId;
import java.time.ZonedDateTime;

public class Log {
    private Long id;
    private String action;
    private Long beforeCard;
    private Long afterCard;
    private Long fromColumn;
    private Long toColumn;
    private ZonedDateTime actionedAt;
    private Long boardId;
    private Long userId;

    public Log(Long id, String action,
               Long beforeCard,
               Long afterCard,
               Long fromColumn,
               Long toColumn,
               Long boardId,
               Long userId) {
        this.id = id;
        this.action = action;
        this.beforeCard = beforeCard;
        this.afterCard = afterCard;
        this.fromColumn = fromColumn;
        this.toColumn = toColumn;
        this.actionedAt = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
        this.boardId = boardId;
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public String getAction() {
        return action;
    }

    public Long getBeforeCard() {
        return beforeCard;
    }

    public Long getAfterCard() {
        return afterCard;
    }

    public Long getFromColumn() {
        return fromColumn;
    }

    public Long getToColumn() {
        return toColumn;
    }

    public ZonedDateTime getActionedAt() {
        return actionedAt;
    }

    public Long getBoardId() {
        return boardId;
    }
}
