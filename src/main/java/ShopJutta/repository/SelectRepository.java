package ShopJutta.repository;

import ShopJutta.entity.Select;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.awt.print.Pageable;
import java.util.List;

public interface SelectRepository extends JpaRepository<Select, Long> {
    @Query("select s from Select s " +
        "where s.member.email = :email" +
        "select by s.selectDate desc"
    )
    List<Select> findSelect(@Param("email") String email, Pageable pageable);

    @Query("select count(s) from Select s" +
        "where s.member.email = :email")

    Long countSelect(@Param("email") String email);
}