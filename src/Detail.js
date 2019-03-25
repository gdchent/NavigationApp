import React ,{Component} from 'react' 
import {Platform, StyleSheet, Text, View,BackHandler} from 'react-native';
import { TouchableOpacity } from 'react-native-gesture-handler';
class Detail extends React.Component{

    render(){

        const {navigation}=this.props
        console.log('navigation_re',navigation)
        let key=navigation.getParam('key',"defaultValue")
        console.log('key',key)
        let keyvalue=navigation.state.params.key
        console.log('get',keyvalue)
        console.log('callback',navigation.state.params)
        // navigation.state.params.callback("回传数据")
        // navigation.goBack()
        return (
            <View style={{flex:1}}>
                <Text>我是text组件</Text>
                <TouchableOpacity onPress={
                    ()=>{
                        this.goToPage()
                    }
                }>
                    <Text>去Recyclerview页面</Text>
                </TouchableOpacity>
            </View>
        )
    }

    //点击去列表页
    goToPage=()=>{
        const {navigation}=this.props
        navigation.navigate('RecyclerView',{
             
        })
    }

    componentDidMount=()=>{
        //
        // if(Platform.OS="android"){
        //     this.listener=BackHandler.addEventListener('hardwareBackPress',this.onBackAndroid)
        // }
        
    }

    onBackAndroid=()=>{
         const {navigation}=this.props
         //获取当前路由
        //  console.log('route',navigation.state.routeName)
        //  if(navigation.state.routeName=='Detail'){
        //     console.log('Detail点击返回键',navigation)
        //  }else{
        //      console.log('不是Detail的回键')
        //  }
        
    }

    componentWillUnmount =()=>{
        console.log('unWillDetail')
        // if(Platform.OS='android'){
        //     console.log('Detail移除监听')
        //     this.listener.remove()
        // }
    }
}
export default Detail