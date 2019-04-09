import React, { PureComponent } from 'react'
import { View, Text, Image, StyleSheet, Dimensions, StatusBar, Platform, ImageBackground } from 'react-native'
import PropTypes from 'prop-types'
import Modal from 'react-native-modalbox'
import updateBackground from '../images/updateBackground.png'
import updateAppLogo from '../images/updateAppLogo.png'
import { TouchableOpacity } from 'react-native-gesture-handler';
export const { width, height } = Dimensions.get('window')
class AppUpdateModelBox extends PureComponent {


    static propTypes = {
        onSelect: PropTypes.func.isRequired,
        onPress: PropTypes.func.isRequired,
        onCancle: PropTypes.func,
        onData: PropTypes.string,
    }


    static defaultProps = {
        onCancle: () => { },
    }
    //传数据打开
    open() {
        this.refs.SelectPicModal.open()
    }

    render() {
        return (
            <Modal
                style={[styles.background]}
                ref="SelectPicModal"
                backdropPressToClose={true}
                backButtonClose
                position="center"
                entry="top"
                swipeToClose={false}

            >
                <View style={styles.container}>
                    <ImageBackground style={styles.backgroundIcon} source={updateBackground}>

                        <View style={{flex:1,alignItems:'center',justifyContent:'center'}}>
                            <Text style={styles.titleText}>发现新版本</Text>
                            <Text>1.页面全量更新</Text>
                            <Text>2.bug修复,赶快去体验吧</Text>

                        </View>

                        <View style={styles.flexSpace}>
                            <TouchableOpacity
                                style={styles.confirmTouch}
                                onPress={() => {
                                    this.props.onPress()
                                }}
                            >
                                <View style={styles.confirmView}>
                                    <Text style={styles.confirmTextColor}>立即更新</Text>
                                </View>

                            </TouchableOpacity>
                        </View>

                    </ImageBackground>

                </View>
                <View style={styles.topView}>
                    <Image style={{ width: 60, height: 60 }} source={updateAppLogo} />
                </View>

            </Modal>

        )
    }
}

const styles = StyleSheet.create({
    modalView: {
        height: 280,
        width: width * 0.6,

    },
    background: {
        width: width * 0.6,
        height: 250,
        backgroundColor: '#fff',
        borderRadius: 8,
    },
    container: {
        height: 250,
        alignItems: 'center',
    },
    backgroundIcon: {
        height: 250,
        width: width * 0.6,
        justifyContent: 'center',
        alignItems: 'center'
    },
    titleText: {
        fontSize: 25,
        fontWeight: 'bold',
    },
    flexSpace: {
        justifyContent: 'flex-end',
        marginBottom:10,
    },
    confirmTouch: {

    },
    confirmView: {
        width: width * 0.5,
        backgroundColor: '#e96e64',
        paddingVertical: 10,
        marginHorizontal: 10,
        borderRadius: 3,
    },
    confirmTextColor: {
        fontWeight: 'bold',
        textAlign: 'center',
        color: '#fff',
    },
    topView: {
        width: width * 0.6,
        position: 'absolute',
        top: -30,
        zIndex: 999,
        alignItems: 'center',
    }



})
export default AppUpdateModelBox 