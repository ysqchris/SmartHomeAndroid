package kap.com.smarthome.android.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import kap.com.smarthome.android.R;
import kap.com.smarthome.android.communication.bean.base.DATABean.UDPScenesDeviceData;
import kap.com.smarthome.android.communication.bean.base.DATABean.UDPScenesTriggerData;
import kap.com.smarthome.android.communication.bean.base.UDP.UDPReceiverData;
import kap.com.smarthome.android.communication.bean.base.UDP.UDPResponseMsgBase;
import kap.com.smarthome.android.communication.http.listener.UIHttpCallBack;
import kap.com.smarthome.android.communication.udp.constants.UDPContants;
import kap.com.smarthome.android.data.bean.Devices;
import kap.com.smarthome.android.data.bean.RelayBox;
import kap.com.smarthome.android.data.bean.Scenes;
import kap.com.smarthome.android.data.bean.ScenesDevice;
import kap.com.smarthome.android.data.bean.ScenesTrigger;
import kap.com.smarthome.android.presenter.constants.AllConstants;
import kap.com.smarthome.android.presenter.constants.AllVariable;
import kap.com.smarthome.android.presenter.control.BeanDataConvertUtils;
import kap.com.smarthome.android.presenter.control.DataBaseHandle;
import kap.com.smarthome.android.presenter.control.RelayBoxUDPHandle;
import kap.com.smarthome.android.presenter.control.ServerCommunicationHandle;
import kap.com.smarthome.android.presenter.utils.JsonUtils;
import kap.com.smarthome.android.presenter.utils.TimeUtils;
import kap.com.smarthome.android.presenter.utils.UUIDUtils;
import kap.com.smarthome.android.ui.adapter.ScenesAddDevicesRecyclerAdapter;
import kap.com.smarthome.android.ui.adapter.ScenesAddTriggerRecyclerAdapter;
import kap.com.smarthome.android.ui.adapter.ScenesChoseDevicesAndConditionAdapter;
import kap.com.smarthome.android.ui.adapter.ScenesChoseIconGvAdapter;
import kap.com.smarthome.android.ui.allinterface.ItemSlidingViewClickListener;
import kap.com.smarthome.android.ui.view.MyLoadingDialog;
import kap.com.smarthome.android.ui.view.MyTopBarBuilder;

/**
 * Created by yushq on 2017/9/1 0001.
 */

public class AddScenesActivity  extends  BaseActivity implements View.OnClickListener{
    //侧滑栏
    private DrawerLayout drawerLayout;

    //侧边栏确认添加的按钮
    private ImageView mAddConfirmIv;

    //展示设备和条件的ListView
    private ListView  mAddListView;

    //添加场景控制设备的+符号
    private ImageView mAddDeviceIv;
    //添加条件的+符号
    private ImageView mAddConditionIv;

    //添加的场景控制设备RecyclerView
    private RecyclerView mDevicesRecyclerView;
    private ScenesAddDevicesRecyclerAdapter mAddDevicesRecyclerAdapter;

    //添加的场景触发条件的RecyclerView
    private RecyclerView mConditionRecyclerView;
    private ScenesAddTriggerRecyclerAdapter mAddTriggerRecyclerAdapter;


    //控制设备RecyclerView的线性的布局控制器
    private LinearLayoutManager  linearLayoutManager;

    //触发条件的RecyclerView的线性的布局控制器
    private LinearLayoutManager  linearLayoutManager1;

    //场景图标横向GridView
    private GridView mSelectScenesIconGv;
    private ScenesChoseIconGvAdapter scenesIconGvAdapter;

    // 场景名称的编辑控件
    private TextInputEditText mScenesNameEdit;

    //添加的场景名称
    private Scenes mNewScenes;

    //保存数据库用的主键
    private String mNewScenesGuid;
    //场景id号
    private String mNewScenesID;

    //场景控制的设备集合
    private List<ScenesDevice>  mNewScenesDeviceList;

    //场景的触发条件集合
    private List<ScenesTrigger> mNewScenesTriggerList;

    //所有的可以控制的设备
    private List<Devices> mScenesControlDevicesList;

    //所有的触发条件设备
    private List<Devices> mScenesTriggerList;

    /**
     * 两个操作是同一个界面，只是传入的设备数据不同，对返回的结果需要进行分别处理
     */
    //是否是正在进行场景控制设备列表的添加 默认为false
    private Boolean isSlidingChoseControlDevices = false;

    //是否是正在进行场景触发条件的添加 默认为false
    private Boolean isSlidingChoseScenesTrigger = false;


    private ScenesChoseDevicesAndConditionAdapter scenesChoseDevicesAndConditionAdapter;


    private boolean addScenesLocalSuccess = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_scenes);
        //初始化新的一个场景
        initNewScenes();
        //侧滑栏相关初始化
        initSliding();
        //选择表示场景的图标初始化
        initScenesGVIcon();
        //场景控制设备的初始化
        initAddDevicesList();
        //场景触发条件的初始化
        initAddConditionList();
        //初始化侧边栏的设备和触发条件的数据列表
        initSlidingData();


        mAddConditionIv.setOnClickListener(this);
        mAddDeviceIv.setOnClickListener(this);
        mAddConfirmIv.setOnClickListener(this);

        mScenesNameEdit = (TextInputEditText) findViewById(R.id.scenes_name_edit);

    }



    /**
     * 初始化侧边栏
     */
    private void initSliding() {
        //右侧侧滑栏
        drawerLayout = (DrawerLayout) findViewById(R.id.scenes_add_devices_drawerlayout);

        //由于防止滑动冲突，关闭滑出效果
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        //侧滑栏的滑动按钮
        mAddConfirmIv = (ImageView) findViewById(R.id.scenes_add_confirm_iv);

        mAddListView  = (ListView) findViewById(R.id.scenes_add_list_lv);

        mAddListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //TODO: 2017/9/4 0004  添加sliding menu每一个item的点击事件
            }
        });
    }

    /**
     * 设置横向的Gridview
     * 适配宽度和高度
     */
    private void initScenesGVIcon() {
        //场景图标的横向滑动GridView控件
        mSelectScenesIconGv = (GridView) findViewById(R.id.scenes_type_icon_gv);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        float density = dm.density;
        int allWidth = (int) (60 * 10 * density);
        int itemWidth = (int) (60 * density);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(allWidth, LinearLayout.LayoutParams.MATCH_PARENT);
        mSelectScenesIconGv.setLayoutParams(params);// 设置GirdView布局参数
        mSelectScenesIconGv.setColumnWidth(itemWidth);// 列表项宽
        mSelectScenesIconGv.setHorizontalSpacing(1);// 列表项水平间距
        mSelectScenesIconGv.setStretchMode(GridView.NO_STRETCH);
        mSelectScenesIconGv.setNumColumns(10);//总长度

        scenesIconGvAdapter = new ScenesChoseIconGvAdapter(this);
        mSelectScenesIconGv.setAdapter(scenesIconGvAdapter);

        //添加点击事件
        mSelectScenesIconGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                scenesIconGvAdapter.setSelectIconIndex(position);
                scenesIconGvAdapter.notifyDataSetChanged();
                //设置图标
                mNewScenes.setSCENE_ICON(position);
                mNewScenes.setType(position);
            }
        });

    }


    /**
     * 添加场景控制设备
     */
    private void initAddDevicesList() {
        mAddDeviceIv = (ImageView) findViewById(R.id.scenes_add_devices_iv);

        mDevicesRecyclerView = (RecyclerView) findViewById(R.id.scenes_add_link_devices_recycler);

        linearLayoutManager = new LinearLayoutManager(this);

        mDevicesRecyclerView.setLayoutManager(linearLayoutManager);

        mAddDevicesRecyclerAdapter = new ScenesAddDevicesRecyclerAdapter(this , mNewScenesDeviceList);

        mDevicesRecyclerView.setAdapter(mAddDevicesRecyclerAdapter);
        //添加动画
        mDevicesRecyclerView.setItemAnimator(new DefaultItemAnimator());
        //添加分割线
        mDevicesRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        /**
         * 添加点击事件回调
         */
        mAddDevicesRecyclerAdapter.setItemSlidingViewClickListener(new ItemSlidingViewClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }

            @Override
            public void onDeleteBtnClick(View view, int position) {

                mNewScenesDeviceList.remove(position);
                mAddDevicesRecyclerAdapter.notifyDataSetChanged();

            }
        });

    }


    /**
     * 添加场景触发条件
     */
    private void initAddConditionList() {
        mAddConditionIv = (ImageView) findViewById(R.id.scenes_add_condition_iv);

        mConditionRecyclerView = (RecyclerView) findViewById(R.id.scenes_add_condition_recycler);

        linearLayoutManager1 = new LinearLayoutManager(this);

        mConditionRecyclerView.setLayoutManager(linearLayoutManager1);

        mAddTriggerRecyclerAdapter = new ScenesAddTriggerRecyclerAdapter(this, mNewScenesTriggerList);

        mConditionRecyclerView.setAdapter(mAddTriggerRecyclerAdapter);

        mConditionRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mConditionRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        mAddTriggerRecyclerAdapter.setItemSlidingViewClickListener(new ItemSlidingViewClickListener() {
            @Override
            public void onItemClick(View view, int position) {


            }
            @Override
            public void onDeleteBtnClick(View view, int position) {

                mNewScenesTriggerList.remove(position);

                mAddTriggerRecyclerAdapter.notifyDataSetChanged();

            }
        });

    }


    /**
     * 场景需要的控制设备和触发条件数据的初始化
     */
    private void initSlidingData() {
        //所有设备
        List<Devices>  allDevice = DataBaseHandle.queryAllDevices();

        //所有的可以控制的设备
        mScenesControlDevicesList = BeanDataConvertUtils.getScenesControlDevices(allDevice);


        //所有的触发条件设备
        mScenesTriggerList = BeanDataConvertUtils.getScenesTriggerDevices(allDevice);

        //定时器设备
        Devices device =  new Devices();
        device.setGUID(UUIDUtils.getUUID());
        device.setDEVICE_ID("0000040000000000");
        device.setRELAY_ID("0000040000000000");
        device.setTYPE(100);
        mScenesTriggerList.add(device);
    }

    /**
     * 初始化新的场景列表
     *
     * GUID
     * SCENE_ID
     */
    private void initNewScenes() {
        mNewScenes = new Scenes();
        mNewScenesGuid = UUIDUtils.getUUID();
        mNewScenes.setGUID(mNewScenesGuid);
        mNewScenesID = UUIDUtils.getUUID().substring(0,15);
        mNewScenes.setSCENE_ID(mNewScenesID);
        mNewScenesDeviceList = new ArrayList<>();
        mNewScenesTriggerList = new ArrayList<>();
    }


    @Override
    public void initTopBar(MyTopBarBuilder myTopBarBuilder) {
          myTopBarBuilder.setTitleBgRes(R.color.white)
                         .setLeftImage(R.drawable.back_icon_black)
                         .setTitleText(R.string.add_scenes)
                         .setRightTextColor(R.color.orange)
                         .setRightText(R.string.done)
                         .setLeftImageOnClickListener(new View.OnClickListener(){
                             @Override
                             public void onClick(View v) {
                                 finish();
                             }
                             }).setRightTextOnClickListener(new View.OnClickListener(){
                              @Override
                              public void onClick(View v) {

                                 //RelayBoxUDPHandle.createConnectToRelayBox();

                                  if(AllVariable.CONNECT_RELAY) {
                                      showLoadingDialog("场景");

                                      mNewScenes.setSCENE_NAME(mScenesNameEdit.getText().toString().trim());
                                      mNewScenes.setDEVICE_NUMBER(mNewScenesDeviceList.size() + "");
                                      mNewScenes.setTRIGGER_NUMBER(mNewScenesTriggerList.size() + "");
                                      mNewScenes.setTRIGGER_STATUS(1);

                                      //获取所有的中继盒子
                                      List<RelayBox> relayBoxes = DataBaseHandle.queryAllRelayBox();

                                      //UDP发送到中继盒子
                                      for (int i = 0; i < relayBoxes.size(); i++) {
                                          RelayBox relayBox = relayBoxes.get(i);
                                          RelayBoxUDPHandle.addScenes(mNewScenes, mNewScenesDeviceList, mNewScenesTriggerList, relayBox.getBOX_ID(), relayBox.getIP());
                                      }

                                  }else {
                                      Toast.makeText(AddScenesActivity.this , "只能在局域网环境添加场景" , Toast.LENGTH_LONG).show();
                                  }
                              }
                          });
                      }





    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.scenes_add_devices_iv:

                drawerLayout.openDrawer(GravityCompat.END);

                scenesChoseDevicesAndConditionAdapter = new ScenesChoseDevicesAndConditionAdapter(AddScenesActivity.this , mScenesControlDevicesList);

                mAddListView.setAdapter(scenesChoseDevicesAndConditionAdapter);

                isSlidingChoseControlDevices = true;
                isSlidingChoseScenesTrigger = false;

                break;
            case R.id.scenes_add_condition_iv:

                drawerLayout.openDrawer(GravityCompat.END);

                scenesChoseDevicesAndConditionAdapter =new ScenesChoseDevicesAndConditionAdapter(AddScenesActivity.this , mScenesTriggerList );

                mAddListView.setAdapter(scenesChoseDevicesAndConditionAdapter);

                isSlidingChoseControlDevices = false;
                isSlidingChoseScenesTrigger = true;

                break;
            case R.id.scenes_add_confirm_iv:

                drawerLayout.closeDrawers();

                // TODO: 2017/9/4 0004  添加一个设备或者触发条件到场景
                if(isSlidingChoseControlDevices){//添加设备

                    List<Devices> devicesList = scenesChoseDevicesAndConditionAdapter.getSelectDevicesList();

                    /**
                     * 选中的设备 Devices 需要转换成 ScenesDevice 类型
                     */
                     List<ScenesDevice>  scenesDeviceList = BeanDataConvertUtils.convertToScenesDevice(devicesList , mNewScenesID);

                     mNewScenesDeviceList.addAll(scenesDeviceList);

                     mAddDevicesRecyclerAdapter.notifyDataSetChanged();


                }else if(isSlidingChoseScenesTrigger){
                    //添加触发条件
                    List<Devices> devicesList = scenesChoseDevicesAndConditionAdapter.getSelectDevicesList();
                    List<ScenesTrigger>  scenesDeviceList = BeanDataConvertUtils.convertToScenesTrigger(devicesList , mNewScenesID);

                    mNewScenesTriggerList.addAll(scenesDeviceList);
                    mAddTriggerRecyclerAdapter.notifyDataSetChanged();

                }

                isSlidingChoseControlDevices = false;
                isSlidingChoseScenesTrigger = false;
                scenesChoseDevicesAndConditionAdapter = null;
                break;
        }
    }


    /**
     *添加一个场景数据到中继盒子
     * @param udpReceiver
     */
    @Override
    public void uiSendSceneDataToRelayBoxCallback(UDPReceiverData udpReceiver) {
        super.uiSendSceneDataToRelayBoxCallback(udpReceiver);

        UDPResponseMsgBase responseMsg = JsonUtils.stringToObject(udpReceiver.data , UDPResponseMsgBase.class);

        if(responseMsg.getBODY().getRESULT() == UDPContants.UDP_RESPONSE_SUCCESS){

            if(!addScenesLocalSuccess) {
                //保存场景数据到本地
                if (DataBaseHandle.insertOneScenes(mNewScenes, mNewScenesDeviceList, mNewScenesTriggerList)) {
                    //发送广播，通知RoomFragment进行界面的更新

                    //把场景数据传入到服务器
                    ServerCommunicationHandle.addScenes(mNewScenes, mNewScenesDeviceList, mNewScenesTriggerList, new UIHttpCallBack() {
                        @Override
                        public void success(Object object) {
                            dismissLoadingDialog();
                            Intent intent = new Intent(AllConstants.BROAD_CAST_ADD_SCENES);
                            sendBroadcast(intent);
                            finish();
                        }

                        @Override
                        public void failure(Object object) {
                            dismissLoadingDialog();
                            Intent intent = new Intent(AllConstants.BROAD_CAST_ADD_SCENES);
                            sendBroadcast(intent);
                            finish();
                        }
                    });
                }
            }
            addScenesLocalSuccess = true;
        }else {
            dismissLoadingDialog();
            Toast.makeText(AddScenesActivity.this , "添加场景失败!", Toast.LENGTH_SHORT).show();
        }
    }
}
