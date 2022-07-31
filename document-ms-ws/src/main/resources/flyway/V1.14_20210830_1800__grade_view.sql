create function varchar_param_p1() returns VARCHAR(50) NO SQL return @var_char_p1;


CREATE VIEW site_grade_view AS
	(
	SELECT
    		grade.code,
    		translate(grade.description,varchar_param_p1()) as description,
    		grade.color,
    		grade.unit_price_net,
    		grade.is_enabled as `enabled`,
    		grade.created_on,
    		grade.updated_on,
    		product.code AS `product.code`,
    		'' AS `product.color`,
    		translate(product.description,varchar_param_p1()) AS `product.description`,
    		translate(product.short_description,varchar_param_p1()) AS `product.short_description`,
    		grade_product.product_percentage AS `product.percentage`,
    		product_group.code AS `product_group.code`,
    		translate(product_group.description,varchar_param_p1()) AS `product_group.description`,
    		'' AS `product_group.color`
    	FROM grade
    	LEFT JOIN grade_product ON
    		grade_product.grade_id = grade.id AND
    		grade_product.id = (SELECT (grade_product.id) FROM grade_product WHERE grade_product.grade_id = grade.id ORDER BY grade_product.product_percentage  DESC LIMIT 1)
    	LEFT JOIN product ON
    		product.id = grade_product.product_id
    	LEFT JOIN product_group ON
    		product.product_group_id = product_group.id
    );