import React, { Component, PureComponent } from 'react';
import { Platform, StyleSheet, Text, View, TouchableOpacity } from 'react-native';


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

            </View>
        )
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
    componentWillUnmount=()=>{
        console.log('home_willunmoun')
    }
}

export default HomeScreen 