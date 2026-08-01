# Dqm 架构

版权所有 © 2026 上海如静知华信息科技有限公司。

浏览器通过 Vue 管理端或数据质量专员端访问 Spring Boot REST API。安全层完成 JWT 与角色鉴权，业务层负责质量任务、商品、规则目录、核验和结果记录，JPA/Flyway 管理 MySQL 数据。

管理端角色为 `DOMAIN_OPERATOR`、`QUALITY`、`ADMIN`；执行端角色为 `DOMAIN_USER`。正式部署建议将规则目录连接置于独立采集服务，并隔离数据责任域网络和办公网络。
