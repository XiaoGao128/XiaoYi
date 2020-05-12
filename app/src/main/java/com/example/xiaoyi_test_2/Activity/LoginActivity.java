package com.example.xiaoyi_test_2.Activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.xiaoyi_test_2.Bean.UserBean;
import com.example.xiaoyi_test_2.R;
import com.example.xiaoyi_test_2.Service.LogAndRegister;
import com.example.xiaoyi_test_2.Utils.SPUtils;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.api.BasicCallback;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, LogAndRegister.sendRgRes, LogAndRegister.SendRegSucc {
    private Button btn_register, btn_login, btn_QQ, btn_sendyz;
    private String nowusername;
    boolean istrue = false, canpass = false;
    private ProgressBar progressBar;
    private Boolean onlog = false, onregis = false;
    private String userDetail;

    private AlertDialog alertDialog;
    //    private AlertDialog.Builder builder_regis;
//    private Button btn_regis,btn_log;
//    private View view_regis;
    private EditText et_lookcode_regis, et_lookcode_log, et_name_regis, et_phone_regis, phone_log;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        bindViews();
        progressBar.setVisibility(View.INVISIBLE);
    }

    private void bindViews() {
        btn_register = findViewById(R.id.login_btn_regster);      //注册
        btn_login = findViewById(R.id.log_btn_log);             //登录
        btn_QQ = findViewById(R.id.log_btn_QQ);           //qq登陆
        btn_sendyz = findViewById(R.id.log_send);             //发送验证码
        progressBar = findViewById(R.id.log_show_progress);
        progressBar.setVisibility(View.VISIBLE);
        btn_register.setOnClickListener(this);
        btn_login.setOnClickListener(this);
        btn_QQ.setOnClickListener(this);
        btn_sendyz.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.login_btn_regster: {
                final AlertDialog.Builder builder_regis = new AlertDialog.Builder(this);
                final LayoutInflater inflater = LayoutInflater.from(this);
                View view_regis = inflater.inflate(R.layout.layout_register, null);
                Button btn_regis = view_regis.findViewById(R.id.adm_btn);
                et_phone_regis = view_regis.findViewById(R.id.adm_phone);
                et_name_regis = view_regis.findViewById(R.id.adm_name);
                et_lookcode_regis = view_regis.findViewById(R.id.adm_lookcode);
                final Button btn_send = view_regis.findViewById(R.id.log_regis_send);
                SMSSDK.registerEventHandler(eventHandler);
                btn_send.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        SMSSDK.getVerificationCode("86", et_phone_regis.getText().toString());
                        //下面代码直接写在onclick事件中
                        if (!et_phone_regis.getText().toString().equals("") && (et_phone_regis.getText().length() == 11)) {
                            CountDownTimer timer = new CountDownTimer(60000, 1000) {//参数为 （倒计时长，间隔）
                                @Override
                                public void onTick(long millisUntilFinished) {
                                    btn_send.setEnabled(false);
                                    btn_send.setText("已发送(" + millisUntilFinished / 1000 + ")");
                                }

                                @Override
                                public void onFinish() {
                                    btn_send.setEnabled(true);
                                    btn_send.setText("重新发送");
                                }
                            }.start();

                        } else
                            Toast.makeText(LoginActivity.this, "请正确填写手机号", Toast.LENGTH_SHORT).show();
                    }
                });
                alertDialog = builder_regis.setView(view_regis).show();
                alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                btn_regis.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        progressBar.setVisibility(View.VISIBLE);
                        if (et_phone_regis.getText().toString().equals("") || et_name_regis.getText().toString().equals("") || et_lookcode_regis.getText().toString().equals("")) {
                            Toast.makeText(getApplicationContext(), "请补全信息！", Toast.LENGTH_LONG).show();
                        } else {
                            onregis = true;
                            LogAndRegister.snetenceHaveRegist(et_phone_regis.getText().toString(), LoginActivity.this);
                        }
                    }
                });
                break;
            }
            case R.id.log_btn_log: {
                phone_log = findViewById(R.id.login_et_phone);
                et_lookcode_log = findViewById(R.id.login_et_yanz);
                if (et_lookcode_log == null) {
                    Toast.makeText(this, "请填写验证码！", Toast.LENGTH_LONG).show();
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    onlog = true;
                    LogAndRegister.snetenceHaveRegist(phone_log.getText().toString(), this);
                }
//                SPUtils.put(LoginActivity.this,"userid",2);
//                SPUtils.put(LoginActivity.this, "username", "王梦怡啊");
//                SPUtils.put(LoginActivity.this, "phone", "19803090657");
//                SPUtils.put(LoginActivity.this, "head", "http://123.56.137.134/photo_XY/mm.png");
//                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                onDestroy();
//                startActivity(intent);
                break;
            }
            case R.id.log_btn_QQ: {
//                nowusername = "defult";
//                Bundle bundle = new Bundle();
//                bundle.putString("username", nowusername);
//                bundle.putInt("type", 1);
//                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                intent.putExtras(bundle);
//                finish();
//                startActivity(intent);
                break;

            }
            case R.id.log_send: {
                final EditText phone = findViewById(R.id.login_et_phone);
                if ((phone.getText().length() != 11)) {
                    Toast.makeText(this, "手机号输入错误！", Toast.LENGTH_SHORT).show();
                } else {
                    String s = "select * from user where phone ='" + phone.getText() + "'";
                    progressBar.setVisibility(View.VISIBLE);
                    SMSSDK.registerEventHandler(eventHandler);
                    SMSSDK.getVerificationCode("86", phone.getText().toString());
                    //下面代码直接写在onclick事件中
                    CountDownTimer timer = new CountDownTimer(60000, 1000) {//参数为 （倒计时长，间隔）
                        @Override
                        public void onTick(long millisUntilFinished) {
                            btn_sendyz.setEnabled(false);
                            btn_sendyz.setText("已发送(" + millisUntilFinished / 1000 + ")");
                        }

                        @Override
                        public void onFinish() {
                            btn_sendyz.setEnabled(true);
                            btn_sendyz.setText("重新发送");
                        }
                    }.start();
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }
        }
    }

    EventHandler eventHandler = new EventHandler() {
        public void afterEvent(int event, int result, Object data) {
            // afterEvent会在子线程被调用，因此如果后续有UI相关操作，需要将数据发送到UI线程
            Message msg = new Message();
            msg.arg1 = event;
            msg.arg2 = result;
            msg.obj = data;
            new Handler(Looper.getMainLooper(), new Handler.Callback() {
                @Override
                public boolean handleMessage(@NonNull Message msg) {
                    int event = msg.arg1;
                    int result = msg.arg2;
                    Object data = msg.obj;
                    if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        if (result == SMSSDK.RESULT_COMPLETE) {
                            // TODO 处理成功得到验证码的结果
                            istrue = true;
                            Log.d("----istrue", "" + istrue);
                            // 请注意，此时只是完成了发送验证码的请求，验证码短信还需要几秒钟之后才送达
                        } else {
                            // TODO 处理错误的结果
                            ((Throwable) data).printStackTrace();
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    } else if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        if (result == SMSSDK.RESULT_COMPLETE) {
                            // TODO 处理验证码验证通过的结果
                            if (onlog) {
                                onlog = false;
                                progressBar.setVisibility(View.INVISIBLE);
                                JSONObject jsonObject = JSONObject.parseObject(userDetail);
                                JMessageClient.login(jsonObject.getString("phone"), jsonObject.getString("username"), new BasicCallback() {
                                    @Override
                                    public void gotResult(int i, String s) {

                                    }
                                });
                                SPUtils.put(LoginActivity.this, "username", jsonObject.get("username"));
                                SPUtils.put(LoginActivity.this, "phone", jsonObject.get("phone"));
                                SPUtils.put(LoginActivity.this, "userid", jsonObject.get("id"));
                                SPUtils.put(LoginActivity.this, "head", jsonObject.get("head"));
                                SPUtils.put(LoginActivity.this, "sex", jsonObject.get("sex"));
                                Intent intent;
                                Bundle bundle = new Bundle();
                                if (SPUtils.get(LoginActivity.this,"Detail","1")!="1") {
                                    intent = new Intent();
                                    intent.putExtras(bundle);
                                    onDestroy();
                                    finish();
                                    setResult(0, intent);
                                } else {
                                intent = new Intent(LoginActivity.this, MainActivity.class);
                                onDestroy();
                                finish();
                                startActivity(intent);

                                }

                            } else if (onregis) {
                                onregis = false;
                                UserBean userBean = new UserBean();
                                userBean.setUsername(et_name_regis.getText().toString());
                                userBean.setPhone(et_phone_regis.getText().toString());
                                JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(userBean));
                                Log.d("LoginActivity--------", jsonObject.toJSONString());
                                LogAndRegister.Login(jsonObject, LoginActivity.this);
                                JMessageClient.register(et_phone_regis.getText().toString(), et_name_regis.getText().toString(), new BasicCallback() {
                                    @Override
                                    public void gotResult(int i, String s) {

                                    }
                                });
                            }
                        } else {
                            // TODO 处理错误的结果
                            Toast.makeText(LoginActivity.this, "验证码错误", Toast.LENGTH_LONG).show();
                            ((Throwable) data).printStackTrace();
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    }
                    // TODO 其他接口的返回结果也类似，根据event判断当前数据属于哪个接口
                    return false;
                }
            }).sendMessage(msg);
        }
    };

    // 使用完EventHandler需注销，否则可能出现内存泄漏
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterEventHandler(eventHandler);
    }

    //返回键


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent;
        if (SPUtils.get(LoginActivity.this, "Detail", "") != null) {
            intent = new Intent();
            onDestroy();
            setResult(0, intent);
            finish();
        }
    }

    @Override
    public void sendRgRes(String body) {
        if (onregis) {
            if (body.equals("false")) {
                SMSSDK.submitVerificationCode("86", et_phone_regis.getText().toString(), et_lookcode_regis.getText().toString());
            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(LoginActivity.this, "此手机号已注册！", Toast.LENGTH_LONG).show();
                    }
                });
                progressBar.setVisibility(View.INVISIBLE);
                onregis = false;
            }
        } else if (onlog) {
            if (body.equals("false")) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(LoginActivity.this, "此手机号尚未注册", Toast.LENGTH_LONG).show();
                    }
                });
                progressBar.setVisibility(View.INVISIBLE);
                onlog = false;
            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        SMSSDK.submitVerificationCode("86", phone_log.getText().toString(), et_lookcode_log.getText().toString());
                        userDetail = body;
                    }
                });

            }
        }
    }

    @Override
    public void sendRegSucc(boolean succ) {
        if (succ) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(LoginActivity.this, "注册成功！", Toast.LENGTH_SHORT).show();
                    alertDialog.dismiss();
                    progressBar.setVisibility(View.INVISIBLE);
                }
            });


        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    alertDialog.dismiss();
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(LoginActivity.this, "连接服务器失败！", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
