package kr.codesquad.todo9.repository;

import kr.codesquad.todo9.domain.Card;
import kr.codesquad.todo9.dto.CardDTO;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

public interface CardRepository extends CrudRepository<Card, Long> {

    @Query("SELECT card.id AS id," + " card.contents AS contents," + " card.created_at AS created_at," + " card.updated_at AS updated_at," + " card.archived_at AS archived_at," + " card.is_archived AS is_archived," + " card.card_order AS card_order," + " card.column_id AS column_id," + " createdUser.id AS createdUser_id," + " updatedUser.id AS updatedUser_id," + " createdUser.username AS createdUser_username," + " updatedUser.username AS updatedUser_username," + " createdUser.profile_image_url AS createdUser_profile_image_url," + " updatedUser.profile_image_url AS updatedUser_profile_image_url" + " FROM card INNER JOIN user AS updatedUser ON updatedUser.id = card.upd_user_id" + " INNER JOIN user AS createdUser ON createdUser.id = card.crt_user_id" + " ORDER BY card.card_order desc")
    Iterable<CardDTO> findAllCards();

    @Query("SELECT COUNT(*) + 1 FROM CARD WHERE COLUMN_ID = :columnId")
    int getNextOrderOfColumn(Long columnId);

}
