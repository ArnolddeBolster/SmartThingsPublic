metadata {
        definition (name: "LED SMART", namespace: "", author: "smartthings") {
        capability "Switch"
        capability "Refresh"
        capability "Switch Level"
    }

	// simulator metadata
	simulator {
	}
/*
	tiles {
		multiAttributeTile(name:"switch", type: "generic", width: 6, height: 4, canChangeIcon: true){
		tileAttribute ("device.switch", key: "PRIMARY_CONTROL") {
                attributeState "on", label:'${name}', action:"switch.off", icon:"https://postfiles.pstatic.net/MjAxODAzMjdfNjgg/MDAxNTIyMTUzOTg0NzMx.YZwxpTpbz-9oqHVDLhcLyOcdWvn6TE0RPdpB_D7kWzwg.97WcX3XnDGPr5kATUZhhGRYJ1IO1MNV2pbDvg8DXruog.PNG.shin4299/Yeelight_tile_on.png?type=w580", backgroundColor:"#00a0dc", nextState:"turningOff"
                attributeState "off", label:'${name}', action:"switch.on", icon:"https://postfiles.pstatic.net/MjAxODAzMjdfMTA0/MDAxNTIyMTUzOTg0NzIz.62-IbE4S7wAOxe3hufTJctU8mlQmrIUQztDaSTnf3kog.sxe2rqceUxFEPqrfYZ_DLkjxM5IPSotCqhErG87DI0Mg.PNG.shin4299/Yeelight_tile_off.png?type=w580", backgroundColor:"#ffffff", nextState:"turningOn"
                
                attributeState "turningOn", label:'${name}', action:"switch.off", icon:"https://postfiles.pstatic.net/MjAxODAzMjdfMTA0/MDAxNTIyMTUzOTg0NzIz.62-IbE4S7wAOxe3hufTJctU8mlQmrIUQztDaSTnf3kog.sxe2rqceUxFEPqrfYZ_DLkjxM5IPSotCqhErG87DI0Mg.PNG.shin4299/Yeelight_tile_off.png?type=w580", backgroundColor:"#ffffff", nextState:"turningOff"
                attributeState "turningOff", label:'${name}', action:"switch.on", icon:"https://postfiles.pstatic.net/MjAxODAzMjdfNjgg/MDAxNTIyMTUzOTg0NzMx.YZwxpTpbz-9oqHVDLhcLyOcdWvn6TE0RPdpB_D7kWzwg.97WcX3XnDGPr5kATUZhhGRYJ1IO1MNV2pbDvg8DXruog.PNG.shin4299/Yeelight_tile_on.png?type=w580", backgroundColor:"#00a0dc", nextState:"turningOn"
*/

	// UI tile definitions


	tiles {
			standardTile("button", "device.switch", width: 2, height: 2, canChangeIcon: true) {
//			state "off", label: 'Off', action: "switch.off", icon: "st.Kids.kid10", backgroundColor: "#ffffff", nextState: "off"
	//		state "on", label: 'On', action: "switch.on", icon: "st.Kids.kid10", backgroundColor: "#79b821", nextState: "on"
			state "off", label: 'Off', action: "switch.on", icon: "st.Kids.kid10", backgroundColor: "#ffffff", nextState: "on"
			state "on", label: 'On', action: "switch.off", icon: "st.Kids.kid10", backgroundColor: "#79b821", nextState: "off"
		}
			standardTile("refresh", "device.switch", inactiveLabel: false, decoration: "flat") {
			state "default", label:'', action:"refresh.refresh", icon:"st.secondary.refresh"
		}        
        controlTile("levelSliderControl", "device.level", "slider", height: 1, width: 2, inactiveLabel: false, backgroundColor:"#ffe71e") {
            state "level", action:"switch level.setLevel"
        }
        valueTile("lValue", "device.level", inactiveLabel: true, height:1, width:1, decoration: "flat") {
            state "levelValue", label:'${currentValue}%', unit:"", backgroundColor: "#53a7c0"
        }

		main(["button"])
		details(["button", "refresh","levelSliderControl","lValue"])
	}
}


def parse(String description) {
}

def on() {
	sendEvent(name: "switch", value: "on")
    log.info "Dimmer On"
}

def off() {
	sendEvent(name: "switch", value: "off")
    log.info "Dimmer Off"
}

def setLevel(val){
    log.info "setLevel $val"
    // make sure we don't drive switches past allowed values (command will hang device waiting for it to
    // execute. Never commes back)
	if( val > 100) val = 100;
    if(val <= 0) val = 1;
    
    if( val > 0){
        on()
        sendEvent(name:"level",value:Math.round(val), units: '%')

    }
    
    if (val == 0){ // I liked that 0 = off
    	sendEvent(name:"level",value:val)
    //	off()
    }
    else
    {
    	sendEvent(name:"level",value:val)
//    	sendEvent(name:"level",value:val) // had to add this to work if apps subscribed to
                                                    // setLevel event. "Dim With Me" was one.
    }
}

def refresh() {
    log.info "refresh"
}
