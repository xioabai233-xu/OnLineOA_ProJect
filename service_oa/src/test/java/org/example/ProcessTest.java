package org.example;

import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ProcessTest {

    @Autowired
    private RuntimeService runtimeService;
    @Test
    public void startUPProcess(){
        test01();
    }

    @Test
    public void test01(){
        int[][] intervals = {{1,4},{2,4},{3,6},{4,4}};
        int[] queries ={2,3,4,5};
        Arrays.sort(intervals, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o2.length - o1.length;
            }
        });
        int[] res = new int[queries.length];
        for (int i = 0; i < queries.length; i++) {
            for (int j = 0; j < intervals.length; j++) {
                if (queries[i] >= intervals[j][0] && queries[i] <= intervals[j][1]) {
                    res[i] = intervals[j][1] - intervals[j][0] + 1;
                    break;
                }
            }
        }
    }
    @Autowired
    private RepositoryService repositoryService;

    @Test
    public void deployProcess(){
        //
        Deployment deploy = repositoryService.createDeployment()
                .addClasspathResource("process/qingjia.bpmn20.xml")
                .name("请假审批流程")
                .deploy();
        System.out.println(deploy.getId());
        System.out.println(deploy.getName());
    }

    @Autowired
    private TaskService taskService;

    @Test
    public void findPendingTaskList(){
        // 任务负责人
        String assign ="Assignee lisi";
        List<Task> assignList = taskService.createTaskQuery()
                .taskAssignee(assign)
                .list();
        for(Task task : assignList){
            System.out.println("任务id"+task.getId());
            System.out.println("任务名称"+task.getName());
            System.out.println("任务负责人"+task.getAssignee());
            System.out.println("流程实例id"+task.getProcessInstanceId());
        }
    }


    @Test
    public void CompleteTask(){
        String assign ="Assignee zhangsan";
        Task task = taskService.createTaskQuery()
                .taskAssignee(assign)
                .singleResult();
        System.out.println(task.getId());

        taskService.complete(task.getId());

    }

    @Test
    public void startUpProcessAddBusinessKey(){
        String businessKey ="1";
        // 启动流程实例，指定业务标识，也就是请假申请单id
        ProcessInstance qingjia = runtimeService.startProcessInstanceByKey("qingjia", businessKey);
        System.out.println("业务id："+qingjia.getBusinessKey());

    }
    @Autowired
    private HistoryService historyService;
    @Test
    public void findProcessTaskList(){
        String assign="Assignee zhangsan";
        List<HistoricTaskInstance> list = historyService.createHistoricTaskInstanceQuery()
                .taskAssignee(assign)
                .list();
        System.out.println(list);
        System.out.println("complete history");
    }
    @Test
    public void suspendProcessInstance(){
        ProcessDefinition qingjia = repositoryService.createProcessDefinitionQuery()
                .processDefinitionKey("qingjia")
                .singleResult();
        boolean suspend = qingjia.isSuspended();  // 是否挂起
        if(suspend){
            // 三个参数 第一个：id 第二个：是否为真 第三个：时间点
            repositoryService.activateProcessDefinitionById(qingjia.getId(),true,null);
            System.out.println("流程定义："+qingjia.getId()+"激活");
        }else{
            repositoryService.suspendProcessDefinitionById(qingjia.getId(),true,null);
            System.out.println("流程定义："+qingjia.getId()+"挂起");
        }
    }

}
