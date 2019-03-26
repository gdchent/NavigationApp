/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 * @flow
 * @lint-ignore-every XPLATJSCOPYRIGHT1
 */

import React, { Component } from 'react';
import { Platform, StyleSheet, Text, View } from 'react-native';
import CodePush from "react-native-code-push"; // 引入code-push
import { createAppContainer, createBottomTabNavigator, createMaterialTopTabNavigator } from 'react-navigation'
import AppNavigator from './src/tab/AppNavigator' //获取页面导航栏 StackNavigator

const CODEPUSH_TIMEOUT = 10 * 1000 // 10s 超时 
//获取一个AppContainer
const AppContainer = createAppContainer(AppNavigator);
const instructions = Platform.select({
  ios: 'Press Cmd+R to reload,\n' + 'Cmd+D or shake for dev menu',
  android:
    'Double tap R on your keyboard to reload,\n' +
    'Shake or press menu button for dev menu',
});
// 关闭警告
console.disableYellowBox = true
type Props = {};
class App extends Component<Props> {

  constructor(props) {
    super(props)
    this.state = {
      syncMessage: '',
      progress: true,
      enterHome: false, //是否进入主页面
    }
    console.log('app', __DEV__)
  }

  componentWillMount = () => {

    setTimeout(() => {
      //定时器 磨10s钟
      CodePush.disallowRestart() // 禁止重启
      this.setState({ enterHome: true })

    }, CODEPUSH_TIMEOUT);

    //开发环境  如果是生产环境这里取反
    if (__DEV__) {
      // 启动热更新
      if (Platform.OS === 'ios') {
        this.sync()
      } else {
        this.syncImmediate()
      }
    }
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

  热更新的状态
  codePushStatusDidChange(syncStatus) {
    console.log('获取状态', syncStatus)
    switch (syncStatus) {
      case CodePush.SyncStatus.CHECKING_FOR_UPDATE:
        this.setState({ syncMessage: "检测最新配置信息" })
        break
      case CodePush.SyncStatus.DOWNLOADING_PACKAGE:
        this.setState({ syncMessage: "下载最新配置文件" })
        break
      case CodePush.SyncStatus.AWAITING_USER_ACTION:
        this.setState({ syncMessage: "等待操作" })
        break
      case CodePush.SyncStatus.INSTALLING_UPDATE:
        this.setState({ syncMessage: "更新完成" })
        break
      case CodePush.SyncStatus.UP_TO_DATE:
        this.setState({ syncMessage: "当前已是最新版本", progress: false })
        break
      case CodePush.SyncStatus.UPDATE_IGNORED:
        this.setState({ syncMessage: "更新已被取消", progress: false })
        break
      case CodePush.SyncStatus.UPDATE_INSTALLED:
        this.setState({ syncMessage: "更新已安装等待重启生效", progress: false })
        break
      case CodePush.SyncStatus.UNKNOWN_ERROR:
        this.setState({ syncMessage: "更新发生错误", progress: false })
        break
    }
  }

  //测试显示更新文件
  CodePushView = ({ syncMessage, progress }) => {
    return (
      <View style={{ flex: 1, backgroundColor: 'white' }}>
        <View style={styles.codepush}>
          <Text>{syncMessage}</Text>
          {!!progress.receivedBytes && !!progress.totalBytes && (
            <Text>
              正在下载更新文件: {(progress.receivedBytes / progress.totalBytes * 100).toFixed(2)}%
            </Text>
          )}
        </View>
      </View>
    )
  }
  codePushDownloadDidProgress(progress) {
    //console.log('progress', progress)
    this.setState({ progress })
  }

  render() {

    // 倒计时结束未进入下载状态直接进入首页
    const { syncMessage, progress, enterHome } = this.state
    //console.log('render',progress,enterHome)
    if (!!progress && !enterHome && __DEV__) {
      return <CodePushView syncMessage={syncMessage} progress={progress} />
    }
    return (
      <AppContainer
      />
    );
  }
}
export default App
