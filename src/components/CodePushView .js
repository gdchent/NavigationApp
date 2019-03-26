
import React from 'react'
import { View, Text, Image, StyleSheet } from 'react-native'

//测试显示更新文件
CodePushView = ({ syncMessage, progress }) => {
    console.log('codepushview',syncMessage)
    console.log('progress',progress)
    return (
      <View style={{ flex: 1, backgroundColor: 'white',justifyContent:'center',alignItems:'center' }}>
        <View>
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
  export default CodePushView