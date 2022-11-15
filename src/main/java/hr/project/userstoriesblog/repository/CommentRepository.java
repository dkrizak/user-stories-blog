package hr.project.userstoriesblog.repository;

import hr.project.userstoriesblog.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Modifying()
    @Query("UPDATE Comment c SET c.text = :text WHERE c.id = :commentId")
    public void updateComment(@Param("commentId") long id, @Param("text") String text);

}
