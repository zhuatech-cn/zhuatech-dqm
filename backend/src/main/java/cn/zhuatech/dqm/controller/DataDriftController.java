/* Copyright 2026 上海如静知华信息科技有限公司 */
package cn.zhuatech.dqm.controller;
import cn.zhuatech.dqm.common.ApiResponse;import cn.zhuatech.dqm.service.DataDriftService;import jakarta.validation.Valid;import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/dqm/insights/data-drift") public class DataDriftController {private final DataDriftService service;public DataDriftController(DataDriftService service){this.service=service;}@PostMapping ApiResponse<DataDriftService.Result> evaluate(@Valid @RequestBody DataDriftService.Request request){return ApiResponse.ok(service.evaluate(request));}}
