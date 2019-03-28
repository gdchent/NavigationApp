import React, { Component, PureComponent } from 'react';
import { Platform, StyleSheet, Text, View, TouchableOpacity } from 'react-native';
//import RNfetchBlob from 'react-native-fetch-blob'
import RNFS from 'react-native-fs';
//const android = RNfetchBlob.android
/**
 * Home主页面
 */
class HomeScreen extends Component {

    render() {
        return (
            <View style={{ flex: 1, backgroundColor: 'red' }}>
                <TouchableOpacity
                    style={{ borderWidth: 1, borderRadius: 1 }}
                    onPress={() => {
                        this.startActivity()
                    }}
                >
                    <Text>startActivityForResult模式</Text>

                </TouchableOpacity>
                <TouchableOpacity onPress={() => {
                    this.goToPage()
                }}>
                    <Text>
                        点击去个人中心
                    </Text>
                </TouchableOpacity>
                <TouchableOpacity
                    onPress={() => {
                        this.startActivity()
                    }
                    }
                >
                    <Text>startActivity---Detail</Text>
                </TouchableOpacity>

                <Text>新版本0.0.2骚2===========继续骚5555566666</Text>

                <TouchableOpacity
                    onPress={() => {
                        //this.downloadFile()
                        this.downLoadFile()
                    }}
                >

                    <Text>点击下载</Text>
                </TouchableOpacity>
                <TouchableOpacity
                    onPress={() => {
                        //this.downloadFile()
                        this.downLoadFile()
                    }}
                >

                    <Text>fetch方式  下载</Text>
                </TouchableOpacity>
            </View>
        )
    }

    downLoadFile = () => {
        // 音频
        const downloadDest = `${RNFS.MainBundlePath}/${((Math.random() * 1000) | 0)}.apk`;
        // http://wvoice.spriteapp.cn/voice/2015/0902/55e6fc6e4f7b9.mp3
        const formUrl = 'http://180.153.105.144/dd.myapp.com/16891/E2F3DEBB12A049ED921C6257C5E9FB11.apk';

        const options = {
            fromUrl: formUrl,
            //toFile: downloadDest,
            background: true,
            begin: (res) => {
                console.log('begin', res);
                console.log('contentLength:', res.contentLength / 1024 / 1024, 'M');
            },
            progress: (res) => {
                console.log('res',res)
                let pro = res.bytesWritten / res.contentLength;

                this.setState({
                    progressNum: pro,
                });
            }
        };

        try {
            const ret = RNFS.downloadFile(options);
            ret.promise.then(res => {
                console.log('success', res);

               // console.log('file://' + downloadDest)

                // // 例如保存图片
                // CameraRoll.saveToCameraRoll(downloadDest)
                //     .then(() => {
                //         Toast.showShortCenter('图片已保存到相册')
                //     }).catch(() => {
                //         Toast.showShortCenter('图片保存失败')
                //     })

            }).catch(err => {
                console.log('err', err);
            });
        }
        catch (e) {
           // console.log(error);
        }

    }
  

    //  downLoadFile=()=>{
    //     RNfetchBlob.config({
    //         addAndroidDownloads : {
    //           useDownloadManager : true,
    //           title : 'awesome.apk',
    //           description : 'An APK that will be installed',
    //           mime : 'application/vnd.android.package-archive',
    //           mediaScannable : true,
    //           notification : true,
    //         }
    //       })
    //       .fetch('GET', `http://180.153.105.144/dd.myapp.com/16891/E2F3DEBB12A049ED921C6257C5E9FB11.apk`)
    //       .then((res) => {
    //           console.log('res',res)
    //           //android.actionViewIntent(res.path(), 'application/vnd.android.package-archive')
    //       })
    //  }

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
    componentWillUnmount = () => {
        console.log('home_willunmoun')
    }
}

export default HomeScreen 