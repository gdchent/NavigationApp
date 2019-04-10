import React, { Component, PureComponent } from 'react';
import { Platform, StyleSheet, Text, View, Image, TouchableOpacity, NativeModules, DeviceEventEmitter, Linking, ToastAndroid } from 'react-native';
import { Base64 } from 'js-base64'
import aesjs from 'aes-js'
import CryptoJS from 'crypto-js'
import AppUpdateModelBox from '../components/AppUpdateModelBox'
/**
 * Home主页面
 */

url = ""
class HomeScreen extends Component {

    constructor(props) {
        super(props)

    }
    componentDidMount = () => {
        DeviceEventEmitter.addListener('nativeCallRn', (msg) => {
            let title = "nativeCallRn：" + msg
            console.log('nativeCallRn',title)
            this.startActivity()
        })
        DeviceEventEmitter.addListener('nativeCallRnDetail', (msg) => {
            let title = "Detail" + msg
            console.log('detail',title)
             this.startActivity()
        })
        //打开IOS特定的浏览器
        this.openWebUrl()
    }


 
    //解密方法
    decrypt = (context, key, iv) => {
        const encryptedBase64 = CryptoJS.enc.Base64.parse(context);
        const str = CryptoJS.enc.Base64.stringify(encryptedBase64);
        const decrypt = CryptoJS.AES.decrypt(str, key, {
            iv: iv,
            mode: CryptoJS.mode.CBC,
            padding: CryptoJS.pad.Pkcs7
        });
        const decryptedStr = decrypt.toString(CryptoJS.enc.Utf8);
        console.log(decryptedStr.toString());
        return decryptedStr.toString();
    }


    render() {
        return (
            <View style={{ flex: 1 }}>
                <TouchableOpacity
                    style={{ borderWidth: 1, borderRadius: 1 }}
                    onPress={() => {
                        this.startActivity()
                    }}
                >
                    <Text>startActivityForResult模式</Text>
                </TouchableOpacity>
                <TouchableOpacity onPress={() => {
                    this.rnCallNative() //点击跳转到原生交互
                }}>
                    <Text>跳转到原生拨号界面</Text>
                </TouchableOpacity>

                <TouchableOpacity onPress={() => {
                    let json={"key":'promise_value'}
                    this.rnCallNativeFromCallback(JSON.stringify(json)) //点击跳转到原生交互
                }}>
                    <Text>callback方式调用原生同时接收到原生的回调结果</Text>
                </TouchableOpacity>



                <TouchableOpacity onPress={() => {
                    let json={"key":'value'}
                    this.promiseComm(JSON.stringify(json)) //点击跳转到原生交互
                }}>
                    <Text>Promise方式调用原生同时接收到原生的回调结果</Text>
                </TouchableOpacity>



                
                <TouchableOpacity onPress={() => {
                      this.gotoActivity()
                }}>
                    <Text>点击跳转到原生</Text>
                </TouchableOpacity>


                
                <AppUpdateModelBox
                    ref={(ref) => {
                        this.AppUpdateModelBox = ref;
                    }}
                    onPress={() => {
                        this.openWebUrl()
                    }}
                    onSelect={
                        () => {

                        }
                    }
                >
                </AppUpdateModelBox>
            </View>
        )
    }

    gotoActivity=()=>{
        NativeModules.updateDownloadModule.gotoActivity()
    }

    //跳转原生拨号界面
    rnCallNative = () => {
        let phone = '10086';
        NativeModules.updateDownloadModule.rnCallNative(phone);
    }

    /**
       * Callback 通信方式
       */
      rnCallNativeFromCallback=(msg)=> {
        NativeModules.updateDownloadModule.rnCallNativeFromCallback(msg, (result) => {
            ToastAndroid.show("CallBack收到消息:" + result, ToastAndroid.SHORT);
        })
    }


     /**
     * Promise 通信方式
     */
    promiseComm=(msg) =>{
        NativeModules.updateDownloadModule.rnCallNativeFromPromise(msg).then(
            (result) =>{
                ToastAndroid.show("Promise收到消息:" + result, ToastAndroid.SHORT)
            }
        ).catch((error) =>{console.log(error)});
    }

    openWebUrl = () => {

        let url="http://www.baidu.com";
        console.log('webUrl', url)
        if (!!url) {
            Linking.canOpenURL(url).then(supported => {
                if (!supported) {
                    console.log('Can\'t handle url: ' + url);
                } else {
                    return Linking.openURL(url);
                }
            }).catch(err => console.error('An error occurred', err));
        } else {

        }

    }


    goToPage = () => {
        const { navigation } = this.props
        navigation.navigate('Mine', {
            mine: 'mine',
        })
    }

    //开启新页面
    startActivity = () => {
        console.log('onclick')
        const { navigation } = this.props
        console.log('navigation', navigation)
        navigation.navigate('Detail', {
            key: 'abc',
            // callback: (data) => {
            //     console.log('data', data)
            // }
        })

    }

}

export default HomeScreen 