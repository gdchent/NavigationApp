/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 * @flow
 * @lint-ignore-every XPLATJSCOPYRIGHT1
 */

import React, { Component } from 'react';
import { Platform, StyleSheet, Text, View, ToastAndroid } from 'react-native';
import CodePush from "react-native-code-push"; // 引入code-push
import { createAppContainer, createBottomTabNavigator, createMaterialTopTabNavigator } from 'react-navigation'
import AppNavigator from './src/tab/AppNavigator' //获取页面导航栏 StackNavigator
import CodePushView from './src/components/CodePushView '
const CODEPUSH_TIMEOUT = 10 * 1000 // 10s 超时 '
//获取一个AppContainer
const AppContainer = createAppContainer(AppNavigator);
// 关闭警告
console.disableYellowBox = true
class App extends Component {

  constructor(props) {
    super(props)
    this.state = {
      syncMessage: '',
      progress: true,
      enterHome: false, //是否进入主页面
    }

  }

  componentWillMount = () => {
    console.log('conponentWill')
    const { progress } = this.state
    //检测是否有热更新
    // CodePush.checkForUpdate()
    //   .then((update) => {
    //     if (!update) {
    //       //设置直接进入主页面
    //       console.log('当前已经是最新版本')
    //     } else {
    //       setTimeout(() => {
    //         console.log('will定时器', progress)
    //         if (!(progress && progress.receivedBytes && progress.totalBytes)) {
    //           //定时器 磨10s钟
    //           CodePush.disallowRestart() // 禁止重启
    //           this.setState({ enterHome: true })
    //         }
    //       }, CODEPUSH_TIMEOUT);
    //       // 启动热更新
    //       if (Platform.OS === 'ios') {
    //         this.sync()
    //       } else {
    //         this.syncImmediate()
    //       }
    //     }
    //   })


  }
  componentDidMount = () => {
    // CodePush.allowRestart()
    // CodePush.notifyAppReady()
  }
  /** Update is downloaded silently, and applied on restart (recommended) */
  sync() {
    CodePush.sync(
      {},
      this.codePushStatusDidChange.bind(this),
      this.codePushDownloadDidProgress.bind(this)
    )
  }

  /** Update pops a confirmation dialog, and then immediately reboots the app */
  syncImmediate() {
    CodePush.sync(
      { installMode: CodePush.InstallMode.IMMEDIATE },
      this.codePushStatusDidChange.bind(this),
      this.codePushDownloadDidProgress.bind(this)
    )
  }

  //热更新的状态
  codePushStatusDidChange(syncStatus) {
    switch (syncStatus) {
      case CodePush.SyncStatus.CHECKING_FOR_UPDATE:
        console.log('检测最新配置状态')
        this.setState({ syncMessage: "检测最新配置信息" })
        break
      case CodePush.SyncStatus.DOWNLOADING_PACKAGE:
        console.log('下载最新配置文件')
        this.setState({ syncMessage: "下载最新配置文件" })
        break
      case CodePush.SyncStatus.AWAITING_USER_ACTION:
        console.log('"等待操作" ')
        this.setState({ syncMessage: "等待操作" })
        break
      case CodePush.SyncStatus.INSTALLING_UPDATE:
        console.log('更新完成')
        this.setState({ syncMessage: "更新完成" })
        break
      case CodePush.SyncStatus.UP_TO_DATE:
        console.log('当前已经是最新版本')
        this.setState({ syncMessage: "当前已是最新版本", progress: false })
        break
      case CodePush.SyncStatus.UPDATE_IGNORED:
        console.log('更新已经取消')
        this.setState({ syncMessage: "更新已被取消", progress: false })
        break
      case CodePush.SyncStatus.UPDATE_INSTALLED:
        console.log('更新安装已经完成')
        this.setState({ syncMessage: "更新已安装等待重启生效", progress: false })
        break
      case CodePush.SyncStatus.UNKNOWN_ERROR:
        console.log('更新发生错误')
        this.setState({ syncMessage: "更新发生错误", progress: false })
        break
    }
  }


  codePushDownloadDidProgress(progress) {
    //console.log('progress', progress)
    this.setState({ progress: progress })
  }

  render() {
    // 倒计时结束未进入下载状态直接进入首页
    const { syncMessage, progress, enterHome } = this.state
    // if (!!progress && !enterHome) {
    //   return <CodePushView syncMessage={syncMessage} progress={progress} />
    // }
    return (
      <AppContainer />
    );
  }
}

const codePushOptions = {
  checkFrequency: CodePush.CheckFrequency.ON_APP_START
}
const AppCodePush = CodePush(codePushOptions)(App)
export default AppCodePush
