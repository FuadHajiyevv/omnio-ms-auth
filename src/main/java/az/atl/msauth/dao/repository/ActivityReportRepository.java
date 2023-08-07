package az.atl.msauth.dao.repository;

import az.atl.msauth.dao.entity.ActivityReportEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityReportRepository extends JpaRepository<ActivityReportEntity, Long> {
}
