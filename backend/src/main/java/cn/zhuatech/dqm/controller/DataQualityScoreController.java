/* Copyright 2026 上海如静知华信息科技有限公司 */
package cn.zhuatech.dqm.controller;

import cn.zhuatech.dqm.common.ApiResponse;
import cn.zhuatech.dqm.service.DataQualityScoreService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class DataQualityScoreController {
    private final DataQualityScoreService service;
    public DataQualityScoreController(DataQualityScoreService service) { this.service = service; }
    @PostMapping("/quality-score")
    public ApiResponse<DataQualityScoreService.Result> calculate(@Valid @RequestBody DataQualityScoreService.Request request) {
        return ApiResponse.ok(service.calculate(request));
    }
}
