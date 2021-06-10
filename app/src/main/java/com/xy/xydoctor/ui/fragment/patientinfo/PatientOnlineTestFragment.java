package com.xy.xydoctor.ui.fragment.patientinfo;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.SPStaticUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.bumptech.glide.Glide;
import com.clj.fastble.BleManager;
import com.clj.fastble.callback.BleNotifyCallback;
import com.clj.fastble.callback.BleReadCallback;
import com.clj.fastble.callback.BleScanAndConnectCallback;
import com.clj.fastble.callback.BleWriteCallback;
import com.clj.fastble.data.BleDevice;
import com.clj.fastble.exception.BleException;
import com.clj.fastble.scan.BleScanRuleConfig;
import com.clj.fastble.utils.HexUtil;
import com.ft.analysis.obj.BloodfatData;
import com.ft.analysis.obj.BodyData;
import com.ft.analysis.obj.EcgData;
import com.ft.analysis.obj.GluData;
import com.ft.analysis.obj.NibpData;
import com.ft.analysis.obj.Spo2Data;
import com.ft.analysis.obj.TempData;
import com.ft.analysis.obj.UrineData;
import com.ft.analysis.obj.UserInfo;
import com.ft.analysis.scan.BLEController;
import com.ft.analysis.scan.ExchangeInterface;
import com.lyd.baselib.base.fragment.BaseFragment;
import com.lyd.baselib.util.TurnsUtils;
import com.lyd.baselib.util.ble.BleStatusBroadcastReceiver;
import com.lyd.baselib.util.ble.BleUtils;
import com.qmuiteam.qmui.widget.QMUIRadiusImageView;
import com.rxjava.rxlife.RxLife;
import com.wei.android.lib.colorview.view.ColorTextView;
import com.xy.xydoctor.R;
import com.xy.xydoctor.bean.GetOnlineBloodBean;
import com.xy.xydoctor.bean.OnlineTestGetBloodSugar;
import com.xy.xydoctor.bean.UserInfoBean;
import com.xy.xydoctor.constant.ConstantParam;
import com.xy.xydoctor.net.ErrorInfo;
import com.xy.xydoctor.net.OnError;
import com.xy.xydoctor.net.XyUrl;
import com.xy.xydoctor.utils.DataConvert;
import com.xy.xydoctor.utils.NumberUtils;
import com.xy.xydoctor.utils.StringToHexUtils;
import com.xy.xydoctor.utils.progress.BleDialogUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.rxjava3.functions.Consumer;
import rxhttp.wrapper.param.RxHttp;

/**
 * 描述: 在线测量
 * 作者: LYD
 * 创建日期: 2019/7/12 15:44
 */
public class PatientOnlineTestFragment extends BaseFragment implements ExchangeInterface, BleStatusBroadcastReceiver.BleStatusChangeListener {
    private static final String TAG = "xxx";
    private static final int OPEN_BLE = 10086;
    @BindView(R.id.ll_online_test_empty)
    LinearLayout llOnlineTestEmpty;
    @BindView(R.id.tv_get_data)
    ColorTextView tvGetData;
    @BindView(R.id.img_head)
    QMUIRadiusImageView imgHead;
    @BindView(R.id.tv_person_name)
    TextView tvPersonName;
    @BindView(R.id.img_sex)
    ImageView imgSex;
    @BindView(R.id.tv_person_age)
    TextView tvPersonAge;
    @BindView(R.id.tv_height)
    TextView tvHeight;
    @BindView(R.id.tv_weight)
    TextView tvWeight;
    @BindView(R.id.tv_check_time)
    TextView tvCheckTime;
    //血糖开始
    @BindView(R.id.ll_online_test_blood_sugar)
    LinearLayout llBloodSugar;
    @BindView(R.id.tv_blood_sugar)
    TextView tvBloodSugar;
    @BindView(R.id.img_blood_sugar)
    ImageView imgBloodSugar;
    //血糖结束
    //血氧开始
    @BindView(R.id.ll_online_test_blood_oxygen)
    LinearLayout llBloodOxygen;
    @BindView(R.id.tv_blood_oxygen)
    TextView tvBloodOxygen;
    @BindView(R.id.img_blood_oxygen)
    ImageView imgBloodOxygen;
    @BindView(R.id.tv_blood_pulse)
    TextView tvBloodPulse;
    @BindView(R.id.img_blood_pulse)
    ImageView imgBloodPulse;
    //血氧结束
    //血氧开始
    @BindView(R.id.ll_online_test_body_fat)
    LinearLayout llBodyFat;
    @BindView(R.id.tv_online_test_weight)
    TextView tvOnlineTestWeight;
    @BindView(R.id.tv_bmi)
    TextView tvBmi;
    @BindView(R.id.img_bmi)
    ImageView imgBmi;
    //血氧结束
    //血压开始
    @BindView(R.id.ll_online_test_blood_pressure)
    LinearLayout llBloodPressure;
    @BindView(R.id.tv_blood_pressure_systolic_pressure)
    TextView tvSystolicPressure;
    @BindView(R.id.img_blood_pressure_systolic_pressure)
    ImageView imgSystolicPressure;
    @BindView(R.id.tv_blood_pressure_diastolic_pressure)
    TextView tvDiastolicPressure;
    @BindView(R.id.img_blood_pressure_diastolic_pressure)
    ImageView imgDiastolicPressure;
    //应用层回复血压计已经连接
    String bCommand1 = "04B0A054";
    //应用层要求血压计开始测量
    String bCommand2 = "04B0A155";
    //血压结束
    private Timer timer = new Timer();
    //蓝牙状态广播接受者
    private BleStatusBroadcastReceiver receiver;
    //身高后台数据
    private String height;
    private BleDialogUtils bleDialogUtils;
    private boolean isConnectSuccess;
    //血压扫描超时时间
    private int scanTime = 10 * 1000;
    //扫描不到设备不到超时时间(获取血糖超时时间+血压一体机超时时间+40秒)
    private int overTime = ConstantParam.DEFAULT_TIMEOUT + scanTime + 40 * 1000;
    //血压计开始
    //设备名称
    private String bDeviceName = "ClinkBlood";
    //服务名称
    private String bs1 = "0000fc00-0000-1000-8000-00805f9b34fb";
    //通知 特征名
    private String bs2 = "0000fca1-0000-1000-8000-00805f9b34fb";
    //写命令 特征名
    private String bs3 = "0000fca0-0000-1000-8000-00805f9b34fb";
    //血压计结束
    //一体机开始
    //设备名称
    private String eDeviceName = "SH-eScale";
    //服务名称
    private String es1 = "53480001-534d-4152-542d-455343414c45";
    //通知 特征名
    private String es2 = "53480003-534d-4152-542d-455343414c45";
    //写命令 特征名
    private String es3 = "53480002-534d-4152-542d-455343414c45";
    //联机命令(发送后，电子秤返回60$，说明电子秤在线)
    //private String eCommand1 = "313024";
    //启动测量命令(发送后，电子秤返回11$，启动测量身高体重，测量结束后返回测量结果)
    private String eCommand2 = "313124";
    //一体机结束

    private UUID uuid_service;
    private UUID uuid_chara;

    private String bDeviceBName = "My Thermometer";
    private String bDeviceBCommend = "0XFFB2";


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_patient_online_test;
    }

    @Override
    protected void init(View rootView) {
        setScanRule();
        registerBleStatusReceiver();
        setTopInfo();
        initDialog();
    }


    /**
     * 设置扫描规则
     */
    private void setScanRule() {
        BleScanRuleConfig scanRuleConfig =
                new BleScanRuleConfig.Builder()
                        //只扫描指定广播名的设备，可选
                        .setDeviceName(true, eDeviceName, bDeviceBName)
                        //连接时的autoConnect参数，可选，默认false
                        .setAutoConnect(false)
                        //扫描超时时间，可选，默认10秒
                        .setScanTimeOut(scanTime)
                        .build();
        BleManager.getInstance().initScanRule(scanRuleConfig);
    }

    /**
     * 设置头部信息
     */
    private void setTopInfo() {
        String userId = getArguments().getString("userid");
        HashMap<String, Object> map = new HashMap<>();
        map.put("userid", userId);
        RxHttp.postForm(XyUrl.GET_USER_INFO)
                .addAll(map)
                .asResponseList(UserInfoBean.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<List<UserInfoBean>>() {
                    @Override
                    public void accept(List<UserInfoBean> myTreatPlanBeans) throws Exception {
                        List<UserInfoBean> data = myTreatPlanBeans;
                        UserInfoBean user = data.get(0);
                        if (1 == user.getSex()) {
                            Glide.with(Utils.getApp()).load(user.getPicture()).error(R.drawable.head_man).placeholder(R.drawable.head_man).into(imgHead);
                            imgSex.setImageResource(R.drawable.male);
                        } else {
                            Glide.with(Utils.getApp()).load(user.getPicture()).error(R.drawable.head_woman).placeholder(R.drawable.head_woman).into(imgHead);
                            imgSex.setImageResource(R.drawable.female);
                        }
                        if (TextUtils.isEmpty(user.getNickname())) {
                            tvPersonName.setText(user.getUsername());
                        } else {
                            tvPersonName.setText(user.getNickname());
                        }
                        tvPersonAge.setText(user.getAge() + "岁");
                        height = user.getHeight();
                        tvHeight.setText(height + "cm");
                        tvWeight.setText(user.getWeight() + "kg");
                        String checkTime = SPStaticUtils.getString("checkTime");
                        if (TextUtils.isEmpty(checkTime)) {
                            tvCheckTime.setText("最新检测时间:" + "-- -- --");
                        } else {
                            tvCheckTime.setText("最新检测时间:" + checkTime);
                        }
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {

                    }
                });
    }


    /**
     * 初始化Dialog
     */
    private void initDialog() {
        bleDialogUtils = new BleDialogUtils();
    }


    @OnClick(R.id.tv_get_data)
    public void onViewClicked() {
        //PatientOnlineTestFragmentPermissionsDispatcher.obtainAccessFineLocationWithPermissionCheck(this);
        requestLocation();
    }

    private void requestLocation() {
        PermissionUtils
                .permission(PermissionConstants.LOCATION)
                .callback(new PermissionUtils.SimpleCallback() {
                    @Override
                    public void onGranted() {
                        isConnectSuccess = false;
                        getSugarData();
                    }

                    @Override
                    public void onDenied() {
                        ToastUtils.showShort(R.string.please_open_gps);
                        Log.e(TAG, "accessFineLocationDenied");
                    }
                }).request();
    }

    /**
     * 首先获取血糖数据
     */
    private void getSugarData() {
        HashMap<String, Object> map = new HashMap<>();
        RxHttp.postForm(XyUrl.GET_ONLINE_TEST_BLOOD_SUGAR)
                .addAll(map)
                .asResponse(OnlineTestGetBloodSugar.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<OnlineTestGetBloodSugar>() {
                    @Override
                    public void accept(OnlineTestGetBloodSugar data) throws Exception {
                        setBloodSugar(data);
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {
                        getBloodData();
                    }
                });
    }


    /**
     * 获取血压
     */
    private void getBloodData() {
        HashMap<String, Object> map = new HashMap<>();
        RxHttp.postForm(XyUrl.GET_ONLINE_TEST_BLOOD_PRESSURE)
                .addAll(map)
                .asResponse(GetOnlineBloodBean.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<GetOnlineBloodBean>() {
                    @Override
                    public void accept(GetOnlineBloodBean data) throws Exception {
                        setBloodPressure(data);
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {
                        BleStart();
                    }
                });

    }

    /**
     * 开始蓝牙相关
     */
    private void BleStart() {
        boolean bleState = BleUtils.getBleState();
        if (bleState) {
            dialogStartConnect();
            startScanBpAndAio();
            dialogPostDelay();
        } else {
            BleUtils.openBle(this, OPEN_BLE);
        }
    }

    /**
     * 开始连接
     */
    private void dialogStartConnect() {
        bleDialogUtils.showProgress(getActivity(), getString(R.string.ble_connecting));
    }

    /**
     * 连接成功
     */
    private void dialogConnectSuccess() {
        bleDialogUtils.dismissProgress();
        bleDialogUtils.mProgressDialog = null;
        bleDialogUtils.showProgress(getActivity(), getString(R.string.ble_connected_and_wait_data));
    }

    /**
     * 取消显示
     */
    private void dialogDismiss() {
        bleDialogUtils.dismissProgress();
    }

    /**
     * 连接失败
     */
    private void dialogConnectFailed() {
        bleDialogUtils.dismissProgress();
        ToastUtils.showLong(R.string.ble_connect_error);
    }

    /**
     * 加载延时
     */
    private void dialogPostDelay() {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                //定时任务到时候执行的任务
                //Log.e(TAG, "isConnectSuccess: " + isConnectSuccess);
                if (isConnectSuccess == false) {
                    dialogConnectFailed();
                }
            }
        }, overTime);
    }

    /**
     * 血压计以及一体机开始扫描并连接
     */
    private void startScanBpAndAio() {
        BleManager.getInstance().scanAndConnect(new BleScanAndConnectCallback() {
            @Override
            public void onScanStarted(boolean success) {
                Log.e(TAG, "onScanStarted");
            }

            @Override
            public void onScanFinished(BleDevice scanResult) {
                Log.e(TAG, "onScanFinished");
                //扫描结束，结果即为扫描到的第一个符合扫描规则的BLE设备，如果为空表示未搜索到（主线程）
                if (scanResult == null) {
                    Log.e(TAG, "onScanFinished:" + "列表为空");
                    startScanYC();
                } else {
                    Log.e(TAG, "onScanFinished:" + "有设备");
                }
            }

            @Override
            public void onStartConnect() {
                Log.e(TAG, "onStartConnect");
            }

            @Override
            public void onConnectFail(BleDevice bleDevice, BleException exception) {
                //连接失败
                Log.e(TAG, "onConnectFail");
                startScanYC();
            }

            @Override
            public void onConnectSuccess(BleDevice bleDevice, BluetoothGatt gatt, int status) {
                String name = bleDevice.getName();
                Log.e(TAG, "连接成功的设备名称==" + name);
                Log.i("yys", "mac===" + bleDevice.getMac());
                if (bDeviceBName.equals(name)) {
                    for (BluetoothGattService service : gatt.getServices()) {
                        uuid_service = service.getUuid();
                        Log.i(TAG, "uuid_service==" + uuid_service.toString());
                        List<BluetoothGattCharacteristic> characteristicList = service.getCharacteristics();
                        for (BluetoothGattCharacteristic characteristic : characteristicList) {
                            uuid_chara = characteristic.getUuid();
                            //                        Log.i(TAG,"uuid_chara=="+uuid_chara.toString());
                        }
                    }
                    //                Log.i(TAG,"final uuid_service=="+uuid_service.toString());
                    //                Log.i(TAG,"final uuid_chara=="+uuid_chara.toString());
                    BleManager.getInstance().notify(
                            bleDevice,
                            uuid_service.toString(),
                            uuid_chara.toString(),
                            new BleNotifyCallback() {
                                @Override
                                public void onNotifySuccess() {
                                    // 打开通知操作成功
                                    Log.i(TAG, "打开通知成功");
                                    BleManager.getInstance().read(
                                            bleDevice,
                                            uuid_service.toString(),
                                            uuid_chara.toString(),
                                            new BleReadCallback() {
                                                @Override
                                                public void onReadSuccess(byte[] data) {
                                                    Log.i(TAG, "onReadSuccess");
                                                }

                                                @Override
                                                public void onReadFailure(BleException exception) {
                                                    Log.i(TAG, "onReadFailure");
                                                }
                                            }
                                    );
                                }

                                @Override
                                public void onNotifyFailure(BleException exception) {
                                    // 打开通知操作失败
                                    Log.i(TAG, "onNotifyFailure");
                                }

                                @Override
                                public void onCharacteristicChanged(byte[] data) {
                                    // 打开通知后，设备发过来的数据将在这里出现
                                    Log.i(TAG, "onCharacteristicChanged" + data);
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Log.e(TAG, "data==" + data);
                                            dialogDismiss();
                                            String convertString = ConvertUtils.bytes2HexString(data);
                                            ToastUtils.showShort(DataConvert.hexStrToUTF8(convertString));
                                        }
                                    });
                                }
                            });
                }

                openBleNotify(bleDevice, name);
                dialogConnectSuccess();
            }

            @Override
            public void onDisConnected(boolean isActiveDisConnected, BleDevice device, BluetoothGatt gatt, int status) {
                Log.e(TAG, "onDisConnected");
            }

            @Override
            public void onScanning(BleDevice bleDevice) {
                Log.e(TAG, "onScanning");
            }
        });
    }


    /**
     * 开启蓝牙通知
     *
     * @param bleDevice
     * @param name
     */
    private void openBleNotify(BleDevice bleDevice, String name) {
        //服务名称
        String uuidService = "";
        //特征名之 打开通知
        String uuidNotify = "";
        if (bDeviceName.equals(name)) {
            uuidService = bs1;
            uuidNotify = bs2;
            getNotify(bleDevice, uuidService, uuidNotify, name);
        } else if (eDeviceName.equals(name)) {
            uuidService = es1;
            uuidNotify = es2;
            BleManager.getInstance().notify(
                    bleDevice, uuidService, uuidNotify,
                    new BleNotifyCallback() {
                        @Override
                        public void onNotifySuccess() {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Log.e(TAG, "开启蓝牙通知:" + "onNotifySuccess");
                                    startTestStepOne(bleDevice, name);
                                }
                            });
                        }

                        @Override
                        public void onNotifyFailure(final BleException exception) {

                        }

                        @Override
                        public void onCharacteristicChanged(final byte[] data) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Log.e(TAG, "data==" + data);
                                    StringBuilder stringBuilder = new StringBuilder();
                                    String convertString = ConvertUtils.bytes2HexString(data);
                                    stringBuilder.append(convertString);
                                    String allString = stringBuilder.toString();
                                    Log.e(TAG, "所有字符串:" + allString);
                                    if (bDeviceName.equals(name)) {
                                        //血压
                                        if (allString.contains("070BBD")) {
                                            isConnectSuccess = true;
                                            String bloodPressureString = allString.substring(allString.indexOf("080BB8") + 8, allString.indexOf("080BB8") + 12);
                                            //16进制 收缩压 舒张压
                                            //Log.e(TAG, "bloodPressureString==" + bloodPressureString);
                                            //setBloodPressure(bloodPressureString);
                                            showdownBle(bleDevice);
                                        }
                                    } else if (eDeviceName.equals(name)) {
                                        //连接成功返回 313124
                                        if (!"313124".equals(allString)) {
                                            BodyData bodyData = new BodyData();
                                            setBodyFat(bodyData, allString);
                                        }
                                    }
                                }
                            });
                        }
                    });
        }

    }


    private void getNotify(BleDevice bleDevice, String uuidService, String uuidNotify, String name) {
        BleManager.getInstance().notify(
                bleDevice, uuidService, uuidNotify,
                new BleNotifyCallback() {
                    @Override
                    public void onNotifySuccess() {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Log.e(TAG, "开启蓝牙通知:" + "onNotifySuccess");
                                startTestStepOne(bleDevice, name);
                            }
                        });
                    }

                    @Override
                    public void onNotifyFailure(final BleException exception) {

                    }

                    @Override
                    public void onCharacteristicChanged(final byte[] data) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Log.e(TAG, "data==" + data);
                                StringBuilder stringBuilder = new StringBuilder();
                                String convertString = ConvertUtils.bytes2HexString(data);
                                stringBuilder.append(convertString);
                                String allString = stringBuilder.toString();
                                Log.e(TAG, "所有字符串:" + allString);
                                if (bDeviceName.equals(name)) {
                                    //血压
                                    if (allString.contains("070BBD")) {
                                        isConnectSuccess = true;
                                        String bloodPressureString = allString.substring(allString.indexOf("080BB8") + 8, allString.indexOf("080BB8") + 12);
                                        //16进制 收缩压 舒张压
                                        //Log.e(TAG, "bloodPressureString==" + bloodPressureString);
                                        //setBloodPressure(bloodPressureString);
                                        showdownBle(bleDevice);
                                    }
                                } else if (eDeviceName.equals(name)) {
                                    //连接成功返回 313124
                                    if (!"313124".equals(allString)) {
                                        BodyData bodyData = new BodyData();
                                        setBodyFat(bodyData, allString);
                                    }
                                }
                            }
                        });
                    }
                });
    }

    /**
     * 血压计开始测量第一步
     *
     * @param bleDevice
     * @param name
     */
    private void startTestStepOne(BleDevice bleDevice, String name) {
        //服务名称
        String uuidService = "";
        //特征名之 打开通知
        String uuidWrite = "";
        //指令
        String command = "";
        if (bDeviceName.equals(name)) {
            uuidService = bs1;
            uuidWrite = bs3;
            command = bCommand1;
        } else if (eDeviceName.equals(name)) {
            uuidService = es1;
            uuidWrite = es3;
            command = eCommand2;
        }
        BleManager.getInstance().write(
                bleDevice, uuidService, uuidWrite,
                HexUtil.hexStringToBytes(command),
                new BleWriteCallback() {
                    @Override
                    public void onWriteSuccess(final int current, final int total, final byte[] justWrite) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (bDeviceName.equals(name)) {
                                    Log.e(TAG, "蓝牙测量第一步" + "onWriteSuccess");
                                    startTestStepTwo(bleDevice, name);
                                }
                            }
                        });
                    }

                    @Override
                    public void onWriteFailure(final BleException exception) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Log.e(TAG, "蓝牙测量第一步" + "onWriteFailure");
                            }
                        });
                    }
                });
    }

    /**
     * 血压计开始测量第二步
     *
     * @param bleDevice
     * @param name
     */
    private void startTestStepTwo(BleDevice bleDevice, String name) {
        //服务名称
        String uuidService = "";
        //特征名之 打开通知
        String uuidWrite = "";
        //指令
        String command = "";
        if (bDeviceName.equals(name)) {
            uuidService = bs1;
            uuidWrite = bs3;
            command = bCommand2;
        } else if (bDeviceBName.equals(name)) {
            uuidService = uuid_service.toString();
            uuidWrite = uuid_chara.toString();
            command = bDeviceBCommend;
        }
        BleManager.getInstance().write(
                bleDevice, uuidService, uuidWrite,
                HexUtil.hexStringToBytes(command),
                new BleWriteCallback() {

                    @Override
                    public void onWriteSuccess(final int current, final int total, final byte[] justWrite) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Log.e(TAG, "蓝牙测量第二步:" + "onWriteSuccess");
                            }
                        });
                    }

                    @Override
                    public void onWriteFailure(final BleException exception) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Log.e(TAG, "蓝牙测量第二步:" + "onWriteFailure");
                            }
                        });
                    }
                });
    }

    /**
     * 关机
     *
     * @param bleDevice
     */
    private void showdownBle(BleDevice bleDevice) {
        //应用层要求血压计关机
        String hex2 = "04B0A65A";
        //服务名称
        String s0 = "0000fc00-0000-1000-8000-00805f9b34fb";
        //特征名之 写指令
        String s1 = "0000fca0-0000-1000-8000-00805f9b34fb";
        BleManager.getInstance().write(
                bleDevice, s0, s1,
                HexUtil.hexStringToBytes(hex2),
                new BleWriteCallback() {
                    @Override
                    public void onWriteSuccess(final int current, final int total, final byte[] justWrite) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Log.e(TAG, "关机" + "onWriteSuccess");
                            }
                        });
                    }

                    @Override
                    public void onWriteFailure(final BleException exception) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Log.e(TAG, "关机" + "onWriteFailure");
                            }
                        });
                    }
                });
    }

    /**
     * 血氧仪和体重计开始扫描
     */
    private void startScanYC() {
        BLEController bleController = new BLEController(getPageContext(), this, ExchangeInterface.USING_TYPE_FULL);
        bleController.startScan();
    }


    //设置数据开始

    /**
     * 设置血糖
     *
     * @param bean
     */
    private void setBloodSugar(OnlineTestGetBloodSugar bean) {
        dialogDismiss();
        //显示隐藏
        llOnlineTestEmpty.setVisibility(View.GONE);
        llBloodSugar.setVisibility(View.VISIBLE);
        llBloodOxygen.setVisibility(View.GONE);
        llBodyFat.setVisibility(View.GONE);
        llBloodPressure.setVisibility(View.GONE);
        //展示数据
        //OnlineTestGetBloodSugar data = (OnlineTestGetBloodSugar) baseBean.getData();
        //设置时间
        long datetime = bean.getDatetime();
        String checkTime = TimeUtils.millis2String(datetime * 1000L);
        tvCheckTime.setText("最新检测时间:" + checkTime);
        double glucosevalue = bean.getGlucosevalue();
        tvBloodSugar.setText(glucosevalue + "mol/L");
        //血糖4.4~10.0
        if (glucosevalue > 10.0) {
            imgBloodSugar.setImageResource(R.drawable.online_test_blood_sugar_high);
        } else if (glucosevalue > 4.4) {
            imgBloodSugar.setImageResource(R.drawable.online_test_blood_sugar_common);
        } else {
            imgBloodSugar.setImageResource(R.drawable.online_test_blood_sugar_low);
        }
        uploadBloodSugar(bean);
    }

    /**
     * 设置血压数据
     *
     * @param bean
     */
    private void setBloodPressure(GetOnlineBloodBean bean) {

        dialogDismiss();
        //显示隐藏
        llOnlineTestEmpty.setVisibility(View.GONE);
        llBloodSugar.setVisibility(View.GONE);
        llBloodOxygen.setVisibility(View.GONE);
        llBodyFat.setVisibility(View.GONE);
        llBloodPressure.setVisibility(View.VISIBLE);
        String systolic = bean.getSystolic();
        String diastole = bean.getDiastole();
        String checkTime = bean.getDatetime();
        //设置数据
        if (!TextUtils.isEmpty(systolic) && !TextUtils.isEmpty(diastole)) {
            tvSystolicPressure.setText(systolic + "mmHg");
            tvDiastolicPressure.setText(diastole + "mmHg");
            double high = TurnsUtils.getDouble(systolic, 0);
            double low = TurnsUtils.getDouble(diastole, 0);
            //收缩压90~140
            if (high > 140) {
                imgSystolicPressure.setImageResource(R.drawable.online_test_blood_pressure_high);
            } else if (high > 90) {
                imgSystolicPressure.setImageResource(R.drawable.online_test_blood_pressure_common);
            } else {
                imgSystolicPressure.setImageResource(R.drawable.online_test_blood_pressure_low);
            }
            //舒张压60~90
            if (low > 90) {
                imgDiastolicPressure.setImageResource(R.drawable.online_test_blood_pressure_high);
            } else if (low > 60) {
                imgDiastolicPressure.setImageResource(R.drawable.online_test_blood_pressure_common);
            } else {
                imgDiastolicPressure.setImageResource(R.drawable.online_test_blood_pressure_low);
            }
            tvCheckTime.setText("最新检测时间:" + checkTime);
            //上传血压
            uploadBloodPressure(high, low, checkTime);
        }
    }

    /**
     * 设置血氧
     *
     * @param spo2Data
     */
    private void setBloodOxygen(Spo2Data spo2Data) {
        dialogDismiss();
        //isConnectSuccess = true;
        int spo2 = spo2Data.getSpo2();//血氧饱和度
        int pr = spo2Data.getPr();//脉率值
        long systemTime = spo2Data.getSystemTime();
        llOnlineTestEmpty.setVisibility(View.GONE);
        llBloodSugar.setVisibility(View.GONE);
        llBloodOxygen.setVisibility(View.VISIBLE);
        llBodyFat.setVisibility(View.GONE);
        llBloodPressure.setVisibility(View.GONE);
        //设置时间
        String checkTime = TimeUtils.millis2String(systemTime);
        tvCheckTime.setText("最新检测时间:" + checkTime);
        //血氧没有偏高项，95~99为正常，95以下为偏低
        tvBloodOxygen.setText(spo2 + "%");
        if (spo2 < 95) {
            imgBloodOxygen.setImageResource(R.drawable.online_test_blood_oxygen_abnormal);
        } else {
            imgBloodOxygen.setImageResource(R.drawable.online_test_blood_oxygen_common);
        }
        //心率60~100为正常，小于60为偏低，大于100为偏高.
        tvBloodPulse.setText(pr + "bmp");
        if (pr > 100) {
            imgBloodPulse.setImageResource(R.drawable.online_test_blood_pulse_high);
        } else if (pr > 60) {
            imgBloodPulse.setImageResource(R.drawable.online_test_blood_pulse_common);
        } else {
            imgBloodPulse.setImageResource(R.drawable.online_test_blood_pulse_low);
        }
        uploadBloodOxygen(spo2Data);
    }

    /**
     * 设置身高体重
     *
     * @param bodyData
     */
    private void setBodyFat(BodyData bodyData, String resultHex) {
        dialogDismiss();
        llOnlineTestEmpty.setVisibility(View.GONE);
        llBloodSugar.setVisibility(View.GONE);
        llBloodOxygen.setVisibility(View.GONE);
        Log.i("yys", "setBodyFat====");
        llBodyFat.setVisibility(View.VISIBLE);
        llBloodPressure.setVisibility(View.GONE);
        String weight = "";
        if (TextUtils.isEmpty(resultHex)) {
            //怡成体脂称计算
            weight = bodyData.getWeight() + "";
        } else {
            //一体机计算
            //resultHex = "573A3037342E3620483A3135382E350D0A";
            resultHex = StringToHexUtils.hexStr2Str(resultHex);
            String[] resultArray = resultHex.split("\\s+");
            if (2 == resultArray.length) {
                String heightTotal = resultArray[1];
                String weightTotal = resultArray[0];
                height = heightTotal.substring(heightTotal.indexOf(":") + 1);
                weight = weightTotal.substring(weightTotal.indexOf(":") + 1);
                if (weight.startsWith("0")) {
                    weight = weight.substring(weight.indexOf("0") + 1);
                }
            }
        }
        //设置体重
        tvOnlineTestWeight.setText(weight + "kg");
        tvWeight.setText(weight + "kg");
        double weightDouble = TurnsUtils.getDouble(weight, 0);
        double doubleHeightM = TurnsUtils.getDouble(height, 0) / 100;
        double bmi = weightDouble / (doubleHeightM * doubleHeightM);
        double bmiOneBit = NumberUtils.saveOneBitOneRound(bmi);
        tvBmi.setText(bmiOneBit + "");
        //bmi18.5~23.9
        if (bmi > 23.9) {
            imgBmi.setImageResource(R.drawable.body_fat_high);
        } else if (bmi > 18.5) {
            imgBmi.setImageResource(R.drawable.body_fat_common);
        } else {
            imgBmi.setImageResource(R.drawable.body_fat_low);
        }
        //设置时间
        String checkTime = TimeUtils.getNowString();
        tvCheckTime.setText("最新检测时间:" + checkTime);
        //最后上传
        uploadBodyFat(weight, checkTime);
    }

    //设置数据结束
    //上传数据开始

    /**
     * 上传血糖
     *
     * @param bean
     */
    private void uploadBloodSugar(OnlineTestGetBloodSugar bean) {
        String userid = getArguments().getString("userid");
        String glucosevalue = bean.getGlucosevalue() + "";
        String category = bean.getCategory() + "";
        long datetime = bean.getDatetime();
        String checkTime = TimeUtils.millis2String(datetime * 1000L);
        SPStaticUtils.put("checkTime", checkTime);
        String type = bean.getType() + "";
        HashMap<String, Object> map = new HashMap<>();
        map.put("uid", userid);
        map.put("docsugar", "1");
        map.put("glucosevalue", glucosevalue);
        map.put("category", category);
        map.put("datetime", checkTime);
        map.put("type", type);
        RxHttp.postForm(XyUrl.ADD_BLOOD_SUGAR)
                .addAll(map)
                .asResponse(String.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String response) throws Exception {
                        ToastUtils.showShort(R.string.ble_data_upload_success);
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {

                    }
                });

    }

    /**
     * 上传血压
     *
     * @param high
     * @param low
     * @param checkTime
     */
    private void uploadBloodPressure(double high, double low, String checkTime) {
        String userid = getArguments().getString("userid");
        SPStaticUtils.put("checkTime", checkTime);
        HashMap map = new HashMap<>();
        map.put("uid", userid);
        map.put("systolic", high);//收缩
        map.put("diastole", low);//舒张
        map.put("datetime", checkTime);
        map.put("type", "1");
        RxHttp.postForm(XyUrl.ADD_BLOOD)
                .addAll(map)
                .asResponse(String.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        ToastUtils.showShort(R.string.ble_data_upload_success);
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {

                    }
                });
    }

    /**
     * 上传血氧
     *
     * @param spo2Data
     */
    private void uploadBloodOxygen(Spo2Data spo2Data) {
        String userid = getArguments().getString("userid");
        int spo2 = spo2Data.getSpo2();//血氧饱和度
        int pr = spo2Data.getPr();//脉率值
        long systemTime = spo2Data.getSystemTime();
        String checkTime = TimeUtils.millis2String(systemTime);
        SPStaticUtils.put("checkTime", checkTime);
        HashMap<String, Object> map = new HashMap<>();
        map.put("uid", userid);
        map.put("oxygen", spo2 + "");
        map.put("bpmval", pr + "");
        map.put("type", 1);
        map.put("datetime", checkTime);
        RxHttp.postForm(XyUrl.ADD_BLOOD_OXYGEN)
                .addAll(map)
                .asResponse(String.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String response) throws Exception {
                        ToastUtils.showShort(R.string.ble_data_upload_success);
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {

                    }
                });
    }

    /**
     * 上传身高体重
     *
     * @param
     */
    private void uploadBodyFat(String weight, String checkTime) {
        String userid = getArguments().getString("userid");
        SPStaticUtils.put("checkTime", checkTime);
        HashMap<String, Object> map = new HashMap<>();
        map.put("uid", userid);
        map.put("height", height);
        map.put("weight", weight);
        map.put("datetime", checkTime);
        map.put("type", 1);
        RxHttp.postForm(XyUrl.ADD_BMI)
                .addAll(map)
                .asResponse(String.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String response) throws Exception {
                        ToastUtils.showShort(R.string.ble_data_upload_success);
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {

                    }
                });
    }
    //上传数据结束

    //YC BLE回调开始
    @Override
    public void onStateResponed(int i) {
        Log.e(TAG, "onStateResponed" + i);
        //        switch (i) {
        //            case 6://设备扫描
        //                break;
        //            case 3://开始连接设备
        //                break;
        //            case 4://设备连接成功
        //                break;
        //            case 9://连接失败
        //                break;
        //            case 16://接收数据完毕
        //                break;
        //        }
        if (6 == i || 3 == i) {

        } else if (4 == i) {
            dialogConnectSuccess();
        } else if (16 == i) {
            isConnectSuccess = true;
            dialogDismiss();
        }
    }


    /**
     * 血氧饱和度
     *
     * @param spo2Data
     */

    @Override
    public void onSpo2Changed(Spo2Data spo2Data) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.e(TAG, "血氧测量完成");
                setBloodOxygen(spo2Data);
            }
        });
    }


    /**
     * 体成份分析发生变化时的处理过程
     *
     * @param bodyData
     */
    @Override
    public void onBodyChanged(BodyData bodyData) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.e(TAG, "体脂测量完成");
                setBodyFat(bodyData, "");
            }
        });
    }


    /*** 当心电图波形发生变化时，该接口函数主要用来处理心电图的实时变化（可用30秒模式，也可用于连续模式
     * @param arrayList 从蓝牙接收的每一包的心电图数
     * @param workingmode 工作模式30秒模式和连续模式
     */
    @Override
    public void onEcgWaveChanged(ArrayList<Integer> arrayList, int workingmode, int hr, int state) {

    }

    /***心电参数发生变化的处理过程
     * @param ecgData 心电结构
     */
    @Override
    public void onEcgChanged(EcgData ecgData) {

    }

    /**
     * 血糖
     *
     * @param gluData
     */
    @Override
    public void onGluChanged(GluData gluData) {

    }

    @Override
    public UserInfo getUserInfo() {
        return null;
    }

    /**
     * 血压
     *
     * @param nibpData
     */
    @Override
    public void onNibpChanged(NibpData nibpData) {

    }

    /**
     * 尿11项参数发生变化时的处理过程
     *
     * @param urineData 尿液分析结构
     */
    @Override
    public void onUrineChanged(UrineData urineData) {

    }


    /**
     * 血脂
     *
     * @param bloodfatData
     */
    @Override
    public void onBFChanged(BloodfatData bloodfatData) {

    }

    /**
     * 体温
     *
     * @param tempData
     */
    @Override
    public void onTempChanged(TempData tempData) {

    }

    /**
     * 解除Ble-Yc绑定
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        //if (bleController != null) {
        //    bleController.onDestroy();
        //}
        unregisterBleStatusReceiver();
    }

    //YC BLE回调结束


    //权限结束


    //蓝牙监听开始

    /**
     * 注册蓝牙状态监听器
     */
    private void registerBleStatusReceiver() {
        if (receiver == null) {
            receiver = new BleStatusBroadcastReceiver(this);
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        intentFilter.addAction(BluetoothDevice.ACTION_ACL_CONNECTED);
        intentFilter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED);
        intentFilter.addAction("android.bluetooth.BluetoothAdapter.STATE_OFF");
        intentFilter.addAction("android.bluetooth.BluetoothAdapter.STATE_ON");
        getActivity().registerReceiver(receiver, intentFilter);
    }

    /**
     * 取消注册监听
     */
    private void unregisterBleStatusReceiver() {
        if (receiver != null) {
            getActivity().unregisterReceiver(receiver);
            receiver = null;
        }
    }

    /**
     * 蓝牙关闭状态回调
     */
    @Override
    public void onBleClose() {
        //取消定时任务
        cancelTask();
        //转圈圈取消
        dialogDismiss();
        ToastUtils.showShort("您的蓝牙已断开,请重新打开");
    }

    //蓝牙监听结束

    /**
     * 取消定时任务
     */
    private void cancelTask() {
        if (timer != null) {
            timer.cancel();
        }
    }

    /**
     * 打开蓝牙的回调
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == OPEN_BLE) {
            switch (resultCode) {
                case Activity.RESULT_OK://打开成功
                    //Log.e(TAG, "打开成功");
                    dialogStartConnect();
                    startScanBpAndAio();
                    //开始一个延时操作
                    dialogPostDelay();
                    break;
                case Activity.RESULT_CANCELED://打开失败
                    //Log.e(TAG, "打开失败");
                    ToastUtils.showShort("请重新获取");
                    break;
            }
        }
    }
}
