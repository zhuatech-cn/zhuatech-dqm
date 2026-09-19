/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.dqm.controller;
import cn.zhuatech.dqm.common.ApiResponse;import cn.zhuatech.dqm.service.DataDriftService;import jakarta.validation.Valid;import org.springframework.web.bind.annotation.*;
/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@RestController @RequestMapping("/api/dqm/insights/data-drift") public class DataDriftController {private final DataDriftService service;/**
                                                                                                                                          * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                                                                                                                          */
public DataDriftController(DataDriftService service){this.service=service;}/**
                                                                                                                                                                                                                     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                                                                                                                                                                                                     */
@PostMapping ApiResponse<DataDriftService.Result> evaluate(@Valid @RequestBody DataDriftService.Request request){return ApiResponse.ok(service.evaluate(request));}}
