import React, {Component} from 'react';
import {View} from 'react-native';
import {NativeTextView} from './index'
import PropTypes from 'prop-types'

class TextView extends Component {
    static propTypes = {
        text: PropTypes.string.isRequired,
        ...View.propTypes
    }

    constructor(props) {
        super(props);
    }
    _onClick(e){
        if(!this.props.onClick){
            return
        }
        console.log("TextView点击事件触发原生",e.nativeEvent.message)
        this.props.onClick(e.nativeEvent.message)
    }
    render() {

        return (
            <NativeTextView
                {...this.props}
                onClick={(e) => this._onClick(e)}
            >
            </NativeTextView>
        );
    }
}

export default TextView;
