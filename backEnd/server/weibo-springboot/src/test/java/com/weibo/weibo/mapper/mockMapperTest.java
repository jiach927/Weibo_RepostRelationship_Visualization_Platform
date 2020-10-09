package com.weibo.weibo.mapper;

import com.weibo.weibo.entity.RepoRelationship;
import org.junit.After; // JUnit 4
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

// 疑惑:明明不支持JUnit4,但JUnit4的方法仍在被使用
import static org.junit.jupiter.api.Assertions.assertTrue; //JUnit 5
// import org,junit.Assert.assertTrue; // JUnit 4
import static org.mockito.Mockito.when;

// 模拟测试(mock testing):在测试过程中对某些不容易构造/不容易获取的对象，用虚拟的对象创建以便测试
// Mockito用于生成模拟对象
// @SpringBootTest springboot项目的单元测试(导入springboot测试包)
public class mockMapperTest {
    
    @Mock // 创建的虚拟对象,拥有类或接口的所有属性和方法
    private RepoRelationshipMapper repoRelationshipMapper;
    @Before // 在@Test类之前运行 
    // JUnit4 @BeforeClass 在所有方法加载前、静态类加载后执行,适合加载配置文件
    public void setUp() throws Exception{
        MockitoAnnotations.initMocks(this);
    }
    @After // 在@Test类后执行
    // JUnit4 @AfterClass 在所有方法执行后执行,用于清理资源（关闭数据库连接）
    public void tearDown() throws Exception{}
    @Test
    public void selectRepoRelationshipByCenter_bw_id() throws Exception{
        String center_bw_id="4523566437432908"; // step1
        List<RepoRelationship> repoRelationships=new ArrayList<>();
        // Stub打桩:把所需的测试数据塞入对象中，关注输入和输出，适用于基于状态的测试
        // 对于static和final方法无法进行when...thenReturn...
        when(repoRelationshipMapper.selectRepoRelationshipByCenter_bw_id(center_bw_id)).thenReturn(repoRelationships); //step2
        List<RepoRelationship> repoRelationships1=repoRelationshipMapper.selectRepoRelationshipByCenter_bw_id(center_bw_id);
        assertTrue(repoRelationships1.isEmpty()); //检查是否为真
    }
}
/*
Mock测试流程:
1.准备测试用的输入
2.给Mock对象设置预期的输出（被测对象是虚拟出来的,响应需要自己设置）
when...thenReturns...
3.运行被测方法
4.检查运行结果是否与预期一致（断言）

断言:一般在JUnit 4测试时使用,用于在测试阶段确定程序内部的错误位置
assertNotNull(object) 当object非空时程序正常执行,当object为空时程序抛出异常，不打印错误信息
*/

