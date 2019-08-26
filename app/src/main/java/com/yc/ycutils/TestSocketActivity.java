package com.yc.ycutils;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.yc.yclibrary.base.YcAppCompatActivity;
import com.yc.ycutilslibrary.common.YcLog;
import com.yc.ycutilslibrary.net.YcSocketServer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 *
 */
public class TestSocketActivity extends YcAppCompatActivity {
    @BindView(R.id.textSocketServerDataTv)
    TextView mServerDataTv;
    @BindView(R.id.textSocketClientDataTv)
    TextView mClientDataTv;
    @BindView(R.id.textSocketServerSendEdt)
    EditText mServerSendEdt;
    @BindView(R.id.textSocketClientSendEdt)
    EditText mClientSendEdt;
    private int mPort = 5417;
    private String mIp = "10.1.3.88";

    @Override
    protected int getLayoutId() {
        return R.layout.test_socket;
    }

    @Override
    protected void initView(Bundle bundle) {

    }

    private YcSocketServer mYcSocketServer;
    private ServerSocket mServerSocket;

    @SuppressLint("CheckResult")
    private void open() {
        Observable.just(0)
                .observeOn(Schedulers.newThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer o) throws Exception {
                        mServerSocket = new ServerSocket(mPort);
                        Socket client = null;
                        while (true) {
                            System.out.println("waiting for...");
                            client = mServerSocket.accept();
                            BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()));
                            System.out.println(br.readLine());
                            serverAcceptData(br.readLine());
                        }
                    }
                });//下游
    }

    @SuppressLint("CheckResult")
    private void serverAcceptData(String data) {
        Observable.just(data)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        mServerDataTv.setText(mServerDataTv.getText().toString() + "\n" + s);
                    }
                });//下游
    }

    //    @SuppressLint("CheckResult")
//    private void serverSendData(String data) {
//       if(mServerSocket!=null){
//           PrintWriter printWriter = new PrintWriter(mServerSocket.get());
//           printWriter.print(msg);
//           printWriter.flush();
//           printWriter.close();
//       }
//    }
    private TestWebSocket mSocketClient;

    @SuppressLint("CheckResult")
    private void connServer(String ip, int port) {
        String serverUrl = "ws://10.1.3.88:5417/api/webSocket";
        URI recognizeUri = null;
        try {
            recognizeUri = new URI(serverUrl);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        TestWebSocket  mSocketClient = new TestWebSocket(recognizeUri);

        mSocketClient.connect();
//        Observable.just(0)
//                .observeOn(Schedulers.newThread())
//                .subscribe(new Consumer<Integer>() {
//                    @Override
//                    public void accept(Integer o) throws Exception {
//                        try {
//                            YcLog.e("连接服务器");
//                            String url = "10.1.3.88:3333"+"/api";
////                            mSocketClient = new Socket()
//                            mSocketClient = new Socket(ip, port);
//                        } catch (Exception e) {
//                            YcLog.e("连接失败");
//                            e.printStackTrace();
//                        }
//                    }
//                });//下游

    }

    /**
     * 发送一条消息
     *
     * @param msg 消息内容
     */
    public void clientSendData(String msg) {
        mSocketClient.send(msg);
        YcLog.e("发送数据");
//        PrintWriter printWriter = null;
//        try {
//            printWriter = new PrintWriter(mSocketClient.getOutputStream());
//            printWriter.print(msg);
//            printWriter.flush();
//            printWriter.close();    //一定要关闭输出流，要不然消息无法送达到服务器
//        } catch (IOException e) {
//            e.printStackTrace();
//            YcLog.e("发送失败");
//        }
    }

    @OnClick({R.id.textSocketServerSentTv, R.id.textSocketClientSendTv, R.id.textSocketOpenServerTv, R.id.textSocketConnServerTv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.textSocketServerSentTv:
                break;
            case R.id.textSocketClientSendTv:
                clientSendData(mClientSendEdt.getText().toString());
                break;
            case R.id.textSocketOpenServerTv:
                open();
                break;
            case R.id.textSocketConnServerTv:
                connServer(mIp, mPort);
                break;
        }
    }
}
