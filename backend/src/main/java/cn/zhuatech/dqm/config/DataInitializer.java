/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.dqm.config;

import cn.zhuatech.dqm.model.*;
import cn.zhuatech.dqm.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.List;

@Configuration
public class DataInitializer {
    @Bean
    CommandLineRunner seed(OperatingUnitRepository operatingUnits, WorkRecordRepository orders,
                           ResourceRegisterRepository resources, ReviewRecordRepository reviewRecords,
                           UserRepository users, PasswordEncoder encoder) {
        return args -> {
            if (operatingUnits.count() > 0) return;
            OperatingUnit primaryUnit = operatingUnits.save(new OperatingUnit("DQM-CHEM", "客户数据域", "质量运营中心", 180));
            OperatingUnit secondaryUnit = operatingUnits.save(new OperatingUnit("DQM-MICRO", "产品数据域", "研发中心", 120));
            OperatingUnit tertiaryUnit = operatingUnits.save(new OperatingUnit("DQM-MAT", "交易数据域", "工程中心", 96));

            WorkRecord t1 = orders.save(new WorkRecord("DQ-260801-018", "CUS-ID-UNI", "客户证件号码唯一性整改", tertiaryUnit, 24, 16, 1, LocalDate.now().plusDays(1), WorkRecord.Status.RUNNING, "GW-Q3"));
            WorkRecord t2 = orders.save(new WorkRecord("DQ-260801-021", "SUP-BANK-CMP", "供应商银行账号完整性校验", primaryUnit, 18, 8, 0, LocalDate.now().plusDays(1), WorkRecord.Status.RUNNING, "TERM-12"));
            WorkRecord t3 = orders.save(new WorkRecord("DQ-260802-006", "PRD-CAT-STD", "商品分类编码标准化", secondaryUnit, 12, 0, 0, LocalDate.now().plusDays(3), WorkRecord.Status.RELEASED, "SP-2026"));
            WorkRecord t4 = orders.save(new WorkRecord("DQ-260728-015", "ORG-HIER", "组织层级有效性核验", primaryUnit, 20, 20, 1, LocalDate.now(), WorkRecord.Status.COMPLETED, "SEA-09"));

            resources.saveAll(List.of(
                new ResourceRegister("CAT-HPLC-03", "批量规则引擎", primaryUnit, ResourceRegister.Status.RUNNING, 88),
                new ResourceRegister("CAT-ICP-02", "实时质量探针", primaryUnit, ResourceRegister.Status.IDLE, 76),
                new ResourceRegister("CAT-UTM-05", "元数据血缘服务", tertiaryUnit, ResourceRegister.Status.RUNNING, 91),
                new ResourceRegister("CAT-INC-08", "质量规则中心", secondaryUnit, ResourceRegister.Status.ALARM, 62)
            ));
            reviewRecords.saveAll(List.of(
                new ReviewRecord("ISS-260801-032", t1, "异常样本验证", 6, 0, ReviewRecord.Result.PASSED, "陈序"),
                new ReviewRecord("ISS-260801-011", t2, "规则执行复核", 3, 0, ReviewRecord.Result.PASSED, "顾遥"),
                new ReviewRecord("ISS-260801-018", t4, "整改关闭复核", 5, 1, ReviewRecord.Result.FAILED, "陈序"),
                new ReviewRecord("ISS-260802-003", t3, "责任归属确认", 4, 0, ReviewRecord.Result.PENDING, "顾遥")
            ));
            String demo = encoder.encode("Demo@2026");
            users.saveAll(List.of(
                new UserAccount("operator", demo, "顾遥", UserAccount.Role.DOMAIN_USER, "DQM-CHEM"),
                new UserAccount("planner", demo, "陈序", UserAccount.Role.DOMAIN_OPERATOR, null),
                new UserAccount("quality", demo, "顾清", UserAccount.Role.QUALITY, null),
                new UserAccount("admin", encoder.encode("ZhuaTech@2026"), "系统管理员", UserAccount.Role.ADMIN, null)
            ));
        };
    }
}
