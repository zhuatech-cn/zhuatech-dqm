/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.dqm.workbench;

import static cn.zhuatech.dqm.workbench.WorkbenchInput.*;

import java.math.*;
import java.util.*;
import org.springframework.stereotype.Service;

/** 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
@Service
public class AiWorkbenchService {
  /** 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
  public Map<String, Object> execute(Map<String, Object> r) {
    validate(r);

    keys(r, "alpha", "baseline", "current");
    double alpha = decimal(r, "alpha", 0.000001, 100).doubleValue();
    Map<String, Long> before = new TreeMap<>(), after = new TreeMap<>();
    long totalBefore = 0, totalAfter = 0;
    for (String side : List.of("baseline", "current")) {
      Map<String, Long> target = side.equals("baseline") ? before : after;
      for (var row : rows(r, side, 1, 200)) {
        keys(row, "id", "count");
        String id = id(row, "id");
        long count = integer(row, "count", 0, 1000000000);
        if (target.putIfAbsent(id, count) != null) throw new IllegalArgumentException("分类编号重复");
        if (side.equals("baseline")) totalBefore += count;
        else totalAfter += count;
      }
    }
    if (totalBefore == 0 || totalAfter == 0) throw new IllegalArgumentException("两份分布计数合计都必须为正");
    Set<String> categories = new TreeSet<>(before.keySet());
    categories.addAll(after.keySet());
    double psi = 0;
    List<Map<String, Object>> out = new ArrayList<>();
    for (String id : categories) {
      long a = before.getOrDefault(id, 0L), b = after.getOrDefault(id, 0L);
      double p = (a + alpha) / (totalBefore + alpha * categories.size()),
          q = (b + alpha) / (totalAfter + alpha * categories.size()),
          contribution = (q - p) * Math.log(q / p);
      psi += contribution;
      out.add(
          Map.of(
              "id",
              id,
              "baselineCount",
              a,
              "currentCount",
              b,
              "baselineShare",
              round(p),
              "currentShare",
              round(q),
              "psiContribution",
              round(contribution),
              "newCategory",
              !before.containsKey(id),
              "vanishedCategory",
              !after.containsKey(id)));
    }
    return Map.of(
        "psi",
        round(psi),
        "categories",
        out,
        "smoothingAlpha",
        round(alpha),
        "automaticPipelineBlock",
        false);
  }
}
