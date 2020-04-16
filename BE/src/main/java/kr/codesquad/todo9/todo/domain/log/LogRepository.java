package kr.codesquad.todo9.todo.domain.log;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LogRepository extends CrudRepository<Log, Long> {

    @Query("SELECT log.id AS id, " +
                  "log.type AS type, " +
                  "log.action AS action, " +
                  "log.actioned_at AS actioned_at, " +
                  "log.after_card_contents AS after_card_contents, " +
                  "log.before_card_contents AS before_card_contents, " +
                  "user.id AS user_id, " +
                  "user.username AS user_username, " +
                  "user.profile_image_url AS user_profile_image_url, " +
                  "toColumn.id AS toColumn_id, " +
                  "toColumn.name AS toColumn_name, " +
                  "toColumn.board_key AS toColumn_board_key, " +
                  "toColumn.updated_at AS toColumn_updated_at, " +
                  "toColumn.created_at AS toColumn_created_at, " +
                  "toColumn.archived_at AS toColumn_archived_at, " +
                  "toColumn.is_archived AS toColumn_is_archived, " +
                  "toColumn.CRT_USER_ID AS toColumn_CRT_USER_ID, " +
                  "toColumn.UPD_USER_ID AS toColumn_UPD_USER_ID, " +
                  "afterCard.id AS afterCard_id, " +
                  "afterCard.column AS afterCard_column, " +
                  "afterCard.contents AS afterCard_contents, " +
                  "afterCard.updated_at AS afterCard_updated_at, " +
                  "afterCard.column_key AS afterCard_column_key, " +
                  "afterCard.created_at AS afterCard_created_at, " +
                  "afterCard.is_archived AS afterCard_is_archived, " +
                  "afterCard.archived_at AS afterCard_archived_at, " +
                  "afterCard.UPD_USER_ID AS afterCard_UPD_USER_ID, " +
                  "afterCard.CRT_USER_ID AS afterCard_CRT_USER_ID, " +
                  "fromColumn.id AS fromColumn_id, " +
                  "beforeCard.id AS beforeCard_id, " +
                  "fromColumn.name AS fromColumn_name, " +
                  "beforeCard.column AS beforeCard_column, " +
                  "fromColumn.board_key AS fromColumn_board_key, " +
                  "beforeCard.contents AS beforeCard_contents, " +
                  "beforeCard.created_at AS beforeCard_created_at, " +
                  "fromColumn.created_at AS fromColumn_created_at, " +
                  "beforeCard.column_key AS beforeCard_column_key, " +
                  "fromColumn.updated_at AS fromColumn_updated_at, " +
                  "beforeCard.updated_at AS beforeCard_updated_at, " +
                  "beforeCard.archived_at AS beforeCard_archived_at, " +
                  "beforeCard.is_archived AS beforeCard_is_archived, " +
                  "fromColumn.is_archived AS fromColumn_is_archived, " +
                  "fromColumn.archived_at AS fromColumn_archived_at, " +
                  "beforeCard.CRT_USER_ID AS beforeCard_CRT_USER_ID, " +
                  "beforeCard.UPD_USER_ID AS beforeCard_UPD_USER_ID, " +
                  "fromColumn.UPD_USER_ID AS fromColumn_UPD_USER_ID, " +
                  "fromColumn.CRT_USER_ID AS fromColumn_CRT_USER_ID " +
                  "FROM log LEFT OUTER JOIN user AS user ON user.id = log.user_id " +
                  "LEFT OUTER JOIN `column` AS toColumn ON toColumn.id = log.to_column_id " +
                  "LEFT OUTER JOIN card AS afterCard ON afterCard.id = log.after_card_id " +
                  "LEFT OUTER JOIN `column` AS fromColumn ON fromColumn.id = log.from_column_id " +
                  "LEFT OUTER JOIN card AS beforeCard ON beforeCard.id = log.before_card_id " +
                  "WHERE log.board = :boardId " +
                  "ORDER BY log.id DESC")
    List<LogDTO> getLogDTOList(Long boardId);

    @Query("SELECT log.id AS id, " +
            "log.type AS type, " +
            "log.action AS action, " +
            "log.actioned_at AS actioned_at, " +
            "log.after_card_contents AS after_card_contents, " +
            "log.before_card_contents AS before_card_contents, " +
            "user.id AS user_id, " +
            "user.username AS user_username, " +
            "user.profile_image_url AS user_profile_image_url, " +
            "toColumn.id AS toColumn_id, " +
            "toColumn.name AS toColumn_name, " +
            "toColumn.board_key AS toColumn_board_key, " +
            "toColumn.updated_at AS toColumn_updated_at, " +
            "toColumn.created_at AS toColumn_created_at, " +
            "toColumn.archived_at AS toColumn_archived_at, " +
            "toColumn.is_archived AS toColumn_is_archived, " +
            "toColumn.CRT_USER_ID AS toColumn_CRT_USER_ID, " +
            "toColumn.UPD_USER_ID AS toColumn_UPD_USER_ID, " +
            "afterCard.id AS afterCard_id, " +
            "afterCard.column AS afterCard_column, " +
            "afterCard.contents AS afterCard_contents, " +
            "afterCard.updated_at AS afterCard_updated_at, " +
            "afterCard.column_key AS afterCard_column_key, " +
            "afterCard.created_at AS afterCard_created_at, " +
            "afterCard.is_archived AS afterCard_is_archived, " +
            "afterCard.archived_at AS afterCard_archived_at, " +
            "afterCard.UPD_USER_ID AS afterCard_UPD_USER_ID, " +
            "afterCard.CRT_USER_ID AS afterCard_CRT_USER_ID, " +
            "fromColumn.id AS fromColumn_id, " +
            "beforeCard.id AS beforeCard_id, " +
            "fromColumn.name AS fromColumn_name, " +
            "beforeCard.column AS beforeCard_column, " +
            "fromColumn.board_key AS fromColumn_board_key, " +
            "beforeCard.contents AS beforeCard_contents, " +
            "beforeCard.created_at AS beforeCard_created_at, " +
            "fromColumn.created_at AS fromColumn_created_at, " +
            "beforeCard.column_key AS beforeCard_column_key, " +
            "fromColumn.updated_at AS fromColumn_updated_at, " +
            "beforeCard.updated_at AS beforeCard_updated_at, " +
            "beforeCard.archived_at AS beforeCard_archived_at, " +
            "beforeCard.is_archived AS beforeCard_is_archived, " +
            "fromColumn.is_archived AS fromColumn_is_archived, " +
            "fromColumn.archived_at AS fromColumn_archived_at, " +
            "beforeCard.CRT_USER_ID AS beforeCard_CRT_USER_ID, " +
            "beforeCard.UPD_USER_ID AS beforeCard_UPD_USER_ID, " +
            "fromColumn.UPD_USER_ID AS fromColumn_UPD_USER_ID, " +
            "fromColumn.CRT_USER_ID AS fromColumn_CRT_USER_ID " +
            "FROM log LEFT OUTER JOIN user AS user ON user.id = log.user_id " +
            "LEFT OUTER JOIN `column` AS toColumn ON toColumn.id = log.to_column_id " +
            "LEFT OUTER JOIN card AS afterCard ON afterCard.id = log.after_card_id " +
            "LEFT OUTER JOIN `column` AS fromColumn ON fromColumn.id = log.from_column_id " +
            "LEFT OUTER JOIN card AS beforeCard ON beforeCard.id = log.before_card_id " +
            "WHERE log.board = :boardId " +
            "AND log.id = :logId")
    LogDTO getLogDTO(Long boardId, Long logId);

}
