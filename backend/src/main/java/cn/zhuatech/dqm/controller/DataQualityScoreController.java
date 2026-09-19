/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.dqm.controller;

import cn.zhuatech.dqm.common.ApiResponse;
import cn.zhuatech.dqm.service.DataQualityScoreService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@RestController
@RequestMapping("/api/admin")
public class DataQualityScoreController {
    private final DataQualityScoreService service;
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public DataQualityScoreController(DataQualityScoreService service) { this.service = service; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @PostMapping("/quality-score")
    public ApiResponse<DataQualityScoreService.Result> calculate(@Valid @RequestBody DataQualityScoreService.Request request) {
        return ApiResponse.ok(service.calculate(request));
    }
}
