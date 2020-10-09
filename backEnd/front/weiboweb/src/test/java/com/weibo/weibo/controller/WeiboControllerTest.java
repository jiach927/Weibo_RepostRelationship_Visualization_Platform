package com.weibo.weibo.controller;


import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import com.weibo.weibo.util.loadJsonFile;
import java.io.File;

// MockMvc由spring-test提供,实现对Http请求的模拟,使用网络形式转换到Controller的调用
// 导入jar包:spring-boot-starter-test
@Slf4j
@SpringBootTest
@WebApplicationConfiguration // 测试环境使用的是WebApplicationContext 
public class WeiboControllerTest {
    
    private MockMvc mockMvc;

    @Autowired
    protected WebApplicationContext wac;
    @Value("classpath:static/testFiles/getWeibo01.json")
    // @Value("classpath:在resources下找") 注入文件资源
    private Resource testWeibo01; // 文件资源Resource
    @Value("classpath:static/testFiles/getRelationship01.json")
    private Resource testRelationship01;

    @BeforeEach // JUnit5相当于@Before
    public void setup(){
        log.info("测试开始...");
        // 实例化mockMvc对象
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void query() throws Exception{
        // 使用mockmvc来模拟请求并接收结果
        MvcResult mvcResult = null;
        mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/getWeibo/新冠/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print()).andReturn();
        // 提取结果中的字符串
        String result = mvcResult.getResponse().getContentAsString();
        // 测试希望匹配的答案（暂时不知道答案是什么就先放着，先保证代码跑通）
        File file = testWeibo01.getFile();
        String answer = loadJsonFile.parseJson(file);
        // 由于json生成字符串每次同级的顺序都不一样，因此需要用这个JSONAssert，需要导入特定依赖
        JSONAssert.assertEquals(answer, result, false);
    }

    @Test
    public void relationship() throws Exception{
        // 使用mockmvc来模拟请求并接收结果
        MvcResult mvcResult = null; // 构造get请求
        mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/getRelationship/4529066247461463")
                .contentType(MediaType.APPLICATION_JSON_UTF8)) // 返回值类型为UTF-8格式的json数据(响应头的contentType为application/json)
                .andExpect(MockMvcResultMatchers.status().isOk()) // 执行完成后断言,结果是否匹配
                .andDo(MockMvcResultHandlers.print()) // 添加对结果的处理:打印结果
                .andReturn();  // 返回验证成功后的结果
        // 提取结果中的字符串
        String result = mvcResult.getResponse().getContentAsString();
        // 测试希望匹配的答案（暂时不知道答案是什么就先放着，先保证代码跑通）
        File file = testRelationship01.getFile();
        String answer = loadJsonFile.parseJson(file);
        // 把文件资源转换为文件，读取文件数据作为字符串
        // 测试JSON对象,字段顺序无关紧要
        JSONAssert.assertEquals(answer, result, false);
        // 需要导入jsonassert依赖包
    }




}
/*
mockMvc.perform 执行一个请求;返回ResultActions
ResultActions.andExpect添加执行完成后的断言
ResultActions.andDo添加一个结果处理器
ResultActions.andReturn 表示执行完成后返回结果
*/ 