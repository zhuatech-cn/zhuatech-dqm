/* Copyright 2026 上海如静知华信息科技有限公司 */
package cn.zhuatech.dqm.service;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DataQualityScoreService {
    public Result calculate(Request request) {
        int score = Math.max(0, (int) Math.round(request.completeness() * .30 + request.accuracy() * .30
            + request.consistency() * .20 + request.timeliness() * .20 - request.criticalRuleFailures() * 8));
        String grade = score >= 90 ? "EXCELLENT" : score >= 75 ? "GOOD" : score >= 60 ? "WATCH" : "POOR";
        List<String> actions = new ArrayList<>();
        if (request.completeness() < 90) actions.add("补齐必填字段并修复采集入口校验");
        if (request.accuracy() < 90) actions.add("对异常值执行源系统核验");
        if (request.consistency() < 90) actions.add("统一跨系统编码和口径");
        if (request.timeliness() < 90) actions.add("缩短数据同步与刷新周期");
        if (request.criticalRuleFailures() > 0) actions.add("优先关闭关键质量规则失败项");
        return new Result(request.datasetName(), score, grade, score < 60, actions);
    }

    public record Request(@NotBlank String datasetName,
                          @DecimalMin("0") @DecimalMax("100") double completeness,
                          @DecimalMin("0") @DecimalMax("100") double accuracy,
                          @DecimalMin("0") @DecimalMax("100") double consistency,
                          @DecimalMin("0") @DecimalMax("100") double timeliness,
                          @Min(0) int criticalRuleFailures) {}
    public record Result(String datasetName, int qualityScore, String grade,
                         boolean releaseBlocked, List<String> actions) {}
}
