package com.petrotec.documentms.repositories;

import com.petrotec.documentms.entities.Site;
import com.petrotec.documentms.entities.SiteGrade;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;
import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;

import java.util.List;
import java.util.Optional;

@Repository
public abstract class SiteGradeRepository implements JpaRepository<SiteGrade, Long> {

    public abstract List<SiteGrade> findAllBySiteCode(String siteCode);

    public abstract List<SiteGrade> findBySiteCode(String siteCode);

    public abstract Page<SiteGrade> findBySiteCode(String siteCode, Pageable pageable);

//    public abstract SiteGrade findBySiteCodeAndGradeCode(String siteCode, String gradeCode);

    public abstract Optional<SiteGrade> findBySiteCodeAndGradeCode(String siteCode, String gradeCode);

    public abstract boolean existsBySiteCodeAndFccGradeCode(String siteCode, Short fccGradeCode);

    public abstract List<SiteGrade> findBySite(Site site);
}
