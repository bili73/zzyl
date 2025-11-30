-- 检查新增的表是否创建成功
SELECT
    TABLE_NAME,
    TABLE_COMMENT,
    TABLE_ROWS
FROM
    INFORMATION_SCHEMA.TABLES
WHERE
    TABLE_SCHEMA = 'zzyl'
    AND TABLE_NAME IN (
        'user', 'role', 'user_role', 'resource', 'role_resource',
        'dept', 'post', 'reservation', 'nursing_project_plan'
    )
ORDER BY TABLE_NAME;