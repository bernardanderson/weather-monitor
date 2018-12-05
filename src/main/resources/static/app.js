
var weatherSocket = null;
var rainSocket = null;
var backendIp = "http://192.168.0.156:8001";

$(document).ready(
    function(){
        
        setAllIntervals();
        activateSockets();
        
        $("#main-menu").click( function(event) {
            $("#main-menu").children().each( function() {
                $(this).removeClass("active");
            });
            $(event.target).addClass("active");
        });
    }
);

function activateSockets() {
    weatherSocket = new SockJS(backendIp + "/weather-websocket");
    rainSocket = new SockJS(backendIp + "/rain-websocket");
    
    weatherSocket.onopen = function(data) {
      console.log("Successful Weather Socket Connection");
    };
      
    weatherSocket.onmessage = function(jsonString) {
        retrieveCurrentWeatherJson(JSON.parse(jsonString.data));
    };
    
    rainSocket.onopen = function(data) {
        console.log("Successful Rain Socket Connection");
    };
    
    rainSocket.onmessage = function(jsonString) {
        retrieveRainJson(JSON.parse(jsonString.data));
    };
}

function setAllIntervals(){
    
    //Runs the Clock
    setInterval(function(){ 
        $("#current-time").text(moment().format('MMM Do YYYY, h:mm:ss a'));
    }, 1000);
    
    //Rotates the bottom readout
    setInterval(function() {
        $("#sub-info-rain").toggle();
        $("#sub-info-wind").toggle();
    }, 10000);
}

function windDirection(sentWindDirectionInDegrees) {
    if ((sentWindDirectionInDegrees >= 349) && (sentWindDirectionInDegrees <= 011)) return "N";
    if ((sentWindDirectionInDegrees >= 12) && (sentWindDirectionInDegrees <= 33)) return "NNE";
    if ((sentWindDirectionInDegrees >= 34) && (sentWindDirectionInDegrees <= 56)) return "NE";
    if ((sentWindDirectionInDegrees >= 57) && (sentWindDirectionInDegrees <= 78)) return "ENE";
    if ((sentWindDirectionInDegrees >= 79) && (sentWindDirectionInDegrees <= 101)) return "E";
    if ((sentWindDirectionInDegrees >= 102) && (sentWindDirectionInDegrees <= 123)) return "ESE";
    if ((sentWindDirectionInDegrees >= 124) && (sentWindDirectionInDegrees <= 146)) return "SE";
    if ((sentWindDirectionInDegrees >= 147) && (sentWindDirectionInDegrees <= 168)) return "SSE";
    if ((sentWindDirectionInDegrees >= 169) && (sentWindDirectionInDegrees <= 191)) return "S";
    if ((sentWindDirectionInDegrees >= 192) && (sentWindDirectionInDegrees <= 213)) return "SSW";
    if ((sentWindDirectionInDegrees >= 214) && (sentWindDirectionInDegrees <= 236)) return "SW";
    if ((sentWindDirectionInDegrees >= 237) && (sentWindDirectionInDegrees <= 258)) return "WSW";
    if ((sentWindDirectionInDegrees >= 259) && (sentWindDirectionInDegrees <= 281)) return "W";
    if ((sentWindDirectionInDegrees >= 282) && (sentWindDirectionInDegrees <= 303)) return "WNW";
    if ((sentWindDirectionInDegrees >= 304) && (sentWindDirectionInDegrees <= 326)) return "NW";
    if ((sentWindDirectionInDegrees >= 327) && (sentWindDirectionInDegrees <= 348)) return "NNW";
}

function retrieveCurrentWeatherJson(data) {
    
    let windDirectionInWords = windDirection(data.winddirection);
    let lastUpdatedDate = moment(data.timestamp).format('MMM Do YYYY, h:mm:ss a');
    
    $("#out-temp > div.value").html(`${data.outtemp}&degF`);
    $("#out-windchill > div.value").html(`${data.windchill}/${data.heatindex}/${data.outdewpoint}&degF`);
    $("#out-humidity > div.value").html(`${data.outhumidity}% `);
    
    $("#in-temp > div.value").html(`${data.intemp}&degF`);
    $("#in-dewpoint > div.value").html(`${data.indewpoint}&degF`);
    $("#in-humidity > div.value").text(`${data.inhumidity}%`);
    
    $("#wind-direction > div.value").text(`${windDirectionInWords}`);
    $("#wind-speed > div.value").text(`${data.windspeed.toFixed(2)}`);
    $("#bar-pressure > div.value").text(`${data.barpress.toFixed(2)}`);

    $("#rain-ytd > div.value").text(`${data.rainfall}`);
    $("#last-updated").text(`${lastUpdatedDate}`);
}

function retrieveRainJson(data) {
    $("#rain-today > div.value").text(`${data.dailyRainfall.toFixed(2)}`);
    $("#rain-month > div.value").text(`${data.monthlyRainfall.toFixed(2)}`);
    $("#rain-ytd > div.value").text(`${data.ytdRainfall.toFixed(2)}`);
}


