package kr.codesquad.todo9.repository;

import kr.codesquad.todo9.dto.CardDTO;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CardRepository extends CrudRepository<CardDTO, Long> {

    @Query("SELECT card.id AS id," +
            " card.column AS `column`," +
            " card.contents AS contents," +
            " card.created_at AS created_at," +
            " card.updated_at AS updated_at," +
            " card.column_key AS column_key," +
            " card.archived_at AS archived_at," +
            " card.is_archived AS is_archived," +
            " createdUser.id AS createdUser_id," +
            " updatedUser.id AS updatedUser_id," +
            " updatedUser.username AS updatedUser_username," +
            " createdUser.username AS createdUser_username," +
            " updatedUser.profile_image_url AS updatedUser_profile_image_url," +
            " createdUser.profile_image_url AS createdUser_profile_image_url" +
            " FROM card" +
            " LEFT OUTER JOIN user AS updatedUser ON updatedUser.id = card.upd_user_id" +
            " LEFT OUTER JOIN user AS createdUser ON createdUser.id = card.crt_user_id" +
            " WHERE card.column = :columnId" +
            " ORDER BY card.column_key DESC")
    List<CardDTO> getCardList(Long columnId);
}
