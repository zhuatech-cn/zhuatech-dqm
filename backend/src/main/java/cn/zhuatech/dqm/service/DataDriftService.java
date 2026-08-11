/* Copyright 2026 上海如静知华信息科技有限公司 */
package cn.zhuatech.dqm.service;
import jakarta.validation.constraints.*;import org.springframework.stereotype.Service;import java.util.*;
@Service public class DataDriftService {
 public Result evaluate(Request r){int score=0;List<String> signals=new ArrayList<>();double volumeDelta=Math.abs(r.currentVolume()-r.baselineVolume())*100.0/r.baselineVolume();double nullDelta=r.currentNullRate()-r.baselineNullRate();if(r.schemaChanged()){score+=70;signals.add("数据结构发生变化");}if(volumeDelta>30){score+=25;signals.add("数据量偏离基线 "+Math.round(volumeDelta)+"%");}if(nullDelta>5){score+=25;signals.add("空值率显著上升");}if(r.distributionShift()>15){score+=35;signals.add("关键字段分布发生漂移");}if(r.freshnessDelayMinutes()>r.freshnessSlaMinutes()){score+=20;signals.add("数据新鲜度超过 SLA");}score=Math.min(100,score);String status=score>=70?"BLOCK_PIPELINE":score>=25?"INVESTIGATE":"STABLE";if(signals.isEmpty())signals.add("数据特征处于基线容差范围");return new Result(score,Math.round(volumeDelta*10)/10.0,Math.round(nullDelta*10)/10.0,status,signals);}
 public record Request(@Min(1) long baselineVolume,@Min(0) long currentVolume,@DecimalMin("0") @DecimalMax("100") double baselineNullRate,@DecimalMin("0") @DecimalMax("100") double currentNullRate,@DecimalMin("0") @DecimalMax("100") double distributionShift,@NotNull Boolean schemaChanged,@Min(0) int freshnessDelayMinutes,@Min(1) int freshnessSlaMinutes){}
 public record Result(int driftScore,double volumeDeltaRate,double nullRateDelta,String status,List<String> signals){}
}
