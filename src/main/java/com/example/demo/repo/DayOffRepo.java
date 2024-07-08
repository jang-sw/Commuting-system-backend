package com.example.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.DayOffEntity;
import com.example.demo.repo.custom.DayOffRepoCustom;

public interface DayOffRepo extends JpaRepository<DayOffEntity, Long>, DayOffRepoCustom{

	
	@Query(value = ""
			+ "WITH day_ranges AS ( "
			+ "    SELECT  "
			+ "        account_id, "
			+ "        category, "
			+ "        GREATEST(start, DATE_FORMAT(CURDATE(), '%Y-01-01')) AS adjusted_start, "
			+ "        LEAST(end, DATE_FORMAT(CURDATE(), '%Y-12-31')) AS adjusted_end "
			+ "    FROM  "
			+ "        tb_day_off "
			+ "    WHERE  "
			+ "        (start <= DATE_FORMAT(CURDATE(), '%Y-12-31') AND end >= DATE_FORMAT(CURDATE(), '%Y-01-01')) "
			+ "		AND account_id = :accountId "
			+ ") "
			+ "SELECT  "
			+ "    SUM( "
			+ "        CASE  "
			+ "            WHEN (category LIKE '%오전%' OR category LIKE '%오후%' ) THEN (DATEDIFF(adjusted_end, adjusted_start) + 1) * 0.5 "
			+ "            ELSE DATEDIFF(adjusted_end, adjusted_start) + 1 "
			+ "        END "
			+ "    )  AS days_off_this_year "
			+ "FROM  "
			+ "    day_ranges "
			+ "WHERE account_id = :accountId", nativeQuery=true)
	public Double findUsedByYear(@Param("accountId")Long accountId);
	
	@Query(value = ""
			+ "WITH day_ranges AS ( "
			+ "    SELECT  "
			+ "        account_id, "
			+ "        category, "
			+ "        GREATEST(start, DATE_FORMAT(CURDATE(), '%Y-%m-01')) AS adjusted_start, "
			+ "        LEAST(end, LAST_DAY(CURDATE())) AS adjusted_end "
			+ "    FROM  "
			+ "        tb_day_off "
			+ "    WHERE  "
			+ "        (start <= LAST_DAY(CURDATE()) AND end >= DATE_FORMAT(CURDATE(), '%Y-%m-01')) "
			+ "		AND account_id = :accountId "
			+ ") "
			+ "SELECT  "
			+ "    SUM( "
			+ "        CASE  "
			+ "            WHEN (category LIKE '%오전%' OR category LIKE '%오후%' ) THEN (DATEDIFF(adjusted_end, adjusted_start) + 1) * 0.5 "
			+ "            ELSE DATEDIFF(adjusted_end, adjusted_start) + 1 "
			+ "        END "
			+ "    ) AS days_off_this_month "
			+ "FROM  "
			+ "    day_ranges "
			+ "WHERE account_id = :accountId", nativeQuery=true)
	public Double findUsedByMonth(@Param("accountId")Long accountId);
}
