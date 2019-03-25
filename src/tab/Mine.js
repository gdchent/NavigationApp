import React, {Component} from 'react';
import {Platform, StyleSheet, Text, View,TouchableOpacity} from 'react-native';


class Mine extends React.Component{

     constructor(props){
         super(props)
         const {navigation}=this.props
         //构造器 定义state初始状态
       
     }

     componentDidMount =() =>{
        const {navigation}=this.props
        this._subDidFocus = navigation.addListener('didFocus', this.onFocus);
     }
     //卸载组件
     componentWillUnmount=()=>{
        this._subDidFocus.remove()
     }
     //第二次进来会来加载这个页面
     onFocus = () => {
        console.log('onFocus')
     }
     render(){
         return (
             <View style={{flex:1,backgroundColor:'green'}}>
                <Text>我是个人中心</Text>
             </View>
         )
     }
}

export default Mine