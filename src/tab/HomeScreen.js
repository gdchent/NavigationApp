import React, {Component} from 'react';
import {Platform, StyleSheet, Text, View,TouchableOpacity} from 'react-native';


/**
 * Home主页面
 */
class HomeScreen extends Component{

    render(){
        return (
            <View style={{flex:1,backgroundColor:'red'}}>
                <TouchableOpacity
                   style={{borderWidth:1,borderRadius:1}}
                   onPress={()=>{
                       this.startActivity()
                   }}
                >
                <Text>我是HomeScreen页面,点击跳转到详情页</Text>
                </TouchableOpacity>
                
            </View>
        )
    }

    //开启新页面
    startActivity=()=>{
         console.log('onclick')
         const {navigation} =this.props
         console.log('navigation',navigation)
         navigation.navigate('Detail',{para1:'abc'})

    }
}

export default HomeScreen 