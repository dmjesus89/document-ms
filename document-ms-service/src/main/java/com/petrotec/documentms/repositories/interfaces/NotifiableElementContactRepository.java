package com.petrotec.documentms.repositories.interfaces;

import javax.annotation.Nullable;

import java.util.List;
import java.util.Optional;

import com.petrotec.documentms.entities.NotifiableElement;
import com.petrotec.documentms.entities.NotifiableElementContact;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;


@Repository
public interface NotifiableElementContactRepository extends JpaRepository<NotifiableElementContact, Long> {

	// Retrieves all ENABLED notifiable elements from notifiable_site. Can be filtered by site CODE and is_enabled
	static final String NOTIFIABLE_SITE_QUERY = "SELECT ne.* FROM notifiable_element AS ne INNER JOIN notifiable n ON n.id = ne.notifiable_id "
			+ "LEFT JOIN notifiable_site ns ON ns.notifiable_code = n.code LEFT JOIN site s ON s.code = ns.site_code WHERE (s.code = :siteCode OR :siteCode IS NULL) AND (n.is_enabled = :enabled OR :enabled IS NULL)";

	// Retrieves all notifiable elements from notifiable_site_group_item. Can be filtered by site CODE and is_enabled
	static final String NOTIFIABLE_SITE_GROUP_ITEM_QUERY = "SELECT ne.* FROM notifiable_site_group_item nsgi "
			+ "LEFT JOIN site_group_item sgi on (sgi.code = nsgi.site_group_item_code) "
			+ "LEFT JOIN site_group sg on (sg.id = sgi.site_group_id) "
			+ "LEFT JOIN site_group_item_site sgis on (sgi.id = sgis.site_group_item_id) "
			+ "LEFT JOIN site s on(s.id = sgis.site_id) "
			+ "INNER JOIN notifiable n on (nsgi.notifiable_code = n.code) "
			+ "INNER JOIN notifiable_element ne ON n.id = ne.notifiable_id "
			+ "LEFT JOIN notifiable_site ns ON ns.notifiable_code = n.code "
			+ "WHERE (s.code = :siteCode OR :siteCode IS NULL) "
			+ "AND ((sg.is_enabled = :enabled AND n.is_enabled = :enabled) OR :enabled IS NULL)";

	// Retrieves all notifiable elements from notifiable_element and is_enabled
	static final String NOTIFIABLE_ELEMENT_QUERY = "SELECT ne.* FROM notifiable_element AS ne INNER JOIN notifiable n ON n.id = ne.notifiable_id "
			+ "INNER JOIN notifiable_entity ON notifiable_entity.notifiable_code = n.code WHERE (n.is_enabled = :enabled OR :enabled IS NULL)";

	// Retrieves all notifiable elements. Can be filter by site CODE and is_enabled
	static final String FIND_ALL_QUERY = NOTIFIABLE_SITE_QUERY + " UNION " + NOTIFIABLE_SITE_GROUP_ITEM_QUERY;

	// Retrivies entity notifiable elements. Can be filter by site CODE and alarmCode and is_enabled
	static final String FIND_ALL_SEVERITY_BELONGING_TO_ENTITY_QUERY = "SELECT nec.code as code, nec.notifiable_method_code as notifiableMethodCode, nec.alarm_severity_code as alarmSeverityCode, nec.contact as contact, nec.name as name, nec.locale_code as localeCode, nec.updated_on AS updatedOn, nec.created_on AS createdOn "
			+ "FROM (SELECT nm.code AS notifiable_method_code, nec.alarm_severity_code, nec.contact, ne.name, ne.locale_code, nec.created_on, nec.updated_on "
			+ "FROM (" + NOTIFIABLE_ELEMENT_QUERY + ") AS ne "
			+ "LEFT JOIN notifiable_element_contact nec ON nec.notifiable_element_id = ne.id "
			+ "LEFT JOIN notifiable_method nm ON nm.id = nec.notifiable_method_id "
			+ ") nec";

	// Retrivies all notifiable elements. Can be filter by site CODE and alarmCode and is_enabled
	static final String FIND_ALL_SEVERITY_QUERY = "SELECT ne.* "
			+ "FROM (" + FIND_ALL_QUERY + ") AS ne "
			+ "LEFT JOIN notifiable_element_contact nec ON nec.notifiable_element_id = ne.id "
			+ "WHERE (nec.alarm_severity_code = :alarmCode OR :alarmCode IS NULL)";

	@Query(value = "select nec.* from notifiable_element_contact nec join notifiable_element ne on(nec.notifiable_element_id = ne.id) join notifiable n on(n.id = ne.notifiable_id) where ne.name = :notifiableElementName and n.code = :notifiableCode and nec.contact = :contact", nativeQuery = true)
	Optional<NotifiableElementContact> findByNameAndNotifiableCodeAndContact(String notifiableElementName,
			String notifiableCode, String contact);

	@Query(value = NOTIFIABLE_ELEMENT_QUERY, nativeQuery = true)
	List<NotifiableElement> findAllBelongingToEntity(@Nullable Boolean enabled);

	@Query(value = FIND_ALL_SEVERITY_QUERY, nativeQuery = true)
	List<NotifiableElement> findAll(@Nullable String siteCode, @Nullable String alarmCode,@Nullable Boolean enabled);
}
