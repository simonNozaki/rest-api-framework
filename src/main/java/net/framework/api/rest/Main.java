package net.framework.api.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;

import net.framework.api.rest.client.WebApiClient;
import net.framework.api.rest.model.Errors;
import net.framework.api.rest.util.ObjectInspector;
import net.framework.api.rest.util.ObjectUtil;

/**
 * 利用サンプルクラス
 */
public class Main {

    /**
     * サンプルデータクラス
     */
    class Data{
        private String key;
        private String value;
        public String getKey() {
            return key;
        }
        public void setKey(String key) {
            this.key = key;
        }
        public String getValue() {
            return value;
        }
        public void setValue(String value) {
            this.value = value;
        }
    }

    static class Task {
        private String taskId;
        private String taskTitle;
        private List<Label> labels;
        public String getTaskId(){
            return this.taskId;
        }
        public void setTaskId(String taskId) {
            this.taskId = taskId;
        }
        public String getTaskTitle(){
            return this.taskTitle;
        }
        public void setTaskTitle(String taskTitle) {
            this.taskTitle = taskTitle;
        }
        public List<Label> getLabels() {
            return labels;
        }
        public void setLabels(List<Label> labels) {
            this.labels = labels;
        }
    }

    static class Label {
        private String id;
        private String name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    /**
     * メイン関数
     * @param args
     */
    public static void main(String args[]) throws Exception{
        System.out.println("Boot Java.");
        Map<String, String> header = new HashMap<String, String>();
        header.put("Content-Type", "application/json");
        WebApiClient<Task> client = new WebApiClient("https://raw.githubusercontent.com", "/simonNozaki/stubjs/master/stub/task-register.json", header);
        Task res = client.get(Task.class);

        Task task = generateTask();
        ObjectInspector.of(task)
                .testFromIterable(task.labels, (List<Label> labels) -> { labels } , "")
                .build();

        System.out.println(new ObjectMapper().writeValueAsString(res));
    }

    /**
     * サンプルメソッド。入力検査のイテレーション。
     * @throws Exception
     */
    public void iterate(List<Data> dataList) throws Exception{
        List<Errors> errors = new ArrayList<>();

        // ここから本題
        // ループのパターン
        for(Data subject : dataList){
            Errors error = ObjectInspector.of(subject)
                    .isNull(subject.getKey(), "LOG002")
                    .log("LOG001", "入力検査の実施")
                    .build();
            Optional.ofNullable(error).ifPresent((Errors instance) -> {
                errors.add(instance);
            });
        }

        // ストリームでまるっと処理
        List<Errors> object = ObjectUtil.getStream(dataList)
                .map((Data subject) -> {
                    return ObjectInspector.of(subject)
                            .isNull(subject.getKey(), "LOG002")
                            .log("LOG001", "入力検査の実施")
                            .build();
                })
                .collect(Collectors.toList());

        System.out.println(new ObjectMapper().writeValueAsString(object));
    }

    private static Task generateTask() {
        Task task = new Task();
        task.setTaskId("tk0001");
        task.setTaskTitle("test task");
        List<Label> labels = new ArrayList();
        Label label1 = new Label();
        label1.setId("lb0001");
        label1.setName("label 1");
        Label label2 = new Label();
        label2.setId("lb0002");
        label2.setName("label 2");
        Label label3 = new Label();
        label3.setId("lb0003");
        label3.setName("label 3");
        labels.add(label1);
        labels.add(label2);
        labels.add(label3);
        return task;
    }

}
