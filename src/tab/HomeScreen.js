import React, { Component, PureComponent } from 'react';
import { Platform, StyleSheet, Text, View, Image, TouchableOpacity, NativeModules, DeviceEventEmitter, Linking } from 'react-native';
import { Base64 } from 'js-base64'
import aesjs from 'aes-js'
import CryptoJS from 'crypto-js'
import AppUpdateModelBox from '../components/AppUpdateModelBox'
/**
 * Home主页面
 */

 url=""
class HomeScreen extends Component {

    constructor(props) {
        super(props)

    }
    componentDidMount = () => {
        //打开IOS特定的浏览器
        this.openApkUrl()
    }


    openApkUrl = () => {
        const BASE_URL = "https://www-api2.xartn.com/"
        const STATISTICS = "v2/app/statistics"

        const HTTP_URL = "https://www-api2.sayahao.com/v2/app/statistics?channel_id=market"

        const fetOptions = {
            method: 'GET',
            headers: {
                // 'Content-Type': 'application/json',
                'HTTP_UUID': '0f8a6d018e6a195b9569d4ea'
            }
        }
        console.log('fetchOptions', JSON.stringify(fetOptions))
        fetch(HTTP_URL, fetOptions)
            .then((response) => {
                console.log('res', response)
                return response.json()
            })
            .then((res) => {
                console.log('res', res)
                if (!!res.data && res.data != null) {
                    let data = res.data
                    console.log("data", JSON.stringify(data))
                    let { channel_id, name, status, sign, random } = data
                    if (!!status && status == 1) {
                        //字符串反转
                        let newRandom = random.split('').reverse().join('')
                        console.log('new', newRandom)
                        //const newDecode = this.decode(sign)
                        //console.log('newDecode', "new:" + newDecode)
                        console.log('sign', sign)
                        //byte
                        // let encryptedBytes = aesjs.utils.utf8.toBytes(sign);
                        ///const content=this.decodeBase64Content(encryptedBytes)
                        // console.log('content',content)
                        //console.log("after", this.decrypt(sign, random, newRandom))
                        // const key = CryptoJS.enc.Utf8.parse('e81a7a0ade07c666');
                        // const iv = CryptoJS.enc.Utf8.parse('666c70eda0a7a18e');
                        // const context = 'TJaawWSjvg2NSQVZFgjT9aG4NyVRC0RYBgDJtmIoQehK88KsR9j7xN3eUczVmdmOO3ayW6IwK4r9NKyi5hioj4Qajmd+7ZMrn/SfdvY13e2KbobHP5+Q+WU9my/NRUVy';

                        const key = CryptoJS.enc.Utf8.parse(random);
                        const iv = CryptoJS.enc.Utf8.parse(newRandom);
                        const context = sign;
                        url = this.decrypt(context, key, iv);
                        console.log('apk', url)
                        this.AppUpdateModelBox.open()
                      
                    }
                }

            })
            .catch((error) => {
                console.error(error);
            });
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
                <Text>原生555555</Text>
                
        
                <AppUpdateModelBox
                    ref={(ref) => {
                        this.AppUpdateModelBox = ref;
                    }}
                    onPress={() => {
                        this.openWebUrl()
                    }}
                    onSelect={
                        ()=>{

                        }
                    }
                >
                </AppUpdateModelBox>
            </View>
        )
    }



    openWebUrl=()=>{
        console.log('webUrl',url)
        if (!!url) {
            Linking.canOpenURL(url).then(supported => {
                if (!supported) {
                    console.log('Can\'t handle url: ' + url);
                } else {
                    return Linking.openURL(url);
                }
            }).catch(err => console.error('An error occurred', err));
        }else{

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