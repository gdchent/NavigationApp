import React, {Component} from 'react';
import {Platform, StyleSheet, Text, View,TouchableOpacity} from 'react-native';

class Mine extends React.Component{

     constructor(props){
         super(props)
         //构造器 定义state初始状态
         this.state={

         }
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