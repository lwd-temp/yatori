package com.cbq.yatori.core.action.yinghua;

import com.cbq.yatori.core.action.yinghua.entity.examinform.ConverterExamInform;
import com.cbq.yatori.core.action.yinghua.entity.examinform.ExamInformRequest;
import com.cbq.yatori.core.action.yinghua.entity.examinform.ExamInformResult;
import com.cbq.yatori.core.entity.AccountCacheYingHua;
import com.cbq.yatori.core.entity.User;
import com.cbq.yatori.core.utils.CustomTrustManager;
import okhttp3.*;

import javax.net.ssl.X509TrustManager;
import java.io.IOException;

/**
 * @description: TODO 考试相关
 * @author 长白崎
 * @date 2023/12/1 12:11
 * @version 1.0
 */
public class ExamAction {

    /**
     * 获取考试相关的一些信息
     * {"_code":0,"status":true,"msg":"获取数据成功","result":{"list":[{"id":1006680,"title":"0-3岁婴幼儿家庭教育与指导","topicNumber":33,"score":100,"addTime":"2024-04-09 12:10:58","nodeId":1430751,"courseId":1010822,"limitedTime":120,"remarks":"","startTime":"2024-04-09 12:10:10","endTime":"2024-04-30 23:55:55","createUserId":"1249678","classList":"[]","isPrivate":"0","teacherType":"1","allow":"1","frequency":"1","hasCollect":"0","schoolId":"9","parsing":"0","addDate":"2024-04-09","random":"0","randData":null,"randNumber":"0","type":3,"flag":0,"start":1,"finish":0,"url":"https:\/\/mooc.lidapoly.edu.cn\/api\/exam?nodeId=1430751&examId=1006680&token=sid.8bXX72LTPHQNhcki2CJf9cZ3oINhqt"}]}}
     * 这里会获取到考试内容相关的信息
     * 其中list中的id其实就是examId,nodeId就是nodeId
     * @param user 用户类
     * @param nodeId 对应考试的节点id
     */
    public static ExamInformRequest getExam(User user, String nodeId){
        AccountCacheYingHua cache = (AccountCacheYingHua) user.getCache();
        OkHttpClient client = new OkHttpClient().newBuilder()
                .sslSocketFactory(CustomTrustManager.getSSLContext().getSocketFactory(), new CustomTrustManager())
                .hostnameVerifier((hostname, session) -> true) // Bypass hostname verification
                .build();
        MediaType mediaType = MediaType.parse("multipart/form-data; boundary=--------------------------393670526422134055864302");
        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("platform","Android")
                .addFormDataPart("version","1.4.8")
                .addFormDataPart("nodeId",nodeId)
                .addFormDataPart("token",cache.getToken())
                .addFormDataPart("terminal","Android")
                .build();
        Request request = new Request.Builder()
                .url(user.getUrl()+"/api/node/exam.json")
                .method("POST", body)
                .addHeader("User-Agent", "Apifox/1.0.0 (https://apifox.com)")
                .addHeader("Accept", "*/*")
                .addHeader("Host", user.getUrl().replace("https://","").replace("http://","").replace("/",""))
                .addHeader("Connection", "keep-alive")
                .addHeader("Content-Type", "multipart/form-data; boundary=--------------------------393670526422134055864302")
                .build();
        try {
            Response response = client.newCall(request).execute();
            ExamInformRequest examInformRequest = ConverterExamInform.fromJsonString(response.body().string());
            return examInformRequest;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 开始考试接口
     * @param user 用户
     * @param courseId 课程ID
     * @param nodeId 对应章节结点Id
     * @param examId 考试Id
     */
    public static void startExam(User user,String courseId,String nodeId,String examId){
        AccountCacheYingHua cache = (AccountCacheYingHua)user.getCache();
        OkHttpClient client = new OkHttpClient().newBuilder()
                .sslSocketFactory(CustomTrustManager.getSSLContext().getSocketFactory(), new CustomTrustManager())
                .hostnameVerifier((hostname, session) -> true) // Bypass hostname verification
                .build();
        MediaType mediaType = MediaType.parse("multipart/form-data; boundary=--------------------------244120585315146738764763");
        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("platform","Android")
                .addFormDataPart("version","1.4.8")
                .addFormDataPart("nodeId",nodeId)
                .addFormDataPart("token",cache.getToken())
                .addFormDataPart("terminal","Android")
                .addFormDataPart("examId",examId)
                .addFormDataPart("courseId",courseId)
                .build();
        Request request = new Request.Builder()
                .url(user.getUrl()+"/api/exam/start.json")
                .method("POST", body)
                .addHeader("User-Agent", "Apifox/1.0.0 (https://apifox.com)")
                .addHeader("Accept", "*/*")
                .addHeader("Host", user.getUrl().replace("https://","").replace("http://","").replace("/",""))
                .addHeader("Connection", "keep-alive")
                .addHeader("Content-Type", "multipart/form-data; boundary=--------------------------244120585315146738764763")
                .build();
        try {
            Response response = client.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 提交答题的接口
     * @param user 用户
     * @param examId 答题的考试试卷Id
     * @param answerId 答题的题目Id
     * @param answer 所提交的答案或选项，比如A，B等
     * @param finish 是否是最后提交并且结束考试，0代表不是，1代表是
     */
    public static void submitExam(User user,String examId,String answerId, String answer,String finish){
        AccountCacheYingHua cache = (AccountCacheYingHua) user.getCache();
        OkHttpClient client = new OkHttpClient().newBuilder()
                .sslSocketFactory(CustomTrustManager.getSSLContext().getSocketFactory(), new CustomTrustManager())
                .hostnameVerifier((hostname, session) -> true) // Bypass hostname verification
                .build();
        MediaType mediaType = MediaType.parse("multipart/form-data; boundary=--------------------------326388482122783598484776");
        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("platform","Android")
                .addFormDataPart("version","1.4.8")
                .addFormDataPart("examId",examId)
                .addFormDataPart("terminal","Android")
                .addFormDataPart("answerId",answerId)
                .addFormDataPart("finish",finish)
                .addFormDataPart("token",cache.getToken())
                .addFormDataPart("answer",answer)
                .build();
        Request request = new Request.Builder()
                .url(user.getUrl()+"/api/exam/submit.json")
                .method("POST", body)
                .addHeader("User-Agent", "Apifox/1.0.0 (https://apifox.com)")
                .addHeader("Accept", "*/*")
                .addHeader("Host", user.getUrl().replace("https://","").replace("http://","").replace("/",""))
                .addHeader("Connection", "keep-alive")
                .addHeader("Content-Type", "multipart/form-data; boundary=--------------------------326388482122783598484776")
                .build();
        try {
            Response response = client.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
