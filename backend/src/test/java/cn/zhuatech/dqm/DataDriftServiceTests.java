/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.dqm;
import cn.zhuatech.dqm.service.DataDriftService;import org.junit.jupiter.api.Test;import static org.junit.jupiter.api.Assertions.*;
/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
class DataDriftServiceTests {private final DataDriftService service=new DataDriftService();/**
                                                                                            * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                                                                            */
@Test void blocksSchemaBreakingDrift(){var r=service.evaluate(new DataDriftService.Request(1000,500,1,15,30,true,90,30));assertEquals("BLOCK_PIPELINE",r.status());}/**
                                                                                                                                                                                                                                                                * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                                                                                                                                                                                                                                                */
@Test void acceptsStableDataset(){var r=service.evaluate(new DataDriftService.Request(1000,1050,1,1.5,3,false,10,30));assertEquals("STABLE",r.status());}}
