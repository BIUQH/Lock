var exec = require('cordova/exec');
/**
*初始化锁SDK
*无参数
*/
exports.coolMethod = function (onSuccess, onError) {
    exec(onSuccess, onError,  "Lock", "coolMethod", []);
};
/**
*开锁
*message参数格式{ lockData:"锁数据",lockMac:"锁Mac地址"}
*/
exports.open = function (message, onSuccess, onError) {
    exec(onSuccess, onError,  "Lock", "open", [message]);
};
/**
*开启蓝牙
*/
exports.bluetooth = function (onSuccess, onError) {
    exec(onSuccess, onError,  "Lock", "bluetooth", []);
};
