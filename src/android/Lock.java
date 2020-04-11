package ljr.cordova;

import android.bluetooth.BluetoothAdapter;

import com.ttlock.bl.sdk.api.TTLockClient;
import com.ttlock.bl.sdk.callback.ControlLockCallback;
import com.ttlock.bl.sdk.entity.LockError;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaArgs;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONException;

/**
 * Created by 94085 on 2020/4/11.
 */

public class Lock extends CordovaPlugin {
private BluetoothAdapter mBluetoothAdapter = null;

    @Override
    public boolean execute(String action, CordovaArgs args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("coolMethod")) {
            this.scanDevice(callbackContext);
            return true;
        }
        if (action.equals("open")){
            this.doUnlock(callbackContext, args.getJSONObject(0).get("lockData").toString(), args.getJSONObject(0).get("lockMac").toString());
            return true;
        }
        if (action.equals("bluetooth")){
            this.Bluetooth(callbackContext);
            return true;
        }
        return false;
    }
    private void scanDevice(CallbackContext callbackContext) {
        try {
            TTLockClient.getDefault().prepareBTService(webView.getContext());
            callbackContext.success("SDK初始化成功");

        }
        catch (Exception e){
            callbackContext.error(e.toString());
        }
    }

    public void doUnlock(CallbackContext callbackContext, String a, String b){

        Bluetooth(callbackContext);
        TTLockClient.getDefault().controlLock( 3, a, b,new ControlLockCallback() {
            @Override
            public void onControlLockSuccess(int lockAction, int battery, int uniqueId) {
                callbackContext.success("开锁成功");
            }

            @Override
            public void onFail(LockError error) {
                callbackContext.error(error.toString());
            }
        });
    }
    public void Bluetooth(CallbackContext callbackContext){
        if (mBluetoothAdapter == null)
            mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();     //初始化蓝牙适配器
        // 判断手机是否支持蓝牙
        if (mBluetoothAdapter == null) {
            callbackContext.error( "此设备不支持蓝牙或没有开启蓝牙权限" );
            return;
        }
        if (!mBluetoothAdapter.isEnabled()) {
            mBluetoothAdapter.enable();
        }
    }
}
