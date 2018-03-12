package com.AR.Core;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

/**
 * Created by abner on 2018/3/11.
 */

public class Audio{

        private Button btn_start, btn_stop;
        private ListView lv_content;
        private File sdcardfile = null;
        private String[] files;
        private MediaRecorder recorder=null;

    /**
     * ②获取内存卡中文件的方法
     */
    public Audio(){
        getSDCardFile();
//        getFileList();
    }
    private void getSDCardFile() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {//内存卡存在
            sdcardfile=Environment.getExternalStorageDirectory();//获取目录文件
        }else {
        }
    }
    /**
     * ③获取文件列表（listView中的数据源）
     * 返回指定文件类型的文件名的集合作为数据源
     */
    public File getSdcardfile (){
        return sdcardfile;
    }
//    private void getFileList(){
//        if(sdcardfile!=null){
//            files=sdcardfile.list(new MyFilter());
//            lv_content.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,files));
//            //⑥给ListView中的元素添加点击播放事件
//            lv_content.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                    //⑩定义播放音频的方法
//                    play(files[position]);
//                }
//            });
//        }
//    }

//    @Override
//    public void onClick(View v) {
//        switch (v.getId()){
//            case R.id.btn_stat:
//                //⑧申请录制音频的动态权限
//                if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.RECORD_AUDIO)
//                        != PackageManager.PERMISSION_GRANTED){
//                    ActivityCompat.requestPermissions(this,new String[]{
//                            android.Manifest.permission.RECORD_AUDIO},1);
//                }else {
//                    startRecord();
//                }
//                break;
//            case R.id.btn_stop:
//                stopRcecord();
//                break;
//        }
//
//    }

    /**
     * ④定义一个文件过滤器MyFilter的内部类,实现FilenameFilter接口
     * 重写里边accept方法
     */
    class MyFilter implements FilenameFilter {

        @Override
        public boolean accept(File pathname, String fileName) {
            return fileName.endsWith(".amr");
        }
    }
    /**
     * ⑦给两个按钮定义开始和暂停的方法
     *
     */
    public void startRecord(){
        if(recorder==null){
            recorder=new MediaRecorder();
        }
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);//设置音频源为手机麦克风
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);//设置输出格式3gp
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);//设置音频编码为amr格式
        //获取内存卡的根目录，创建临时文件
        try {
            File file=File.createTempFile("录音_",".amr",sdcardfile);
            recorder.setOutputFile(file.getAbsolutePath());//设置文件输出路径
            //准备和启动录制音频
            recorder.prepare();
            recorder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //启动后交换两个按钮的可用状态
        btn_start.setEnabled(false);
        btn_stop.setEnabled(true);

    }
    public void stopRcecord(){
        if(recorder!=null){
            recorder.stop();
            recorder.release();
            recorder=null;
        }
        btn_start.setEnabled(true);
        btn_stop.setEnabled(false);
        //刷新列表数据
//        getFileList();

    }
    /**
     * ⑨重写onRequestPermissionsResult方法
     * 获取动态权限请求的结果,再开启录制音频
     */
    /**
     * ⑩定义播放音频的方法
     */
    public Intent  play(String fileName){
        Intent intent=new Intent(Intent.ACTION_VIEW);
        //播放音频需要uri,从文件中获取,文件中需要路径
        Uri uri=Uri.fromFile(new File(sdcardfile.getAbsoluteFile()+File.separator+fileName));
        //设置播放数据和类型
        intent.setDataAndType(uri,"audio/*");
        return intent;
//        startActivity(intent);
    }
}
